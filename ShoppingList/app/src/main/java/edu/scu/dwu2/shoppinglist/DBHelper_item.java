package edu.scu.dwu2.shoppinglist;

/**
 * Created by Danni on 2/16/16.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper_item extends SQLiteOpenHelper {

    public static final String ITEMID_COLUMN = "itemid";
    public static final String LISTID_COLUMN = "listid";
    public static final String ITEMNAME_COLUMN = "itemname";
    public static final String COUNT_COLUMN = "itemcount";
    public static final String PRICE_COLUMN = "itemprice";
    public static final String FILENAME_COLUMN = "itemfilename";
    public static final String STORE_COLUMN = "itemstore";
    public static final String BOUGHT_COLUMN = "boughtflag";


    public static final String DATABASE_TABLE = "DB_items";
    public static final int DATABASE_VERSION = 2;
    private static final String DATABASE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "  %s integer primary key autoincrement, " +
                    "  %s text," +
                    "  %s text," +
                    "  %s text," +
                    "  %s text," +
                    "  %s text," +
                    "  %s text," +
                    "  %s text)",
            DATABASE_TABLE, ITEMID_COLUMN, LISTID_COLUMN, ITEMNAME_COLUMN, COUNT_COLUMN, PRICE_COLUMN, FILENAME_COLUMN,  STORE_COLUMN, BOUGHT_COLUMN);

    public DBHelper_item(Context context) {
        super(context, DATABASE_TABLE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }
}

