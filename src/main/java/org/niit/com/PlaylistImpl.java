package org.niit.com;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlaylistImpl {
    private static MusicPlayer musicPlayer;
    static Scanner scanner=new Scanner(System.in);
    static SongImpl songImpl=new SongImpl();
    public static void showPlaylistsForUser(int userId) {
        try {
            PreparedStatement preparedStatement = ConnectionManager.con.prepareStatement(
                    "SELECT p.playlistId, p.playlistName " +
                            "FROM Playlist p " +
                            "INNER JOIN userplaylist up ON p.playlistId = up.playlistId " +
                            "WHERE up.userId = ?");
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();

            System.out.println(" ----------------------------------------------------------------------------------");
            System.out.println("Playlists for User ID " + userId + ":");
            System.out.println("------------------------------------------------------------------------------");
            System.out.println("PlaylistId\t    |        PlaylistName");
            System.out.println("------------------------------------------------------------------------------");

            boolean playlistsExist = false;

            while (rs.next()) {
                playlistsExist = true;
                System.out.println(rs.getInt("playlistId") + "             |  \t\t" + rs.getString("playlistName"));
            }

            if (!playlistsExist) {
                System.out.println("No Playlist exists for this User!");
                return; // Exiting the method and thereby the application
            }



            System.out.println(" ----------------------------------------------------------------------------------");
        } catch (Exception exception) {
            System.out.println("Error in showPlaylistsForUser: " + exception.getMessage());
        }
    }



    protected static void displayPlaylistContents(int playlistId, int userId) {
        try {
            PreparedStatement preparedStatement = ConnectionManager.con.prepareStatement("SELECT * FROM userplaylist WHERE playlistId = ? AND userId = ?");
            preparedStatement.setInt(1, playlistId);
            preparedStatement.setInt(2, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                System.out.println("--------------------------------------------------------------------------------");
                System.out.println("Playlist does not exist for this user. Exiting app");
                System.out.println("--------------------------------------------------------------------------------");
                return;
            }

            preparedStatement = ConnectionManager.con.prepareStatement("SELECT s.songId, s.songName, s.filePath, s.duration FROM userplaylistsong ups JOIN song s ON ups.songId = s.songId WHERE ups.playlistId = ?");
            preparedStatement.setInt(1, playlistId);
            resultSet = preparedStatement.executeQuery();

            List<String> songPaths = new ArrayList<>();
            List<Long> songDurations=new ArrayList<>();
            List<String> songNameL=new ArrayList<>();
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.println("                        Songs in the playlist: ");
            System.out.println("-----------------------------------------------------------------------------------");

            System.out.println("SongId  \tSongName");

            while (resultSet.next()) {
                int songId = resultSet.getInt("songId");
                String songName = resultSet.getString("songName");
                String filePath = resultSet.getString("filePath");
                String durationString = resultSet.getString("duration"); // Assuming duration is in seconds

                System.out.println(songId + "\t\t" + songName);
                songPaths.add(filePath);
                songNameL.add(songName);
                try {
                    Long duration = Long.parseLong(durationString); // Converting duration string to double
                    songDurations.add(duration);
                } catch (NumberFormatException numberFormatException) {
                    System.out.println("Error in displaying the playlists: "+numberFormatException);
                }
            }
            System.out.println("Do you want to play all songs(y/n)?");
            String playChoice= scanner.next();
            // Play songs one by one
            if(playChoice.equalsIgnoreCase("y")) {
                for (int i = 0; i < songPaths.size(); i++) {
                    playSong(songPaths.get(i));
                    System.out.println("--------------------------------------------------------");
                    System.out.println("Playing: " + songNameL.get(i));
                    System.out.println("Enter 0 to exit!");
                    System.out.println("--------------------------------------------------------");
                    int ex = scanner.nextInt();
                    if (ex == 0) {
                        stopSong();
                        return; // Exit the method if the user entered 0
                    }
                    waitUntilSongEnds(songDurations.get(i));// Waiting for the duration of the song
                    stopSong();
                }

            }else
            {
                System.out.println("Redirecting to main menu!");
                return;
            }


        } catch (Exception e) {
            System.out.println("Error displaying playlist contents: ");
            e.printStackTrace();
        }
    }

    private static void playSong(String filePath) {
        try {
            MusicPlayer.filePath = filePath;
            musicPlayer = new MusicPlayer();

            musicPlayer.play();
        } catch (Exception e) {
            System.out.println("Error playing song: " + e.getMessage());
        }
    }
    private static void stopSong() {
        try {
            if (musicPlayer == null) {
                musicPlayer = new MusicPlayer();
            }
            musicPlayer.stop();
        } catch (Exception e) {
            System.out.println("Error stopping song: " + e.getMessage());
        }
    }


    private static void waitUntilSongEnds(long duration) {
        // Add code to wait until the song ends before proceeding
        // You may need to check the status of the MusicPlayer or use a timer
        // to determine when the song has finished playing.
        // This could involve checking the length of the song or using events/callbacks.
        // Implement this method based on the functionality provided by your MusicPlayer class.
        // For simplicity, you can use a Thread.sleep() as a placeholder, but it's not recommended for production code.
        try {
            Thread.sleep(duration); // Wait for the duration of the song
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void createPlaylist(int userId) {
        ConnectionManager.createConnection();
        try {
            PreparedStatement preparedStatement = ConnectionManager.con.prepareStatement("INSERT INTO Playlist(playlistId, playlistName) VALUES (?, ?)");

            System.out.print("Assign a playlist id to your playlist (must be integer): ");
            int playlistId = scanner.nextInt();
            preparedStatement.setInt(1, playlistId);

            System.out.print("Enter the name of your new playlist: ");
            String playlistName = scanner.next();
            preparedStatement.setString(2, playlistName);
            PreparedStatement userPlaylistStatement = ConnectionManager.con.prepareStatement("INSERT INTO userplaylist(userId, playlistId) VALUES (?, ?)");

            int status = preparedStatement.executeUpdate();
            if (status > 0) {
                System.out.println("Playlist created successfully!");
            }
            System.out.println("Do you want to add song to the newly created playlist? (y/n)");
            String adds= scanner.next();
            if(adds.equalsIgnoreCase("y"))
            {
                songImpl.showSongs();
                System.out.println("Choose a Song to add to this playlist(SongID only): ");
                int sid= scanner.nextInt();
                addToPlaylist(sid, userId);
            }
            userPlaylistStatement.setInt(1, userId);
            userPlaylistStatement.setInt(2, playlistId);
            userPlaylistStatement.executeUpdate();
        } catch (Exception exception) {
            System.out.println("Error in creating playlist: " + exception.getMessage());
        }
    }
    public static void addToPlaylist(int songId, int userId) {
        try {
            PlaylistImpl.showPlaylistsForUser(userId);
            // Get playlistId from user input
            System.out.print("Enter the PlaylistId where you want to add the song: ");
            int playlistId = scanner.nextInt();

            // Check if the playlist belongs to the user
            if (isUserPlaylist(playlistId, userId)) {
                // Check if the song exists
                if (isSongExists(songId)) {
                    // Check if the song is already in the playlist
                    if (!isSongInPlaylist(playlistId, songId)) {
                        // Add the song to the playlist
                        PreparedStatement preparedStatement = ConnectionManager.con.prepareStatement("INSERT INTO userplaylistsong(userid, playlistId, songId) VALUES (?, ?, ?)");

                        preparedStatement.setInt(1, userId);
                        preparedStatement.setInt(2, playlistId);
                        preparedStatement.setInt(3, songId);
                        int status = preparedStatement.executeUpdate();

                        if (status > 0) {
                            System.out.println("Song added to the playlist successfully!");
                            Menu.MainMenu(userId);
                        } else {
                            System.out.println("Failed to add the song to the playlist.");
                            Menu.playlistMenu(userId);
                        }
                    } else {
                        System.out.println("Song is already in the playlist.");
                    }
                } else {
                    System.out.println("Song does not exist.");
                }
            } else {
                System.out.println("Playlist does not belong to the user.");
            }
        } catch (Exception exception) {
            System.out.println("Error in addToPlaylist: " + exception.getMessage());
        }
    }




    private static boolean isUserPlaylist(int playlistId, int userId) throws SQLException {
        PreparedStatement preparedStatement = ConnectionManager.con.prepareStatement("SELECT * FROM userplaylist WHERE playlistId = ? AND userId = ?");
        preparedStatement.setInt(1, playlistId);
        preparedStatement.setInt(2, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    private static boolean isSongExists(int songId) throws SQLException {
        PreparedStatement preparedStatement = ConnectionManager.con.prepareStatement("SELECT * FROM song WHERE songId = ?");
        preparedStatement.setInt(1, songId);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public static boolean isSongInPlaylist(int playlistId, int songId) throws SQLException {
        PreparedStatement preparedStatement = ConnectionManager.con.prepareStatement("SELECT * FROM userplaylistsong WHERE playlistId = ? AND songId = ?");
        preparedStatement.setInt(1, playlistId);
        preparedStatement.setInt(2, songId);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

}
