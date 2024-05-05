package com.aetherteam.aether.item.accessories.cape;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.item.accessories.AccessoryItem;
import com.aetherteam.aether.item.accessories.SlotIdentifierHolder;
import net.minecraft.resources.ResourceLocation;

public class CapeItem extends AccessoryItem implements SlotIdentifierHolder {
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

    /**
     * @return {@link CapeItem}'s own identifier for its accessory slot,
     * using a static method as it is used in other conditions without access to an instance.
     */
    @Override
    public String getIdentifier() {
        return getIdentifierStatic();
    }

    /**
     * @return {@link CapeItem}'s own identifier for its accessory slot.
     */
    public static String getIdentifierStatic() {
        return AetherConfig.COMMON.use_curios_menu.get() ? "back" : "aether_cape";
    }
}
