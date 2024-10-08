package com.aetherteam.aether.item.combat.loot;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.attachment.AetherPlayerAttachment;
import com.aetherteam.aether.entity.EntityUtil;
import com.aetherteam.aether.entity.miscellaneous.CloudMinion;
import com.aetherteam.aether.item.AetherItems;
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

public class CloudStaffItem extends Item {
    public CloudStaffItem() {
        super(new Item.Properties().durability(60).rarity(AetherItems.AETHER_LOOT));
    }

    /**
     * Summons two Cloud Minions if the player has none summoned, spawning particles and taking 1 durability off the item. If the player already has Cloud Minions, nothing happens. <br><br>
     * If the player shifts and right clicks, the Cloud Minions are despawned.<br><br>
     * The tracking for whether Cloud Minions are summoned or not is handled with {@link AetherPlayerAttachment#setCloudMinions(CloudMinion, CloudMinion)} and {@link AetherPlayerAttachment#getCloudMinions()}.
     *
     * @param level  The {@link Level} of the user.
     * @param player The {@link Player} using this item.
     * @param hand   The {@link InteractionHand} in which the item is being used.
     * @return Pass (do nothing) (we handle swing behavior manually in the lambda earlier on). This is an {@link InteractionResultHolder InteractionResultHolder&lt;ItemStack&gt;}.
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);
        var data = player.getData(AetherDataAttachments.AETHER_PLAYER);
        if (data.getCloudMinions().isEmpty()) {
            player.swing(hand);
            if (!level.isClientSide()) {
                if (!player.getAbilities().instabuild) {
                    heldItem.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
                }
                CloudMinion cloudMinionRight = new CloudMinion(level, player, HumanoidArm.RIGHT);
                CloudMinion cloudMinionLeft = new CloudMinion(level, player, HumanoidArm.LEFT);
                level.addFreshEntity(cloudMinionRight);
                level.addFreshEntity(cloudMinionLeft);
                data.setCloudMinions(player, cloudMinionRight, cloudMinionLeft);
            }
            this.spawnExplosionParticles(player);
        } else if (player.isShiftKeyDown()) {
            player.swing(hand);
            for (CloudMinion cloudMinion : data.getCloudMinions()) {
                cloudMinion.setLifeSpan(0);
            }
        }
        return InteractionResultHolder.pass(heldItem);
    }

    /**
     * Sets that the cloud minions should shoot and sets a 40 tick cooldown on the item. The actual shooting behavior is handled in {@link CloudMinion#tick()}.
     *
     * @param stack  The {@link ItemStack} being swung.
     * @param entity The swinging {@link LivingEntity}.
     * @return Whether the item was successfully swung (we don't change this, so it uses the superclass' behavior), as a {@link Boolean}.
     */
    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if (entity instanceof Player player) {
            var data = player.getData(AetherDataAttachments.AETHER_PLAYER);
            if (!player.getCooldowns().isOnCooldown(this) && data.isHitting()) {
                boolean hasMinions = false;
                for (int i = 0; i < data.getCloudMinions().size(); i++) {
                    CloudMinion cloudMinion = data.getCloudMinions().get(i);
                    if (cloudMinion != null) {
                        cloudMinion.setShouldShoot(true);
                        hasMinions = true;
                    }
                }
                if (hasMinions && !player.getAbilities().instabuild) {
                    player.getCooldowns().addCooldown(this, AetherConfig.SERVER.cloud_staff_cooldown.get());
                }
            }
        }
        return super.onEntitySwing(stack, entity);
    }

    /**
     * @param player The {@link Player} to spawn the particles at.
     * @see EntityUtil#spawnSummoningExplosionParticles(Entity)
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
