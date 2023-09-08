package com.aetherteam.aether.data.generators.tags;

//import com.aetherteam.aether.Aether;
//import com.aetherteam.aether.AetherTags;
//import com.aetherteam.aether.data.resources.registries.AetherStructures;
//import net.minecraft.core.HolderLookup;
//import net.minecraft.data.PackOutput;
//import net.minecraft.data.tags.StructureTagsProvider;
//import net.minecraftforge.common.data.ExistingFileHelper;
//
//import javax.annotation.Nullable;
//import java.util.concurrent.CompletableFuture;
//
//public class AetherStructureTagData extends StructureTagsProvider {
//    public AetherStructureTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, @Nullable ExistingFileHelper helper) {
//        super(output, registries, Aether.MODID, helper);
//    }
//
//    @Override
//    public void addTags(HolderLookup.Provider provider) {
//        this.tag(AetherTags.Structures.DUNGEONS).add(
//                AetherStructures.BRONZE_DUNGEON,
//                AetherStructures.SILVER_DUNGEON,
//                AetherStructures.GOLD_DUNGEON
//        );
//    }
//}
