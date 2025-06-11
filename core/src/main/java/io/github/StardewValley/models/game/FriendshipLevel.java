package io.github.StardewValley.models.game;

public enum FriendshipLevel {
	LEVEL0(0),
	LEVEL1(1),
	LEVEL2(2),
	LEVEL3(3),
	LEVEL4(4);

	private final int level;

	FriendshipLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}


	private boolean canMarry() {
		return level == 3;
	}

	private boolean isMarried() {
		return level == 4;
	}

}
