package com.viscum.device.led.minwang;

import java.util.Arrays;
import java.util.List;

/**
 * 民望led设备配置类
 *
 * @author viscum
 */
public class MinWangLedConfig {
	private String ip;

	private int port;

	private PlayOptions playOptions;

	/**
	 * 诱导屏显示位数
	 */
	private int ledBlock;

	/**
	 * 入口led显示text
	 */
	private String ledText;

	/**
	 * 是否显示剩余车位数
	 */
	private boolean remainingPark;

	private List<ShowOptions> showOptions;

	private boolean singleColor = false;

	// 诱导屏区域list
	private List<String> regionNoList;

	public static class ShowOptions {
		private int speed;

		private int color;

		public ShowOptions() {
		}

		public ShowOptions(int color, int speed) {
			this.color = color;
			this.speed = speed;
		}

		public ShowOptions(int color) {
			this.color = color;
		}

		public int getSpeed() {
			return speed;
		}

		public void setSpeed(int speed) {
			this.speed = speed;
		}

		public int getColor() {
			return color;
		}

		public void setColor(int color) {
			this.color = color;
		}

		@Override
		public String toString() {
			return "ShowOptions{" +
					"speed=" + speed +
					", color=" + color +
					'}';
		}
	}

	public static class PlayOptions {
		private int volume;

		private int speed;

		private int intonation;

		private int soundMode;

		public PlayOptions() {
		}

		public PlayOptions(int volume, int speed, int intonation, int soundMode) {
			this.volume = volume;
			this.speed = speed;
			this.intonation = intonation;
			this.soundMode = soundMode;
		}

		public int getVolume() {
			return volume;
		}

		public void setVolume(int volume) {
			this.volume = volume;
		}

		public int getSpeed() {
			return speed;
		}

		public void setSpeed(int speed) {
			this.speed = speed;
		}

		public int getIntonation() {
			return intonation;
		}

		public void setIntonation(int intonation) {
			this.intonation = intonation;
		}

		public int getSoundMode() {
			return soundMode;
		}

		public void setSoundMode(int soundMode) {
			this.soundMode = soundMode;
		}

		@Override
		public String toString() {
			return "PlayOptions{" +
					"volume=" + volume +
					", speed=" + speed +
					", intonation=" + intonation +
					", soundMode=" + soundMode +
					'}';
		}
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public PlayOptions getPlayOptions() {
		return playOptions;
	}

	public void setPlayOptions(PlayOptions playOptions) {
		this.playOptions = playOptions;
	}

	public List<ShowOptions> getShowOptions() {
		return showOptions;
	}

	public void setShowOptions(List<ShowOptions> showOptions) {
		this.showOptions = showOptions;
	}

	public boolean isSingleColor() {
		return singleColor;
	}

	public void setSingleColor(boolean singleColor) {
		this.singleColor = singleColor;
	}

	public List<String> getRegionNoList() {
		return regionNoList;
	}

	public void setRegionNoList(List<String> regionNoList) {
		this.regionNoList = regionNoList;
	}

	public String getLedText() {
		return ledText;
	}

	public void setLedText(String ledText) {
		this.ledText = ledText;
	}

	public boolean isRemainingPark() {
		return remainingPark;

	}

	public void setRemainingPark(boolean remainingPark) {
		this.remainingPark = remainingPark;
	}

	@Override
	public String toString() {
		return "MinWangParkDeviceConfig{" +
				"ip='" + ip + '\'' +
				", port=" + port +
				", playOptions=" + playOptions +
				", ledBlock=" + ledBlock +
				", ledText='" + ledText + '\'' +
				", remainingPark=" + remainingPark +
				", showOptions=" + showOptions +
				", singleColor=" + singleColor +
				", regionNoList=" + regionNoList +
				'}';
	}

}
