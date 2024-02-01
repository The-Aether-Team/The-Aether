package com.aetherteam.aether.data;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.FreezingBlock;
import com.aetherteam.aether.blockentity.AltarBlockEntity;
import com.aetherteam.aether.blockentity.FreezerBlockEntity;
import com.aetherteam.aether.blockentity.IncubatorBlockEntity;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.storage.loot.Deserializers;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.eventbus.api.SubscribeEvent;
import net.neoforged.neoforge.fml.common.Mod;

import java.util.Map;


@Mod.EventBusSubscriber(modid = Aether.MODID)
public class ReloadListeners {
    @SubscribeEvent
    public static void reloadListenerSetup(AddReloadListenerEvent event) {
        event.addListener(new FuelReloadListener());
        event.addListener(new RecipeReloadListener());
    }

    public static class FuelReloadListener extends SimpleJsonResourceReloadListener {
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
    }

    public static class RecipeReloadListener extends SimpleJsonResourceReloadListener {
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
    }
}
