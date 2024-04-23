package com.aetherteam.aether.item.combat.loot;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.capability.player.AetherPlayerCapability;
import com.aetherteam.aether.entity.EntityUtil;
import com.aetherteam.aether.entity.miscellaneous.CloudMinion;
import com.aetherteam.aether.item.AetherItems;
import io.github.fabricators_of_create.porting_lib.item.EntitySwingListenerItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class CloudStaffItem extends Item implements EntitySwingListenerItem {
    public CloudStaffItem() {
        super(new Item.Properties().durability(60).rarity(AetherItems.AETHER_LOOT));
    }

    /**
     * Summons two Cloud Minions if the player has none summoned, spawning particles and taking 1 durability off the item. If the player already has Cloud Minions, nothing happens. <br><br>
     * If the player shifts and right clicks, the Cloud Minions are despawned.<br><br>
     * The tracking for whether Cloud Minions are summoned or not is handled with {@link AetherPlayerCapability#setCloudMinions(CloudMinion, CloudMinion)} and {@link AetherPlayerCapability#getCloudMinions()}.
     * @param level The {@link Level} of the user.
     * @param player The {@link Player} using this item.
     * @param hand The {@link InteractionHand} in which the item is being used.
     * @return Pass (do nothing) (we handle swing behavior manually in the lambda earlier on). This is an {@link InteractionResultHolder InteractionResultHolder&lt;ItemStack&gt;}.
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);
        AetherPlayer.getOptional(player).ifPresent(aetherPlayer -> {
            Player innerPlayer = aetherPlayer.getPlayer();
            Level innerLevel = innerPlayer.level();
            if (aetherPlayer.getCloudMinions().isEmpty()) {
                innerPlayer.swing(hand);
                if (!innerLevel.isClientSide()) {
                    if (!innerPlayer.getAbilities().instabuild) {
                        heldItem.hurtAndBreak(1, innerPlayer, (p) -> p.broadcastBreakEvent(hand));
                    }
                    CloudMinion cloudMinionRight = new CloudMinion(innerLevel, innerPlayer, HumanoidArm.RIGHT);
                    CloudMinion cloudMinionLeft = new CloudMinion(innerLevel, innerPlayer, HumanoidArm.LEFT);
                    innerLevel.addFreshEntity(cloudMinionRight);
                    innerLevel.addFreshEntity(cloudMinionLeft);
                    aetherPlayer.setCloudMinions(cloudMinionRight, cloudMinionLeft);
                }
                this.spawnExplosionParticles(innerPlayer);
            } else if (innerPlayer.isShiftKeyDown()) {
                innerPlayer.swing(hand);
                for (CloudMinion cloudMinion : aetherPlayer.getCloudMinions()) {
                    cloudMinion.setLifeSpan(0);
                }
            }
        });
        return InteractionResultHolder.pass(heldItem);
    }

    /**
     * Sets that the cloud minions should shoot and sets a 40 tick cooldown on the item. The actual shooting behavior is handled in {@link CloudMinion#tick()}.
     * @param stack The {@link ItemStack} being swung.
     * @param entity The swinging {@link LivingEntity}.
     * @return Whether the item was successfully swung (we don't change this, so it uses the superclass' behavior), as a {@link Boolean}.
     */
    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if (entity instanceof Player player) {
            AetherPlayer.getOptional(player).ifPresent(aetherPlayer -> {
                if (!aetherPlayer.getPlayer().getCooldowns().isOnCooldown(this) && aetherPlayer.isHitting()) {
                    boolean hasMinions = false;
                    for (int i = 0; i < aetherPlayer.getCloudMinions().size(); i++) {
                        CloudMinion cloudMinion = aetherPlayer.getCloudMinions().get(i);
                        if (cloudMinion != null) {
                            cloudMinion.setShouldShoot(true);
                            hasMinions = true;
                        }
                    }
                    if (hasMinions && !aetherPlayer.getPlayer().getAbilities().instabuild) {
                        aetherPlayer.getPlayer().getCooldowns().addCooldown(this, AetherConfig.SERVER.cloud_staff_cooldown.get());
                    }
                }
            });
        }
        return false;
    }

    /**
     * @see EntityUtil#spawnSummoningExplosionParticles(Entity)
     * @param player The {@link Player} to spawn the particles at.
     */
    private void spawnExplosionParticles(Player player) {
        if (player.level().isClientSide()) {
            EntityUtil.spawnSummoningExplosionParticles(player);
        }
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return !player.isCreative();
    }
}
