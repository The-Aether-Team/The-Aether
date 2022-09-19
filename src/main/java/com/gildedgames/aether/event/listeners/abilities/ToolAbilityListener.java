package com.gildedgames.aether.event.listeners.abilities;

import com.gildedgames.aether.event.hooks.AbilityHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ToolAbilityListener {
    @SubscribeEvent
    public static void setupToolModifications(BlockEvent.BlockToolModificationEvent event) {
        LevelAccessor levelAccessor = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState oldState = event.getState();
        ToolAction toolAction = event.getToolAction();
        BlockState newState = AbilityHooks.ToolHooks.setupToolActions(levelAccessor, pos, oldState, toolAction);
        if (newState != oldState && !event.isSimulated()) {
            event.setFinalState(newState);
        }
    }

    /**
     * @see AbilityHooks.ToolHooks#handleHolystoneToolAbility(Player, Level, BlockPos, ItemStack) 
     */
    @SubscribeEvent
    public static void doHolystoneAbility(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Level level = player.getLevel();
        BlockPos blockPos = event.getPos();
        ItemStack itemStack = player.getMainHandItem();
        AbilityHooks.ToolHooks.handleHolystoneToolAbility(player, level, blockPos, itemStack);
    }
    
    /**
     * @see AbilityHooks.ToolHooks#handleZaniteToolAbility(ItemStack, float)
     * @see AbilityHooks.ToolHooks#reduceToolEffectiveness(BlockState, ItemStack, float)
     */
    @SubscribeEvent
    public static void modifyBreakSpeed(PlayerEvent.BreakSpeed event) {
        BlockState blockState = event.getState();
        Player player = event.getEntity();
        ItemStack itemStack = player.getMainHandItem();
        event.setNewSpeed(AbilityHooks.ToolHooks.handleZaniteToolAbility(itemStack, event.getNewSpeed()));
        event.setNewSpeed(AbilityHooks.ToolHooks.reduceToolEffectiveness(blockState, itemStack, event.getNewSpeed()));
    }

    /**
     * @see AbilityHooks.ToolHooks#handleGravititeToolAbility(Level, BlockPos, ItemStack, BlockState, Player, InteractionHand) 
     */
    @SubscribeEvent
    public static void doGravititeAbility(BlockEvent.BlockToolModificationEvent event) {
        Level level = event.getContext().getLevel();
        BlockPos blockPos = event.getPos();
        ItemStack itemStack = event.getContext().getItemInHand();
        BlockState blockState = event.getState();
        Player player = event.getPlayer();
        InteractionHand interactionHand = event.getContext().getHand();
        if (AbilityHooks.ToolHooks.handleGravititeToolAbility(level, blockPos, itemStack, blockState, player, interactionHand)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void doGoldenOakStripping(BlockEvent.BlockToolModificationEvent event) {
        LevelAccessor levelAccessor = event.getLevel();
        BlockState oldState = event.getState();
        ItemStack itemStack = event.getHeldItemStack();
        ToolAction toolAction = event.getToolAction();
        UseOnContext context = event.getContext();
        if (!event.isSimulated()) {
            AbilityHooks.ToolHooks.stripGoldenOak(levelAccessor, oldState, itemStack, toolAction, context);
        }
    }
}
