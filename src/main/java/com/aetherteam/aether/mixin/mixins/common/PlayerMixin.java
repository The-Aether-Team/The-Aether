package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.entity.passive.MountableAnimal;
import com.aetherteam.aether.event.hooks.AbilityHooks;
import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.aether.mixin.AetherMixinHooks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {
    /**
     * Damages gloves only once during a sweeping attack, instead of once for every damaged entity in the attack.
     *
     * @param target The target {@link Entity}.
     * @param ci     The {@link CallbackInfo} for the void method return.
     * @see AbilityHooks.AccessoryHooks#damageGloves(Player)
     */
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;doPostHurtEffects(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/Entity;)V", shift = At.Shift.AFTER), method = "attack(Lnet/minecraft/world/entity/Entity;)V")
    private void attack(Entity target, CallbackInfo ci) {
        Player player = (Player) (Object) this;
        AbilityHooks.AccessoryHooks.damageGloves(player);
    }

    /**
     * Used to set whether the player tried to crouch for {@link MountableAnimal}, before crouching is cancelled for mounts by the {@link Player} class.
     *
     * @param ci The {@link CallbackInfo} for the void method return.
     */
    @Inject(at = @At(value = "HEAD"), method = "rideTick()V")
    private void rideTick(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        if (!player.level().isClientSide()) {
            if (player.isPassenger() && player.getVehicle() instanceof MountableAnimal mountableAnimal) {
                mountableAnimal.setPlayerTriedToCrouch(player.isShiftKeyDown());
            }
        }
    }

    /**
     * Sets the player as having a loaded cape if they have a cape accessory equipped and visible.
     *
     * @param cir The {@link Boolean} {@link CallbackInfoReturnable} used for the method's return value.
     */
    @Inject(at = @At(value = "HEAD"), method = "isModelPartShown(Lnet/minecraft/world/entity/player/PlayerModelPart;)Z", cancellable = true)
    private void isModelPartShown(PlayerModelPart part, CallbackInfoReturnable<Boolean> cir) {
        Player player = (Player) (Object) this;
        if (EquipmentUtil.hasCape(player) && AetherMixinHooks.isCapeVisible(player)) {
            cir.setReturnValue(true); //todo
        }
    }
}
