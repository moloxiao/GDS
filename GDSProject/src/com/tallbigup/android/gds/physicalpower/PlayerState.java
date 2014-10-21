package com.tallbigup.android.gds.physicalpower;

public class PlayerState {
	private int currentPower;
	private long second;

	public PlayerState(int currentPower, long second) {
		super();
		this.currentPower = currentPower;
		this.second = second;
	}

	public int getCurrentPower() {
		return currentPower;
	}

	public void setCurrentPower(int currentPower) {
		this.currentPower = currentPower;
	}

	public long getSecond() {
		return second;
	}

	public void setSecond(long second) {
		this.second = second;
	}

}
