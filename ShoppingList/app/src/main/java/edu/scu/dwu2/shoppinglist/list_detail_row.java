package edu.scu.dwu2.shoppinglist;

/**
 * Created by Danni on 2/2/16.
 */

import android.os.Parcel;
import android.os.Parcelable;

public class list_detail_row implements Parcelable{
    private String itemid;
    private String listid;
    private String itemname;
    private String itemcount;
    private String itemprice;
    private String itemimagefilename;
    private String itemstore;
    private String boughtflag;

    public list_detail_row(Parcel source){
        String[] data = new String[8];
        source.readStringArray(data);
        itemid = data[0];
        listid = data[1];
        itemname = data[2];
        itemcount = data[3];
        itemprice = data[4];
        itemimagefilename = data[5];
        itemstore = data[6];
        boughtflag = data[7];
    }

    public list_detail_row(String itemid, String listid, String itemname, String itemcount, String itemprice, String itemimagefilename, String itemstore, String boughtflag) {
        this.itemid = itemid;
        this.listid = listid;
        this.itemname = itemname;
        this.itemcount = itemcount;
        this.itemprice = itemprice;
        this.itemimagefilename = itemimagefilename;
        this.itemstore = itemstore;
        this.boughtflag = boughtflag;
    }
    public String GetItemId() { return itemid; }
    public String GetListId() { return listid; }
    public String GetItemName() { return itemname;}
    public String GetItemCount() { return itemcount;}
    public String GetItemPrice() { return itemprice; }
    public String GetItemImageFilename() { return itemimagefilename; }
    public String GetItemStore() { return itemstore; }
    public String GetBoughtFlag() { return boughtflag; }

    public static final Creator CREATOR = new Creator(){
        @Override
        public Object createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new list_detail_row(source);
        }
        @Override
        public Object[] newArray(int size) {
            // TODO Auto-generated method stub
            return new list_detail_row[size];
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
        dest.writeStringArray(new String[]{this.itemid, this.listid, this.itemname, this.itemcount, this.itemprice, this.itemimagefilename, this.itemstore, this.boughtflag});
    }
}