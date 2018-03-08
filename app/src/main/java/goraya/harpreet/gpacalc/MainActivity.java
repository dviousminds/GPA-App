package goraya.harpreet.gpacalc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button mCalculateButton;
    private Button mAddCourseButton;
    private Button mClearButton;
    private EditText mEnterGPA;
    private TextView mGpaResult;
    private EditText mCredit;
    private Spinner spinner;
    private boolean four_scale;

    private ArrayList<Grades> gradesList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mCalculateButton = findViewById(R.id.calc_button);
        mAddCourseButton = findViewById(R.id.add_button);
        mClearButton = findViewById(R.id.clear_button);
        mEnterGPA = findViewById(R.id.enter_gps_box);
        mEnterGPA.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(2)});
        mGpaResult = findViewById(R.id.result_Textview);
        mCredit = findViewById(R.id.credit_enter_box);
        spinner = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gpa_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this); //runs spinner's onItemSelected

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String sSelected = parent.getItemAtPosition(position).toString();
        Toast.makeText(this, sSelected, Toast.LENGTH_SHORT).show();
        if (spinner.getSelectedItemPosition() == 0) {
            Log.d("grades", "four_scale is now true");
            four_scale = true;
        } else {
            four_scale = false;
            Log.d("grades", "four_scale is now false");

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void buttonClicked(View v) {
        if (v.getId() == mAddCourseButton.getId()) {

            if (isValid()) {

                String gpa = mEnterGPA.getText().toString();
                Double credit = Double.parseDouble(mCredit.getText().toString());

                Grades grades = new Grades(gpa, credit, four_scale);
                gradesList.add(grades);

                Log.d("mytag", "filling list");
                displayGradeList();
                mClearButton.setVisibility(View.VISIBLE);
                Log.d("mytag", "display result");
                mEnterGPA.setText("");
                mCredit.setText("");

            } else {
                Toast.makeText(getApplicationContext(), "Invalid input, try again.", Toast.LENGTH_SHORT).show();
                mEnterGPA.setText("");
                mCredit.setText("");
            }

        } else if (v.getId() == mCalculateButton.getId()) {


            mGpaResult.setText(String.format("GPA: %.2f", calculteGPA()));

        } else if (v.getId() == mClearButton.getId()) {
            gradesList.clear();
            mGpaResult.setText("");
            mClearButton.setVisibility(View.INVISIBLE);
        } else if (v.getId() == spinner.getId()) {


        } else {
            Toast.makeText(getApplicationContext(), "Umm, no buttons pressed?", Toast.LENGTH_SHORT).show();
        }


    }


    public void displayGradeList() {
        mGpaResult.setText(null);
        for (Grades g : gradesList) {
            mGpaResult.append(g.toString() + "\n");
        }
    }

    public double calculteGPA() {
        double gpaResult = 0;
        double totalCredits = 0;
        double totalCreditTimes = 0;

        for (Grades c : gradesList) {
            totalCredits += c.getCredit(); // 3 + 2 + 3
            totalCreditTimes += c.getCreditTimes(); // A(4.0) * 3
        }

        return totalCreditTimes / totalCredits;
    }


    public boolean isValid() {
        if (mCredit.getText() != null && mCredit.getText().toString() != "" && mEnterGPA.getText().toString() != "") {
            if ((mEnterGPA.getText().toString().matches("[A-D][+-]?|F")) && (Integer.parseInt(mCredit.getText().toString()) >= 1)) {
                return true;
            }
        }
        return false;
    }

}


class Grades {

    private String grade;
    private double credit;
    private double creditTimes;
    private static HashMap<String, Double> map = new HashMap<>();

    public Grades(String g, double c, boolean four_scale) {
        grade = g;
        credit = c;

        if (four_scale == true) {
            map.put("A+", 4.0);
            map.put("A", 3.8);
            map.put("B+", 3.3);
            map.put("B", 3.0);
            map.put("C+", 2.3);
            map.put("C", 2.0);
            map.put("D+", 1.3);
            map.put("D", 1.0);
            map.put("F", 0.0);

            Log.d("grades", "Crated 4-scale list");
        } else {

            map.put("A+", 9.0);
            map.put("A", 8.0);
            map.put("B+", 7.0);
            map.put("B", 6.0);
            map.put("C+", 5.0);
            map.put("C", 4.0);
            map.put("D+", 3.0);
            map.put("D", 2.0);
            map.put("F", 0.0);
            Log.d("grades", "Crated 9-scale list");


        }


        creditTimes = map.get(grade) * credit;


    }


    public String getGrade() {
        return grade;
    }


    public double getCredit() {
        return credit; //3
    }

    public double getCreditTimes() {
        return creditTimes;
    }


    @Override
    public String toString() {
        return getGrade() + " - " + getCredit();
    }
}





