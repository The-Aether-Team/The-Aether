package com.gildedgames.aether.common.item.misc;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.entity.block.ColdParachuteEntity;
import com.gildedgames.aether.common.entity.block.ParachuteEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ParachuteItem extends Item
{
    private final EntityType<?> parachute;

    public ParachuteItem(EntityType<?> parachute, Properties properties) {
        super(properties);
        this.parachute = parachute;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack item = playerEntity.getItemInHand(hand);

        if (!playerEntity.isOnGround() && !playerEntity.isInWater()) {
            Entity entity = this.parachute.create(world);
            if (entity instanceof ParachuteEntity) {
                ParachuteEntity parachute = (ParachuteEntity) entity;
                parachute.setPlayerUUID(playerEntity.getUUID());
                parachute.absMoveTo(playerEntity.getX(), playerEntity.getY() - 1.0, playerEntity.getZ(), 0.0F, 0.0F);
                world.addFreshEntity(parachute);
                parachute.spawnExplosionParticle();
            }

            item.hurtAndBreak(1, playerEntity, (p) -> p.broadcastBreakEvent(hand));

            return ActionResult.success(item);
        }

        return super.use(world, playerEntity, hand);
    }
}
