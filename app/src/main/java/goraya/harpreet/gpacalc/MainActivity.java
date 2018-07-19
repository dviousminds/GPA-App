package goraya.harpreet.gpacalc;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Button mCalculateButton;
    private Button mAddCourseButton;
    private EditText mEnterGPA;
    private TextView mGpaResult;
    private EditText mCredit;
    private Spinner spinner;
    private boolean four_scale;
    private ListView listView;
    public List list;
    public Grades grades;
    private ArrayList<Grades> gradesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            gradesList = new ArrayList<>();
            Log.d("onCreate", "onCreate: new intance created");
        } else {
            gradesList = savedInstanceState.getParcelableArrayList("myGradeList");
        }

        mCalculateButton = findViewById(R.id.calc_button);
        mAddCourseButton = findViewById(R.id.add_button);
        mEnterGPA = findViewById(R.id.enter_gps_box);
        mEnterGPA.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(2)});
        mGpaResult = findViewById(R.id.result_Textview);

        mCredit = findViewById(R.id.credit_enter_box);
        spinner = findViewById(R.id.spinner);
        listView = findViewById(R.id.result_listview);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gpa_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this); //runs spinner's onItemSelected
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("myGradeList", gradesList);
        Log.d("onSavedIns", "onSaveInstanceState: saving grade list");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //   gradesList = savedInstanceState.getParcelableArrayList("myGradesList");

    }

    //Flips the scale of GPA 4.0 <=> 9.0
    public void flipGPAScale() {
        for (int i = 0; i < gradesList.size(); i++) {
            gradesList.get(i).changeScale(four_scale);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String sSelected = parent.getItemAtPosition(position).toString();
        Toast.makeText(this, sSelected, Toast.LENGTH_SHORT).show();

        if (spinner.getSelectedItemPosition() == 0) {
            Log.d("spinner", "four_scale is now true");
            four_scale = true;

            flipGPAScale();

            //If there are grades in the gradelist and a different gpa scale is selected,
            //mGpaResult gets updated automatically (via calcuateButton)
            if (!gradesList.isEmpty()) {
                mCalculateButton.performClick();
                Log.d("gradelist", "4.0: grade list not empty [Spinner]");
            }
        } else {
            four_scale = false;

            flipGPAScale();

            //If there are grades in the gradelist and a different gpa scale is selected,
            //mGpaResult gets updated automatically (via calcuateButton)
            if (!gradesList.isEmpty()) {
                mCalculateButton.performClick();
                Log.d("gradelist", "9.0: grade list not empty [Spinner]");
            }

            Log.d("spinner", "four_scale is now false");
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.clear_button) {
            clearResultList();
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    public void populateGPAResultList() {

        list = gradesList;

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Clear the grade list result
    public void clearResultList() {
        gradesList.clear();
        mGpaResult.setText("");
    }

    //Handel's button clicks
    public void buttonClicked(View v) {

        if (v.getId() == mAddCourseButton.getId()) { //If add button is clicked

            if (isValid()) {

                String gpa = mEnterGPA.getText().toString();
                Double credit = Double.parseDouble(mCredit.getText().toString());

                grades = new Grades(gpa, credit, four_scale);
                gradesList.add(grades);

                Log.d("mytag", "filling list");
                populateGPAResultList(); //POPULATING LIST
                Log.d("mytag", "display result");
                mEnterGPA.setText("");
                mCredit.setText("");
            } else {
                Toast.makeText(getApplicationContext(), "Invalid input, try again.", Toast.LENGTH_SHORT).show();
                mEnterGPA.setText("");
                mCredit.setText("");
            }
        } else if (v.getId() == mCalculateButton.getId()) {

            mGpaResult.setText(String.format(Locale.US, "GPA: %.2f", calculateGPA()));
        } else if (v.getId() == spinner.getId()) {

        } else {
            Toast.makeText(getApplicationContext(), "Umm, no buttons pressed?", Toast.LENGTH_SHORT).show();
        }
    }

    //Calculates the GPA
    public double calculateGPA() {
        double gpaResult = 0;
        double totalCredits = 0;
        double totalCreditTimes = 0;

        for (Grades c : gradesList) {
            totalCredits += c.getCredit(); // 3 + 2 + 3
            totalCreditTimes += c.getCreditTimes(); // A(4.0) * 3
        }

        return totalCreditTimes / totalCredits;
    }

    //Checks if the Credit and Grade entered by user is correct
    public boolean isValid() {
        Log.d("valid", "Checking isValid? : credit" + mCredit.getText().toString() + " GPA:  " + mEnterGPA.getText().toString());

        if (mCredit.getText() != null && !mCredit.getText().toString().isEmpty() && !mEnterGPA.getText().toString().isEmpty()) {
            Log.d("valid", "VALID ENTRY" + mCredit.getText().toString() + " GPA:  " + mEnterGPA.getText().toString());
            return (mEnterGPA.getText().toString().matches("[A-D][+-]?|F")) && (Integer.parseInt(mCredit.getText().toString()) >= 1);
        }
        return false;
    }
}



