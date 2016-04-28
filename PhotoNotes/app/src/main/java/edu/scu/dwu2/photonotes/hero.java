package edu.scu.dwu2.photonotes;

/**
 * Created by Danni on 2/13/16.
 */

import android.os.Parcel;
import android.os.Parcelable;

public class hero implements Parcelable{
    private String name;
    private String filename;

    public hero(Parcel source){
        String[] data = new String[2];
        source.readStringArray(data);
        name = data[0];
        filename = data[1];
    }
    public hero(String name, String filename) {
        this.name = name;
        this.filename = filename;
    }

    public String getName() {
        return name;
    }

    public String getFileName() {
        return filename;
    }
    public static final Creator CREATOR = new Creator(){

        @Override
        public Object createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new hero(source);
        }

        @Override
        public Object[] newArray(int size) {
            // TODO Auto-generated method stub
            return new hero[size];
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
        dest.writeStringArray(new String[]{ this.name, this.filename});
    }
}
