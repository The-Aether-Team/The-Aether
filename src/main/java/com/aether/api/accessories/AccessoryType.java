package com.aether.api.accessories;

public enum AccessoryType {
	RING("Ring", 11, 13),
	PENDANT("Pendant", 16, 7),
	CAPE("Cape", 15, 5),
	SHIELD("Shield", 13, 0),
	GLOVES("Gloves", 10, 0),
	MISC("Miscellaneous", 10, 0);

	private final String displayName;
	private final int maxDamage, damageReduced;
	
	private AccessoryType(String displayName, int maxDamage, int damageReduced) {
		this.displayName = displayName;
		this.maxDamage = maxDamage;
		this.damageReduced = damageReduced;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public int getMaxDamage() {
		return maxDamage;
	}
	
	public int getDamageReduced() {
		return damageReduced;
	}
	
}
