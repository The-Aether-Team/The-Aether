package com.aetherteam.aether.data;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.FreezingBlock;
import com.aetherteam.aether.item.AetherItems;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

import java.util.Map;

public class ReloadListeners {
    /**
     * @see Aether#eventSetup(IEventBus)
     */
    public static void reloadListenerSetup(AddReloadListenerEvent event) {
        event.addListener(new RecipeReloadListener());
        event.addListener(new BannerReloadListener());
    }

    public static class RecipeReloadListener extends SimpleJsonResourceReloadListener {
        public static final Gson GSON_INSTANCE = new GsonBuilder().create();

        public RecipeReloadListener() {
            super(GSON_INSTANCE, Registries.elementsDirPath(Registries.RECIPE));
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

    public static class BannerReloadListener extends SimpleJsonResourceReloadListener {
        public static final Gson GSON_INSTANCE = new GsonBuilder().create();

        public BannerReloadListener() {
            super(GSON_INSTANCE, Registries.elementsDirPath(Registries.BANNER_PATTERN));
        }

        @Override
        protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
            AetherItems.SWET_BANNER = null;
        }
    }
}
