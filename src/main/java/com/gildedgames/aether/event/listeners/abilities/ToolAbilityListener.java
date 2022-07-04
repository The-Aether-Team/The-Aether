package com.gildedgames.aether.event.listeners.abilities;

import com.gildedgames.aether.event.hooks.AbilityHooks;
import com.gildedgames.aether.item.tools.abilities.GravititeTool;
import com.gildedgames.aether.item.tools.abilities.HolystoneTool;
import com.gildedgames.aether.item.tools.abilities.ZaniteTool;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ToolAbilityListener {
    @SubscribeEvent
    public static void doHolystoneAbility(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Level level = player.getLevel();
        BlockPos blockPos = event.getPos();
        ItemStack itemStack = player.getMainHandItem();
        HolystoneTool.dropAmbrosium(player, level, blockPos, itemStack);
    }

    @SubscribeEvent
    public static void doZaniteAbility(PlayerEvent.BreakSpeed event) {
        Player player = event.getPlayer();
        BlockState blockState = event.getState();
        ItemStack itemStack = player.getMainHandItem();
        Level level = player.getLevel();
        event.setNewSpeed(ZaniteTool.increaseSpeed(itemStack, event.getNewSpeed()));
        event.setNewSpeed(AbilityHooks.ToolHooks.reduceToolEffectiveness(level, blockState, itemStack, event.getNewSpeed()));
    }

    @SubscribeEvent
    public static void doGravititeAbility(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getWorld();
        BlockPos blockPos = event.getPos();
        ItemStack itemStack = event.getItemStack();
        BlockState blockState = level.getBlockState(blockPos);
        Player player = event.getPlayer();
        InteractionHand interactionHand = event.getHand();
        if (GravititeTool.floatBlock(level, blockPos, itemStack, blockState, player, interactionHand)) {
            event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void setupToolModifications(BlockEvent.BlockToolModificationEvent event) {
        LevelAccessor levelAccessor = event.getWorld();
        BlockPos pos = event.getPos();
        BlockState oldState = event.getState();
        ToolAction toolAction = event.getToolAction();
        BlockState newState = AbilityHooks.ToolHooks.setupToolActions(levelAccessor, pos, oldState, toolAction);
        if (newState != oldState && !event.isSimulated()) {
            event.setFinalState(newState);
        }
    }
    @SubscribeEvent
    public static void doGoldenOakStripping(BlockEvent.BlockToolModificationEvent event) {
        LevelAccessor levelAccessor = event.getWorld();
        BlockState oldState = event.getState();
        ItemStack itemStack = event.getHeldItemStack();
        ToolAction toolAction = event.getToolAction();
        UseOnContext context = event.getContext();
        if (!event.isSimulated()) {
            AbilityHooks.ToolHooks.stripGoldenOak(levelAccessor, oldState, itemStack, toolAction, context);
        }
    }
}
