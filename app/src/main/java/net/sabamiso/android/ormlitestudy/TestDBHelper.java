package net.sabamiso.android.ormlitestudy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class TestDBHelper extends OrmLiteSqliteOpenHelper  {
    public final String TAG = getClass().getSimpleName();

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "test.db";

    public TestDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        Log.i(TAG, "onCreate()");
        try {
            TableUtils.createTable(connectionSource, Test.class);
        } catch (SQLException e) {
            Log.e(TAG, "TableUtils.createTable() failed...", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(TAG, "onUpgrade()");
            TableUtils.dropTable(connectionSource, Test.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            Log.e(TAG, "TableUtils.dropTable() failed...", e);
        }
    }

    private Dao<Test, Integer> simpleDao = null;

    public Dao<Test, Integer> getTestDao() {
        if (simpleDao == null) {
            try {
                simpleDao = getDao(Test.class);
            }
            catch (SQLException e) {
                Log.e(TAG, "getDao() failed...", e);
            }
        }
        return simpleDao;
    }

    @Override
    public void close() {
        super.close();
        simpleDao = null;
    }

}
