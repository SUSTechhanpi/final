package view;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Music implements Runnable {

    private File audioFile;
    private AudioInputStream audioInputStream;
    private AudioFormat audioFormat;
    private SourceDataLine audioDataLine;
    private DataLine.Info info;
    private volatile boolean run = true;
    private FloatControl floatControl;
    private int status;
    private String audioLocation;

    public Music(int status) {
        this.status = status;
        switch(status) {
            case 0 -> audioLocation = "./images/bgm.wav";
            case 1 -> audioLocation = "./images/mouseclicked.wav";
            case 2 -> audioLocation = "./images/button.wav";
        }
    }

    @Override
    public void run() {
        prefetch();
        do {
            this.playMusic();
        } while(status == 0);
    }

    public void playMusic() {
        try {
            synchronized (this) {
                run = true;
            }
            audioInputStream = AudioSystem.getAudioInputStream(new File(audioLocation));
            int count;
            byte tempBuff[] = new byte[1024];

            while ((count = audioInputStream.read(tempBuff, 0, tempBuff.length)) != -1) {
                synchronized (this) {
                    while (!run) {
                        wait();
                    }
                }
                floatControl = (FloatControl) audioDataLine.getControl(FloatControl.Type.MASTER_GAIN);
                floatControl.setValue(-20f);
                audioDataLine.write(tempBuff, 0, count);

            }

        } catch (UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
//        try {
//            AudioInputStream ais = AudioSystem.getAudioInputStream(new File("project/images/bgm.wav"));
//            AudioFormat aif = ais.getFormat();
//            final SourceDataLine sdl;
//            DataLine.Info info = new DataLine.Info(SourceDataLine.class, aif);
//            sdl = (SourceDataLine) AudioSystem.getLine(info);
//            sdl.open(aif);
//            sdl.start();
//            FloatControl fc = (FloatControl) sdl.getControl(FloatControl.Type.MASTER_GAIN);
//            //value调节音量
//            double value = 0.5;
//            float dB = (float) (Math.log(value == 0.0 ? 0.0001 : value) / Math.log(10.0) * 20.0);
//            fc.setValue(dB);
//            int nByte = 0;
//            final int SIZE = 1024 * 64;
//            byte[] buffer = new byte[SIZE];
//            while (nByte != -1) {
//                nByte = ais.read(buffer, 0, SIZE);
//                sdl.write(buffer, 0, nByte);
//            }
//        } catch (Exception e) {
//            /* e.printStackTrace();*/
//        }
    }

    private void prefetch() {
        audioFile = new File(audioLocation);
        if (!audioFile.exists()) {
            System.err.println("File not found.");
            return;
        }

        audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(audioFile);

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        audioFormat = audioInputStream.getFormat();
        audioDataLine = null;
        info = new DataLine.Info(SourceDataLine.class, audioFormat, AudioSystem.NOT_SPECIFIED);
        try {
            audioDataLine = (SourceDataLine) AudioSystem.getLine(info);
            audioDataLine.open(audioFormat);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        audioDataLine.start();
    }

}

