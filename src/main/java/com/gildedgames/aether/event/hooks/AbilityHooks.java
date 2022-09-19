package com.gildedgames.aether.event.hooks;

import com.gildedgames.aether.entity.projectile.PoisonNeedle;
import com.gildedgames.aether.entity.projectile.dart.EnchantedDart;
import com.gildedgames.aether.entity.projectile.dart.GoldenDart;
import com.gildedgames.aether.entity.projectile.dart.PoisonDart;
import com.gildedgames.aether.item.accessories.gloves.GlovesItem;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.item.AetherItems;
import com.gildedgames.aether.item.tools.abilities.GravititeTool;
import com.gildedgames.aether.item.tools.abilities.HolystoneTool;
import com.gildedgames.aether.item.tools.abilities.ZaniteTool;
import com.gildedgames.aether.loot.AetherLoot;
import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.data.resources.AetherDimensions;
import com.gildedgames.aether.AetherConfig;
import com.gildedgames.aether.capability.arrow.PhoenixArrow;
import com.gildedgames.aether.capability.lightning.LightningTracker;
import com.gildedgames.aether.capability.player.AetherPlayer;
import com.gildedgames.aether.loot.AetherLootContexts;
import com.gildedgames.aether.util.EquipmentUtil;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;
import java.util.Map;

public class AbilityHooks {
    public static class AccessoryHooks {
        public static void damageGloves(Player player, Entity target) {
            if (!player.level.isClientSide() && target instanceof LivingEntity livingTarget) {
                if (livingTarget.isAttackable() && !livingTarget.skipAttackInteraction(player)) {
                    CuriosApi.getCuriosHelper().findFirstCurio(player, (stack) -> stack.getItem() instanceof GlovesItem).ifPresent((slotResult) -> slotResult.stack().hurtAndBreak(1, player, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND)));
                }
            }
        }
    }

    public static class ArmorHooks {
        public static boolean fallCancellation(LivingEntity entity) {
            return entity.getItemBySlot(EquipmentSlot.FEET).is(AetherItems.SENTRY_BOOTS.get()) || EquipmentUtil.hasFullGravititeSet(entity) || EquipmentUtil.hasFullValkyrieSet(entity);
        }
    }

    public static class ToolHooks {
        public static final Map<Block, Block> STRIPPABLES = (new ImmutableMap.Builder<Block, Block>())
                .put(AetherBlocks.SKYROOT_LOG.get(), AetherBlocks.STRIPPED_SKYROOT_LOG.get())
                .put(AetherBlocks.GOLDEN_OAK_LOG.get(), AetherBlocks.STRIPPED_SKYROOT_LOG.get())
                .put(AetherBlocks.SKYROOT_WOOD.get(), AetherBlocks.STRIPPED_SKYROOT_WOOD.get())
                .put(AetherBlocks.GOLDEN_OAK_WOOD.get(), AetherBlocks.STRIPPED_SKYROOT_WOOD.get())
                .build();

        public static final Map<Block, Block> FLATTENABLES = (new ImmutableMap.Builder<Block, Block>())
                .put(AetherBlocks.AETHER_GRASS_BLOCK.get(), AetherBlocks.AETHER_DIRT_PATH.get())
                .put(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get(), AetherBlocks.AETHER_DIRT_PATH.get())
                .put(AetherBlocks.AETHER_DIRT.get(), AetherBlocks.AETHER_DIRT_PATH.get())
                .build();

        public static final Map<Block, Block> TILLABLES = (new ImmutableMap.Builder<Block, Block>())
                .put(AetherBlocks.AETHER_DIRT.get(), AetherBlocks.AETHER_FARMLAND.get())
                .put(AetherBlocks.AETHER_GRASS_BLOCK.get(), AetherBlocks.AETHER_FARMLAND.get())
                .put(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get(), AetherBlocks.AETHER_FARMLAND.get())
                .put(AetherBlocks.AETHER_DIRT_PATH.get(), AetherBlocks.AETHER_FARMLAND.get())
                .build();

        public static BlockState setupToolActions(LevelAccessor accessor, BlockPos pos, BlockState old, ToolAction action) {
            Block oldBlock = old.getBlock();
            if (action == ToolActions.AXE_STRIP) {
                if (STRIPPABLES.containsKey(oldBlock)) {
                    return STRIPPABLES.get(oldBlock).withPropertiesOf(old);
                }
            } else if (action == ToolActions.SHOVEL_FLATTEN) {
                if (FLATTENABLES.containsKey(oldBlock)) {
                    return FLATTENABLES.get(oldBlock).withPropertiesOf(old);
                }
            } else if (action == ToolActions.HOE_TILL) {
                if (accessor.getBlockState(pos.above()).isAir()) {
                    if (TILLABLES.containsKey(oldBlock)) {
                        return TILLABLES.get(oldBlock).withPropertiesOf(old);
                    }
                }
            }
            return old;
        }

        /**
         * Handles ability for {@link com.gildedgames.aether.item.tools.abilities.HolystoneTool}.
         * @see HolystoneTool#dropAmbrosium(Player, Level, BlockPos)
         */
        public static void handleHolystoneToolAbility(Player player, Level level, BlockPos pos, ItemStack stack) {
            if (stack.getItem() instanceof HolystoneTool holystoneTool) {
                holystoneTool.dropAmbrosium(player, level, pos);
            }
        }

        /**
         * Handles ability for {@link com.gildedgames.aether.item.tools.abilities.ZaniteTool}.<br>
         * @see ZaniteTool#increaseSpeed(ItemStack, float)
         */
        public static float handleZaniteToolAbility(ItemStack stack, float speed) {
            if (stack.getItem() instanceof ZaniteTool zaniteTool) {
                return zaniteTool.increaseSpeed(stack, speed);
            }
            return speed;
        }

        /**
         * Handles ability for {@link com.gildedgames.aether.item.tools.abilities.GravititeTool}.
         * @see GravititeTool#floatBlock(Level, BlockPos, ItemStack, BlockState, Player, InteractionHand)
         */
        public static boolean handleGravititeToolAbility(Level level, BlockPos pos, ItemStack stack, BlockState state, Player player, InteractionHand hand) {
            if (stack.getItem() instanceof GravititeTool gravititeTool) {
                return gravititeTool.floatBlock(level, pos, stack, state, player, hand);
            }
            return false;
        }

        public static float reduceToolEffectiveness(Level level, BlockState state, ItemStack stack, float amount) { //todo wait did i ever even add functionality for this for aether tools to have regular effectiveness
            if (AetherConfig.COMMON.tools_debuff.get()) {
                if (level.dimension() == AetherDimensions.AETHER_LEVEL) {
                    if (!stack.isEmpty() && !stack.is(AetherTags.Items.EFFECTIVE_IN_AETHER) && stack.isCorrectToolForDrops(state)) {
                        amount = (float) Math.pow(amount, -0.2);
                    }
                }
            }
            return amount;
        }

        public static void stripGoldenOak(LevelAccessor accessor, BlockState state, ItemStack stack, ToolAction action, UseOnContext context) {
            if (action == ToolActions.AXE_STRIP) {
                if (accessor instanceof Level level) {
                    if (state.is(AetherTags.Blocks.GOLDEN_OAK_LOGS)) {
                        if (level.getServer() != null) {
                            Vec3 vector = context.getClickLocation();
                            LootContext.Builder lootContext = new LootContext.Builder((ServerLevel) level)
                                    .withParameter(LootContextParams.BLOCK_STATE, state)
                                    .withParameter(LootContextParams.ORIGIN, vector)
                                    .withParameter(LootContextParams.TOOL, stack);
                            LootTable loottable = level.getServer().getLootTables().get(AetherLoot.STRIP_GOLDEN_OAK);
                            List<ItemStack> list = loottable.getRandomItems(lootContext.create(AetherLootContexts.STRIPPING));
                            for (ItemStack itemStack : list) {
                                ItemEntity itemEntity = new ItemEntity(level, vector.x(), vector.y(), vector.z(), itemStack);
                                itemEntity.setDefaultPickUpDelay();
                                level.addFreshEntity(itemEntity);
                            }
                        }
                    }
                }
            }
        }
    }

    public static class WeaponHooks {
        public static void phoenixArrowHit(HitResult result, Projectile projectile) {
            if (result.getType() == HitResult.Type.ENTITY && result instanceof EntityHitResult entityHitResult && projectile instanceof AbstractArrow abstractArrow) {
                Entity impactedEntity = entityHitResult.getEntity();
                PhoenixArrow.get(abstractArrow).ifPresent(phoenixArrow -> {
                    if (phoenixArrow.isPhoenixArrow() && phoenixArrow.getFireTime() > 0) {
                        impactedEntity.setSecondsOnFire(phoenixArrow.getFireTime());
                    }
                });
            }
        }

        public static void stickDart(LivingEntity entity, DamageSource source) {
            if (entity instanceof Player player && !player.level.isClientSide()) {
                Entity sourceEntity = source.getDirectEntity();
                if (sourceEntity instanceof GoldenDart) {
                    AetherPlayer.get(player).ifPresent(aetherPlayer -> aetherPlayer.setGoldenDartCount(aetherPlayer.getGoldenDartCount() + 1));
                } else if (sourceEntity instanceof PoisonDart || sourceEntity instanceof PoisonNeedle) {
                    AetherPlayer.get(player).ifPresent(aetherPlayer -> aetherPlayer.setPoisonDartCount(aetherPlayer.getPoisonDartCount() + 1));
                } else if (sourceEntity instanceof EnchantedDart) {
                    AetherPlayer.get(player).ifPresent(aetherPlayer -> aetherPlayer.setEnchantedDartCount(aetherPlayer.getEnchantedDartCount() + 1));
                }
            }
        }

        public static void lightningTracking(EntityStruckByLightningEvent event, Entity entity, LightningBolt lightning) {
            if (entity instanceof LivingEntity livingEntity) {
                LightningTracker.get(lightning).ifPresent(lightningTracker -> {
                    if (lightningTracker.getOwner() != null) {
                        if (livingEntity == lightningTracker.getOwner() || livingEntity == lightningTracker.getOwner().getVehicle()) {
                            event.setCanceled(true);
                        }
                    }
                });
            }
        }
    }
}
