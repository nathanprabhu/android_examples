package com.cs478.main.musicplayerclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

//This class is the launcher activity that displays the list of songs in the album
public class MainActivity extends AppCompatActivity {

    ListView songList;

    //The list of ids of the thumbnail pictures of each song
    int[] thumbNailIds = {R.mipmap.friends, R.mipmap.shutuppic, R.mipmap.sugarpic, R.mipmap.lastsongpic, R.mipmap.thousandyearspic, R.mipmap.euphoriapic};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] songNames = getResources().getStringArray(R.array.songNames);
        String[] artistNames = getResources().getStringArray(R.array.artistNames);

        //Populate the list of songs in the list view using the MySongAdapter class
        songList = (ListView) findViewById(R.id.myListView);
        songList.setAdapter(new MySongAdapter(songNames, artistNames, thumbNailIds));

        //On clicking each item in the song list, the MusicPlayerActivity is launched that starts the MusicPlayerService
        songList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("hey");
                Intent playerIntent = new Intent(getApplicationContext(), MusicPlayerActivity.class);
                playerIntent.putExtra("SONGID", position);
                startActivity(playerIntent);
            }
        });

        //The DB icon when clicked launches the TableActivity which displays the records of the Transactions table
        ImageView dbIcon = (ImageView) findViewById(R.id.dbIcon);
            dbIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tableIntent = new Intent(getApplicationContext(), TableActivity.class);
                startActivity(tableIntent);
            }
        });
    }

    //This class populates the list of songs in the album
    class MySongAdapter extends BaseAdapter {

        String[] songNames;
        String[] artistNames;
        int[] thumbNailIds;
        LayoutInflater layoutInflater;

        MySongAdapter() {
            super();
            songNames = null;
            artistNames = null;
            thumbNailIds = null;
            layoutInflater = getLayoutInflater();
        }

        MySongAdapter(String[] sNames, String[] aNames, int[] imageIds) {
            super();
            songNames = sNames;
            artistNames = aNames;
            thumbNailIds = imageIds;
            layoutInflater = getLayoutInflater();
        }

        @Override
        public int getCount() {
            return songNames.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            //Inflate the list view with the row_layout.xml file
            convertView = layoutInflater.inflate(R.layout.row_layout, null);

            //Set the values for Song name, Artist name and Thumbnail image
            TextView songName = (TextView) (convertView.findViewById(R.id.songName));
            songName.setText(songNames[position]);
            TextView artistName = (TextView) (convertView.findViewById(R.id.artistName));
            String artName = artistNames[position];
            artistName.setText(artName);
            ImageView thumbNail = (ImageView) (convertView.findViewById(R.id.thumbNail));
            thumbNail.setImageResource(thumbNailIds[position]);
            return convertView;
        }
    }
}