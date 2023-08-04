package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.event.hooks.AbilityHooks;
import com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener;
import com.aetherteam.aether.item.tools.abilities.ValkyrieTool;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.extensions.IForgePlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(IForgePlayer.class)
public class IForgePlayerMixin {
    /**
     * Modifies the entity reach if the player is using a valkyrie tool.
     *
     * @param reach The original reach calculated by {@code self().getAttributeValue(ForgeMod.ENTITY_REACH.get())}.
     * @return The modified value. It can return the original value if no change is required.
     */
    @ModifyExpressionValue(at = @At(value = "INVOKE", target = "net/minecraft/world/entity/player/Player.getAttributeValue(Lnet/minecraft/world/entity/ai/attributes/Attribute;)D"), method = "getEntityReach()D")
    private double modifyEntityReach(double reach) {
        return modifyReach((Player) (Object) this, reach);
    }

    /**
     * Modifies the block reach if the player is using a valkyrie tool.
     *
     * @param reach The original reach calculated by {@code self().getAttributeValue(ForgeMod.BLOCK_REACH.get())}.
     * @return The modified value. It can return the original value if no change is required.
     */
    @ModifyExpressionValue(at = @At(value = "INVOKE", target = "net/minecraft/world/entity/player/Player.getAttributeValue(Lnet/minecraft/world/entity/ai/attributes/Attribute;)D"), method = "getBlockReach()D")
    private double modifyBlockReach(double reach) {
        return modifyReach((Player) (Object) this, reach);
    }

    private static double modifyReach(Player player, double reach) {
        InteractionHand hand = ToolAbilityListener.INTERACTION_HAND;

        if (hand == InteractionHand.OFF_HAND && AbilityHooks.ToolHooks.hasValkyrieItemInMainHandOnly(player)) {
            AttributeInstance reachDistance = player.getAttribute(ForgeMod.BLOCK_REACH.get());
            if (reachDistance != null) {
                AttributeModifier valkyrieModifier = reachDistance.getModifier(ValkyrieTool.REACH_DISTANCE_MODIFIER_UUID);
                if (valkyrieModifier != null) {
                    return reach - valkyrieModifier.getAmount();
                }
            }
        }

        return reach;
    }
}
