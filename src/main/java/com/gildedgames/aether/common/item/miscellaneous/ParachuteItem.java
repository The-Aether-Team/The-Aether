package com.gildedgames.aether.common.item.miscellaneous;

import com.gildedgames.aether.common.entity.equipment.AbstractParachuteEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class ParachuteItem extends Item
{
    private final Supplier<EntityType<?>> parachute;

    public ParachuteItem(Supplier<EntityType<?>> parachute, Properties properties) {
        super(properties);
        this.parachute = parachute;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack item = playerEntity.getItemInHand(hand);

        //TODO: If player is already mounted to parachute and it is of a different type, delete that parachute.
        //TODO: Mounting offsets the player weirdly at first so see if I can maybe adjust the y offset in that setpos function to make it more seamless.

        if (!playerEntity.isOnGround() && !playerEntity.isInWater() && !playerEntity.isInLava()) {
            AbstractParachuteEntity parachute = (AbstractParachuteEntity) this.parachute.get().create(playerEntity.level);
            if (parachute != null) {
                playerEntity.level.addFreshEntity(parachute);
                parachute.setPos(playerEntity.getX(), playerEntity.getY() - 0.5, playerEntity.getZ());
                playerEntity.startRiding(parachute);
                parachute.spawnExplosionParticle();
            }
            item.hurtAndBreak(1, playerEntity, (p) -> p.broadcastBreakEvent(hand));

            return ActionResult.success(item);
        }

        return super.use(world, playerEntity, hand);
    }
}
