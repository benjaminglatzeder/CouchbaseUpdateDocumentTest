package com.couchbaseupdatedocumenttest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseOptions;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openCouchbase();
        findViewById(R.id.button_add_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long t1 = System.currentTimeMillis();
                DocumentUpdateUtils.add1ToDoc(database);
                long t2 = System.currentTimeMillis();
                Log.d("time", "=" + (t2 - t1));
            }
        });
        findViewById(R.id.button_add_100).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long t1 = System.currentTimeMillis();
                DocumentUpdateUtils.add100ToDoc(database);
                long t2 = System.currentTimeMillis();
                Log.d("time", "=" + (t2 - t1));
            }
        });
        findViewById(R.id.button_get_no_of_people).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long t1 = System.currentTimeMillis();
                ArrayList<PersonPOJO> list = DocumentUpdateUtils.getNoOfPeople(database);
                //                for (PersonPOJO personPOJO : list)
                //                    Log.d("person", personPOJO.toString());
                long t2 = System.currentTimeMillis();
                Log.d("size", "=" + list.size());
                Log.d("time", "=" + (t2 - t1));
            }
        });
    }

    private void openCouchbase() {
        try {
            Manager manager = new Manager(new AndroidContext(this), Manager.DEFAULT_OPTIONS);
            DatabaseOptions options = new DatabaseOptions();
            options.setCreate(true);
            options.setStorageType(Manager.SQLITE_STORAGE);
            database = manager.openDatabase("cb_database", options);
        } catch (IOException | CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }
}
