package com.aetherteam.aether;

import com.aetherteam.aether.data.generators.*;
import com.aetherteam.aether.data.generators.tags.*;
import com.aetherteam.aether.data.resources.AetherMobCategory;
import com.google.common.reflect.Reflection;
import net.minecraft.SharedConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class AetherData {
    public static void dataSetup(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        PackOutput packOutput = generator.getPackOutput();

        Reflection.initialize(AetherMobCategory.class);

        // Client Data
        generator.addProvider(event.includeClient(), new AetherBlockStateData(packOutput, fileHelper));
        generator.addProvider(event.includeClient(), new AetherItemModelData(packOutput, fileHelper));
        generator.addProvider(event.includeClient(), new AetherLanguageData(packOutput));
        generator.addProvider(event.includeClient(), new AetherSoundData(packOutput, fileHelper));

        // Server Data
        generator.addProvider(event.includeServer(), new AetherRegistrySets(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new AetherRecipeData(packOutput));
        generator.addProvider(event.includeServer(), AetherLootTableData.create(packOutput));
        generator.addProvider(event.includeServer(), new AetherLootModifierData(packOutput));
        generator.addProvider(event.includeServer(), new AetherAdvancementData(packOutput, lookupProvider, fileHelper));
        AetherBlockTagData blockTags = new AetherBlockTagData(packOutput, lookupProvider, fileHelper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new AetherItemTagData(packOutput, lookupProvider, blockTags.contentsGetter(), fileHelper));
        generator.addProvider(event.includeServer(), new AetherEntityTagData(packOutput, lookupProvider, fileHelper));
        generator.addProvider(event.includeServer(), new AetherFluidTagData(packOutput, lookupProvider, fileHelper));
        generator.addProvider(event.includeServer(), new AetherBiomeTagData(packOutput, lookupProvider, fileHelper));
        generator.addProvider(event.includeServer(), new AetherStructureTagData(packOutput, lookupProvider, fileHelper));
        generator.addProvider(event.includeServer(), new AetherDamageTypeTagData(packOutput, lookupProvider, fileHelper));

        // pack.mcmeta
        PackMetadataGenerator packMeta = new PackMetadataGenerator(packOutput);
        Map<PackType, Integer> packTypes = Map.of(PackType.SERVER_DATA, SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA));
        packMeta.add(PackMetadataSection.TYPE, new PackMetadataSection(Component.translatable("pack.aether.mod.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES), packTypes));
        generator.addProvider(true, packMeta);
    }
}
