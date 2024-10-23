package com.aetherteam.aether.item.accessories.cape;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.inventory.AetherAccessorySlots;
import com.aetherteam.aether.item.accessories.AccessoryItem;
import com.aetherteam.aether.item.accessories.SlotIdentifierHolder;
import io.wispforest.accessories.api.slot.SlotTypeReference;
import net.minecraft.resources.ResourceLocation;

public class CapeItem extends AccessoryItem implements SlotIdentifierHolder {
    protected ResourceLocation CAPE_LOCATION;

    public CapeItem(String capeLocation, Properties properties) {
        this(ResourceLocation.fromNamespaceAndPath(Aether.MODID, capeLocation), properties);
    }

    public CapeItem(ResourceLocation capeLocation, Properties properties) {
        super(AetherSoundEvents.ITEM_ACCESSORY_EQUIP_CAPE, properties);
        this.setRenderTexture(capeLocation.getNamespace(), capeLocation.getPath());
    }

    public void setRenderTexture(String modId, String registryName) {
        this.CAPE_LOCATION = ResourceLocation.fromNamespaceAndPath(modId, "textures/models/accessory/capes/" + registryName + "_accessory.png");
    }

    public ResourceLocation getCapeTexture() {
        return this.CAPE_LOCATION;
    }


    /**
     * @return {@link CapeItem}'s own identifier for its accessory slot,
     * using a static method as it is used in other conditions without access to an instance.
     */
    @Override
    public SlotTypeReference getIdentifier() {
        return getStaticIdentifier();
    }

    public static SlotTypeReference getStaticIdentifier() {
        return AetherConfig.COMMON.use_default_accessories_menu.get() ? new SlotTypeReference("cape") : AetherAccessorySlots.getCapeSlotType();
    }
}
