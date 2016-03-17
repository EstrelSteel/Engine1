package com.estrelsteel.engine1.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	private String src;
	private Clip clip;
	
	public Sound(String src) {
		this.src = src;
	}
	
	public String getSRC() {
		return src;
	}
	
	public Clip getClip() {
		return clip;
	}
	
	public synchronized void play() {
		if(clip == null || !clip.isRunning()) {
			try {
				File file = new File(src);
				clip = AudioSystem.getClip();
				AudioInputStream ais = AudioSystem.getAudioInputStream(file);
				clip.open(ais);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} 
			catch (LineUnavailableException e) {
				e.printStackTrace();
			}
			clip.flush();
			clip.start();
		}
	}
	
	public void setSRC(String src) {
		this.src = src;
	}
	
	public void setClip(Clip clip) {
		this.clip = clip;
	}
	
}
