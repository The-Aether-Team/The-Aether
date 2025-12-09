package com.aetherteam.aether.mixin.mixins.client.aether_fabric;

import com.aetherteam.aether.client.AetherKeys;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyMapping.class)
public abstract class KeyMappingMixin {

    @Shadow
    private int clickCount;

    // TODO: DUE TO FUTURE CHANGES MOST LIKELY WON'T NEED SUCH AS KEYMAPPINGS ARE BETTER IN HIGHER VERSIONS
    @Inject(method = "set", at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;", shift = At.Shift.AFTER))
    private static void aetherFabric$checkOtherBinds1(InputConstants.Key key, boolean held, CallbackInfo ci) {
        if (AetherKeys.GRAVITITE_JUMP_ABILITY.nitrogen_fabric$isActiveAndMatches(key)) AetherKeys.GRAVITITE_JUMP_ABILITY.setDown(held);
        if (AetherKeys.INVISIBILITY_TOGGLE.nitrogen_fabric$isActiveAndMatches(key)) AetherKeys.INVISIBILITY_TOGGLE.setDown(held);
        if (AetherKeys.OPEN_ACCESSORY_INVENTORY.nitrogen_fabric$isActiveAndMatches(key)) AetherKeys.OPEN_ACCESSORY_INVENTORY.setDown(held);
    }

    // TODO: DUE TO FUTURE CHANGES MOST LIKELY WON'T NEED SUCH AS KEYMAPPINGS ARE BETTER IN HIGHER VERSIONS
    @Inject(method = "click", at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;", shift = At.Shift.AFTER))
    private static void aetherFabric$checkOtherBinds2(InputConstants.Key key, CallbackInfo ci) {
        if (AetherKeys.GRAVITITE_JUMP_ABILITY.nitrogen_fabric$isActiveAndMatches(key)) ((KeyMappingMixin) (Object) AetherKeys.GRAVITITE_JUMP_ABILITY).clickCount++;
        if (AetherKeys.INVISIBILITY_TOGGLE.nitrogen_fabric$isActiveAndMatches(key)) ((KeyMappingMixin) (Object) AetherKeys.INVISIBILITY_TOGGLE).clickCount++;
        if (AetherKeys.OPEN_ACCESSORY_INVENTORY.nitrogen_fabric$isActiveAndMatches(key)) ((KeyMappingMixin) (Object) AetherKeys.OPEN_ACCESSORY_INVENTORY).clickCount++;
    }
}
