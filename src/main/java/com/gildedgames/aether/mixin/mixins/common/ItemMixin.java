package com.gildedgames.aether.mixin.mixins.common;

import com.gildedgames.aether.event.hooks.AbilityHooks;
import com.gildedgames.aether.event.listeners.abilities.ToolAbilityListener;
import com.gildedgames.aether.item.tools.abilities.ValkyrieTool;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(at = @At(value = "HEAD"), method = "getPlayerPOVHitResult", cancellable = true)
    private static void getPlayerPOVHitResult(Level level, Player player, ClipContext.Fluid fluidMode, CallbackInfoReturnable<BlockHitResult> cir) {
        InteractionHand hand = ToolAbilityListener.INTERACTION_HAND;
        if (hand != null) {
            BlockHitResult hitResult = interactionTooFar(level, player, hand, fluidMode);
            if (hitResult != null) {
                cir.setReturnValue(hitResult);
            }
        }
    }

    /**
     * Checks if a block-item interaction is too far away for the player to be able to interact with if they're trying to interact using a hand that doesn't contain a {@link ValkyrieTool}, but are still holding a Valkyrie Tool in another hand.
     * @param player The {@link Player} attempting to interact.
     * @param hand The {@link InteractionHand} used to interact.
     * @return Whether the player is too far to interact, as a {@link Boolean}.
     */
    private static BlockHitResult interactionTooFar(Level level, Player player, InteractionHand hand, ClipContext.Fluid fluidMode) {
        ItemStack heldStack = player.getItemInHand(hand);
        if (AbilityHooks.ToolHooks.hasValkyrieItemInOneHand(player) && !(heldStack.getItem() instanceof ValkyrieTool)) {
            UUID uuidForOppositeHand = hand == InteractionHand.MAIN_HAND ? ValkyrieTool.REACH_DISTANCE_MODIFIER_OFFHAND_UUID : ValkyrieTool.REACH_DISTANCE_MODIFIER_MAINHAND_UUID; // We're checking the hand being used to interact, which won't contain a Valkyrie Tool, so we must get the UUID of the opposite hand, which will contain a tool.
            AttributeInstance reachDistance = player.getAttribute(ForgeMod.REACH_DISTANCE.get());
            if (reachDistance != null) {
                AttributeModifier valkyrieModifier = reachDistance.getModifier(uuidForOppositeHand);
                if (valkyrieModifier != null) {
                    reachDistance.removeModifier(valkyrieModifier);
                    double reach = player.getAttributeValue(ForgeMod.REACH_DISTANCE.get());
                    double trueReach = reach == 0 ? 0 : reach + (player.isCreative() ? 0.5 : 0); // Copied from IForgePlayer#getReachDistance().
                    BlockHitResult result = getPlayerPOVHitResultForReach(level, player, trueReach, fluidMode);
                    reachDistance.addTransientModifier(valkyrieModifier);
                    return result;
                }
            }
        }
        return null;
    }

    /**
     * Based on {@link net.minecraft.world.item.Item#getPlayerPOVHitResult(Level, Player, ClipContext.Fluid)}.
     */
    private static BlockHitResult getPlayerPOVHitResultForReach(Level level, Player player, double reach, ClipContext.Fluid fluidClip) {
        float f = player.getXRot();
        float f1 = player.getYRot();
        Vec3 vec3 = player.getEyePosition();
        float f2 = Mth.cos(-f1 * ((float) Math.PI / 180.0F) - (float) Math.PI);
        float f3 = Mth.sin(-f1 * ((float) Math.PI / 180.0F) - (float) Math.PI);
        float f4 = -Mth.cos(-f * ((float) Math.PI / 180.0F));
        float f5 = Mth.sin(-f * ((float) Math.PI / 180.0F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        Vec3 vec31 = vec3.add((double) f6 * reach, (double) f5 * reach, (double) f7 * reach);
        return level.clip(new ClipContext(vec3, vec31, ClipContext.Block.OUTLINE, fluidClip, player));
    }
}
