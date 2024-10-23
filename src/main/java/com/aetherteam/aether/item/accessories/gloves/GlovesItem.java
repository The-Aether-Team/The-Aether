package com.aetherteam.aether.item.accessories.gloves;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.inventory.AetherAccessorySlots;
import com.aetherteam.aether.item.accessories.AccessoryItem;
import com.aetherteam.aether.item.accessories.SlotIdentifierHolder;
import io.wispforest.accessories.api.attributes.AccessoryAttributeBuilder;
import io.wispforest.accessories.api.slot.SlotReference;
import io.wispforest.accessories.api.slot.SlotTypeReference;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class GlovesItem extends AccessoryItem implements SlotIdentifierHolder {
    public static final ResourceLocation BASE_PUNCH_DAMAGE_ID = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "base_punch_damage");
    protected final Holder<ArmorMaterial> material;
    protected final double damage;
    protected ResourceLocation GLOVES_TEXTURE;

    public GlovesItem(Holder<ArmorMaterial> material, double punchDamage, String glovesName, Holder<SoundEvent> glovesSound, Properties properties) {
        this(material, punchDamage, ResourceLocation.fromNamespaceAndPath(Aether.MODID, glovesName), glovesSound, properties);
    }

    public GlovesItem(Holder<ArmorMaterial> material, double punchDamage, ResourceLocation glovesName, Holder<SoundEvent> glovesSound, Properties properties) {
        super(glovesSound, properties);
        this.material = material;
        this.damage = punchDamage;
        this.setRenderTexture(glovesName.getNamespace(), glovesName.getPath());
    }

    @Override
    public void getDynamicModifiers(ItemStack stack, SlotReference reference, AccessoryAttributeBuilder builder) {
        if (reference.slotName().equals(AetherAccessorySlots.GLOVES_SLOT_LOCATION.toString())) {
            builder.addStackable(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_PUNCH_DAMAGE_ID, this.damage, AttributeModifier.Operation.ADD_VALUE));
        }
    }

    /**
     * Warning for "deprecation" is suppressed because the method is fine to override.
     */
    @SuppressWarnings("deprecation")
    @Override
    public int getEnchantmentValue() {
        return this.material.value().enchantmentValue();
    }

    @Override
    public boolean isValidRepairItem(ItemStack item, ItemStack material) {
        return this.material.value().repairIngredient().get().test(material) || super.isValidRepairItem(item, material);
    }

    public Holder<ArmorMaterial> getMaterial() {
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

    /**
     * @return {@link GlovesItem}'s own identifier for its accessory slot,
     * using a static method as it is used in other conditions without access to an instance.
     */
    @Override
    public SlotTypeReference getIdentifier() {
        return getStaticIdentifier();
    }

    public static SlotTypeReference getStaticIdentifier() {
        return AetherConfig.COMMON.use_default_accessories_menu.get() ? new SlotTypeReference("hand") : AetherAccessorySlots.getGlovesSlotType();
    }
}
