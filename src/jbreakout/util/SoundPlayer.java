package jbreakout.util;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SoundPlayer {

	private static SoundPlayer instance = null;
	
	private String track;

	final ExecutorService service;
	
    final class MyTask implements Runnable {

        @Override
        public void run() {
            switch (track) {
				case "brick":{
					playBrickHitSound();
					break;
				}
				case "paddle":{
					if (Math.random() < 0.5) {
						playPaddleHitSound1();
					} else {
						playPaddleHitSound2();
					}
					break;
				}
				case "wall":{
					playWallHitSound();
					break;
				}

				default:
					break;
			}
        }
    };
	
	
	String filenameBrickHit = "audiofiles/brick_hit.wav";
	AudioClip clipBrickHit;
	
	String filenamePaddleHit = "audiofiles/paddle_hit.wav";
	AudioClip clipPaddleHit;
	
	
	String filenamePaddleHit2 = "audiofiles/paddle_hit2.wav";
	AudioClip clipPaddleHit2;	
	
	String filenameWallHit = "audiofiles/wall_hit.wav";
	AudioClip clipWallHit;
	
    
    private SoundPlayer() {
    	
    	service = Executors.newCachedThreadPool();
    	
    	try {
    		clipBrickHit = Applet.newAudioClip(new File(filenameBrickHit).toURI().toURL());
    		clipPaddleHit = Applet.newAudioClip(new File(filenamePaddleHit).toURI().toURL());
    		clipPaddleHit2 = Applet.newAudioClip(new File(filenamePaddleHit2).toURI().toURL());
    		clipWallHit = Applet.newAudioClip(new File(filenameWallHit).toURI().toURL());
    	} catch (MalformedURLException e) {
    		e.printStackTrace();
    	}
    	
    }
 
    public void playBrickHitSound() {
    	clipBrickHit.play();
    }
    
    public void playPaddleHitSound1() {
    	clipPaddleHit.play();
    }
    
    public void playPaddleHitSound2() {
    	clipPaddleHit2.play();
    }
    
    public void playWallHitSound() {
    	clipWallHit.play();
    }
    
    public String getTrack() {
		return track;
	}

	public void setTrack(String track) {
		this.track = track;
	}
	
	public void setAndPlayTrack(String track) {
		setTrack(track);
		service.submit(new MyTask());
	}
    
    public static SoundPlayer getInstance() {
    	if(instance == null) {
    		instance = new SoundPlayer();
    	}
    	
    	return instance;
    }    
}