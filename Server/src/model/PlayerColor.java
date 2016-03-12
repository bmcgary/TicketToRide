package model;

public enum PlayerColor {
	Blue("blue"),
	Red("red"),
	Green("green"),
	Yellow("yellow"),
	Black("black");

	private String colorName;

	PlayerColor(String colorName) {
		this.colorName = colorName;
	}

	@Override
	public String toString() {
		return colorName;
	}

	public static PlayerColor getColor(String colorName) {
		for (PlayerColor playerColor : PlayerColor.values()) {
			if (playerColor.colorName.equalsIgnoreCase(colorName)) {
				return playerColor;
			}
		}

		return null;
	}
}
