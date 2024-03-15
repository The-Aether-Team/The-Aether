package com.aetherteam.aether.item.accessories.cape;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.item.accessories.AccessoryItem;
import net.minecraft.resources.ResourceLocation;

public class CapeItem extends AccessoryItem {
    protected ResourceLocation CAPE_LOCATION;

    public CapeItem(String capeLocation, Properties properties) {
        this(new ResourceLocation(Aether.MODID, capeLocation), properties);
    }

    public CapeItem(ResourceLocation capeLocation, Properties properties) {
        super(AetherSoundEvents.ITEM_ACCESSORY_EQUIP_CAPE, properties);
        this.setRenderTexture(capeLocation.getNamespace(), capeLocation.getPath());
    }

    public void setRenderTexture(String modId, String registryName) {
        this.CAPE_LOCATION = new ResourceLocation(modId, "textures/models/accessory/capes/" + registryName + "_accessory.png");
    }

    public ResourceLocation getCapeTexture() {
        return this.CAPE_LOCATION;
    }
}
