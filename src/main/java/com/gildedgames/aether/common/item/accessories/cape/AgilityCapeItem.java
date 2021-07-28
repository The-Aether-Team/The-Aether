package com.gildedgames.aether.common.item.accessories.cape;

import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.client.ResetMaxUpStepPacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class AgilityCapeItem extends CapeItem
{
    public AgilityCapeItem(String capeLocation, Properties properties) {
        super(capeLocation, properties);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        livingEntity.maxUpStep = !livingEntity.isCrouching() ? 1.0F : 0.6F;
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity livingEntity = slotContext.getWearer();
        AetherPacketHandler.sendToNear(new ResetMaxUpStepPacket(livingEntity.getId()), livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.0D, livingEntity.level.dimension());
        livingEntity.maxUpStep = 0.6F;
    }
}
