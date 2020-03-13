package com.java2blog.androidrestjsonexample;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.java2blog.androidrestjsonexample.Country;
import com.java2blog.androidrestjsonexample.R;

import java.util.ArrayList;

public class CustomCountryList extends BaseAdapter {


    private Activity context;
    ArrayList<Poto> potos;


    public CustomCountryList(Activity context, ArrayList<Poto> potos) {
     //   super(context, R.layout.row_item, countries);
        this.context = context;
       this.potos=potos;

    }

    public static class ViewHolder
    {
        TextView textViewId;
        TextView textViewCountry;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;

        LayoutInflater inflater = context.getLayoutInflater();
        ViewHolder vh;
        if(convertView==null) {
            vh=new ViewHolder();
            row = inflater.inflate(R.layout.row_item, null, true);
            vh.textViewId = (TextView) row.findViewById(R.id.textViewId);
            vh.textViewCountry = (TextView) row.findViewById(R.id.textViewPoto);
            // store the holder with the view.
            row.setTag(vh);
        }
        else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.textViewCountry.setText(potos.get(position).getPotoName());
        vh.textViewId.setText(""+potos.get(position).getId());

        return  row;
    }

    public long getItemId(int position) {
        return position;
    }

    public Object getItem(int position) {
        return position;
    }

    public int getCount() {

        if(potos.size()<=0)
            return 1;
        return potos.size();
    }
}

