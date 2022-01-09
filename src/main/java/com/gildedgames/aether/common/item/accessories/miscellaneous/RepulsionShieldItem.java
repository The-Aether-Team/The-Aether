package com.gildedgames.aether.common.item.accessories.miscellaneous;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.item.accessories.AccessoryItem;
import net.minecraft.resources.ResourceLocation;

public class RepulsionShieldItem extends AccessoryItem
{
    private static final ResourceLocation REPULSION_SHIELD = new ResourceLocation(Aether.MODID, "textures/models/accessory/repulsion_shield/repulsion_shield_accessory.png");
    private static final ResourceLocation REPULSION_SHIELD_INACTIVE = new ResourceLocation(Aether.MODID, "textures/models/accessory/repulsion_shield/repulsion_shield_inactive_accessory.png");
    private static final ResourceLocation REPULSION_SHIELD_SLIM = new ResourceLocation(Aether.MODID, "textures/models/accessory/repulsion_shield/repulsion_shield_slim_accessory.png");
    private static final ResourceLocation REPULSION_SHIELD_SLIM_INACTIVE = new ResourceLocation(Aether.MODID, "textures/models/accessory/repulsion_shield/repulsion_shield_slim_inactive_accessory.png");

    public RepulsionShieldItem(Properties properties) {
        super(properties);
    }

    public ResourceLocation getRepulsionShieldTexture() {
        return REPULSION_SHIELD;
    }

    public ResourceLocation getRepulsionShieldInactiveTexture() {
        return REPULSION_SHIELD_INACTIVE;
    }

    public ResourceLocation getRepulsionShieldSlimTexture() {
        return REPULSION_SHIELD_SLIM;
    }

    public ResourceLocation getRepulsionShieldSlimInactiveTexture() {
        return REPULSION_SHIELD_SLIM_INACTIVE;
    }
}
