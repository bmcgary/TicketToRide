package model;

import java.util.Arrays;

public enum TrackColor {
	Purple("Purple"),
	White("White"),
	Blue("Blue"),
	Yellow("Yellow"),
	Orange("Orange"),
	Black("Black"),
	Red("Red"),
	Green("Green"),
	None("None");

	private String colorName;

	TrackColor(String colorName) {
		this.colorName = colorName;
	}

	@Override
	public String toString() {
		return colorName;
	}

	public static TrackColor getColor(String colorName) {
		return Arrays.stream(TrackColor.values()).filter(trackColor -> trackColor.colorName.equalsIgnoreCase(colorName)).findFirst().get();
	}

}
