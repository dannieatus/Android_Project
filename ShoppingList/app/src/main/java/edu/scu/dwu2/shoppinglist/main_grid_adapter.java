package edu.scu.dwu2.shoppinglist;

/**
 * Created by Danni on 1/28/16.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class main_grid_adapter extends ArrayAdapter<main_grid> {

    private final List<main_grid> main_grid;

    public main_grid_adapter(Context context, int resource, List<main_grid> main_grid) {
        super(context, resource, main_grid);
        this.main_grid = main_grid;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getgridView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getgridView(position, convertView, parent);
    }

    public View getgridView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_main_solo, parent, false);


        //set date text
        TextView date = (TextView) row.findViewById(R.id.date);
        date.setText(main_grid.get(position).getDate());


        //set name text
        TextView name = (TextView) row.findViewById(R.id.name);
        name.setText(main_grid.get(position).getName());

        //set totalcount text
        TextView totalcount = (TextView) row.findViewById(R.id.xxitems);
        totalcount.setText(main_grid.get(position).getTotalcount() + " Items");

        return row;
    }
}