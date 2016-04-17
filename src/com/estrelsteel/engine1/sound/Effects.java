package com.estrelsteel.engine1.sound;

import com.estrelsteel.engine1.Engine1;

public enum Effects {
	INVALID(-1, null),
	SELECT(0, new Sound(Engine1.filesPath + "/assets/res/sfx/button_click.wav")),
	PLACE(1, new Sound(Engine1.filesPath + "/assets/res/sfx/place.wav")),
	DEFEAT(2, new Sound(Engine1.filesPath + "/assets/res/sfx/defeat.wav")),
	PAINT(3, new Sound(Engine1.filesPath + "/assets/res/sfx/paint.wav")),
	VICTORY(4, new Sound(Engine1.filesPath + "/assets/res/sfx/victory.wav")),;

	private int id;
	private Sound sound;
	
	Effects(int id, Sound sound) {
		this.id = id;
		this.sound = sound;
	}
	
	public int getID() {
		return id;
	}
	
	public Sound getSound() {
		return sound;
	}
	
	public static Effects findByID(int id) {
		for(Effects sfx : values()) {
			if(sfx.getID() == id) {
				return sfx;
			}
		}
		return Effects.INVALID;
	}
	
	public static void updateSRC(String filesPath) {
		for(int i = 0; i < Effects.values().length; i++) {
			Effects.values()[i].setSRC(filesPath + Effects.values()[i].getSound().getSRC());
		}
	}
	
	private void setSRC(String src) {
		//sound.setSRC(src);
		sound = new Sound(src);
	}
}
