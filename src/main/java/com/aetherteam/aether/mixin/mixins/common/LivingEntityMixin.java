package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.event.hooks.AbilityHooks;
import com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener;
import com.aetherteam.aether.item.combat.abilities.armor.PhoenixArmor;
import com.aetherteam.aether.item.tools.abilities.ValkyrieTool;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    /**
     * Handles vertical swimming for Phoenix Armor in lava without being affected by the upwards speed debuff from lava.
     * @param ci The {@link CallbackInfo} for the void method return.
     * @see PhoenixArmor#boostVerticalLavaSwimming(LivingEntity)
     */
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getFluidJumpThreshold()D", shift = At.Shift.AFTER), method = "travel(Lnet/minecraft/world/phys/Vec3;)V")
    private void travel(CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        PhoenixArmor.boostVerticalLavaSwimming(livingEntity);
    }

    /**
     * Reduces the player's offhand reach to its normal distance when holding a Valkyrie Tool in the mainhand.
     * This ensures that the offhand reach is not extended by the mainhand attribute modifier given by the Valkyrie Tool.
     * @param reach The {@link Double} for the original reach attribute.
     * @param attribute The reach {@link Attribute}.
     * @return The {@link Double} for the new reach attribute.
     */
    @ModifyExpressionValue(at = @At(value = "INVOKE", target = "net/minecraft/world/entity/ai/attributes/AttributeMap.getValue(Lnet/minecraft/world/entity/ai/attributes/Attribute;)D"), method = "getAttributeValue(Lnet/minecraft/world/entity/ai/attributes/Attribute;)D")
    private double modifyReachAttribute(double reach, Attribute attribute) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (livingEntity instanceof Player player && (attribute == ForgeMod.ENTITY_REACH.get() || attribute == ForgeMod.BLOCK_REACH.get())) {
            InteractionHand hand = ToolAbilityListener.INTERACTION_HAND;
            if (hand == InteractionHand.OFF_HAND && AbilityHooks.ToolHooks.hasValkyrieItemInMainHandOnly(player)) {
                AttributeInstance reachDistance = player.getAttribute(attribute);
                if (reachDistance != null) {
                    AttributeModifier valkyrieModifier = reachDistance.getModifier(ValkyrieTool.REACH_DISTANCE_MODIFIER_UUID);
                    if (valkyrieModifier != null) {
                        return reach - valkyrieModifier.getAmount();
                    }
                }
            }
        }
        return reach;
    }
}
