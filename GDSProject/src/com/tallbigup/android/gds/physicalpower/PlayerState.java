package com.tallbigup.android.gds.physicalpower;

public class PlayerState {
	private int currentPower;
	private int maxPower;
	private long second;

	public PlayerState(int currentPower, int maxPower, long second) {
		super();
		this.currentPower = currentPower;
		this.maxPower = maxPower;
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

	public int getMaxPower() {
		return maxPower;
	}

	public void setMaxPower(int maxPower) {
		this.maxPower = maxPower;
	}

}
