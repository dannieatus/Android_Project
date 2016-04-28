package edu.scu.dwu2.zoodirectory;

/**
 * Created by Danni on 1/28/16.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class meet_animals_list_adapter extends ArrayAdapter<meet_animals_list> {
    private final List<meet_animals_list> animals;
    private Context ctxt;

    public meet_animals_list_adapter(Context context, int resource, List<meet_animals_list> animals) {
        super(context, resource, animals);
        this.animals = animals;
        ctxt = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ScrapViewHolder holder;
        holder = new ScrapViewHolder();
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.meet_animals_list, parent, false);


        //set row text
        holder.label = (TextView) row.findViewById(R.id.label);
        holder.label.setText(animals.get(position).getName());

        //set row icon
        try {
            holder.icon = (ImageView) row.findViewById(R.id.thumbnail_png);
            String filename = animals.get(position).getFilename();
            InputStream inputStream = getContext().getAssets().open(filename);
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            holder.icon.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //add row star favorite / un-favorite
        holder.menu = (ImageView) row.findViewById(R.id.menu);
        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String drawableId = (String) holder.menu.getTag();
                if (drawableId.equals("0")) {
                    holder.menu.setImageResource(R.drawable.star_fav);
                    holder.menu.setTag("1");
                }else {
                    holder.menu.setImageResource(R.drawable.star_nonfav);
                    holder.menu.setTag("0");
                }
            }
        });

        return row;
    }

    public class ScrapViewHolder {
        TextView label;
        ImageView icon;
        ImageView menu;
    }

}