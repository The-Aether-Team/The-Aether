package com.gildedgames.aether.common.item.miscellaneous;

import com.gildedgames.aether.core.api.registers.ParachuteType;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ParachuteItem extends Item
{
    private final ParachuteType parachute;

    public ParachuteItem(ParachuteType parachute, Properties properties) {
        super(properties);
        this.parachute = parachute;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack item = playerEntity.getItemInHand(hand);

        if (!playerEntity.isOnGround() && !playerEntity.isInWater()) {
            IAetherPlayer.get(playerEntity).ifPresent((player) -> player.setParachute(this.parachute));
            item.hurtAndBreak(1, playerEntity, (p) -> p.broadcastBreakEvent(hand));

            return ActionResult.success(item);
        }

        return super.use(world, playerEntity, hand);
    }
}
