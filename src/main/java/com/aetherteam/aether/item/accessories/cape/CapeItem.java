package com.aetherteam.aether.item.accessories.cape;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.item.accessories.AccessoryItem;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class CapeItem extends AccessoryItem {
    protected ResourceLocation CAPE_LOCATION;

    public CapeItem(String capeLocation, Properties properties) {
        super(properties);
        this.setRenderTexture(Aether.MODID, capeLocation);
    }

    public CapeItem(ResourceLocation capeLocation, Properties properties) {
        super(properties);
        this.setRenderTexture(capeLocation.getNamespace(), capeLocation.getPath());
    }

    @Override
    public SoundInfo getEquipSound(ItemStack stack, SlotReference slot, LivingEntity entity) {
        return new SoundInfo(AetherSoundEvents.ITEM_ACCESSORY_EQUIP_CAPE.get(), 1.0F, 1.0F);
    }

    public void setRenderTexture(String modId, String registryName) {
        this.CAPE_LOCATION = new ResourceLocation(modId, "textures/models/accessory/capes/" + registryName + "_accessory.png");
    }

    public ResourceLocation getCapeTexture() {
        return this.CAPE_LOCATION;
    }
}
