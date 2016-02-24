package com.estrelsteel.engine1.online;

public class PendingPacket {
	private String msg;
	private String[] packetArgs;
	private boolean expires;
	private long arriveTime;
	private long delTime;
	
	public PendingPacket(String msg) {
		this.msg = msg;
		this.packetArgs = Packets.packetArgs(msg);
		this.expires = false;
		this.arriveTime = System.currentTimeMillis();
		this.delTime = 0l;
	}
	
	public PendingPacket(String msg, boolean expires, long delTime) {
		this.msg = msg;
		this.packetArgs = Packets.packetArgs(msg);
		this.expires = expires;
		this.arriveTime = System.currentTimeMillis();
		this.delTime = delTime;
	}
	
	public String getMessage() {
		return msg;
	}
	
	public String[] getPacketArgs() {
		return packetArgs;
	}
	
	public long getArriveTime() {
		return arriveTime;
	}
	
	public long getDelTime() {
		return delTime;
	}
	
	public boolean doesExpire() {
		return expires;
	}
	
	public boolean shouldDelete() {
		if(expires && System.currentTimeMillis() - delTime > arriveTime) {
			return true;
		}
		return false;
	}
	
	public void setMessage(String msg) {
		this.msg = msg;
		this.packetArgs = Packets.packetArgs(this.msg);
		return;
	}
	
	public void setExpires(boolean expires) {
		this.expires = expires;
		return;
	}
	
	public void setArriveTime() {
		setArriveTime(System.currentTimeMillis());
		return;
	}
	
	public void setArriveTime(long arriveTime) {
		this.arriveTime = arriveTime;
		return;
	}
	
	public void setDelTime(long delTime) {
		this.delTime = delTime;
		return;
	}
	
}
