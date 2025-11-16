package com.aetherteam.aetherfabric.mixin;

import com.aetherteam.aetherfabric.events.AddPackFindersEvent;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.world.level.validation.DirectoryValidator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.file.Path;

@Mixin(ServerPacksSource.class)
public abstract class ServerPacksSourceMixin {
    @Inject(method = "createPackRepository(Ljava/nio/file/Path;Lnet/minecraft/world/level/validation/DirectoryValidator;)Lnet/minecraft/server/packs/repository/PackRepository;", at = @At("RETURN"))
    private static void aetherFabric$addServerPackResources(Path folder, DirectoryValidator validator, CallbackInfoReturnable<PackRepository> cir) {
        AddPackFindersEvent.invokeEvent(PackType.SERVER_DATA, cir.getReturnValue());
    }

    @Inject(method = "createVanillaTrustedRepository", at = @At("RETURN"))
    private static void aetherFabric$addServerPackResourcesVanilla(CallbackInfoReturnable<PackRepository> cir) {
        AddPackFindersEvent.invokeEvent(PackType.SERVER_DATA, cir.getReturnValue());
    }
}
