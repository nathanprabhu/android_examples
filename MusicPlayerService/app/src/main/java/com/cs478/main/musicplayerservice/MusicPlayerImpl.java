package com.cs478.main.musicplayerservice;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

// This is an IntentService class that defines the music player activities
// Launches a background thread that plays, pauses, stops, resumes songs and returns the Transactions table
public class MusicPlayerImpl extends IntentService {

    static MediaPlayer mediaPlayer = new MediaPlayer();

    // The list of ids of the Raw music files
    int[] songList = {R.raw.friends_theme_song, R.raw.walk_the_moon, R.raw.sugar, R.raw.when_i_look_at_you, R.raw.thousand_years, R.raw.euphoria};

    //A variable to store the currentposition so that it can be resumed
    static int currentPosition = 0;

    //For the first transaction, lasttransaction value will be "Yet to start."
    static String lastTransaction = "Yet to start.";

    MyDatabaseHelper myDatabaseHelper;

    public MusicPlayerImpl() {
        super("MusicPlayerImpl");
        myDatabaseHelper = new MyDatabaseHelper(this);
        //clearAll();
    }

    // Stub class that defines the music player activities
    private final IMusicPlayer.Stub musicPlayerBinder = new IMusicPlayer.Stub() {

        // Play song with the songId
        @Override
        public void playSong(int songId) throws RemoteException {
           if(!(mediaPlayer.isPlaying())) {
               mediaPlayer.start();
           }
        }

        //Pauses the song and stores the currentPosition value
        @Override
        public void pauseSong(int songId) throws RemoteException {
            mediaPlayer.pause();
            currentPosition = mediaPlayer.getCurrentPosition();
        }

        //Resumes the song with id songId from the position currentPosition
        @Override
        public void resumeSong(int songId) throws RemoteException {
            mediaPlayer.seekTo(currentPosition);
            mediaPlayer.start();
        }

        //Stops song and sets currentposition value to 0
        @Override
        public void stopSong(int songId) throws RemoteException {
            currentPosition = 0;
            mediaPlayer.stop();
            mediaPlayer.reset();
        }

        //Gets the Transactions table and returns the records in a String array
        @Override
        public void getTable() throws RemoteException {
            Cursor table =  myDatabaseHelper.getWritableDatabase().query(MyDatabaseHelper.TABLE_NAME,
                    MyDatabaseHelper.columns, null, new String[] {}, null, null,
                    null);
            table.moveToFirst();
            String[] results = new String[table.getCount()];
            int i = 0;
            while (!(table.isAfterLast())) {
                results[i] = table.getString(0) + " " + table.getString(1) + " " + table.getString(2) + " " + table.getString(3) + " " + table.getString(4);
                System.out.println(results[i]);
                i++;
                table.moveToNext();
            }
            //System.out.println(results);
            Intent x = new Intent("com.cs478.main.musicplayerclient.receiveTable");
            x.putExtra("TransactionsTable", results);
            System.out.println(x);
            sendBroadcast(x);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // On service unbind, deletes the database
    @Override
    public boolean onUnbind(Intent intent) {
        myDatabaseHelper.getWritableDatabase().close();
        myDatabaseHelper.deleteDatabase();
        super.onDestroy();
        return super.onUnbind(intent);
    }

    // This method handles the intent service requests based on the action values
    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            if (intent != null) {
                final String action = intent.getAction();
                final Integer songId = (Integer) intent.getExtras().get("SONGID");

                if(!(action.endsWith("gettable"))) {
                    recordTransaction(action, songId);
                }

                if (action == "com.cs478.main.musicplayerservice.playsong") {
                    lastTransaction = "Playing song " + songId.toString();
                    if(mediaPlayer.isPlaying()){
                        mediaPlayer.release();
                    }
                    mediaPlayer = MediaPlayer.create(this, songList[songId]);
                    musicPlayerBinder.playSong(songId);
                }
                else if (action == "com.cs478.main.musicplayerservice.pausesong") {
                    lastTransaction = "Paused song " + songId.toString();
                    musicPlayerBinder.pauseSong(songId);
                }
                else if (action == "com.cs478.main.musicplayerservice.resumesong") {
                    lastTransaction = "Resumed playing song " + songId.toString();
                    musicPlayerBinder.resumeSong(songId);
                }
                else if (action == "com.cs478.main.musicplayerservice.stopsong") {
                    lastTransaction = "Stopped song " + songId.toString();
                    musicPlayerBinder.stopSong(songId);
                }
                else if (action == "com.cs478.main.musicplayerservice.gettable") {
                    musicPlayerBinder.getTable();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //This method records each transaction as a new record in the Transactions table
    private void recordTransaction(String action, int songId){

        ContentValues values = new ContentValues();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        values.put(MyDatabaseHelper.TIME, dateFormat.format(cal.getTime()));
        values.put(MyDatabaseHelper.CURRENT_STATE, lastTransaction);
        values.put(MyDatabaseHelper.TYPE, action.substring(action.lastIndexOf(".") + 1, action.length()));
        values.put(MyDatabaseHelper.CLIP_NO, Integer.toString(songId));

        myDatabaseHelper.getWritableDatabase().insert(MyDatabaseHelper.TABLE_NAME, null, values);
    }

    // Delete all records
    private void clearAll() {
        //deleteDatabase("TransactionsDB");
        myDatabaseHelper.getWritableDatabase().close();
        myDatabaseHelper.getWritableDatabase().delete(MyDatabaseHelper.TABLE_NAME, null, null);
        myDatabaseHelper.deleteDatabase();
    }
}
