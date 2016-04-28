package edu.scu.dwu2.shoppinglist;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Danni on 2/16/16.
 */
public class main_grid implements Parcelable {
    private String id;
    private String name;
    private String date;
    private String totalcount;


    public main_grid(Parcel source){
        String[] data = new String[4];
        source.readStringArray(data);
        id = data[0];
        name = data[1];
        date = data[2];
        totalcount = data[3];
    }
    public main_grid(String id, String name, String date, String totalcount) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.totalcount = totalcount;
    }
    public String getId() { return id; }

    public String getName() { return name; }

    public String getDate() { return date; }

    public String getTotalcount() { return totalcount; }


    public static final Creator CREATOR = new Creator(){
        @Override
        public Object createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new main_grid(source);
        }
        @Override
        public Object[] newArray(int size) {
            // TODO Auto-generated method stub
            return new main_grid[size];
        }
    };

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub
        dest.writeStringArray(new String[]{this.id, this.name, this.date, this.totalcount});
    }
}