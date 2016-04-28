package edu.scu.dwu2.test;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.RequiresPermission;

/**
 * Created by Danni on 3/4/16.
 */
public class ReminderDBSupporter extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "reminder.db";

    private static final String SQL_CREATE_REMINDER = "CREATE TABLE " + Reminder.TABLE_NAME + " (" + Reminder._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            Reminder.COLUMN_NAME_REMINDER_NAME + " TEXT," +
            Reminder.COLUMN_NAME_REMINDER_TIME_HOUR + " INTEGER," +
            Reminder.COLUMN_NAME_REMINDER_TIME_MINUTE + " INTEGER," +
            Reminder.COLUMN_NAME_REMINDER_REPEAT_DAYS + " TEXT," +
            Reminder.COLUMN_NAME_REMINDER_REPEAT_WEEKLY + " BOOLEAN," +
            Reminder.COLUMN_NAME_REMINDER_TONE + " TEXT," +
            Reminder.COLUMN_NAME_REMINDER_ENABLED + " BOOLEAN" +
            " )";

    private static final String SQL_DELETE_REMINDER ="DROP TABLE IF EXISTS " +   Reminder.TABLE_NAME;
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_REMINDER);	}
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_REMINDER);
        onCreate(db);		}
    public long createReminder(ReminderModel model) {
        ContentValues values = populateContent(model);
        return getWritableDatabase().insert(Reminder.TABLE_NAME, null, values);	}
    public long updateReminder(ReminderModel model) {
        ContentValues values = populateContent(model);
        return getWritableDatabase().update(Reminder.TABLE_NAME, values, Reminder._ID + " =                                                ?", new String[] { String.valueOf(model.id) });	}
    public ReminderModel getReminder(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM " + Reminder.TABLE_NAME + " WHERE " + Reminder._ID  + " = " + id;
        Cursor c = db.rawQuery(select, null);
        if (c.moveToNext()) {
            return populateModel(c);		}
        return null;	}
    public List<ReminderModel> getReminders() {
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM " + Reminder.TABLE_NAME;
        Cursor c = db.rawQuery(select, null);
        List<ReminderModel> reminderList = new ArrayList<ReminderModel>();
        while (c.moveToNext()) {
            reminderList.add(populateModel(c));	}
        if (!reminderList.isEmpty()) {
            return reminderList;		}
        return null;	}
    public int deleteReminder(long id) {
        return getWritableDatabase().delete(Reminder.TABLE_NAME, Reminder._ID + " = ?", new String[] { String.valueOf(id) });
    }
}