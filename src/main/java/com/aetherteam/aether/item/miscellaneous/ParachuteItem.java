package com.aetherteam.aether.item.miscellaneous;

import com.aetherteam.aether.entity.miscellaneous.Parachute;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class ParachuteItem extends Item {
    /**
     * The {@link Parachute} that this item can spawn in.
     */
    protected final Supplier<? extends EntityType<? extends Parachute>> parachuteEntity;

    public ParachuteItem(Supplier<? extends EntityType<? extends Parachute>> parachuteEntity, Properties properties) {
        super(properties);
        this.parachuteEntity = parachuteEntity;
    }

    /**
     * Spawns in a {@link Parachute} when used dependent on various conditions.
     * @param level The {@link Level} of the user.
     * @param player The {@link Player} using this item.
     * @param hand The {@link InteractionHand} in which the item is being used.
     * @return a {@link InteractionResultHolder#sidedSuccess(Object, boolean)} (success on client, consume on server) if the Parachute is successfully spawned. Otherwise, return a pass.
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);
        if (!player.isOnGround() && !player.isInFluidType() && !player.isShiftKeyDown()) { // Player has to be on ground and can't be in liquid, and also can't be holding shift.
            Entity entity = this.getParachuteEntity().get().create(level);
            if (entity instanceof Parachute parachute) {
                parachute.setPos(player.getX(), player.getY() - 1.0, player.getZ()); // Spawn Parachute below player.
                parachute.setDeltaMovement(player.getDeltaMovement());
                if (player.isPassenger()) {
                    if (player.getVehicle() instanceof Parachute) { // Using a Parachute while already having one will switch to the new one.
                        player.getVehicle().ejectPassengers();
                    } else {
                        return InteractionResultHolder.pass(heldStack);
                    }
                }
                if (!level.isClientSide()) { // Spawn Parachute and damage item (or automatically break for Cold Parachutes since they have 1 durability).
                    level.addFreshEntity(parachute);
                    player.startRiding(parachute);
                    heldStack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
                }
                parachute.spawnExplosionParticle();
                player.awardStat(Stats.ITEM_USED.get(this));
                return InteractionResultHolder.sidedSuccess(heldStack, level.isClientSide());
            }
        }
        return InteractionResultHolder.pass(heldStack);
    }

    public Supplier<? extends EntityType<? extends Parachute>> getParachuteEntity() {
        return this.parachuteEntity;
    }
}