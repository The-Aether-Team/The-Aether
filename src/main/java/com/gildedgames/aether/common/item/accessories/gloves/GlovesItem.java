package com.gildedgames.aether.common.item.accessories.gloves;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.item.accessories.AccessoryItem;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import java.util.UUID;
import java.util.function.Supplier;

public class GlovesItem extends AccessoryItem
{
    protected double damage;
    protected ResourceLocation GLOVES_TEXTURE;
    protected ResourceLocation GLOVES_SLIM_TEXTURE;
    protected final Supplier<SoundEvent> equipSound;

    public GlovesItem(double punchDamage, String glovesName, Supplier<SoundEvent> glovesSound, Properties properties) {
        super(properties);
        this.damage = punchDamage;
        this.setRenderTexture(Aether.MODID, glovesName);
        this.equipSound = glovesSound;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributes = HashMultimap.create();
        attributes.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "Gloves Damage Bonus", this.damage, AttributeModifier.Operation.ADDITION));
        return attributes;
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(this.equipSound.get(), 1.0f, 1.0f);
    }

    public void setRenderTexture(String modId, String registryName) {
        this.GLOVES_TEXTURE = new ResourceLocation(modId, "textures/models/accessory/gloves/" + registryName + "_accessory.png");
        this.GLOVES_SLIM_TEXTURE = new ResourceLocation(modId, "textures/models/accessory/gloves/" + registryName + "_slim_accessory.png");
    }

    public ResourceLocation getGlovesTexture() {
        return this.GLOVES_TEXTURE;
    }

    public ResourceLocation getGlovesSlimTexture() {
        return this.GLOVES_SLIM_TEXTURE;
    }
}