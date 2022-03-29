package com.gildedgames.aether.common.event.listeners.abilities;

import com.gildedgames.aether.common.entity.block.FloatingBlockEntity;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.common.registry.AetherLoot;
import com.gildedgames.aether.common.registry.AetherTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class ToolAbilityListener {
    @SubscribeEvent
    public static void doGoldenOakStripping(PlayerInteractEvent.RightClickBlock event) { //TODO: Potentially  switch to use popResources
        Level world = event.getWorld();
        ItemStack stack = event.getItemStack();
        if (stack.getItem() instanceof AxeItem) {
            BlockState blockState = world.getBlockState(event.getPos());
            if (blockState.is(AetherTags.Blocks.GOLDEN_OAK_LOGS)) {
                if (world.getServer() != null) {
                    Vec3 vector = event.getHitVec().getLocation();
                    LootContext.Builder lootContext = new LootContext.Builder((ServerLevel) world)
                            .withParameter(LootContextParams.BLOCK_STATE, blockState)
                            .withParameter(LootContextParams.ORIGIN, vector)
                            .withParameter(LootContextParams.TOOL, stack);
                    LootTable loottable = world.getServer().getLootTables().get(AetherLoot.STRIP_GOLDEN_OAK);
                    List<ItemStack> list = loottable.getRandomItems(lootContext.create(AetherLoot.STRIPPING));

                    for(ItemStack itemstack : list) {
                        ItemEntity itementity = new ItemEntity(world, vector.x(), vector.y(), vector.z(), itemstack);
                        world.addFreshEntity(itementity);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doHolystoneAbility(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Level level = player.getLevel();
        BlockPos blockPos = event.getPos();
        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.is(AetherTags.Items.HOLYSTONE_TOOLS)) {
            if (!level.isClientSide && player.random.nextInt(100) <= 5) {
                ItemEntity itementity = new ItemEntity(level, blockPos.getX() + 0.5F, blockPos.getY() + 0.5F, blockPos.getZ() + 0.5F, new ItemStack(AetherItems.AMBROSIUM_SHARD.get()));
                level.addFreshEntity(itementity);
            }
        }
    }

    @SubscribeEvent
    public static void doZaniteAbility(PlayerEvent.BreakSpeed event) {
        Player player = event.getPlayer();
        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.is(AetherTags.Items.ZANITE_TOOLS)) {
            event.setNewSpeed(calculateIncrease(itemStack, event.getOriginalSpeed()));
        }
    }

    private static float calculateIncrease(ItemStack tool, float original) { //TODO: This is dependent on the zanite tool durability amount which isn't ideal if this is intended to be hooked into with tags.
        if (original == 6.0F) {
            int current = tool.getDamageValue();
            int maxDamage = tool.getMaxDamage();

            if (maxDamage - 50 <= current && current <= maxDamage) {
                return 12.0F;
            } else if (maxDamage - 110 <= current && current <= maxDamage - 51) {
                return 8.0F;
            } else if (maxDamage - 200 <= current && current <= maxDamage - 111) {
                return 6.0F;
            } else if (maxDamage - 239 <= current && current <= maxDamage - 201) {
                return 4.0F;
            } else {
                return 2.0F;
            }
        } else {
            return 1.0F;
        }
    }

    @SubscribeEvent
    public static void doGravititeAbility(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getWorld();
        BlockPos blockPos = event.getPos();
        ItemStack itemStack = event.getItemStack();
        BlockState blockState = level.getBlockState(blockPos);
        Player player = event.getPlayer();
        InteractionHand interactionHand = event.getHand();
        if (itemStack.is(AetherTags.Items.GRAVITITE_TOOLS)) {
            if (player != null && !player.isShiftKeyDown()) {
                startFloatBlock(level, blockPos, itemStack, blockState, player, interactionHand);
                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
                event.setCanceled(true);
            }
        }
    }

    private static void startFloatBlock(Level level, BlockPos pos, ItemStack stack, BlockState state, Player player, InteractionHand hand) {
        if (stack.getItem() instanceof TieredItem tieredItem) {
            float destroySpeed = stack.getDestroySpeed(state);
            float efficiency = tieredItem.getTier().getSpeed();
            floatBlock(level, pos, stack, state, player, hand, destroySpeed, efficiency);
        }
    }

    private static void floatBlock(Level level, BlockPos pos, ItemStack stack, BlockState state, Player player, InteractionHand hand, float destroySpeed, float efficiency) {
        if ((destroySpeed == efficiency || stack.isCorrectToolForDrops(state)) && level.isEmptyBlock(pos.above())) {
            if (level.getBlockEntity(pos) == null && state.getDestroySpeed(level, pos) >= 0.0F && !state.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF) && !state.is(AetherTags.Blocks.GRAVITITE_ABILITY_BLACKLIST)) {
                if (!level.isClientSide) {
                    FloatingBlockEntity entity = new FloatingBlockEntity(level, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, state);
                    if (state.is(BlockTags.ANVIL)) {
                        entity.setHurtsEntities(2.0F, 40);
                    }
                    level.addFreshEntity(entity);
                    stack.hurtAndBreak(4, player, (p) -> p.broadcastBreakEvent(hand));
                }
            }
        }
    }
}
