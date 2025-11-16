package com.aetherteam.aetherfabric.mixin.client;

import com.aetherteam.aetherfabric.events.AddPackFindersEvent;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.RepositorySource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin {
    @WrapOperation(method = "openFresh", at = @At(value = "NEW", target = "([Lnet/minecraft/server/packs/repository/RepositorySource;)Lnet/minecraft/server/packs/repository/PackRepository;"))
    private static PackRepository aetherFabric$addPacks(RepositorySource[] sources, Operation<PackRepository> original) {
        var repo = original.call((Object) sources);

        AddPackFindersEvent.invokeEvent(PackType.SERVER_DATA, repo);

        return repo;
    }
}
