package com.gildedgames.aether.event.listeners.abilities;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.event.hooks.AbilityHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Aether.MODID)
public class ToolAbilityListener {
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
     * @see AbilityHooks.ToolHooks#handleHolystoneToolAbility(Player, Level, BlockPos, ItemStack) 
     */
    @SubscribeEvent
    public static void doHolystoneAbility(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Level level = player.getLevel();
        BlockPos blockPos = event.getPos();
        ItemStack itemStack = player.getMainHandItem();
        if (!event.isCanceled()) {
            AbilityHooks.ToolHooks.handleHolystoneToolAbility(player, level, blockPos, itemStack);
        }
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
        if (!event.isCanceled()) {
            event.setNewSpeed(AbilityHooks.ToolHooks.handleZaniteToolAbility(itemStack, event.getNewSpeed()));
            event.setNewSpeed(AbilityHooks.ToolHooks.reduceToolEffectiveness(blockState, itemStack, event.getNewSpeed()));
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
     * @see ToolAbilityListener#checkBlockTooFar(PlayerEvent, Player, InteractionHand)
     * @see ToolAbilityListener#checkInteractionTooFar(PlayerEvent, Player, InteractionHand)
     */
    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent event) {
        if (event instanceof PlayerInteractEvent.EntityInteractSpecific entityInteractSpecific) {
            checkEntityTooFar(entityInteractSpecific, entityInteractSpecific.getTarget(), entityInteractSpecific.getEntity(), entityInteractSpecific.getHand());
        } else if (event instanceof PlayerInteractEvent.EntityInteract entityInteract) {
            checkEntityTooFar(entityInteract, entityInteract.getTarget(), entityInteract.getEntity(), entityInteract.getHand());
        } else if (event instanceof PlayerInteractEvent.RightClickBlock rightClickBlock) {
            checkBlockTooFar(event, rightClickBlock.getEntity(), rightClickBlock.getHand());
        } else if (event instanceof PlayerInteractEvent.LeftClickBlock leftClickBlock) {
            checkBlockTooFar(event, leftClickBlock.getEntity(), leftClickBlock.getHand());
        } else if (event instanceof PlayerInteractEvent.RightClickItem rightClickItem) {
            checkInteractionTooFar(event, rightClickItem.getEntity(), rightClickItem.getHand());
        }
    }

    /**
     * Cancels the given event if the targeted entity is too far away.
     * @see AbilityHooks.ToolHooks#entityTooFar(Entity, Player, InteractionHand)
     */
    private static void checkEntityTooFar(PlayerEvent event, Entity target, Player player, InteractionHand hand) {
        if (!event.isCanceled() && AbilityHooks.ToolHooks.entityTooFar(target, player, hand)) {
            event.setCanceled(true);
        }
    }

    /**
     * Cancels the given event if the targeted block is too far away.
     * @see AbilityHooks.ToolHooks#blockTooFar(Player, InteractionHand)
     */
    private static void checkBlockTooFar(PlayerEvent event, Player player, InteractionHand hand) {
        if (!event.isCanceled() && AbilityHooks.ToolHooks.blockTooFar(player, hand)) {
            event.setCanceled(true);
        }
    }

    /**
     * Cancels the given event if the targeted interaction is too far away.
     * @see AbilityHooks.ToolHooks#interactionTooFar(Player, InteractionHand)
     */
    private static void checkInteractionTooFar(PlayerEvent event, Player player, InteractionHand hand) {
        if (!event.isCanceled() && AbilityHooks.ToolHooks.interactionTooFar(player, hand)) {
            event.setCanceled(true);
        }
    }
}
