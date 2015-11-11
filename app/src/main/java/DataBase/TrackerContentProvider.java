package DataBase;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;

import DataBase.tables.RecordsTable;
import DataBase.tables.RemindersTable;

/**
 * Created by Nouha on 07/03/2015.
 */
public class TrackerContentProvider extends ContentProvider
{

    // database
    private TrackerDatabaseHelper mOpenHelper;

    private static final String AUTHORITY = "com.iit.myactivitytracker.database";

    public static final Uri RECORDS_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + RecordsTable.CONTENT_PATH);
    public static final Uri REMINDERS_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + RemindersTable.CONTENT_PATH);

    private static final int RECORDS_LIST = 10;
    private static final int RECORD_ID = 11;
    private static final int REMINDERS_LIST = 19;
    private static final int REMINDER_ID = 20;

    private static final UriMatcher MASTERMIND_URI_MATCHER;

    static {
        MASTERMIND_URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        MASTERMIND_URI_MATCHER.addURI(AUTHORITY, RecordsTable.CONTENT_PATH,
                RECORDS_LIST);
        MASTERMIND_URI_MATCHER.addURI(AUTHORITY, RecordsTable.CONTENT_PATH
                + "/#", RECORD_ID);
        MASTERMIND_URI_MATCHER.addURI(AUTHORITY, RemindersTable.CONTENT_PATH,
                REMINDERS_LIST);
        MASTERMIND_URI_MATCHER.addURI(AUTHORITY, RemindersTable.CONTENT_PATH
                + "/#", REMINDER_ID);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase sqlDB = mOpenHelper.getWritableDatabase();
        int rowsDeleted = 0;
        String id;
        switch (MASTERMIND_URI_MATCHER.match(uri)) {
            case RECORDS_LIST:
                rowsDeleted = sqlDB.delete(RecordsTable.TABLE_RECORDS, selection,
                        selectionArgs);
                break;
            case RECORD_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(RecordsTable.TABLE_RECORDS,
                            RecordsTable._ID + "=" + id, null);
                } else {
                    rowsDeleted = sqlDB.delete(RecordsTable.TABLE_RECORDS,
                            RecordsTable._ID + "=" + id + " and " + selection,
                            selectionArgs);
                }
                break;
            case REMINDERS_LIST:
                rowsDeleted = sqlDB.delete(RemindersTable.TABLE_REMINDERS,
                        selection, selectionArgs);
                break;
            case REMINDER_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(RemindersTable.TABLE_REMINDERS,
                            RemindersTable._ID + "=" + id, null);
                } else {
                    rowsDeleted = sqlDB.delete(RemindersTable.TABLE_REMINDERS, RemindersTable._ID
                            + "=" + id + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        switch (MASTERMIND_URI_MATCHER.match(uri)) {
            case RECORDS_LIST:
                return RecordsTable.CONTENT_TYPE;
            case RECORD_ID:
                return RecordsTable.CONTENT_ITEM_TYPE;
            case REMINDERS_LIST:
                return RemindersTable.CONTENT_TYPE;
            case REMINDER_ID:
                return RemindersTable.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase database = mOpenHelper.getWritableDatabase();
        Uri itemUri = null;

        long id = 0;



        switch (MASTERMIND_URI_MATCHER.match(uri)) {
            case RECORDS_LIST:

                id = database.insert(RecordsTable.TABLE_RECORDS, null, values);
                if (id > 0) {
                    // notify all listeners of changes and return itemUri:
                    itemUri = ContentUris.withAppendedId(uri, id);
                    getContext().getContentResolver().notifyChange(itemUri, null);
                } else {
                    // something went wrong:
                    throw new SQLException("Problem while inserting into "
                            + RecordsTable.TABLE_RECORDS + ", uri: " + uri);
                }
                break;

            case REMINDERS_LIST:
                id = database.insert(RemindersTable.TABLE_REMINDERS, null, values);
                if (id > 0) {
                    // notify all listeners of changes and return itemUri:
                    itemUri = ContentUris.withAppendedId(uri, id);
                    getContext().getContentResolver().notifyChange(itemUri, null);
                } else {
                    // something went wrong:
                    throw new SQLException("Problem while inserting into "
                            + RemindersTable.TABLE_REMINDERS + ", uri: " + uri);
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        return itemUri;
    }

//        @Override
//        public int bulkInsert(Uri uri, ContentValues[] allValues) {
//            SQLiteDatabase database = mOpenHelper.getWritableDatabase();
//            int addedRows = 0;
//            long id = 0;
//            switch (MASTERMIND_URI_MATCHER.match(uri)) {
//
//                default:
//                    // only accept bulk insert during sync
//                    throw new IllegalArgumentException("Unknown URI " + uri);
//            }
//            return addedRows;
//        }

    @Override
    public boolean onCreate() {
        mOpenHelper = new TrackerDatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // Using SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch (MASTERMIND_URI_MATCHER.match(uri)) {
            case RECORDS_LIST:
                // Check if the caller has requested a column which does not
                // exists
                checkRecordsTableColumns(projection);
                // Set the table
                queryBuilder.setTables(RecordsTable.TABLE_RECORDS);
                break;
            case RECORD_ID:
                // Check if the caller has requested a column which does not
                // exists
                checkRecordsTableColumns(projection);
                // Set the table
                queryBuilder.setTables(RecordsTable.TABLE_RECORDS);
                // Adding the ID to the original query
                queryBuilder.appendWhere(RecordsTable._ID + "="
                        + uri.getLastPathSegment());
                break;
            case REMINDERS_LIST:
                // Check if the caller has requested a column which does not
                // exists
                checkRemindersTableColumns(projection);
                // Set the table
                queryBuilder.setTables(RemindersTable.TABLE_REMINDERS);
                break;
            case REMINDER_ID:
                // Check if the caller has requested a column which does not
                // exists
                checkRemindersTableColumns(projection);
                // Set the table
                queryBuilder.setTables(RemindersTable.TABLE_REMINDERS);
                // Adding the ID to the original query
                queryBuilder.appendWhere(RemindersTable._ID + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        // Make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase database = mOpenHelper.getWritableDatabase();
        int rowsUpdated = 0;
        String id;
        switch (MASTERMIND_URI_MATCHER.match(uri)) {
            case RECORD_ID:

                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = database.update(RecordsTable.TABLE_RECORDS,
                            values, RecordsTable._ID + "=" + id, null);
                } else {
                    rowsUpdated = database.update(RecordsTable.TABLE_RECORDS,
                            values, RecordsTable._ID + "=" + id + " and "
                                    + selection, selectionArgs);
                }
                break;
            case REMINDER_ID:

                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = database.update(RemindersTable.TABLE_REMINDERS,
                            values, RemindersTable._ID + "=" + id, null);
                } else {
                    rowsUpdated = database.update(RemindersTable.TABLE_REMINDERS,
                            values, RemindersTable._ID + "=" + id + " and "
                                    + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    /**
     * check if an unavailable column is requested for mastermind records
     *
     * @param projection
     */
    private void checkRecordsTableColumns(String[] projection) {

        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(
                    Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(
                    Arrays.asList(RecordsTable.PROJECTION_ALL));
            // Check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException(
                        "Unknown columns in projection");
            }
        }
    }

    /**
     * check if an unavailable column is requested for mastermind reminders
     *
     * @param projection
     */
    private void checkRemindersTableColumns(String[] projection) {

        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(
                    Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(
                    Arrays.asList(RemindersTable.PROJECTION_ALL));
            // Check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException(
                        "Unknown columns in projection");
            }
        }
    }





}
