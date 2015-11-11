package DataBase.tables;

import android.content.ContentResolver;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by Nouha on 07/03/2015.
 */
public class RemindersTable implements BaseColumns {

    // Reminders database table
    public static final String TABLE_REMINDERS = "reminders";

    // table reminders fields
    public static final String TIMESTAMP = "timestamp";
    public static final String TYPE = "type";
    public static final String IS_NAGGING = "is_nagging";
    public static final String NAGGING_VALUE = "nagging_value";
    public static final String NAGGING_TIMESTAMP = "nagging_timestamp";
    public static final String RECORD_ID = "record_id";

    // Reminders table creation statement
    private static final String CREATE_REMINDERS_TABLE = "CREATE TABLE "
            + TABLE_REMINDERS + " (" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TIMESTAMP + " INTEGER, "
            + TYPE + " INTEGER DEFAULT 1, " + IS_NAGGING
            + " INTEGER DEFAULT 0, " + NAGGING_VALUE + " INTEGER, "
            + NAGGING_TIMESTAMP + " INTEGER, " + RECORD_ID + " INTEGER, "
            + " FOREIGN KEY (" + RECORD_ID + ") REFERENCES "
            + RecordsTable.TABLE_RECORDS + " (" + _ID
            + ") ON DELETE CASCADE );";

    // info for content provider
    public static final String CONTENT_PATH = "reminders";
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/vnd.mastermind.reminders";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/vnd.mastermind.reminder";

    public static final String[] PROJECTION_ALL = { _ID, TIMESTAMP, TYPE,
            IS_NAGGING, NAGGING_VALUE, NAGGING_TIMESTAMP, RECORD_ID };

    public static final String SORT_ORDER_DEFAULT = _ID + " ASC";

    /**
     * create tags table
     *
     * @param database
     */
    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_REMINDERS_TABLE);
    }

    /**
     * upgrade the tags table
     *
     * @param database
     * @param oldVersion
     * @param newVersion
     */
    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        // TODO
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDERS);
        onCreate(database);
    }
}
