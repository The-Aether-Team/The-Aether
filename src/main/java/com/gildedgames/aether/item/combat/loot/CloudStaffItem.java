package com.gildedgames.aether.item.combat.loot;

import com.gildedgames.aether.AetherConfig;
import com.gildedgames.aether.capability.player.AetherPlayerCapability;
import com.gildedgames.aether.entity.miscellaneous.CloudMinion;
import com.gildedgames.aether.item.AetherItems;
import com.gildedgames.aether.capability.player.AetherPlayer;
import com.gildedgames.aether.util.EntityUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class CloudStaffItem extends Item {
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
        AetherPlayer.get(player).ifPresent(aetherPlayer -> {
            if (aetherPlayer.getCloudMinions().isEmpty()) {
                player.swing(hand);
                if (!level.isClientSide()) {
                    if (!player.getAbilities().instabuild) {
                        heldItem.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
                    }
                    CloudMinion cloudMinionRight = new CloudMinion(level, player, HumanoidArm.RIGHT);
                    CloudMinion cloudMinionLeft = new CloudMinion(level, player, HumanoidArm.LEFT);
                    level.addFreshEntity(cloudMinionRight);
                    level.addFreshEntity(cloudMinionLeft);
                    aetherPlayer.setCloudMinions(cloudMinionRight, cloudMinionLeft);
                }
                this.spawnExplosionParticles(player);
            } else if (player.isShiftKeyDown()) {
                player.swing(hand);
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
            AetherPlayer.get(player).ifPresent(aetherPlayer -> {
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
                        aetherPlayer.getPlayer().getCooldowns().addCooldown(this, AetherConfig.COMMON.cloud_staff_cooldown.get());
                    }
                }
            });
        }
        return super.onEntitySwing(stack, entity);
    }

    /**
     * @see EntityUtil#spawnSummoningExplosionParticles(Entity)
     * @param player The {@link Player} to spawn the particles at.
     */
    private void spawnExplosionParticles(Player player) {
        if (player.getLevel().isClientSide()) {
            EntityUtil.spawnSummoningExplosionParticles(player);
        }
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return !player.isCreative();
    }

    /**
     * When in a creative tab, this adds a tooltip to an item indicating what dungeon it can be found in.
     * @param stack The {@link ItemStack} with the tooltip.
     * @param level The {@link Level} the item is rendered in.
     * @param components A {@link List} of {@link Component}s making up this item's tooltip.
     * @param flag A {@link TooltipFlag} for the tooltip type.
     */
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(stack, level, components, flag);
        if (flag.isCreative()) {
            components.add(AetherItems.BRONZE_DUNGEON_TOOLTIP);
        }
    }
}
