package goraya.harpreet.gpacalc;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by harry on 2018-03-07.
 */
class Grades implements Parcelable {
    private String grade;
    private double credit;
    private double creditTimes;
    private boolean scale_four;
    private static HashMap<String, Double> nine_map = new HashMap<String, Double>() {{
        put("A+", 9.0);
        put("A", 8.0);
        put("B+", 7.0);
        put("B", 6.0);
        put("C+", 5.0);
        put("C", 4.0);
        put("D+", 3.0);
        put("D", 2.0);
        put("F", 0.0);
        Log.d("grades", "Crated 9-scale list");
    }}; //Uses 9.0 scale
    private static HashMap<String, Double> four_map = new HashMap<String, Double>() {{
        put("A+", 4.0);
        put("A", 3.8);
        put("B+", 3.3);
        put("B", 3.0);
        put("C+", 2.3);
        put("C", 2.0);
        put("D+", 1.3);
        put("D", 1.0);
        put("F", 0.0);
        Log.d("grades", "Crated 4-scale list");
    }}; //Uses 4.0 scale


    public Grades(String g, double c, boolean four_scale) {
        grade = g;
        credit = c;
        scale_four = four_scale; //Checks which scale user is using

        if (four_scale) {
            creditTimes = four_map.get(grade) * credit;
        } else {
            creditTimes = nine_map.get(grade) * credit;

        }
    }

    //Changed the scale from 4.0 to 9.0 or vise versa (needed so we can instantly switch gpa result between scales)
    public void changeScale(boolean four_scale) {
        if (!four_scale) {
            creditTimes = nine_map.get(grade) * credit;
            Log.d("Scale", "changeScale: scale changed to NINE");
        }

        if (four_scale) {
            creditTimes = four_map.get(grade) * credit;
            Log.d("Scale", "changeScale: scale changed to FOUR");
        }
    }

    private String getGrade() {
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

    private Grades(Parcel in) {
        grade = in.readString();
        credit = in.readDouble();
        creditTimes = in.readDouble();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(grade);
        dest.writeDouble(credit);
        dest.writeDouble(creditTimes);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Grades> CREATOR = new Parcelable.Creator<Grades>() {
        @Override
        public Grades createFromParcel(Parcel in) {
            return new Grades(in);
        }

        @Override
        public Grades[] newArray(int size) {
            return new Grades[size];
        }
    };
}
