package com.aetherteam.aether.event.listeners.abilities;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.AbilityHooks;
import com.aetherteam.nitrogen.fabric.events.CancellableCallback;
import com.aetherteam.nitrogen.fabric.events.PlayerEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.mutable.MutableFloat;

public class ToolAbilityListener {
    /**
     * @see Aether#eventSetup()
     */
    public static void listen() {
//        bus.addListener(ToolAbilityListener::setupToolModifications);
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> doHolystoneAbility(player, world, pos, player.getMainHandItem(), state));
        PlayerEvents.ON_BLOCK_DESTROY.register(ToolAbilityListener::modifyBreakSpeed);
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> ToolAbilityListener.setupDebuffToolState(handler.getPlayer()));
        // AxeItemMixin.nitrogen_fabric$onLogStripping -> ToolAbilityListener.doGoldenOakStripping;
    }

//    /**
//     * @see AbilityHooks.ToolHooks#setupItemAbilities(LevelAccessor, BlockPos, BlockState, ItemAbility)
//     */
//    public static void setupToolModifications(BlockEvent.BlockToolModificationEvent event) {
//        LevelAccessor levelAccessor = event.getLevel();
//        BlockPos pos = event.getPos();
//        BlockState oldState = event.getState();
//        ItemAbility ItemAbility = event.getItemAbility();
//        BlockState newState = AbilityHooks.ToolHooks.setupItemAbilities(levelAccessor, pos, oldState, ItemAbility);
//        if (newState != oldState && !event.isSimulated() && !event.isCanceled()) {
//            event.setFinalState(newState);
//        }
//    }

    /**
     * @see AbilityHooks.ToolHooks#handleHolystoneToolAbility(Player, Level, BlockPos, ItemStack, BlockState)
     */
    public static void doHolystoneAbility(Player player, Level level, BlockPos blockPos, ItemStack itemStack, BlockState blockState) {
        AbilityHooks.ToolHooks.handleHolystoneToolAbility(player, level, blockPos, itemStack, blockState);
    }

    /**
     * @see AbilityHooks.ToolHooks#handleZaniteToolAbility(ItemStack, float)
     * @see AbilityHooks.ToolHooks#reduceToolEffectiveness(Player, BlockState, ItemStack, float)
     */
    public static void modifyBreakSpeed(Player player, BlockState blockState, MutableFloat speed, CancellableCallback callback) {
        ItemStack itemStack = player.getMainHandItem();
        if (!callback.isCanceled()) {
            speed.setValue(AbilityHooks.ToolHooks.handleZaniteToolAbility(itemStack, speed.getValue()));
            speed.setValue(AbilityHooks.ToolHooks.reduceToolEffectiveness(player, blockState, itemStack, speed.getValue()));
        }
    }

    public static void setupDebuffToolState(ServerPlayer player) {
        AbilityHooks.ToolHooks.setDebuffToolsState(player);
    }

    /**
     * @see AbilityHooks.ToolHooks#stripGoldenOak(LevelAccessor, BlockState, ItemStack, UseOnContext)
     */
    public static void doGoldenOakStripping(LevelAccessor levelAccessor, BlockState oldState, ItemStack itemStack, UseOnContext context) {
        AbilityHooks.ToolHooks.stripGoldenOak(levelAccessor, oldState, itemStack, context);
    }
}
