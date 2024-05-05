package com.aetherteam.aether.item.accessories.pendant;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.item.accessories.AccessoryItem;
import com.aetherteam.aether.item.accessories.SlotIdentifierHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class PendantItem extends AccessoryItem implements SlotIdentifierHolder {
    protected ResourceLocation PENDANT_LOCATION;

    public PendantItem(String pendantLocation, Supplier<? extends SoundEvent> pendantSound, Properties properties) {
        this(new ResourceLocation(Aether.MODID, pendantLocation), pendantSound, properties);
    }

    public PendantItem(ResourceLocation pendantLocation, Supplier<? extends SoundEvent> pendantSound, Properties properties) {
        super(pendantSound, properties);
        this.setRenderTexture(pendantLocation.getNamespace(), pendantLocation.getPath());
    }

    public void setRenderTexture(String modId, String registryName) {
        this.PENDANT_LOCATION = new ResourceLocation(modId, "textures/models/accessory/pendant/" + registryName + "_accessory.png");
    }

    public ResourceLocation getPendantTexture() {
        return this.PENDANT_LOCATION;
    }

    /**
     * @return {@link PendantItem}'s own identifier for its accessory slot,
     * using a static method as it is used in other conditions without access to an instance.
     */
    @Override
    public String getIdentifier() {
        return getIdentifierStatic();
    }

    /**
     * @return {@link PendantItem}'s own identifier for its accessory slot.
     */
    public static String getIdentifierStatic() {
        return AetherConfig.COMMON.use_curios_menu.get() ? "necklace" : "aether_pendant";
    }
}
