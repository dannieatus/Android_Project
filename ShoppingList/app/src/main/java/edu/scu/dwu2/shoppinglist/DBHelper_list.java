package edu.scu.dwu2.shoppinglist;

/**
 * Created by Danni on 2/16/16.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper_list extends SQLiteOpenHelper {

    public static final String ID_COLUMN = "_id";
    public static final String NAME_COLUMN = "listname";
    public static final String DATE_COLUMN = "listdate";
    public static final String TOTALCOUNT_COLUMN = "totalcount";


    public static final String DATABASE_TABLE = "DB_list";
    public static final int DATABASE_VERSION = 2;
    private static final String DATABASE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "  %s integer primary key autoincrement, " +
                    "  %s text," +
                    "  %s text," +
                    "  %s text)",
            DATABASE_TABLE, ID_COLUMN, NAME_COLUMN, DATE_COLUMN, TOTALCOUNT_COLUMN);

    public DBHelper_list(Context context) {
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