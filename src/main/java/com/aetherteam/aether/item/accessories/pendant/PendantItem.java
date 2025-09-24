package com.aetherteam.aether.item.accessories.pendant;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.inventory.container.AccessoryContainer;
import com.aetherteam.aether.item.accessories.AccessoryItem;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class PendantItem extends AccessoryItem {
    protected ResourceLocation PENDANT_LOCATION;

    public PendantItem(String pendantLocation, Holder<SoundEvent> pendantSound, Properties properties) {
        this(ResourceLocation.fromNamespaceAndPath(Aether.MODID, pendantLocation), pendantSound, properties);
    }

    public PendantItem(ResourceLocation pendantLocation, Holder<SoundEvent> pendantSound, Properties properties) {
        super(pendantSound, properties, AccessoryContainer.SlotType.PENDANT);
        this.setRenderTexture(pendantLocation.getNamespace(), pendantLocation.getPath());
    }

    public void setRenderTexture(String modId, String registryName) {
        this.PENDANT_LOCATION = ResourceLocation.fromNamespaceAndPath(modId, "textures/models/accessory/pendant/" + registryName + "_accessory.png");
    }

    public ResourceLocation getPendantTexture() {
        return this.PENDANT_LOCATION;
    }
}
