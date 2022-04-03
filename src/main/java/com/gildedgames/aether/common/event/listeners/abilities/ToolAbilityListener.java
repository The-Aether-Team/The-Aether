package com.gildedgames.aether.common.event.listeners.abilities;

import com.gildedgames.aether.common.event.hooks.AbilityHooks;
import com.gildedgames.aether.common.item.tools.abilities.GravititeTool;
import com.gildedgames.aether.common.item.tools.abilities.HolystoneTool;
import com.gildedgames.aether.common.item.tools.abilities.ZaniteTool;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ToolAbilityListener {
    @SubscribeEvent
    public static void doGoldenOakStripping(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getWorld();
        ItemStack itemStack = event.getItemStack();
        BlockPos blockPos = event.getPos();
        BlockHitResult blockHitResult = event.getHitVec();
        AbilityHooks.ToolHooks.stripGoldenOak(level, itemStack, blockPos, blockHitResult);
    }

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
        ItemStack itemStack = player.getMainHandItem();
        event.setNewSpeed(ZaniteTool.increaseSpeed(itemStack, event.getNewSpeed()));
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
}
