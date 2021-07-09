package com.gildedgames.aether.common.item.miscellaneous;

import com.gildedgames.aether.common.entity.miscellaneous.AbstractParachuteEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class ParachuteItem extends Item
{
    protected final Supplier<EntityType<?>> parachuteEntity;

    public ParachuteItem(Supplier<EntityType<?>> parachuteEntity, Properties properties) {
        super(properties);
        this.parachuteEntity = parachuteEntity;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack itemstack = playerEntity.getItemInHand(hand);
        if (!playerEntity.isOnGround() && !playerEntity.isInWater() && !playerEntity.isInLava() && !playerEntity.isShiftKeyDown()) {
            Entity entity = this.parachuteEntity.get().create(world);
            if (entity instanceof AbstractParachuteEntity) {
                AbstractParachuteEntity parachuteEntity = (AbstractParachuteEntity) entity;
                parachuteEntity.setPos(playerEntity.getX(), playerEntity.getY() - 1.0D, playerEntity.getZ());
                if (playerEntity.isPassenger()) {
                    if (playerEntity.getVehicle() instanceof AbstractParachuteEntity) {
                        playerEntity.getVehicle().ejectPassengers();
                    } else {
                        return ActionResult.pass(itemstack);
                    }
                }
                if (!world.isClientSide) {
                    world.addFreshEntity(parachuteEntity);
                    playerEntity.startRiding(parachuteEntity);
                    itemstack.hurtAndBreak(1, playerEntity, (p) -> p.broadcastBreakEvent(hand));
                }
                parachuteEntity.spawnExplosionParticle();
                playerEntity.awardStat(Stats.ITEM_USED.get(this));
                return ActionResult.sidedSuccess(itemstack, world.isClientSide());
            }
        }
        return ActionResult.pass(itemstack);
    }
}