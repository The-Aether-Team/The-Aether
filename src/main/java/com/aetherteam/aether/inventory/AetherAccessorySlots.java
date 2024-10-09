package com.aetherteam.aether.inventory;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherTags;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.slot.SlotBasedPredicate;
import io.wispforest.accessories.api.slot.SlotTypeReference;
import io.wispforest.accessories.api.slot.UniqueSlotHandling;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class AetherAccessorySlots implements UniqueSlotHandling.RegistrationCallback {
    private static final ResourceLocation GLOVES_PREDICATE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "gloves_items");
    private static final ResourceLocation RING_PREDICATE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "ring_items");
    private static final ResourceLocation PENDANT_PREDICATE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "pendant_items");
    private static final ResourceLocation CAPE_PREDICATE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "cape_items");
    private static final ResourceLocation SHIELD_PREDICATE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "shield_items");
    private static final ResourceLocation ACCESSORY_PREDICATE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "accessory_items");

    public static final ResourceLocation GLOVES_SLOT_LOCATION = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "gloves_slot");
    public static final ResourceLocation RING_SLOT_LOCATION = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "ring_slot");
    public static final ResourceLocation PENDANT_SLOT_LOCATION = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "pendant_slot");
    public static final ResourceLocation CAPE_SLOT_LOCATION = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "cape_slot");
    public static final ResourceLocation SHIELD_SLOT_LOCATION = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "shield_slot");
    public static final ResourceLocation ACCESSORY_SLOT_LOCATION = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "accessory_slot");

    public static final AetherAccessorySlots INSTANCE = new AetherAccessorySlots();

    private static SlotTypeReference GLOVES_SLOT;
    private static SlotTypeReference RING_SLOT;
    private static SlotTypeReference PENDANT_SLOT;
    private static SlotTypeReference CAPE_SLOT;
    private static SlotTypeReference SHIELD_SLOT;
    private static SlotTypeReference ACCESSORY_SLOT;

    private AetherAccessorySlots() {
        AccessoriesAPI.registerPredicate(GLOVES_PREDICATE, SlotBasedPredicate.ofItem(item -> new ItemStack(item).is(AetherTags.Items.ACCESSORIES_GLOVES)));
        AccessoriesAPI.registerPredicate(RING_PREDICATE, SlotBasedPredicate.ofItem(item -> new ItemStack(item).is(AetherTags.Items.ACCESSORIES_RINGS)));
        AccessoriesAPI.registerPredicate(PENDANT_PREDICATE, SlotBasedPredicate.ofItem(item -> new ItemStack(item).is(AetherTags.Items.ACCESSORIES_PENDANTS)));
        AccessoriesAPI.registerPredicate(CAPE_PREDICATE, SlotBasedPredicate.ofItem(item -> new ItemStack(item).is(AetherTags.Items.ACCESSORIES_CAPES)));
        AccessoriesAPI.registerPredicate(SHIELD_PREDICATE, SlotBasedPredicate.ofItem(item -> new ItemStack(item).is(AetherTags.Items.ACCESSORIES_SHIELDS)));
        AccessoriesAPI.registerPredicate(ACCESSORY_PREDICATE, SlotBasedPredicate.ofItem(item -> new ItemStack(item).is(AetherTags.Items.ACCESSORIES_MISCELLANEOUS)));
    }

    @Override
    public void registerSlots(UniqueSlotHandling.UniqueSlotBuilderFactory factory) {
        GLOVES_SLOT = factory.create(GLOVES_SLOT_LOCATION, 1).slotPredicates(GLOVES_PREDICATE).validTypes(EntityType.PLAYER).allowEquipFromUse(true).build();
        RING_SLOT = factory.create(RING_SLOT_LOCATION, 2).slotPredicates(RING_PREDICATE).validTypes(EntityType.PLAYER).allowEquipFromUse(true).build();
        PENDANT_SLOT = factory.create(PENDANT_SLOT_LOCATION, 1).slotPredicates(PENDANT_PREDICATE).validTypes(EntityType.PLAYER).allowEquipFromUse(true).build();
        CAPE_SLOT = factory.create(CAPE_SLOT_LOCATION, 1).slotPredicates(CAPE_PREDICATE).validTypes(EntityType.PLAYER).allowEquipFromUse(true).build();
        SHIELD_SLOT = factory.create(SHIELD_SLOT_LOCATION, 1).slotPredicates(SHIELD_PREDICATE).validTypes(EntityType.PLAYER).allowEquipFromUse(true).build();
        ACCESSORY_SLOT = factory.create(ACCESSORY_SLOT_LOCATION, 2).slotPredicates(ACCESSORY_PREDICATE).validTypes(EntityType.PLAYER).allowEquipFromUse(true).build();
    }

    @Nullable
    public static SlotTypeReference getGlovesSlotType() {
        return GLOVES_SLOT;
    }

    @Nullable
    public static SlotTypeReference getRingSlotType() {
        return RING_SLOT;
    }

    @Nullable
    public static SlotTypeReference getPendantSlotType() {
        return PENDANT_SLOT;
    }

    @Nullable
    public static SlotTypeReference getCapeSlotType() {
        return CAPE_SLOT;
    }

    @Nullable
    public static SlotTypeReference getShieldSlotType() {
        return SHIELD_SLOT;
    }

    @Nullable
    public static SlotTypeReference getAccessorySlotType() {
        return ACCESSORY_SLOT;
    }
}
