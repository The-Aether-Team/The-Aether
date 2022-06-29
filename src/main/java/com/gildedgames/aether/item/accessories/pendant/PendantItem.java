package com.gildedgames.aether.item.accessories.pendant;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.item.accessories.AccessoryItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class PendantItem extends AccessoryItem
{
    protected ResourceLocation PENDANT_LOCATION;
    protected final Supplier<SoundEvent> equipSound;

    public PendantItem(String pendantLocation, Supplier<SoundEvent> pendantSound, Properties properties) {
        super(properties);
        this.setRenderTexture(Aether.MODID, pendantLocation);
        this.equipSound = pendantSound;
    }

    @Nonnull
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
