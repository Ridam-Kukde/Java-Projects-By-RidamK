package org.niit.com;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
/*
public class MusicPlayer {
    Scanner sc=new Scanner(System.in);
    static String status;
    Long currentFrame;
    static Clip clip;
    AudioInputStream audioInputStream;
    static String filePath;
    public MusicPlayer()
            throws UnsupportedAudioFileException,
            IOException, LineUnavailableException
    {
        // Here creating AudioInputStream object in default constructor as the following code runs as soon as the class is invoked
        audioInputStream =
                AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

        // now created clip reference
        clip = AudioSystem.getClip();

        // and then finally opening audioInputStream to the clip
        clip.open(audioInputStream);

        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public static void playSong(Songs song) throws UnsupportedAudioFileException, LineUnavailableException, IOException
    {
        filePath=song.getFilePath();
        MusicPlayer musicPlayer=new MusicPlayer();
        musicPlayer.play();
        System.out.println(" ----------------------------------------------------------------------------------");
        System.out.println("|                          Enter 0 to Exit                                         |");
        System.out.println(" ----------------------------------------------------------------------------------");
        int choice=musicPlayer.sc.nextInt();

            switch (choice)
            {
                case 1:
                    musicPlayer.stop();
                    break;
                case 0:
                    musicPlayer.stop();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Wrong choice entered ");
                    break;
            }


    }

    public void play()
    {
        //to start the music/clip
        clip.start();

        status = "play";
    }


    public void stop() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException
    {
        currentFrame = 0L;
        clip.stop();
        clip.close();
    }
    public void resetAudioStream() throws UnsupportedAudioFileException, IOException,
            LineUnavailableException
    {
        audioInputStream = AudioSystem.getAudioInputStream(
                new File(filePath).getAbsoluteFile());
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}*/

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MusicPlayer {
    Scanner sc = new Scanner(System.in);
    static String status;
    static Long currentFrame;
    static Clip clip;
    static AudioInputStream audioInputStream;
    static String filePath;

    public MusicPlayer() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void playSong(Songs song) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        filePath = song.getFilePath();
        MusicPlayer musicPlayer = new MusicPlayer();
        musicPlayer.play();
        System.out.println(" ----------------------------------------------------------------------------------");
        System.out.println("|                          Enter 0 to Exit                                         |");
        System.out.println("|                          Enter 1 to Pause                                        |");
        System.out.println("|                          Enter 2 to Resume                                       |");
        System.out.println(" ----------------------------------------------------------------------------------");
        int choice;
        do {
            choice = musicPlayer.sc.nextInt();
            switch (choice) {
                case 1:
                    musicPlayer.pause();
                    break;
                case 2:
                    musicPlayer.resume();
                    break;
                case 0:
                    musicPlayer.stop();
                    break;
                default:
                    System.out.println("Wrong choice entered ");
                    break;
            }
        } while (choice != 0);
    }

    public void play() {
        clip.start();
        status = "play";
    }

    public void pause() {
        if (status.equals("paused")) {
            System.out.println("audio is already paused");
            return;
        }
        currentFrame = clip.getMicrosecondPosition();
        clip.stop();
        status = "paused";
    }

    public void resume() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (status.equals("play")) {
            System.out.println("Audio is already being played");
            return;
        }
        clip.close();
        resetAudioStream();
        clip.setMicrosecondPosition(currentFrame);
        clip.start();
        status = "play";
    }

    public void stop() {
        currentFrame = 0L;
        clip.stop();
        clip.close();
        status = "stop";
    }

    public void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}

