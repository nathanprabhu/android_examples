package com.cs478.main.musicplayerclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs478.main.musicplayerservice.IMusicPlayer;


//This activity displays the current song that is played.
public class MusicPlayerActivity extends AppCompatActivity {

    TextView playButton;
    TextView prevButton;
    TextView nextButton;
    TextView stopButton;

    TextView songName;
    TextView artistName;
    ImageView albumImage;

    //Boolean value to indicate if a song is being played
    boolean playing = true;
    //Boolean value to indicate if stop button has been clicked
    boolean stopped = false;

    String[] songNames;
    String[] artistNames;
    int[] thumbNailIds = {R.mipmap.friends, R.mipmap.shutuppic, R.mipmap.sugarpic, R.mipmap.lastsongpic, R.mipmap.thousandyearspic, R.mipmap.euphoriapic};
    int songId;

    IMusicPlayer musicPlayerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        //Get the SONGID of the song selected from the mainactivity
        songId = (Integer) getIntent().getExtras().get("SONGID");

        songNames = getResources().getStringArray(R.array.songNames);
        artistNames = getResources().getStringArray(R.array.artistNames);

        songName = (TextView) findViewById(R.id.currentSong);
        artistName = (TextView) findViewById(R.id.artistName);
        albumImage = (ImageView) findViewById(R.id.albumImg);
        playButton = (TextView) findViewById(R.id.playButton);
        prevButton = (TextView) findViewById(R.id.prevButton);
        nextButton = (TextView) findViewById(R.id.nextButton);
        stopButton = (TextView) findViewById(R.id.stopButton);

        //Set the values for the Song name, artist name, album image and change the text on Play button to Pause
        songName.setText(songNames[songId]);
        artistName.setText(artistNames[songId]);
        playButton.setText("PAUSE");
        albumImage.setImageResource(thumbNailIds[songId]);

        //Set onclick listener for play button
        //First time click will play the song
        //Pressing pause will pause the song
        //Pressing Play after pausing will resume the song
        //Pressing Play after stopping will start the song
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(stopped);
                if (playing == true && stopped == false) {
                    playing = false;
                    playButton.setText("PLAY");
                    Intent newMusicPlayerIntent = new Intent("com.cs478.main.musicplayerservice.pausesong");
                    newMusicPlayerIntent.putExtra("SONGID", songId);
                    startService(newMusicPlayerIntent);
                } else if (playing == false && stopped == false) {
                    System.out.println("1111111");
                    playing = true;
                    playButton.setText("PAUSE");
                    Intent newMusicPlayerIntent = new Intent("com.cs478.main.musicplayerservice.resumesong");
                    newMusicPlayerIntent.putExtra("SONGID", songId);
                    startService(newMusicPlayerIntent);
                } else if (stopped == true) {
                    System.out.println("2222222");
                    playing = true;
                    stopped = false;
                    Intent newMusicPlayerIntent = new Intent("com.cs478.main.musicplayerservice.playsong");
                    newMusicPlayerIntent.putExtra("SONGID", songId);
                    startService(newMusicPlayerIntent);
                }
            }
        });

        // Setting the onclick listener for previous button
        // Clicking "Prev" will play the previous song in the list
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(songId>0) {
                    songId = songId - 1;
                }
                songName.setText(songNames[songId]);
                artistName.setText(artistNames[songId]);
                playButton.setText("PAUSE");
                albumImage.setImageResource(thumbNailIds[songId]);

                Intent newMusicPlayerIntent = new Intent("com.cs478.main.musicplayerservice.playsong");
                newMusicPlayerIntent.putExtra("SONGID", songId);
                startService(newMusicPlayerIntent);
            }
        });

        // Setting the onclick listener for Next button
        // Clicking "Next" will play the next song in the list
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(songId<4) {
                    songId = songId + 1;
                }
                songName.setText(songNames[songId]);
                artistName.setText(artistNames[songId]);
                playButton.setText("PAUSE");
                albumImage.setImageResource(thumbNailIds[songId]);

                Intent newMusicPlayerIntent = new Intent("com.cs478.main.musicplayerservice.playsong");
                newMusicPlayerIntent.putExtra("SONGID", songId);
                startService(newMusicPlayerIntent);
            }
        });

        // Setting the onclick listener for Stop button
        // Clicking "Stop" will stop the current song
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playing = false;
                stopped = true;
                songName.setText(songNames[songId]);
                artistName.setText(artistNames[songId]);
                playButton.setText("PLAY");
                albumImage.setImageResource(thumbNailIds[songId]);

                Intent newMusicPlayerIntent = new Intent("com.cs478.main.musicplayerservice.stopsong");
                newMusicPlayerIntent.putExtra("SONGID", songId);
                startService(newMusicPlayerIntent);
            }
        });
    }

    //This activity on start will play the song selected in MainActivity
    @Override
    protected void onStart() {
        super.onStart();
        Intent musicPlayerIntent = new Intent("com.cs478.main.musicplayerservice.playsong");
        musicPlayerIntent.putExtra("SONGID", songId);
        startService(musicPlayerIntent);

    }

    //This activity on destroy will stop the current song
    @Override
    public void onDestroy(){
        super.onDestroy();
        playing = false;
        stopped = true;
        Intent newMusicPlayerIntent = new Intent("com.cs478.main.musicplayerservice.stopsong");
        newMusicPlayerIntent.putExtra("SONGID", songId);
        startService(newMusicPlayerIntent);
    }
}
