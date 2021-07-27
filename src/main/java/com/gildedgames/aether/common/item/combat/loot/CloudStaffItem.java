package com.gildedgames.aether.common.item.combat.loot;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.entity.miscellaneous.CloudMinionEntity;
import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CloudStaffItem extends Item
{
    public CloudStaffItem() {
        super(new Item.Properties().durability(60).rarity(AetherItems.AETHER_LOOT).tab(AetherItemGroups.AETHER_MISC));
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand hand) {
        ItemStack heldItem = playerIn.getItemInHand(hand);
        IAetherPlayer.get(playerIn).ifPresent(aetherPlayer -> {
            if (aetherPlayer.getCloudMinionEntities().isEmpty()) {
                playerIn.swing(hand);
                if (!worldIn.isClientSide) {
                    if (!playerIn.abilities.instabuild) {
                        heldItem.hurtAndBreak(1, playerIn, (p) -> p.broadcastBreakEvent(hand));
                    }
                    CloudMinionEntity cloudMinionRight = new CloudMinionEntity(worldIn, playerIn, HandSide.RIGHT);
                    CloudMinionEntity cloudMinionLeft = new CloudMinionEntity(worldIn, playerIn, HandSide.LEFT);
                    worldIn.addFreshEntity(cloudMinionRight);
                    worldIn.addFreshEntity(cloudMinionLeft);
                    aetherPlayer.setCloudMinions(cloudMinionRight, cloudMinionLeft);
                }
            } else if (playerIn.isShiftKeyDown()) {
                for (CloudMinionEntity cloudMinionEntity : aetherPlayer.getCloudMinionEntities()) {
                    cloudMinionEntity.setLifeSpan(0);
                }
            }
        });
        return ActionResult.pass(heldItem);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if (entity instanceof PlayerEntity) {
            IAetherPlayer.get((PlayerEntity) entity).ifPresent(aetherPlayer -> {
                if (!aetherPlayer.getCloudMinionEntities().isEmpty()) {
                    if (!aetherPlayer.getPlayer().getCooldowns().isOnCooldown(this) && aetherPlayer.isHitting()) {
                        CloudMinionEntity cloudMinionRight = aetherPlayer.getCloudMinionEntities().get(0);
                        if (cloudMinionRight != null) {
                            cloudMinionRight.setShouldShoot(true);
                        }
                        CloudMinionEntity cloudMinionLeft = aetherPlayer.getCloudMinionEntities().get(1);
                        if (cloudMinionLeft != null) {
                            cloudMinionLeft.setShouldShoot(true);
                        }
                        aetherPlayer.getPlayer().getCooldowns().addCooldown(this, 40);
                    }
                }
            });
        }
        return super.onEntitySwing(stack, entity);
    }

    @Override
    public boolean canAttackBlock(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        return !player.isCreative();
    }
}
