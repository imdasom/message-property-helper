package com.konai.search.vo;

public enum ResultClass {
	TotalEqual(1),
	TotalSimilar(2),
	PartialEquals(3),
	PartialSimilar(5),
	Unknown(0);

	private int level;

	ResultClass(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public static ResultClass getResultClassByLevel(int level) {
		switch (level) {
			case 1 : return TotalEqual;
			case 2 : return TotalSimilar;
			case 3 : return PartialEquals;
			case 4 : return PartialSimilar;
			default : return Unknown;
		}
	}
}
