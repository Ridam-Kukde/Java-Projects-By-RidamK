package org.niit.com;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;

import static org.niit.com.MusicPlayer.clip;

public class Playlist {
    private int playlistId;
    private String playlistName;


    public Playlist(int playlistId, String playlistName) {
        this.playlistId = playlistId;
        this.playlistName = playlistName;
    }

    public Playlist() {
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "playlistId=" + playlistId +
                ", playlistName='" + playlistName + '\'' +
                '}';
    }

}
