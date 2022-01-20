package com.gildedgames.aether.core.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class EntityUtil
{
    public static void copyRotations(Entity entity, Player player) {
        entity.setYRot(player.getYRot() % 360.0F);
        entity.setXRot(player.getXRot() % 360.0F);
        entity.setYBodyRot(player.getYRot());
        entity.setYHeadRot(player.getYRot());
    }
}
