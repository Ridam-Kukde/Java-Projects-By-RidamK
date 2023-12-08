package org.niit.com;
import java.sql.PreparedStatement;
import java.util.*;
import java.lang.*;
import java.sql.ResultSet;
import java.sql.Statement;

public class SongImpl {
    static Scanner scanner=new Scanner(System.in);
    public void showSongs()
    {
        try {
            Statement st = ConnectionManager.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("Select * from song");
            System.out.println("SongId        SongName ");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " \t\t\t" + rs.getString(2));
            }
        }catch(Exception e)
        {
            System.out.println("Error in 'showSongs': "+e.getMessage());
        }
    }
    public void showSongsAndPlay()
    {


        try{
            Songs song=new Songs();
            Statement st=ConnectionManager.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery("Select * from song");
            System.out.println("SongId        SongName ");
            while(rs.next()) {
                System.out.println(rs.getInt(1) + " \t\t\t" + rs.getString(2));

            }
            System.out.println("Do you want to play a Song(y/n)?");
            String playChoicess=scanner.next();
            if(playChoicess.equalsIgnoreCase("y")) {
                System.out.print("Select a song to play by id: ");
                int ch = scanner.nextInt();
                rs.beforeFirst();
                while (rs.next()) {

                    if (rs.getInt(1) == ch) {
                        song.setSongId(rs.getInt(1));
                        song.setSongName(rs.getString(2));
                        song.setGenre(rs.getString(3));
                        song.setArtist(rs.getString(4));
                        song.setAlbum(rs.getString(5));
                        song.setDuration(rs.getString(6));
                        song.setFilePath(rs.getString(7));
                    }

                }
                MusicPlayer.playSong(song);
            }else if(playChoicess.equalsIgnoreCase("n")){
                return;
            }else System.out.println("Wrong choice made exiting!");

        }catch(Exception exception)
        {
            System.out.println("Error in showSongs: "+exception);
        }
    }
    public void searchSongById(int id) {
        ConnectionManager.createConnection();
        try {
            Songs song = new Songs();

            PreparedStatement ps = ConnectionManager.con.prepareStatement("SELECT * FROM song WHERE songid=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            boolean found = false;
            System.out.println(" ----------------------------------------------------------------------------------");
            System.out.println("Showing search results: ");
            System.out.println("SongID      SongName        Artist");
            System.out.println(" ----------------------------------------------------------------------------------");


            while (rs.next()) {
                found = true;
                System.out.print(rs.getInt(1) + "    ");
                song.setSongId(rs.getInt(1));
                System.out.print(rs.getString(2) + "    ");
                song.setSongName(rs.getString(2));
                System.out.print(rs.getString(4) + "    ");
                System.out.println();
                song.setArtist(rs.getString(4));
                song.setGenre(rs.getString(3));
                song.setAlbum(rs.getString(5));
                song.setDuration(rs.getNString(6));
                song.setFilePath(rs.getString(7));
                System.out.println(" ----------------------------------------------------------------------------------");

            }

            if (!found) {
                throw new songNotFoundException("The SongId you entered does not exist in the database");
            } else {
                System.out.print("Do you want to play this song(Y/N) :");
                Scanner scanner = new Scanner(System.in);
                String toPlay = scanner.next();
                if (toPlay.equalsIgnoreCase("y")) {
                    MusicPlayer.playSong(song);
                }
            }
        }catch(Exception exception) {
            System.out.println("Error in Searching Song by ID: " + exception);
        }
    }

    public static void searchSongByName(String songName) {
        ConnectionManager.createConnection();
        try {
            PreparedStatement ps = ConnectionManager.con.prepareStatement("SELECT * FROM song WHERE songname=?");
            ps.setString(1, songName);
            ResultSet rs = ps.executeQuery();
            boolean found=false;
                System.out.println("Showing search results: ");
                System.out.println("SongID      SongName        Artist");
                while(rs.next()) {

                    found=true;
                    int songId = rs.getInt(1);
                    String songN = rs.getString(2);
                    String artist = rs.getString(4);
                    String genre= rs.getString(3);
                    String album=rs.getString(5);
                    String  duration=rs.getString(6);
                    String filepath= rs.getString(7);

                    System.out.println(songId + "    " + songN + "    " + artist);

                    System.out.print("Do you want to play this song(Y/N) :");
                    String toPlay = scanner.next();

                    if (toPlay.equalsIgnoreCase("y")) {
                        Songs song = new Songs();
                        song.setSongId(songId);
                        song.setSongName(songN);
                        song.setArtist(artist);
                        song.setAlbum(album);
                        song.setGenre(genre);
                        song.setDuration(duration);
                        song.setFilePath(filepath);
                        MusicPlayer.playSong(song);
                    }
                }
                if(!found) throw new songNotFoundException("");


        } catch (Exception exception) {
            System.out.println("Error in Searching Song by Name: " + exception);
        }
    }

    public static void addSongToDB(Songs song) {
        ConnectionManager.createConnection();
        try {
            PreparedStatement preparedStatement=ConnectionManager.con.prepareStatement("INSERT INTO song(songid, songname, genre, artist, album, duration, filepath) VALUES (?,?,?,?,?,?,?)");
            preparedStatement.setInt(1, song.getSongId());
            preparedStatement.setString(2, song.getSongName());
            preparedStatement.setString(3, song.getGenre());
            preparedStatement.setString(4, song.getArtist());
            preparedStatement.setString(5, song.getAlbum());
            preparedStatement.setString(6, song.getDuration());
            preparedStatement.setString(7, song.getFilePath());
            int stat=preparedStatement.executeUpdate();
            if(stat>0) {
                System.out.println("Song added to database successfully! ");
            }
        } catch (Exception ee) {
            System.out.println("Error in Adding the song to database: " + ee);
        }
    }
}
