package com.gildedgames.aether.client.registry;

import com.gildedgames.aether.Aether;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class AetherModelLayers
{
    public static final ModelLayerLocation AECHOR_PLANT = register("aechor_plant");
    public static final ModelLayerLocation AERBUNNY = register("aerbunny");
    public static final ModelLayerLocation AERWHALE = register("aerwhale");
    public static final ModelLayerLocation AERWHALE_CLASSIC = register("aerwhale", "variation");
    public static final ModelLayerLocation CLOUD_CRYSTAL = register("cloud_crystal");
    public static final ModelLayerLocation CLOUD_MINION = register("cloud_minion");
    public static final ModelLayerLocation COCKATRICE = register("cockatrice");
    public static final ModelLayerLocation FLYING_COW = register("flying_cow");
    public static final ModelLayerLocation FLYING_COW_WINGS = register("flying_cow", "wings");
    public static final ModelLayerLocation MIMIC = register("mimic");
    public static final ModelLayerLocation MOA = register("moa");
    public static final ModelLayerLocation MOA_SADDLE = register("moa", "saddle");
    public static final ModelLayerLocation PHYG = register("phyg");
    public static final ModelLayerLocation PHYG_WINGS = register("phyg", "wings");
    public static final ModelLayerLocation SENTRY = register("sentry");
    public static final ModelLayerLocation SHEEPUFF = register("sheepuff");
    public static final ModelLayerLocation SHEEPUFF_WOOL = register("sheepuff", "outer");
    public static final ModelLayerLocation SHEEPUFF_WOOL_PUFFEED = register("sheepuff", "outer_puffed");
    public static final ModelLayerLocation SLIDER = register("slider");
    public static final ModelLayerLocation SUN_SPIRIT = register("sun_spirit");
    public static final ModelLayerLocation SWET = register("swet");
    public static final ModelLayerLocation VALKYRIE = register("valkyrie");
    public static final ModelLayerLocation VALKYRIE_QUEEN = register("valkyrie_queen");
    public static final ModelLayerLocation ZEPHYR = register("zephyr");
    public static final ModelLayerLocation ZEPHYR_CLASSIC = register("zephyr", "variation");
    public static final ModelLayerLocation ZEPHYR_TRANSPARENCY = register("zephyr", "transparency");

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
