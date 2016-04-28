package edu.scu.dwu2.zoodirectory;

/**
 * Created by Danni on 1/28/16.
 */
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;


public class meet_animals extends BaseActivity implements AdapterView.OnItemClickListener{

    List<meet_animals_list> animals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meet_animals);



        // build animal list
        animals = new ArrayList<>();
        animals.add(new meet_animals_list("Yellow Fish", "fish.png"));
        animals.add(new meet_animals_list("Brown Owl", "owl.png"));
        animals.add(new meet_animals_list("Pink Pig", "pig.png"));
        animals.add(new meet_animals_list("Orange Tiger", "tiger.png"));
        animals.add(new meet_animals_list("Green Turtle", "turtle.png"));

        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(new meet_animals_list_adapter(this, R.layout.meet_animals_list, animals));
        lv.setOnItemClickListener(this);

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String msg = animals.get(position).getFilename();
        String msg_name = getFileNameNoEx(msg);
        //show dialog alert
        if (msg_name.equals("turtle")){
            AlertDialog.Builder builder = new AlertDialog.Builder(meet_animals.this);
            builder.setIcon(R.mipmap.ic_launcher)
                    .setTitle("The Animal is scaring :)")
                    .setMessage("Are you continue?")
                    .setPositiveButton("Yes, I do!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            startnewactivity("turtle");
                            dialog.cancel();
                            }
                    })
                    .setNegativeButton("No, I don't!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                              dialog.dismiss();
                            }
                    })
                    .setCancelable(false)
            ;
            builder.create().show();
        }else{
            startnewactivity(msg_name);
        }
    }
    public void startnewactivity(String msg_name){
        Intent intent = new Intent();
        intent.putExtra("testIntent", msg_name);
        intent.setClass(meet_animals.this, animals_details.class);
        meet_animals.this.startActivity(intent);
    }

    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }
}