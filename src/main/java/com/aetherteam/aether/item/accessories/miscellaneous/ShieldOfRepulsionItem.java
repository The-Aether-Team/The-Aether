package com.aetherteam.aether.item.accessories.miscellaneous;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.inventory.container.AccessoryContainer;
import com.aetherteam.aether.item.accessories.AccessoryItem;
import net.minecraft.resources.ResourceLocation;

public class ShieldOfRepulsionItem extends AccessoryItem {
    private static final ResourceLocation SHIELD_OF_REPULSION = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/models/accessory/shield_of_repulsion/shield_of_repulsion_accessory.png");
    private static final ResourceLocation SHIELD_OF_REPULSION_INACTIVE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/models/accessory/shield_of_repulsion/shield_of_repulsion_inactive_accessory.png");
    private static final ResourceLocation SHIELD_OF_REPULSION_SLIM = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/models/accessory/shield_of_repulsion/shield_of_repulsion_slim_accessory.png");
    private static final ResourceLocation SHIELD_OF_REPULSION_SLIM_INACTIVE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/models/accessory/shield_of_repulsion/shield_of_repulsion_slim_inactive_accessory.png");

    public ShieldOfRepulsionItem(Properties properties) {
        super(properties, AccessoryContainer.SlotType.SHIELD);
    }

    public ResourceLocation getShieldOfRepulsionTexture() {
        return SHIELD_OF_REPULSION;
    }

    public ResourceLocation getShieldOfRepulsionInactiveTexture() {
        return SHIELD_OF_REPULSION_INACTIVE;
    }

    public ResourceLocation getShieldOfRepulsionSlimTexture() {
        return SHIELD_OF_REPULSION_SLIM;
    }

    public ResourceLocation getShieldOfRepulsionSlimInactiveTexture() {
        return SHIELD_OF_REPULSION_SLIM_INACTIVE;
    }
}
