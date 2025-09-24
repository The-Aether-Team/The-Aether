package com.aetherteam.aether.item.accessories.gloves;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.inventory.container.AccessoryContainer;
import com.aetherteam.aether.item.accessories.AccessoryItem;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorMaterial;

import java.util.HashMap;
import java.util.Map;

public class GlovesItem extends AccessoryItem {
    public static final ResourceLocation BASE_PUNCH_DAMAGE_ID = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "base_punch_damage");
    protected final ArmorMaterial material;
    protected final double damage;
    protected ResourceLocation GLOVES_TEXTURE;

    public GlovesItem(ArmorMaterial material, double punchDamage, String glovesName, Holder<SoundEvent> glovesSound, Properties properties) {
        this(material, punchDamage, ResourceLocation.fromNamespaceAndPath(Aether.MODID, glovesName), glovesSound, properties.enchantable(material.enchantmentValue()).repairable(material.repairIngredient()));
    }

    public GlovesItem(ArmorMaterial material, double punchDamage, ResourceLocation glovesName, Holder<SoundEvent> glovesSound, Properties properties) {
        super(glovesSound, properties, AccessoryContainer.SlotType.GLOVES);
        this.material = material;
        this.damage = punchDamage;
        this.setRenderTexture(glovesName.getNamespace(), glovesName.getPath());
    }

    @Override
    public Map<Holder<Attribute>, AttributeModifier> getAttributes(ItemStack stack) {
        Map<Holder<Attribute>, AttributeModifier> map = super.getAttributes(stack);
        map.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_PUNCH_DAMAGE_ID, this.damage, AttributeModifier.Operation.ADD_VALUE));
        return map;
    }

    public ArmorMaterial getMaterial() {
        return this.material;
    }

    public double getDamage() {
        return this.damage;
    }

    public void setRenderTexture(String modId, String registryName) {
        this.GLOVES_TEXTURE = ResourceLocation.fromNamespaceAndPath(modId, "textures/models/accessory/gloves/" + registryName + "_accessory.png");
    }

    public ResourceLocation getGlovesTexture() {
        return this.GLOVES_TEXTURE;
    }
}
