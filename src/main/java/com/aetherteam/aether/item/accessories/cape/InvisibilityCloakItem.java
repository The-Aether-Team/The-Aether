package com.aetherteam.aether.item.accessories.cape;

import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.client.AetherKeys;
import com.aetherteam.aether.item.accessories.AccessoryItem;
import com.aetherteam.aether.mixin.mixins.common.accessor.LivingEntityAccessor;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.server.InvisibilityTogglePacket;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import top.theillusivec4.curios.api.SlotContext;

/**
 * Additional invisibility behavior is handled with {@link com.aetherteam.aether.client.event.listeners.abilities.AccessoryAbilityClientListener#onRenderPlayer(RenderPlayerEvent.Pre)}
 * and {@link com.aetherteam.aether.client.event.listeners.abilities.AccessoryAbilityClientListener#onRenderHand(net.minecraftforge.client.event.RenderArmEvent)}.<br><br>
 * The wearer is also hidden from other entities' targeting by {@link com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener#onTargetSet(LivingEvent.LivingVisibilityEvent)}.
 */
public class InvisibilityCloakItem extends AccessoryItem {
    public InvisibilityCloakItem(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        if (livingEntity.getLevel().isClientSide() && livingEntity instanceof Player player) {
            AetherPlayer.get(player).ifPresent((aetherPlayer) -> {
                if (AetherKeys.INVISIBILITY_TOGGLE.consumeClick()) {
                    AetherPacketHandler.sendToServer(new InvisibilityTogglePacket(player.getId()));
                }
            });
        }
        if (!livingEntity.getLevel().isClientSide() && livingEntity instanceof Player player) {
            AetherPlayer.get(player).ifPresent((aetherPlayer) -> {
                if (aetherPlayer.isInvisibilityEnabled()) {
                    if (!aetherPlayer.isWearingInvisibilityCloak() && !aetherPlayer.attackedWithInvisibility()) {
                        aetherPlayer.setWearingInvisibilityCloak(true);
                    } else if (aetherPlayer.isWearingInvisibilityCloak() && aetherPlayer.attackedWithInvisibility()) {
                        aetherPlayer.setWearingInvisibilityCloak(false);
                    }
                } else {
                    aetherPlayer.setWearingInvisibilityCloak(false);
                }
            });
        }
        if (!livingEntity.isInvisible()) {
            if (livingEntity instanceof Player player) {
                AetherPlayer.get(player).ifPresent((aetherPlayer) -> {
                    if (aetherPlayer.isInvisibilityEnabled()) {
                        livingEntity.setInvisible(true);
                    }
                });
            } else {
                livingEntity.setInvisible(true);
            }
        } else {
            if (livingEntity instanceof Player player) {
                AetherPlayer.get(player).ifPresent((aetherPlayer) -> {
                    if (!aetherPlayer.isInvisibilityEnabled()) {
                        livingEntity.setInvisible(false);
                    }
                });
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        if (!livingEntity.getLevel().isClientSide() && livingEntity instanceof Player player) {
            AetherPlayer.get(player).ifPresent((aetherPlayer) -> aetherPlayer.setWearingInvisibilityCloak(false));
        }
        livingEntity.setInvisible(false);
        ((LivingEntityAccessor) livingEntity).callUpdateEffectVisibility();
    }
}
