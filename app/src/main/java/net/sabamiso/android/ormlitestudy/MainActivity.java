package net.sabamiso.android.ormlitestudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public final String TAG = getClass().getSimpleName();

    TestDBHelper helper;
    Dao<Test, Integer> dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new TestDBHelper(getBaseContext());
        dao = helper.getTestDao();

        setupUI();
        showAllRecodes();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (helper != null) {
            OpenHelperManager.releaseHelper();
            helper = null;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////

    Button buttonInsert;
    Button buttonSelect;
    Button buttonSelect2;
    Button buttonUpdate;
    Button buttonDelete;
    Button buttonDeleteAll;

    TextView textViewMessage;
    EditText editTextWhereId;

    void setupUI() {
        buttonInsert = (Button)findViewById(R.id.buttonInsert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonInsert();
            }
        });

        buttonSelect = (Button)findViewById(R.id.buttonSelect);
        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonSelect();
            }
        });

        buttonSelect2 = (Button)findViewById(R.id.buttonSelect2);
        buttonSelect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonSelect2();
            }
        });

        buttonUpdate = (Button)findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonUpdate();
            }
        });

        buttonDelete = (Button)findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonDelete();
            }
        });

        buttonDeleteAll = (Button)findViewById(R.id.buttonDeleteAll);
        buttonDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonDeleteAll();
            }
        });

        textViewMessage = (TextView)findViewById(R.id.textViewMessage);
        editTextWhereId = (EditText)findViewById(R.id.editTextWhereId);
        editTextWhereId.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    void log_d(String msg) {
        Log.d(TAG, msg);
    }

    void log_e(String msg, Exception e) {
        Log.e(TAG, msg, e);
        Toast.makeText(this, msg + ":" + e.toString(), Toast.LENGTH_LONG).show();
    }

    int getWhereId() {
        String id_str = editTextWhereId.getText().toString();
        return Integer.parseInt(id_str);
    }

    ///////////////////////////////////////////////////////////////////////////////

    int idx = 1;
    String [] domains = {"a.a", "b.b", "c.c"};

    void onButtonInsert() {
        log_d("onButtonInsert()");
        Test ent = new Test("name" + idx, "email" + idx + "@" + domains[idx%3]);
        try {
            dao.create(ent);
        } catch (SQLException e) {
            log_e("dao.create() failed...", e);
        }
        showAllRecodes();
        idx ++;
    }

    void onButtonSelect() {
        log_d("onButtonSelect()");

        try {
            Test result = dao.queryBuilder()
                    .where()
                    .eq(Test.COLUMN_ID, getWhereId())
                    .queryForFirst();
            showRecord(result);

        } catch (SQLException e) {
            log_e("dao.queryForFirst() failed...", e);
        }
    }

    void onButtonSelect2() {
        log_d("onButtonSelect2()");

        try {
            QueryBuilder<Test, Integer> builder = dao.queryBuilder();
            builder.where().like(Test.COLUMN_EMAIL, "%a.a");
            builder.offset(0L).limit(100L);
            List<Test> results = builder.query();
            showRecords(results);
        } catch (SQLException e) {
            log_e("dao.queryForFirst() failed...", e);
        }
    }

    void onButtonUpdate() {
        log_d("onButtonUpdate()");

        try {
            Test obj = dao.queryBuilder()
                    .where()
                    .eq(Test.COLUMN_ID, getWhereId())
                    .queryForFirst();

            obj.setEmail("email" + (int)(Math.random() * Integer.MAX_VALUE % 1000) + "@example.com");

            dao.createOrUpdate(obj);

        } catch (SQLException e) {
            log_e("dao.queryForFirst() failed...", e);
        }

        showAllRecodes();
    }

    void onButtonDelete() {
        log_d("onButtonDelete()");

        try {
            DeleteBuilder<Test, Integer> builder = dao.deleteBuilder();
            builder.where().eq(Test.COLUMN_ID, getWhereId());
            builder.delete();
        } catch (SQLException e) {
            log_e("dao.delete() failed...", e);
        }

        showAllRecodes();
    }

    void onButtonDeleteAll() {
        log_d("onButtonDeleteAll()");

        try {
            DeleteBuilder<Test, Integer> builder = dao.deleteBuilder();
            builder.delete();
        } catch (SQLException e) {
            log_e("dao.delete() failed...", e);
        }

        showAllRecodes();
    }

    void showAllRecodes() {
        try {
            List<Test> list = dao.queryForAll();
            showRecords(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void showRecord(Test t) {
        textViewMessage.setText(t.toString());
    }

    void showRecords(List<Test> list) {
        StringBuffer sb = new StringBuffer();
        for(Test e : list) {
            sb.append(e.toString());
            sb.append("\n");
        }
        textViewMessage.setText(sb.toString());
    }
}
