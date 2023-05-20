package com.aetherteam.aether.item.accessories.cape;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.item.accessories.AccessoryItem;
import com.aetherteam.aether.mixin.mixins.common.accessor.LivingEntityAccessor;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.client.InvisibilityParticlePacket;
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
        if (livingEntity instanceof Player player) {
            AetherPlayer.get(player).ifPresent((aetherPlayer) -> {
                if (!aetherPlayer.isWearingInvisibilityCloak()) {
                    aetherPlayer.setWearingInvisibilityCloak(true);
                }
            });
        }
        if (!livingEntity.isInvisible()) {
            livingEntity.setInvisible(true);
        }
        if (!livingEntity.getLevel().isClientSide() && AetherConfig.COMMON.balance_invisibility_cloak.get()) {
            AetherPacketHandler.sendToNear(new InvisibilityParticlePacket(livingEntity.getId()), livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 64.0, livingEntity.getLevel().dimension());
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        if (livingEntity instanceof Player player) {
            AetherPlayer.get(player).ifPresent((aetherPlayer) -> aetherPlayer.setWearingInvisibilityCloak(false));
        }
        livingEntity.setInvisible(false);
        ((LivingEntityAccessor) livingEntity).callUpdateEffectVisibility();
    }
}
