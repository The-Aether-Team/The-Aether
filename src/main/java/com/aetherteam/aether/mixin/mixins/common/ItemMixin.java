package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.event.hooks.AbilityHooks;
import com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener;
import com.aetherteam.aether.item.tools.abilities.ValkyrieTool;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Item.class)
public class ItemMixin {
    /**
     * Checks if a block-item interaction is too far away.
     *
     * @param original  The initial {@link BlockHitResult} before checking distance.
     * @param level     The {@link Level} for interaction.
     * @param player    The {@link Player} attempting to interact.
     * @param fluidMode The {@link net.minecraft.world.level.ClipContext.Fluid} for interaction.
     * @return The corrected {@link BlockHitResult} if it was incorrect, otherwise the original {@link BlockHitResult}.
     * @see ItemMixin#interactionTooFar(Level, Player, InteractionHand, ClipContext.Fluid)
     */
    @ModifyReturnValue(at = @At(value = "RETURN"), method = "getPlayerPOVHitResult(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/ClipContext$Fluid;)Lnet/minecraft/world/phys/BlockHitResult;")
    private static BlockHitResult getPlayerPOVHitResult(BlockHitResult original, @Local(ordinal = 0, argsOnly = true) Level level, @Local(ordinal = 0, argsOnly = true) Player player, @Local(ordinal = 0, argsOnly = true) ClipContext.Fluid fluidMode) {
        InteractionHand hand = ToolAbilityListener.INTERACTION_HAND;
        if (hand != null) {
            BlockHitResult hitResult = interactionTooFar(level, player, hand, fluidMode);
            if (hitResult != null) return hitResult;
        }
        return original;
    }

    /**
     * Checks if a block-item interaction is too far away for the player to be able to interact with if they're trying to interact using a hand that doesn't contain a {@link ValkyrieTool}, but are still holding a Valkyrie Tool in another hand.
     *
     * @param player The {@link Player} attempting to interact.
     * @param hand   The {@link InteractionHand} used to interact.
     * @return A {@link BlockHitResult} corresponding to the accurate reach, or null if it is accurate.
     * @see ItemMixin#getPlayerPOVHitResultForReach(Level, Player, double, ClipContext.Fluid)
     */
    private static BlockHitResult interactionTooFar(Level level, Player player, InteractionHand hand, ClipContext.Fluid fluidMode) {
        if (hand == InteractionHand.OFF_HAND && AbilityHooks.ToolHooks.hasValkyrieItemInMainHandOnly(player)) {
            AttributeInstance reachDistance = player.getAttribute(Attributes.BLOCK_INTERACTION_RANGE);
            if (reachDistance != null) {
                AttributeModifier valkyrieModifier = reachDistance.getModifier(ValkyrieTool.BLOCK_INTERACTION_RANGE_MODIFIER_UUID);
                if (valkyrieModifier != null) {
                    double reach = player.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE) - valkyrieModifier.amount();
                    double trueReach = reach == 0 ? 0 : reach + (player.isCreative() ? 0.5 : 0); // [CODE COPY] - IForgePlayer#getReachDistance().
                    return getPlayerPOVHitResultForReach(level, player, trueReach, fluidMode);
                }
            }
        }
        return null;
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.item.Item#getPlayerPOVHitResult(Level, Player, ClipContext.Fluid)}.<br><br>
     * Accepts a specified reach value from a parameter instead of a hardcoded one.
     */
    private static BlockHitResult getPlayerPOVHitResultForReach(Level level, Player player, double reach, ClipContext.Fluid fluidClip) {
        float f = player.getXRot();
        float f1 = player.getYRot();
        Vec3 vec3 = player.getEyePosition();
        float f2 = Mth.cos(-f1 * Mth.DEG_TO_RAD - Mth.PI);
        float f3 = Mth.sin(-f1 * Mth.DEG_TO_RAD - Mth.PI);
        float f4 = -Mth.cos(-f * Mth.DEG_TO_RAD);
        float f5 = Mth.sin(-f * Mth.DEG_TO_RAD);
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        Vec3 vec31 = vec3.add((double) f6 * reach, (double) f5 * reach, (double) f7 * reach);
        return level.clip(new ClipContext(vec3, vec31, ClipContext.Block.OUTLINE, fluidClip, player));
    }
}
