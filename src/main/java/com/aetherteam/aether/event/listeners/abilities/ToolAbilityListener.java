package com.aetherteam.aether.event.listeners.abilities;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.AbilityHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Aether.MODID)
public class ToolAbilityListener {
    public static InteractionHand INTERACTION_HAND;

    /**
     * @see AbilityHooks.ToolHooks#setupToolActions(LevelAccessor, BlockPos, BlockState, ToolAction)
     */
    @SubscribeEvent
    public static void setupToolModifications(BlockEvent.BlockToolModificationEvent event) {
        LevelAccessor levelAccessor = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState oldState = event.getState();
        ToolAction toolAction = event.getToolAction();
        BlockState newState = AbilityHooks.ToolHooks.setupToolActions(levelAccessor, pos, oldState, toolAction);
        if (newState != oldState && !event.isSimulated() && !event.isCanceled()) {
            event.setFinalState(newState);
        }
    }

    /**
     * @see AbilityHooks.ToolHooks#handleHolystoneToolAbility(Player, Level, BlockPos, ItemStack, BlockState)
     */
    @SubscribeEvent
    public static void doHolystoneAbility(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Level level = player.level();
        BlockPos blockPos = event.getPos();
        ItemStack itemStack = player.getMainHandItem();
        BlockState blockState = event.getState();
        if (!event.isCanceled()) {
            AbilityHooks.ToolHooks.handleHolystoneToolAbility(player, level, blockPos, itemStack, blockState);
        }
    }
    
    /**
     * @see AbilityHooks.ToolHooks#handleZaniteToolAbility(ItemStack, float)
     * @see AbilityHooks.ToolHooks#reduceToolEffectiveness(Player, BlockState, ItemStack, float)
     */
    @SubscribeEvent
    public static void modifyBreakSpeed(PlayerEvent.BreakSpeed event) {
        BlockState blockState = event.getState();
        Player player = event.getEntity();
        ItemStack itemStack = player.getMainHandItem();
        if (!event.isCanceled()) {
            event.setNewSpeed(AbilityHooks.ToolHooks.handleZaniteToolAbility(itemStack, event.getNewSpeed()));
            event.setNewSpeed(AbilityHooks.ToolHooks.reduceToolEffectiveness(player, blockState, itemStack, event.getNewSpeed()));
        }
    }

    /**
     * @see AbilityHooks.ToolHooks#stripGoldenOak(LevelAccessor, BlockState, ItemStack, ToolAction, UseOnContext)
     */
    @SubscribeEvent
    public static void doGoldenOakStripping(BlockEvent.BlockToolModificationEvent event) {
        LevelAccessor levelAccessor = event.getLevel();
        BlockState oldState = event.getState();
        ItemStack itemStack = event.getHeldItemStack();
        ToolAction toolAction = event.getToolAction();
        UseOnContext context = event.getContext();
        if (!event.isSimulated() && !event.isCanceled()) {
            AbilityHooks.ToolHooks.stripGoldenOak(levelAccessor, oldState, itemStack, toolAction, context);
        }
    }

    /**
     * @see com.aetherteam.aether.mixin.mixins.common.LivingEntityMixin#modifyReachAttribute(double, Attribute)
     */
    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        INTERACTION_HAND = event.getHand();
    }
}
