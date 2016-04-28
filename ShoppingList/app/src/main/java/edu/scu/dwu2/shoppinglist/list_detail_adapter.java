package edu.scu.dwu2.shoppinglist;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Danni on 2/2/16.
 */

public class list_detail_adapter extends ArrayAdapter<list_detail_row> {

    private final List<list_detail_row> list_detail_row;
    private Context ctxt;


    public list_detail_adapter(Context context, int resource, List<list_detail_row> list_detail_row) {
        super(context, resource, list_detail_row);
        this.list_detail_row = list_detail_row;
        ctxt = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getlistview(position, convertView, parent);

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getlistview(position, convertView, parent);
    }

    public View getlistview(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.list_detail_itemrow, parent, false);

        //set  text
        final TextView name;
        name = (TextView)row.findViewById(R.id.nameLabel);
        TextView price = (TextView)row.findViewById(R.id.pricelabel);
        TextView count = (TextView)row.findViewById(R.id.countlabel);
        name.setText(list_detail_row.get(position).GetItemName());
        price.setText(list_detail_row.get(position).GetItemPrice());
        count.setText(list_detail_row.get(position).GetItemCount());

        return row;
    }
}