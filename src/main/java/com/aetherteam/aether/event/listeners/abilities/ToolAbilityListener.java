package com.aetherteam.aether.event.listeners.abilities;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.AbilityHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.ToolAction;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

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
     * @see ToolAbilityListener#checkEntityTooFar(PlayerEvent, Entity, Player, InteractionHand)
     */
    @SubscribeEvent
    public static void onEntityAttack(AttackEntityEvent event) {
        checkEntityTooFar(event, event.getTarget(), event.getEntity(), InteractionHand.MAIN_HAND);
    }

    /**
     * @see ToolAbilityListener#checkEntityTooFar(PlayerEvent, Entity, Player, InteractionHand)
     */
    @SubscribeEvent
    public static void onEntityInteractSpecific(PlayerInteractEvent.EntityInteractSpecific event) {
        checkEntityTooFar(event, event.getTarget(), event.getEntity(), event.getHand());
    }

    /**
     * @see ToolAbilityListener#checkEntityTooFar(PlayerEvent, Entity, Player, InteractionHand)
     */
    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        checkEntityTooFar(event, event.getTarget(), event.getEntity(), event.getHand());
    }

    /**
     * @see ToolAbilityListener#checkBlockTooFar(PlayerEvent, Player, InteractionHand)
     */
    @SubscribeEvent
    public static void onEntityInteractRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        checkBlockTooFar(event, event.getEntity(), event.getHand());
    }

    /**
     * @see ToolAbilityListener#checkBlockTooFar(PlayerEvent, Player, InteractionHand)
     */
    @SubscribeEvent
    public static void onEntityInteractLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        checkBlockTooFar(event, event.getEntity(), event.getHand());
    }

    /**
     * @see com.aetherteam.aether.mixin.mixins.common.ItemMixin#getPlayerPOVHitResult(Level, Player, ClipContext.Fluid, CallbackInfoReturnable)
     */
    @SubscribeEvent
    public static void onEntityInteractRightClickItem(PlayerInteractEvent.RightClickItem event) {
        INTERACTION_HAND = event.getHand();
    }

    /**
     * Cancels the given event if the targeted entity is too far away.
     * @see AbilityHooks.ToolHooks#entityTooFar(Entity, Player, InteractionHand)
     */
    private static void checkEntityTooFar(PlayerEvent event, Entity target, Player player, InteractionHand hand) {
        if (event instanceof ICancellableEvent cancellable && !cancellable.isCanceled() && AbilityHooks.ToolHooks.entityTooFar(target, player, hand)) {
            cancellable.setCanceled(true);
        }
    }

    /**
     * Cancels the given event if the targeted block is too far away.
     * @see AbilityHooks.ToolHooks#blockTooFar(Player, InteractionHand)
     */
    private static void checkBlockTooFar(PlayerEvent event, Player player, InteractionHand hand) {
        if (event instanceof ICancellableEvent cancellable && !cancellable.isCanceled() && AbilityHooks.ToolHooks.blockTooFar(player, hand)) {
            cancellable.setCanceled(true);
        }
    }
}
