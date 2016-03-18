package com.estrelsteel.engine1.sound;

import com.estrelsteel.engine1.Engine1;

public enum Effects {
	INVALID(-1, null),
	SELECT(0, new Sound(Engine1.filesPath + "/assets/res/sfx/select.wav")),
	SWORD_SWING_1(1, new Sound(Engine1.filesPath + "/assets/res/sfx/sword_swing_1.wav")),
	SWORD_SWING_2(2, new Sound(Engine1.filesPath + "/assets/res/sfx/sword_swing_2.wav")),
	AXE_SWING_1(3, new Sound(Engine1.filesPath + "/assets/res/sfx/axe_swing_1.wav")),
	AXE_SWING_2(4, new Sound(Engine1.filesPath + "/assets/res/sfx/axe_swing_2.wav")),
	SPEAR_JAB_1(5, new Sound(Engine1.filesPath + "/assets/res/sfx/spear_jab_1.wav")),
	SPEAR_JAB_2(6, new Sound(Engine1.filesPath + "/assets/res/sfx/spear_jab_2.wav")),
	BOW_SHOOT(7, new Sound(Engine1.filesPath + "/assets/res/sfx/bow_shoot.wav")),
	ALARM_TRAP_PLACE(8, new Sound(Engine1.filesPath + "/assets/res/sfx/alarm_trap_place.wav")),
	ALARM_TRAP_PLAY(9, new Sound(Engine1.filesPath + "/assets/res/sfx/alarm_trap_play.wav")),
	ALARM_TRAP_TRIGGER(10, new Sound(Engine1.filesPath + "/assets/res/sfx/alarm_trap_trigger.wav")),
	DAMAGE(11, new Sound(Engine1.filesPath + "/assets/res/sfx/damage.wav")),
	LEVER(12, new Sound(Engine1.filesPath + "/assets/res/sfx/lever.wav"));

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
