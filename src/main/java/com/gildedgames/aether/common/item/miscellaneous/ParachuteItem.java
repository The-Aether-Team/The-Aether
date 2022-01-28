package com.gildedgames.aether.common.item.miscellaneous;

import com.gildedgames.aether.common.entity.miscellaneous.ParachuteEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class ParachuteItem extends Item
{
    protected final Supplier<EntityType<ParachuteEntity>> parachuteEntity;

    public ParachuteItem(Supplier<EntityType<ParachuteEntity>> parachuteEntity, Properties properties) {
        super(properties);
        this.parachuteEntity = parachuteEntity;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player playerEntity, InteractionHand hand) {
        ItemStack itemstack = playerEntity.getItemInHand(hand);
        if (!playerEntity.isOnGround() && !playerEntity.isInWater() && !playerEntity.isInLava() && !playerEntity.isShiftKeyDown()) {
            Entity entity = this.getParachuteEntity().get().create(world);
            if (entity instanceof ParachuteEntity parachuteEntity) {
                parachuteEntity.setPos(playerEntity.getX(), playerEntity.getY() - 1.0D, playerEntity.getZ());
                if (playerEntity.isPassenger()) {
                    if (playerEntity.getVehicle() instanceof ParachuteEntity) {
                        playerEntity.getVehicle().ejectPassengers();
                    } else {
                        return InteractionResultHolder.pass(itemstack);
                    }
                }
                if (!world.isClientSide) {
                    world.addFreshEntity(parachuteEntity);
                    playerEntity.startRiding(parachuteEntity);
                    itemstack.hurtAndBreak(1, playerEntity, (p) -> p.broadcastBreakEvent(hand));
                }
                parachuteEntity.spawnExplosionParticle();
                playerEntity.awardStat(Stats.ITEM_USED.get(this));
                return InteractionResultHolder.sidedSuccess(itemstack, world.isClientSide());
            }
        }
        return InteractionResultHolder.pass(itemstack);
    }

    public Supplier<EntityType<ParachuteEntity>> getParachuteEntity() {
        return this.parachuteEntity;
    }
}