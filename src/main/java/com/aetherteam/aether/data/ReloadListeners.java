package com.aetherteam.aether.data;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.FreezingBlock;
import com.aetherteam.aether.blockentity.AltarBlockEntity;
import com.aetherteam.aether.blockentity.FreezerBlockEntity;
import com.aetherteam.aether.blockentity.IncubatorBlockEntity;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.storage.loot.Deserializers;

import java.util.Map;

public class ReloadListeners {
    public static final ResourceLocation FUEL = new ResourceLocation(Aether.MODID, "fuel");
    public static final ResourceLocation RECIPE = new ResourceLocation(Aether.MODID, "recipe");

    public static void reloadListenerSetup() {
        ResourceManagerHelper helper = ResourceManagerHelper.get(PackType.CLIENT_RESOURCES);
        helper.registerReloadListener(new FuelReloadListener());
        helper.registerReloadListener(new RecipeReloadListener());
    }

    public static class FuelReloadListener extends SimpleJsonResourceReloadListener implements IdentifiableResourceReloadListener {
        public static final Gson GSON_INSTANCE = Deserializers.createFunctionSerializer().create();

        public FuelReloadListener() {
            super(GSON_INSTANCE, "fuels");
        }

        /**
         * Resets the fuels for furnace-type crafting stations.
         */
        @Override
        protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
            AltarBlockEntity.getEnchantingMap().clear();
            FreezerBlockEntity.getFreezingMap().clear();
            IncubatorBlockEntity.getIncubatingMap().clear();
            AetherBlocks.registerFuels();
        }

        @Override
        public ResourceLocation getFabricId() {
            return FUEL;
        }
    }

    public static class RecipeReloadListener extends SimpleJsonResourceReloadListener implements IdentifiableResourceReloadListener {
        public static final Gson GSON_INSTANCE = Deserializers.createFunctionSerializer().create();

        public RecipeReloadListener() {
            super(GSON_INSTANCE, "recipes");
        }

        /**
         * Resets the block caches for {@link FreezingBlock} recipes.
         */
        @Override
        protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
            FreezingBlock.cachedBlocks.clear();
            FreezingBlock.cachedResults.clear();
        }

        @Override
        public ResourceLocation getFabricId() {
            return RECIPE;
        }
    }
}
