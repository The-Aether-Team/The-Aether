package com.gildedgames.aether.core.capability.player;

public interface IAetherAbility {

	void onUpdate();
	
	boolean shouldExecute();
	
}
