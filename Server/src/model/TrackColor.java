package model;

public enum TrackColor {
	Purple("purple"),
	White("white"),
	Blue("blue"),
	Yellow("yellow"),
	Orange("orange"),
	Black("black"),
	Red("red"),
	Green("green"),
	None("none");

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
