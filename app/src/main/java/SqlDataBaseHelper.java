import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqlDataBaseHelper extends SQLiteOpenHelper
{
    private static final String DataBaseName = "weather";
    private static final int DataBaseVersion = 1;
    private SQLiteDatabase db;
    private String test;


    public SqlDataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, String TableName) {
        super(context, DataBaseName, null, DataBaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQLTable = "CREATE TABLE IF NOT EXISTS " + test+ "( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Name TEXT, " +
                "Phone TEXT," +
                ");";
        db.execSQL(SQLTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        final String SQL = "DROP TABLE " + test;
        db.execSQL(SQL);
    }
}
