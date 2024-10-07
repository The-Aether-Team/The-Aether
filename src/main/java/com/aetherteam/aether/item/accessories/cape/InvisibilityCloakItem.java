package com.aetherteam.aether.item.accessories.cape;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.client.AetherKeys;
import com.aetherteam.aether.item.accessories.AccessoryItem;
import com.aetherteam.aether.mixin.mixins.common.accessor.LivingEntityAccessor;
import com.aetherteam.aether.network.packet.clientbound.SetInvisibilityPacket;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import net.neoforged.neoforge.network.PacketDistributor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import top.theillusivec4.curios.api.SlotContext;

/**
 * Additional invisibility behavior is handled with {@link com.aetherteam.aether.client.event.listeners.abilities.AccessoryAbilityClientListener#onRenderPlayer(RenderPlayerEvent.Pre)}
 * and {@link com.aetherteam.aether.client.event.listeners.abilities.AccessoryAbilityClientListener#onRenderHand(net.neoforged.neoforge.client.event.RenderArmEvent)}.<br><br>
 * The wearer is also hidden from other entities' targeting by {@link com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener#onTargetSet(LivingEvent.LivingVisibilityEvent)}.
 */
public class InvisibilityCloakItem extends AccessoryItem {
    public InvisibilityCloakItem(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        if (livingEntity.level().isClientSide() && livingEntity instanceof Player player) {
            if (AetherKeys.INVISIBILITY_TOGGLE.consumeClick()) {
                var data = player.getData(AetherDataAttachments.AETHER_PLAYER);
                data.setSynched(player.getId(), INBTSynchable.Direction.SERVER, "setInvisibilityEnabled", !data.isInvisibilityEnabled());
            }
        }
        if (!livingEntity.level().isClientSide() && livingEntity instanceof Player player) {
            var data = player.getData(AetherDataAttachments.AETHER_PLAYER);
            if (data.isInvisibilityEnabled()) {
                if (!AetherConfig.SERVER.balance_invisibility_cloak.get()) {
                    data.setSynched(player.getId(), INBTSynchable.Direction.CLIENT, "setWearingInvisibilityCloak", true);
                } else {
                    if (!data.attackedWithInvisibility() && !data.isWearingInvisibilityCloak()) {
                        data.setSynched(player.getId(), INBTSynchable.Direction.CLIENT, "setWearingInvisibilityCloak", true);
                    } else if (data.attackedWithInvisibility() && data.isWearingInvisibilityCloak()) {
                        data.setSynched(player.getId(), INBTSynchable.Direction.CLIENT, "setWearingInvisibilityCloak", false);
                    }
                }
            } else {
                data.setSynched(player.getId(), INBTSynchable.Direction.CLIENT, "setWearingInvisibilityCloak", false);
            }
        }
        if (!livingEntity.level().isClientSide()) {
            if (!livingEntity.isInvisible()) {
                if (livingEntity instanceof Player player) {
                    var data = player.getData(AetherDataAttachments.AETHER_PLAYER);
                    if (data.isWearingInvisibilityCloak()) {
                        player.setInvisible(true);
                        PacketDistributor.sendToAll(new SetInvisibilityPacket(player.getId(), true));
                    }
                } else {
                    livingEntity.setInvisible(true);
                }
            } else {
                if (livingEntity instanceof Player player) {
                    var data = player.getData(AetherDataAttachments.AETHER_PLAYER);
                    if (!data.isWearingInvisibilityCloak()) {
                        player.setInvisible(false);
                        PacketDistributor.sendToAll(new SetInvisibilityPacket(player.getId(), false));
                    }
                }
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        if (!livingEntity.level().isClientSide() && livingEntity instanceof Player player) {
            player.getData(AetherDataAttachments.AETHER_PLAYER).setSynched(player.getId(), INBTSynchable.Direction.CLIENT, "setWearingInvisibilityCloak", false);
        }
        livingEntity.setInvisible(false);
        ((LivingEntityAccessor) livingEntity).callUpdateEffectVisibility();
    }
}
