package com.gildedgames.aether.item.combat.loot;

import com.gildedgames.aether.entity.miscellaneous.CloudMinion;
import com.gildedgames.aether.item.AetherItemGroups;
import com.gildedgames.aether.item.AetherItems;
import com.gildedgames.aether.capability.player.AetherPlayer;
import com.gildedgames.aether.util.EntityUtil;
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

public class CloudStaffItem extends Item
{
    public CloudStaffItem() {
        super(new Item.Properties().durability(60).rarity(AetherItems.AETHER_LOOT).tab(AetherItemGroups.AETHER_MISC));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand hand) {
        ItemStack heldItem = playerIn.getItemInHand(hand);
        AetherPlayer.get(playerIn).ifPresent(aetherPlayer -> {
            if (aetherPlayer.getCloudMinions().isEmpty()) {
                playerIn.swing(hand);
                if (!worldIn.isClientSide) {
                    if (!playerIn.getAbilities().instabuild) {
                        heldItem.hurtAndBreak(1, playerIn, (p) -> p.broadcastBreakEvent(hand));
                    }
                    CloudMinion cloudMinionRight = new CloudMinion(worldIn, playerIn, HumanoidArm.RIGHT);
                    CloudMinion cloudMinionLeft = new CloudMinion(worldIn, playerIn, HumanoidArm.LEFT);
                    worldIn.addFreshEntity(cloudMinionRight);
                    worldIn.addFreshEntity(cloudMinionLeft);
                    aetherPlayer.setCloudMinions(cloudMinionRight, cloudMinionLeft);
                }
                this.spawnExplosionParticles(playerIn);
            } else if (playerIn.isShiftKeyDown()) {
                playerIn.swing(hand);
                for (CloudMinion cloudMinion : aetherPlayer.getCloudMinions()) {
                    cloudMinion.setLifeSpan(0);
                }
            }
        });
        return InteractionResultHolder.pass(heldItem);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if (entity instanceof Player) {
            AetherPlayer.get((Player) entity).ifPresent(aetherPlayer -> {
                if (!aetherPlayer.getCloudMinions().isEmpty()) {
                    if (!aetherPlayer.getPlayer().getCooldowns().isOnCooldown(this) && aetherPlayer.isHitting()) {
                        CloudMinion cloudMinionRight = aetherPlayer.getCloudMinions().get(0);
                        if (cloudMinionRight != null) {
                            cloudMinionRight.setShouldShoot(true);
                        }
                        CloudMinion cloudMinionLeft = aetherPlayer.getCloudMinions().get(1);
                        if (cloudMinionLeft != null) {
                            cloudMinionLeft.setShouldShoot(true);
                        }
                        if (!aetherPlayer.getPlayer().getAbilities().instabuild) {
                            aetherPlayer.getPlayer().getCooldowns().addCooldown(this, 40);
                        }
                    }
                }
            });
        }
        return super.onEntitySwing(stack, entity);
    }

    private void spawnExplosionParticles(Player player) {
        if (player.level.isClientSide()) {
            EntityUtil.spawnSummoningExplosionParticles(player);
        }
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level worldIn, BlockPos pos, Player player) {
        return !player.isCreative();
    }
}
