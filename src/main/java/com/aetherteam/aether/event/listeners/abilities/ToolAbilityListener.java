package com.aetherteam.aether.event.listeners.abilities;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.AbilityHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class ToolAbilityListener {
    public static InteractionHand INTERACTION_HAND;

    /**
     * @see Aether#eventSetup()
     */
    public static void listen(IEventBus bus) {
        bus.addListener(ToolAbilityListener::setupToolModifications);
        bus.addListener(ToolAbilityListener::doHolystoneAbility);
        bus.addListener(ToolAbilityListener::modifyBreakSpeed);
        bus.addListener(ToolAbilityListener::doGoldenOakStripping);
        bus.addListener(ToolAbilityListener::onEntityAttack);
        bus.addListener(ToolAbilityListener::onEntityInteractSpecific);
        bus.addListener(ToolAbilityListener::onEntityInteract);
        bus.addListener(ToolAbilityListener::onEntityInteractRightClickBlock);
        bus.addListener(ToolAbilityListener::onEntityInteractLeftClickBlock);
        bus.addListener(ToolAbilityListener::onEntityInteractRightClickItem);
    }

    /**
     * @see AbilityHooks.ToolHooks#setupItemAbilities(LevelAccessor, BlockPos, BlockState, ItemAbility)
     */
    public static void setupToolModifications(BlockEvent.BlockToolModificationEvent event) {
        LevelAccessor levelAccessor = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState oldState = event.getState();
        ItemAbility ItemAbility = event.getItemAbility();
        BlockState newState = AbilityHooks.ToolHooks.setupItemAbilities(levelAccessor, pos, oldState, ItemAbility);
        if (newState != oldState && !event.isSimulated() && !event.isCanceled()) {
            event.setFinalState(newState);
        }
    }

    /**
     * @see AbilityHooks.ToolHooks#handleHolystoneToolAbility(Player, Level, BlockPos, ItemStack, BlockState)
     */
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
     * @see AbilityHooks.ToolHooks#stripGoldenOak(LevelAccessor, BlockState, ItemStack, ItemAbility, UseOnContext)
     */
    public static void doGoldenOakStripping(BlockEvent.BlockToolModificationEvent event) {
        LevelAccessor levelAccessor = event.getLevel();
        BlockState oldState = event.getState();
        ItemStack itemStack = event.getHeldItemStack();
        ItemAbility ItemAbility = event.getItemAbility();
        UseOnContext context = event.getContext();
        if (!event.isSimulated() && !event.isCanceled()) {
            AbilityHooks.ToolHooks.stripGoldenOak(levelAccessor, oldState, itemStack, ItemAbility, context);
        }
    }

    /**
     * @see ToolAbilityListener#checkEntityTooFar(PlayerEvent, Entity, Player, InteractionHand)
     */
    public static void onEntityAttack(AttackEntityEvent event) {
        checkEntityTooFar(event, event.getTarget(), event.getEntity(), InteractionHand.MAIN_HAND);
    }

    /**
     * @see ToolAbilityListener#checkEntityTooFar(PlayerEvent, Entity, Player, InteractionHand)
     */
    public static void onEntityInteractSpecific(PlayerInteractEvent.EntityInteractSpecific event) {
        checkEntityTooFar(event, event.getTarget(), event.getEntity(), event.getHand());
    }

    /**
     * @see ToolAbilityListener#checkEntityTooFar(PlayerEvent, Entity, Player, InteractionHand)
     */
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        checkEntityTooFar(event, event.getTarget(), event.getEntity(), event.getHand());
    }

    /**
     * @see ToolAbilityListener#checkBlockTooFar(PlayerEvent, Player, InteractionHand)
     */
    public static void onEntityInteractRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        checkBlockTooFar(event, event.getEntity(), event.getHand());
    }

    /**
     * @see ToolAbilityListener#checkBlockTooFar(PlayerEvent, Player, InteractionHand)
     */
    public static void onEntityInteractLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        checkBlockTooFar(event, event.getEntity(), event.getHand());
    }

    /**
     * @see com.aetherteam.aether.mixin.mixins.common.ItemMixin#getPlayerPOVHitResult(Level, Player, ClipContext.Fluid, CallbackInfoReturnable)
     */
    public static void onEntityInteractRightClickItem(PlayerInteractEvent.RightClickItem event) {
        INTERACTION_HAND = event.getHand();
    }

    /**
     * Cancels the given event if the targeted entity is too far away.
     *
     * @see AbilityHooks.ToolHooks#entityTooFar(Entity, Player, InteractionHand)
     */
    private static void checkEntityTooFar(PlayerEvent event, Entity target, Player player, InteractionHand hand) {
        if (event instanceof ICancellableEvent cancellable && !cancellable.isCanceled() && AbilityHooks.ToolHooks.entityTooFar(target, player, hand)) {
            cancellable.setCanceled(true);
        }
    }

    /**
     * Cancels the given event if the targeted block is too far away.
     *
     * @see AbilityHooks.ToolHooks#blockTooFar(Player, InteractionHand)
     */
    private static void checkBlockTooFar(PlayerEvent event, Player player, InteractionHand hand) {
        if (event instanceof ICancellableEvent cancellable && !cancellable.isCanceled() && AbilityHooks.ToolHooks.blockTooFar(player, hand)) {
            cancellable.setCanceled(true);
        }
    }
}
