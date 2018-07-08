package goraya.harpreet.gpacalc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by harry on 2018-03-08.
 */
public class ResultListAdaptor extends BaseAdapter {
    private Context mContext;
    HashMap<String, Double> myMap;
    private int layoutResource;

    public ResultListAdaptor(Context context, int layout, HashMap<String, Double> data) {
        this.mContext = context;
        this.myMap = data;
    }

    @Override
    public int getCount() {

        return myMap.size();
    }

    @Override
    public Object getItem(int i) {
        return myMap.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
