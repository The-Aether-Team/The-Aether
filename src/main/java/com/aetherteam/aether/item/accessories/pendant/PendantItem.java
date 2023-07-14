package com.aetherteam.aether.item.accessories.pendant;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.item.accessories.AccessoryItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.function.Supplier;

public class PendantItem extends AccessoryItem {
    protected ResourceLocation PENDANT_LOCATION;
    protected final Supplier<? extends SoundEvent> equipSound;

    public PendantItem(String pendantLocation, Supplier<? extends SoundEvent> pendantSound, Properties properties) {
        super(properties);
        this.setRenderTexture(Aether.MODID, pendantLocation);
        this.equipSound = pendantSound;
    }

    public PendantItem(ResourceLocation pendantLocation, Supplier<? extends SoundEvent> pendantSound, Properties properties) {
        super(properties);
        this.setRenderTexture(pendantLocation.getNamespace(), pendantLocation.getPath());
        this.equipSound = pendantSound;
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(this.equipSound.get(), 1.0f, 1.0f);
    }

    public void setRenderTexture(String modId, String registryName) {
        this.PENDANT_LOCATION = new ResourceLocation(modId, "textures/models/accessory/pendant/" + registryName + "_accessory.png");
    }

    public ResourceLocation getPendantTexture() {
        return this.PENDANT_LOCATION;
    }
}
