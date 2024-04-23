package com.aetherteam.aether.data;

import com.aetherteam.aether.data.generators.*;
import com.aetherteam.aether.data.generators.tags.*;
import com.aetherteam.aether.data.resources.AetherMobCategory;
import com.google.common.reflect.Reflection;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.SharedConstants;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;

import java.util.Map;

public class AetherData implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        ExistingFileHelper fileHelper = ExistingFileHelper.withResourcesFromArg();
        FabricDataGenerator.Pack pack = generator.createPack();

        Reflection.initialize(AetherMobCategory.class);

        // Client Data
        pack.addProvider((packOutput, r) -> new AetherBlockStateData(packOutput, fileHelper));
        pack.addProvider((packOutput, r) -> new AetherItemModelData(packOutput, fileHelper));
        pack.addProvider(AetherLanguageData::new);
        pack.addProvider((packOutput, r) -> new AetherSoundData(packOutput, fileHelper));

        // Server Data
        pack.addProvider(AetherRegistrySets::new);
        pack.addProvider(AetherRecipeData::new);
        pack.addProvider(AetherLootTableData::create);
        pack.addProvider(AetherLootModifierData::new);
        pack.addProvider(AetherAdvancementData::new);
        AetherBlockTagData blockTags = pack.addProvider(AetherBlockTagData::new);
        pack.addProvider((packOutput, lookupProvider) -> new AetherItemTagData(packOutput, lookupProvider, blockTags));
        pack.addProvider(AetherEntityTagData::new);
        pack.addProvider(AetherFluidTagData::new);
        pack.addProvider(AetherBiomeTagData::new);
        pack.addProvider(AetherStructureTagData::new);
        pack.addProvider(AetherDamageTypeTagData::new);

        // pack.mcmeta
        PackMetadataGenerator packMeta = pack.addProvider((packOutput, r) -> new PackMetadataGenerator(packOutput));
        Map<PackType, Integer> packTypes = Map.of(PackType.SERVER_DATA, SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA));
        packMeta.add(PackMetadataSection.TYPE, new PackMetadataSection(Component.translatable("pack.aether.mod.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES)/*, packTypes*/));
    }
}
