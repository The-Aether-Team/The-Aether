package com.gildedgames.aether.block.dungeon;

/**
 * Subclass of TreasureRoomBlock that's used for doors that are opened and closed by the boss.
 * Use this for blocks that are operated on even when the boss wins.
 */
public class InvisibleBlock extends TreasureRoomBlock {
    public InvisibleBlock(Properties properties) {
        super(properties);
    }
}
