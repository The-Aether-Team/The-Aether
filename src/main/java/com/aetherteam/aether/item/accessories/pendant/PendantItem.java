package com.aetherteam.aether.item.accessories.pendant;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.item.accessories.AccessoryItem;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

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
    public SoundInfo getEquipSound(ItemStack stack, SlotReference slot, LivingEntity entity) {
        return new SoundInfo(this.equipSound.get(), 1.0f, 1.0f);
    }

    public void setRenderTexture(String modId, String registryName) {
        this.PENDANT_LOCATION = new ResourceLocation(modId, "textures/models/accessory/pendant/" + registryName + "_accessory.png");
    }

    public ResourceLocation getPendantTexture() {
        return this.PENDANT_LOCATION;
    }
}
