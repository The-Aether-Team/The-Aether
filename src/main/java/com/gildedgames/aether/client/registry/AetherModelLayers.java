package com.gildedgames.aether.client.registry;

import com.gildedgames.aether.Aether;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class AetherModelLayers {
    public static final ModelLayerLocation SKYROOT_BED_FOOT = register("skyroot_bed_foot");
    public static final ModelLayerLocation SKYROOT_BED_HEAD = register("skyroot_bed_head");
    public static final ModelLayerLocation CHEST_MIMIC = register("chest_mimic");

    public static final ModelLayerLocation PHYG = register("phyg");
    public static final ModelLayerLocation PHYG_WINGS = register("phyg", "wings");
    public static final ModelLayerLocation PHYG_SADDLE = register("phyg", "saddle");
    public static final ModelLayerLocation PHYG_HALO = register("phyg", "halo");
    public static final ModelLayerLocation FLYING_COW = register("flying_cow");
    public static final ModelLayerLocation FLYING_COW_WINGS = register("flying_cow", "wings");
    public static final ModelLayerLocation FLYING_COW_SADDLE = register("flying_cow", "saddle");
    public static final ModelLayerLocation SHEEPUFF = register("sheepuff");
    public static final ModelLayerLocation SHEEPUFF_WOOL = register("sheepuff", "outer");
    public static final ModelLayerLocation SHEEPUFF_WOOL_PUFFED = register("sheepuff", "outer_puffed");
    public static final ModelLayerLocation AERBUNNY = register("aerbunny");
    public static final ModelLayerLocation MOA = register("moa");
    public static final ModelLayerLocation MOA_SADDLE = register("moa", "saddle");
    public static final ModelLayerLocation AERWHALE = register("aerwhale");
    public static final ModelLayerLocation AERWHALE_CLASSIC = register("aerwhale", "classic");

    public static final ModelLayerLocation SWET = register("swet");
    public static final ModelLayerLocation SWET_OUTER = register("swet", "outer");
    public static final ModelLayerLocation AECHOR_PLANT = register("aechor_plant");
    public static final ModelLayerLocation COCKATRICE = register("cockatrice");
    public static final ModelLayerLocation ZEPHYR = register("zephyr");
    public static final ModelLayerLocation ZEPHYR_TRANSPARENCY = register("zephyr", "transparency");
    public static final ModelLayerLocation ZEPHYR_CLASSIC = register("zephyr", "classic");

    public static final ModelLayerLocation MIMIC = register("mimic");
    public static final ModelLayerLocation SENTRY = register("sentry");
    public static final ModelLayerLocation VALKYRIE = register("valkyrie");
    public static final ModelLayerLocation VALKYRIE_WINGS = register("valkyrie", "wings");
    public static final ModelLayerLocation FIRE_MINION = register("fire_minion");

    public static final ModelLayerLocation SLIDER = register("slider");
    public static final ModelLayerLocation VALKYRIE_QUEEN = register("valkyrie_queen");
    public static final ModelLayerLocation SUN_SPIRIT = register("sun_spirit");

    public static final ModelLayerLocation CLOUD_MINION = register("cloud_minion");

    public static final ModelLayerLocation ICE_CRYSTAL = register("ice_crystal");

    public static final ModelLayerLocation VALKYRIE_ARMOR_WINGS = register("valkyrie_armor_wings");

    public static final ModelLayerLocation PENDANT = register("pendant");
    public static final ModelLayerLocation GLOVES = register("gloves");
    public static final ModelLayerLocation GLOVES_SLIM = register("gloves_slim");
    public static final ModelLayerLocation GLOVES_ARM = register("gloves_arm");
    public static final ModelLayerLocation GLOVES_ARM_SLIM = register("gloves_arm_slim");
    public static final ModelLayerLocation GLOVES_SLEEVE = register("gloves_sleeve");
    public static final ModelLayerLocation GLOVES_SLEEVE_SLIM = register("gloves_sleeve_slim");
    public static final ModelLayerLocation CAPE = register("cape");
    public static final ModelLayerLocation REPULSION_SHIELD = register("repulsion_shield");
    public static final ModelLayerLocation REPULSION_SHIELD_SLIM = register("repulsion_shield_slim");
    public static final ModelLayerLocation REPULSION_SHIELD_ARM = register("repulsion_shield_arm");
    public static final ModelLayerLocation REPULSION_SHIELD_ARM_SLIM = register("repulsion_shield_arm_slim");

    public static final ModelLayerLocation PLAYER_HALO = register("player_halo");

    private static ModelLayerLocation register(String name) {
        return register(name, "main");
    }

    private static ModelLayerLocation register(String name, String type) {
        return register(new ResourceLocation(Aether.MODID, name), type);
    }

    private static ModelLayerLocation register(ResourceLocation identifier, String type) {
        return new ModelLayerLocation(identifier, type);
    }
}
