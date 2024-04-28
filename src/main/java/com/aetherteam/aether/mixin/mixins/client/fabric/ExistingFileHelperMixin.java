package com.aetherteam.aether.mixin.mixins.client.fabric;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.fabric.impl.resource.loader.ModNioResourcePack;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(ExistingFileHelper.class)
public class ExistingFileHelperMixin {
    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/fabricmc/loader/api/FabricLoader;getModContainer(Ljava/lang/String;)Ljava/util/Optional;"))
    private Optional<ModContainer> testData(FabricLoader instance, String existingMod, @Local(index = 6) List<PackResources> candidateClientResources, @Local(index = 7) List<PackResources> candidateServerResources) {
        ModContainer modFileInfo = FabricLoader.getInstance().getModContainer(existingMod).orElse(null);
        if (modFileInfo != null) {
            candidateClientResources.add(ModNioResourcePack.create(existingMod, modFileInfo, null, PackType.CLIENT_RESOURCES, ResourcePackActivationType.ALWAYS_ENABLED));
//            candidateServerResources.add(ModNioResourcePack.create(existingMod, modFileInfo, null, PackType.SERVER_DATA, ResourcePackActivationType.ALWAYS_ENABLED));
        }
        return Optional.empty();
    }
}
