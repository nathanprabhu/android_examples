package com.cs478.main.musicplayerclient;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//This activity is to display the Transactions table. Entries are not deleted
public class TableActivity extends AppCompatActivity {

    ArrayAdapter<String> arrayAdapter;
    String[] responseString;

    //This activity on create launches the music player service with the action "gettable"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        //Register TableReceiver to receive the transactions table records.
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.cs478.main.musicplayerclient.receiveTable");
        (TableActivity.this).registerReceiver(new TableReceiver(), filter);

        Intent getTableIntent = new Intent("com.cs478.main.musicplayerservice.gettable");
        getTableIntent.putExtra("SONGID", -1);
        startService(getTableIntent);

    }
    //This receiver receives the table and populates the list of transactions using an ArrayAdapter
    public class TableReceiver extends BroadcastReceiver {

        public TableReceiver(){
            super();
        }

        //On receiving the transactions table records as a string array, the listview is populated
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                responseString = intent.getStringArrayExtra("TransactionsTable");
                arrayAdapter = new ArrayAdapter<String>(context, R.layout.table_row_layout, R.id.transaction,responseString);

                ListView tableView = (ListView) findViewById(R.id.transactionsList);
                tableView.setAdapter(arrayAdapter);

            } catch (Exception e) {
                e.getMessage();
                e.printStackTrace();
            }

        }

    }
}