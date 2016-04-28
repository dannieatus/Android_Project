package edu.scu.dwu2.shoppinglist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Danni on 2/27/16.
 */
public class store_price_adapter extends ArrayAdapter<store_price_class> {

    private final List<store_price_class> animals;
    private Context ctxt;

    public store_price_adapter(Context context, int resource, List<store_price_class> animals) {
        super(context, resource, animals);
        this.animals = animals;
        ctxt = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.store_price_row, null);

        //set store name
        TextView name = (TextView) row.findViewById(R.id.store_row_tet);
        name.setText(animals.get(position).getName());

        //set store icon
        try {
            ImageView icon = (ImageView) row.findViewById(R.id.store_row_logo);
            String filename = animals.get(position).getfileName();
            InputStream inputStream = getContext().getAssets().open(filename);
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            icon.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return row;
    }
}