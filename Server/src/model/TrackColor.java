package model;

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
		for (TrackColor trackColor : TrackColor.values()) {
			if (trackColor.colorName.equalsIgnoreCase(colorName)) {
				return trackColor;
			}
		}

		return null;
	}

}
