package edu.scu.dwu2.photonotes;

/**
 * Created by Danni on 2/13/16.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;


public class list_view_adapter extends ArrayAdapter<hero> {

    private final List<hero> heros;

    public list_view_adapter(Context context, int resource, List<hero> heros) {
        super(context, resource, heros);
        this.heros = heros;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getHeroView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getHeroView(position, convertView, parent);
    }

    public View getHeroView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.list_row, null);

        //get caption
        TextView textView = (TextView) row.findViewById(R.id.row_name);
        textView.setText(heros.get(position).getName());

        //get thumbnail picture
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 16; // Experiment with different sizes Bitmap b = BitmapFactory.decodeFile(filePath, options);
        ImageView rowimage = (ImageView) row.findViewById(R.id.row_image);
        String filename = heros.get(position).getFileName();
        Bitmap myBitmap = BitmapFactory.decodeFile(filename, options);
        rowimage.setImageBitmap(myBitmap);

        return row;
    }
}