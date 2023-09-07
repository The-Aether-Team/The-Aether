package com.aetherteam.aether.event.listeners;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.FreezeEvent;
import com.aetherteam.aether.event.PlacementBanEvent;
import com.aetherteam.aether.event.PlacementConvertEvent;
import com.aetherteam.aether.event.hooks.RecipeHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Aether.MODID)
public class RecipeListener {
    /**
     * @see RecipeHooks#checkInteractionBanned(Player, Level, BlockPos, Direction, ItemStack, BlockState, boolean)
     */
    @SubscribeEvent
    public static void checkBanned(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockPos blockPos = event.getPos();
        Direction direction = event.getFace();
        InteractionHand interactionHand = event.getHand();
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (itemStack.isEmpty()) {
            itemStack = player.getItemInHand(interactionHand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);
        }
        BlockState blockState = level.getBlockState(blockPos);
        event.setCanceled(RecipeHooks.checkInteractionBanned(player, level, blockPos, direction, itemStack, blockState, !player.getItemInHand(interactionHand).isEmpty()));
    }

    /**
     * @see RecipeHooks#checkExistenceBanned(LevelAccessor, BlockPos)
     */
    @SubscribeEvent
    public static void onNeighborNotified(BlockEvent.NeighborNotifyEvent event) {
        LevelAccessor levelAccessor = event.getLevel();
        BlockPos blockPos = event.getPos();
        RecipeHooks.checkExistenceBanned(levelAccessor, blockPos);
        RecipeHooks.sendIcestoneFreezableUpdateEvent(levelAccessor, blockPos);
    }

    /**
     * @see RecipeHooks#preventBlockFreezing(LevelAccessor, BlockPos, BlockPos)
     */
    @SubscribeEvent
    public static void onBlockFreeze(FreezeEvent.FreezeFromBlock event) {
        LevelAccessor level = event.getLevel();
        BlockPos sourcePos = event.getSourcePos();
        BlockPos pos = event.getPos();
        if (RecipeHooks.preventBlockFreezing(level, sourcePos, pos)) {
            event.setCanceled(true);
        }
    }

    /**
     * @see RecipeHooks#banOrConvert(LevelAccessor, BlockPos)
     */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onConvert(PlacementConvertEvent event) {
        LevelAccessor levelAccessor = event.getLevel();
        BlockPos blockPos = event.getPos();
        if (!event.isCanceled()) {
            RecipeHooks.banOrConvert(levelAccessor, blockPos);
        }
    }

    /**
     * @see RecipeHooks#banOrConvert(LevelAccessor, BlockPos)
     */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBanned(PlacementBanEvent.SpawnParticles event) {
        LevelAccessor levelAccessor = event.getLevel();
        BlockPos blockPos = event.getPos();
        if (!event.isCanceled()) {
            RecipeHooks.banOrConvert(levelAccessor, blockPos);
        }
    }
}
