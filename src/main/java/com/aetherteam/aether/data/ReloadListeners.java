package com.aetherteam.aether.data;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.FreezingBlock;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.storage.loot.Deserializers;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;


@Mod.EventBusSubscriber(modid = Aether.MODID)
public class ReloadListeners {
    @SubscribeEvent
    public static void reloadListenerSetup(AddReloadListenerEvent event) {
        event.addListener(new RecipeReloadListener());
    }

    public static class RecipeReloadListener extends SimpleJsonResourceReloadListener {
        public static final Gson GSON_INSTANCE = Deserializers.createFunctionSerializer().create();

        public RecipeReloadListener() {
            super(GSON_INSTANCE, "recipes");
        }

        /**
         * Resets the block cache for {@link FreezingBlock} recipes.
         */
        @Override
        protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
            FreezingBlock.cachedBlocks.clear();
        }
    }
}
