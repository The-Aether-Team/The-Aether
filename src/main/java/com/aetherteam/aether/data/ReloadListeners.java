package com.aetherteam.aether.data;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.FreezingBlock;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

import java.util.Map;


@Mod.EventBusSubscriber(modid = Aether.MODID)
public class ReloadListeners {
    @SubscribeEvent
    public static void reloadListenerSetup(AddReloadListenerEvent event) {
        event.addListener(new RecipeReloadListener());
    }

    public static class RecipeReloadListener extends SimpleJsonResourceReloadListener {
        public static final Gson GSON_INSTANCE = new GsonBuilder().create();

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
