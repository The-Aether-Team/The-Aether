package com.gildedgames.aether.common.item.miscellaneous;

import com.gildedgames.aether.common.entity.miscellaneous.ParachuteEntity;
import com.gildedgames.aether.core.api.registers.ParachuteType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.List;

public class ParachuteItem extends Item
{
    private final ParachuteType parachute;

    public ParachuteItem(ParachuteType parachute, Properties properties) {
        super(properties);
        this.parachute = parachute;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack itemstack = playerEntity.getItemInHand(hand);
        if (!playerEntity.isOnGround() && !playerEntity.isInWater() && !playerEntity.isInLava()) {
            ParachuteEntity parachuteEntity = new ParachuteEntity(world, playerEntity.getX(), playerEntity.getY() - 1.0D, playerEntity.getZ());
            parachuteEntity.setParachuteType(this.parachute);
            if (playerEntity.isPassenger()) {
                if (playerEntity.getVehicle() instanceof ParachuteEntity) {
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
        return ActionResult.pass(itemstack);
    }
}