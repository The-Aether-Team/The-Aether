package com.aetherteam.aether.item.accessories.gloves;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.item.accessories.AccessoryItem;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;
import java.util.function.Supplier;

public class GlovesItem extends AccessoryItem {
    protected final ArmorMaterial material;
    protected final double damage;
    protected ResourceLocation GLOVES_TEXTURE;

    public GlovesItem(ArmorMaterial material, double punchDamage, String glovesName, Supplier<? extends SoundEvent> glovesSound, Properties properties) {
        this(material, punchDamage, new ResourceLocation(Aether.MODID, glovesName), glovesSound, properties);
    }

    public GlovesItem(ArmorMaterial material, double punchDamage, ResourceLocation glovesName, Supplier<? extends SoundEvent> glovesSound, Properties properties) {
        super(glovesSound, properties);
        this.material = material;
        this.damage = punchDamage;
        this.setRenderTexture(glovesName.getNamespace(), glovesName.getPath());
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributes = HashMultimap.create();
        attributes.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "Gloves Damage Bonus", this.damage, AttributeModifier.Operation.ADDITION));
        return attributes;
    }

    /**
     * Warning for "deprecation" is suppressed because the method is fine to override.
     */
    @SuppressWarnings("deprecation")
    @Override
    public int getEnchantmentValue() {
        return this.material.getEnchantmentValue();
    }

    @Override
    public boolean isValidRepairItem(ItemStack item, ItemStack material) {
        return this.material.getRepairIngredient().test(material) || super.isValidRepairItem(item, material);
    }

    public ArmorMaterial getMaterial() {
        return this.material;
    }

    public double getDamage() {
        return this.damage;
    }

    public void setRenderTexture(String modId, String registryName) {
        this.GLOVES_TEXTURE = new ResourceLocation(modId, "textures/models/accessory/gloves/" + registryName + "_accessory.png");
    }

    public ResourceLocation getGlovesTexture() {
        return this.GLOVES_TEXTURE;
    }

    @OnlyIn(Dist.CLIENT)
    public ImmutableTriple<Float, Float, Float> getColors(ItemStack stack) {
        float red = 1.0F;
        float green = 1.0F;
        float blue = 1.0F;
        if (stack.getItem() instanceof LeatherGlovesItem leatherGlovesItem) {
            int i = leatherGlovesItem.getColor(stack);
            red = (float) (i >> 16 & 255) / 255.0F;
            green = (float) (i >> 8 & 255) / 255.0F;
            blue = (float) (i & 255) / 255.0F;
        }
        return new ImmutableTriple<>(red, green, blue);
    }
}