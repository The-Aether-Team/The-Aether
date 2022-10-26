package com.gildedgames.aether.item.accessories.cape;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.AetherSoundEvents;
import com.gildedgames.aether.item.accessories.AccessoryItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class CapeItem extends AccessoryItem {
    protected ResourceLocation CAPE_LOCATION;

    public CapeItem(String capeLocation, Properties properties) {
        super(properties);
        this.setRenderTexture(Aether.MODID, capeLocation);
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(AetherSoundEvents.ITEM_ACCESSORY_EQUIP_CAPE.get(), 1.0F, 1.0F);
    }

    public void setRenderTexture(String modId, String registryName) {
        this.CAPE_LOCATION = new ResourceLocation(modId, "textures/models/accessory/capes/" + registryName + "_accessory.png");
    }

    public ResourceLocation getCapeTexture() {
        return this.CAPE_LOCATION;
    }
}
