package com.aetherteam.aether.item.accessories.miscellaneous;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.inventory.AetherAccessorySlots;
import com.aetherteam.aether.item.accessories.AccessoryItem;
import com.aetherteam.aether.item.accessories.SlotIdentifierHolder;
import io.wispforest.accessories.api.slot.SlotTypeReference;
import net.minecraft.resources.ResourceLocation;

public class ShieldOfRepulsionItem extends AccessoryItem implements SlotIdentifierHolder {
    private static final ResourceLocation SHIELD_OF_REPULSION = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/models/accessory/shield_of_repulsion/shield_of_repulsion_accessory.png");
    private static final ResourceLocation SHIELD_OF_REPULSION_INACTIVE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/models/accessory/shield_of_repulsion/shield_of_repulsion_inactive_accessory.png");
    private static final ResourceLocation SHIELD_OF_REPULSION_SLIM = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/models/accessory/shield_of_repulsion/shield_of_repulsion_slim_accessory.png");
    private static final ResourceLocation SHIELD_OF_REPULSION_SLIM_INACTIVE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/models/accessory/shield_of_repulsion/shield_of_repulsion_slim_inactive_accessory.png");

    public ShieldOfRepulsionItem(Properties properties) {
        super(properties);
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

    /**
     * @return {@link ShieldOfRepulsionItem}'s own identifier for its accessory slot,
     * using a static method as it is used in other conditions without access to an instance.
     */
    @Override
    public SlotTypeReference getIdentifier() {
        return getStaticIdentifier();
    }

    public static SlotTypeReference getStaticIdentifier() {
        return AetherConfig.COMMON.use_default_accessories_menu.get() ? new SlotTypeReference("back") : AetherAccessorySlots.getShieldSlotType();
    }
}
