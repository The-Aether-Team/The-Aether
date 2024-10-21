package com.aetherteam.aether.event.listeners.abilities;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.AbilityHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

public class ToolAbilityListener {
    /**
     * @see Aether#eventSetup()
     */
    public static void listen(IEventBus bus) {
        bus.addListener(ToolAbilityListener::setupToolModifications);
        bus.addListener(ToolAbilityListener::modifyItemAttributes);
        bus.addListener(ToolAbilityListener::doHolystoneAbility);
        bus.addListener(ToolAbilityListener::modifyBreakSpeed);
        bus.addListener(ToolAbilityListener::doGoldenOakStripping);
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

    public static void modifyItemAttributes(ItemAttributeModifierEvent event) {
        ItemStack itemStack = event.getItemStack();
        ItemAttributeModifiers modifiers = event.getDefaultModifiers();
        ItemAttributeModifiers.Entry modifierEntry = AbilityHooks.ToolHooks.handleZaniteAbilityModifiers(modifiers, itemStack);
        if (modifierEntry != null) {
            event.replaceModifier(modifierEntry.attribute(), modifierEntry.modifier(), modifierEntry.slot());
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
}
