package goraya.harpreet.gpacalc;

import android.util.Log;

import java.util.HashMap;

/**
 * Created by harry on 2018-03-07.
 */
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

    public HashMap getMap() {
        return map;
    }

    ;

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
