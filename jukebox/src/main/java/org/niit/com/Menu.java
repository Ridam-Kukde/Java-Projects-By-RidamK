package org.niit.com;

import java.util.*;

public class Menu {
    static  Scanner scanner=new Scanner(System.in);
    public static void MainMenu(int userid) {
        boolean repeatMenu = true;
        while(repeatMenu)
        {
        System.out.println(" ----------------------------------------------------------------------------------");
        System.out.println("---------------------->Enter 1:  Show Songs Menu    <------------------------------");
        System.out.println("---------------------->Enter 2: Show Playlist Menu  <------------------------------");
        System.out.println("---------------------->Enter 3:        Exit         <------------------------------");
        System.out.println(" ----------------------------------------------------------------------------------");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                showSongsMenu();
                break;
            case 2:
                playlistMenu(userid);
                break;
            case 3:
                System.out.println("Goodbye");
                System.out.println("Have a Musical day!");
                System.out.println(" ----------------------------------------------------------------------------------");
                System.exit(0);
                break;
        }
    }
    }

    public static void playlistMenu(int userId)
    {
        System.out.println(" ----------------------------------------------------------------------------------");
        System.out.println("------------------------->Enter 1: Show Playlists <--------------------------------");
        System.out.println("------------------------->Enter 2: Create a new Playlist<--------------------------");
        System.out.println("------------------------->Enter 3: Add to Playlist<--------------------------------");
        System.out.println("------------------------->Enter 4: Show songs from Playlist<-----------------------");
        System.out.println(" ----------------------------------------------------------------------------------");

        int choice= scanner.nextInt();
        switch(choice)
        {
            case 1:
                PlaylistImpl.showPlaylistsForUser(userId);
                break;
            case 2:
                PlaylistImpl.createPlaylist(userId);
                break;
            case 3:
                SongImpl song=new SongImpl();
                song.showSongs();
                System.out.print("Enter songId you want to add: ");
                int sid= scanner.nextInt();;
                PlaylistImpl.addToPlaylist(sid, userId);
                break;
            case 4:
                System.out.println("Select a playlistID from the following:");
                PlaylistImpl.showPlaylistsForUser(userId);
                int pId= scanner.nextInt();
                PlaylistImpl.displayPlaylistContents(pId, userId);
                break;
            default:
                System.out.println("Invalid Choice!");
                break;
        }

    }
    public static void showSongsMenu()
    {



            SongImpl songImpl = new SongImpl();
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("-------------------->  Enter 1: Show Songs List & Play  <--------------------------");
            System.out.println("-------------------->  Enter 2: Search song by SongID   <--------------------------");
            System.out.println("-------------------->  Enter 3: Search song by SongName <--------------------------");
            System.out.println("-------------------->  Enter 4: Add song to Database    <--------------------------");
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            int songMenuChoice = scanner.nextInt();
            switch (songMenuChoice) {
                case 1:
                    songImpl.showSongsAndPlay();
                    break;
                case 2:
                    try {
                        System.out.print("Enter song ID you are Looking for :");
                        int songIDToSearch = scanner.nextInt();
                        songImpl.searchSongById(songIDToSearch);
                    }catch(Exception exc)
                    {
                        System.out.println("Error in your picked choice: "+exc.getMessage());
                    }
                    break;
                case 3:
                    try{
                        Scanner ss=new Scanner(System.in);
                    System.out.print("Enter SongName you are Looking for :");
                    String songNameToSearch = ss.nextLine();
                      //  System.out.println(songNameToSearch);
                    //songNameToSearch.trim();
                    songImpl.searchSongByName(songNameToSearch);
                    }catch(Exception exc)
                    {
                        System.out.println("Error in your picked choice: "+exc.getMessage());
                    }
                    break;
                case 4:
                    try{
                    songImpl.showSongs();
                    Songs song = new Songs();
                    System.out.print("Enter a Song ID not present in the above list : ");
                    int songId = scanner.nextInt();
                    song.setSongId(songId);
                    System.out.print("Enter the Song Name: ");
                    String songName = scanner.next();
                    song.setSongName(songName);
                    System.out.println("Enter the genre of this Song :");
                    String songGenre = scanner.next();
                    song.setGenre(songGenre);
                    System.out.println("Enter the album this song is a part of: ");
                    String songAlbum = scanner.next();
                    song.setAlbum(songAlbum);
                    System.out.println("Enter the artist that this song belongs to: ");
                    String songArtist = scanner.next();
                    song.setArtist(songArtist);
                    System.out.println("Enter the length of this song(in milliseconds): ");
                    String songDuration = scanner.next();
                    song.setDuration(songDuration);
                    System.out.println("Enter the filepath of the song: ");
                    String filepath = scanner.next();
                    song.setFilePath(filepath);
                    SongImpl.addSongToDB(song);
                    }catch(Exception exc)
                    {
                    System.out.println("Error in your picked choice: "+exc.getMessage());
                    }
                    break;
                default:
                    System.out.println("Invalid choice :" + songMenuChoice);
            }

    }
}
