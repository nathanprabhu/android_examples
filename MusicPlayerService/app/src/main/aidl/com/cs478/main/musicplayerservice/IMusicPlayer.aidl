// IMusicPlayer.aidl
package com.cs478.main.musicplayerservice;

// Declare any non-default types here with import statements

interface IMusicPlayer {
      void playSong(int songId);
      void pauseSong(int songId);
      void resumeSong(int songId);
      void stopSong(int songId);
      void getTable();
}
