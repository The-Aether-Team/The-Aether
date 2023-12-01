package com.aetherteam.aether.event.hooks;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.AetherGameEvents;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.FreezingBlock;
import com.aetherteam.aether.blockentity.IcestoneBlockEntity;
import com.aetherteam.aether.event.FreezeEvent;
import com.aetherteam.aether.event.PlacementBanEvent;
import com.aetherteam.aether.event.PlacementConvertEvent;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import com.aetherteam.aether.recipe.recipes.ban.BlockBanRecipe;
import com.aetherteam.aether.recipe.recipes.ban.ItemBanRecipe;
import com.aetherteam.aether.recipe.recipes.block.PlacementConversionRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;

import java.util.Map;

public class RecipeHooks {
    /**
     * Checks if an interaction in the Aether is banned. This is used both for item interaction recipes and interacting with beds in the Aether.
     * @param player The {@link Player} performing the interaction.
     * @param level The {@link Level} that the interaction is in.
     * @param pos The {@link BlockPos} the interaction is at.
     * @param face The {@link Direction} of the block face that is interacted with.
     * @param stack The {@link ItemStack} used for interaction.
     * @param state The {@link BlockState} being interacted with.
     * @param spawnParticles A {@link Boolean} for whether to spawn particles from the interaction's failure.
     * @return Whether an interaction is banned, as a {@link Boolean}.
     * @see com.aetherteam.aether.event.listeners.RecipeListener#checkBanned(PlayerInteractEvent.RightClickBlock)
     */
    public static boolean checkInteractionBanned(Player player, Level level, BlockPos pos, Direction face, ItemStack stack, BlockState state, boolean spawnParticles) {
        if (isItemPlacementBanned(level, pos, face, stack, spawnParticles)) {
            player.displayClientMessage(Component.translatable("aether.banned_item", stack.getItem().getName(stack)), true);
            return true;
        }
        if (level.getBiome(pos).is(AetherTags.Biomes.ULTRACOLD) && AetherConfig.SERVER.enable_bed_explosions.get()) { // Explodes beds in the Aether if the config for it is enabled.
            if (state.is(BlockTags.BEDS) && state.getBlock() != AetherBlocks.SKYROOT_BED.get()) {
                if (!level.isClientSide()) {
                    if (state.getValue(BedBlock.PART) != BedPart.HEAD) {
                        pos = pos.relative(state.getValue(BedBlock.FACING));
                        state = level.getBlockState(pos);
                    }
                    BlockPos blockpos = pos.relative(state.getValue(BedBlock.FACING).getOpposite());
                    if (level.getBlockState(blockpos).is(BlockTags.BEDS) && level.getBlockState(blockpos).getBlock() != AetherBlocks.SKYROOT_BED.get()) {
                        level.removeBlock(blockpos, false);
                    }
                    level.explode(null, DamageSource.badRespawnPointExplosion(), null, (double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5, 5.0F, true, Explosion.BlockInteraction.DESTROY);
                }
                player.swing(InteractionHand.MAIN_HAND);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if an item placement is banned through the {@link AetherRecipeTypes#ITEM_PLACEMENT_BAN} recipe type.
     * @param level The {@link Level} that the interaction is in.
     * @param pos The {@link BlockPos} the interaction is at.
     * @param face The {@link Direction} of the block face that is interacted with.
     * @param stack The {@link ItemStack} used for interaction.
     * @param spawnParticles A {@link Boolean} for whether to spawn particles from the interaction's failure.
     * @return Whether the interaction is banned, as a {@link Boolean}.
     */
    public static boolean isItemPlacementBanned(Level level, BlockPos pos, Direction face, ItemStack stack, boolean spawnParticles) {
        for (Recipe<?> recipe : level.getRecipeManager().getAllRecipesFor(AetherRecipeTypes.ITEM_PLACEMENT_BAN.get())) {
            if (recipe instanceof ItemBanRecipe banRecipe) {
                if (banRecipe.banItem(level, pos, face, stack, spawnParticles)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a block is unable to exist in the Aether, and either removes it or replaces it with another block.
     * @param levelAccessor The {@link LevelAccessor} the block is in.
     * @param pos The {@link BlockPos} of the block.
     * @see com.aetherteam.aether.event.listeners.RecipeListener#onNeighborNotified(BlockEvent.NeighborNotifyEvent)
     */
    public static void checkExistenceBanned(LevelAccessor levelAccessor, BlockPos pos) {
        if (levelAccessor instanceof Level level) {
            BlockState state = levelAccessor.getBlockState(pos);
            if (RecipeHooks.isBlockPlacementBanned(level, pos, state)) { // Check if block can't exist.
                level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                if (state.getBlock().asItem() != Items.AIR) {
                    Block.dropResources(state, level, pos); // Drop block if it can be dropped as an item.
                }
            } else { // Check if block should be replaced.
                RecipeHooks.isBlockPlacementConvertable(level, pos, state);
            }
        }
    }

    /**
     * Checks if a block placement is banned through the {@link AetherRecipeTypes#BLOCK_PLACEMENT_BAN} recipe type.
     * @param level The {@link Level} that the placement is in.
     * @param pos The {@link BlockPos} the placement is at.
     * @param state The placed {@link BlockState}.
     * @return Whether the placement is banned, as a {@link Boolean}.
     */
    private static boolean isBlockPlacementBanned(Level level, BlockPos pos, BlockState state) {
        if (!level.isClientSide()) {
            for (Recipe<?> recipe : level.getRecipeManager().getAllRecipesFor(AetherRecipeTypes.BLOCK_PLACEMENT_BAN.get())) {
                if (recipe instanceof BlockBanRecipe banRecipe) {
                    if (banRecipe.banBlock(level, pos, state)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks if a placed block should be converted through the {@link AetherRecipeTypes#PLACEMENT_CONVERSION} recipe type.
     * @param level The {@link Level} that the placement is in.
     * @param pos The {@link BlockPos} the placement is at.
     * @param state The placed {@link BlockState}.
     */
    private static void isBlockPlacementConvertable(Level level, BlockPos pos, BlockState state) {
        if (!level.isClientSide()) {
            for (Recipe<?> recipe : level.getRecipeManager().getAllRecipesFor(AetherRecipeTypes.PLACEMENT_CONVERSION.get())) {
                if (recipe instanceof PlacementConversionRecipe conversionRecipe) {
                    if (conversionRecipe.convert(level, pos, state)) {
                        return;
                    }
                }
            }
        }
    }

    /**
     * Spawns particles from a ban or conversion recipe interaction.
     * @param accessor The {@link LevelAccessor} that the interaction is in.
     * @param pos The {@link BlockPos} the interaction is at.
     * @see com.aetherteam.aether.event.listeners.RecipeListener#onConvert(PlacementConvertEvent)
     * @see com.aetherteam.aether.event.listeners.RecipeListener#onBanned(PlacementBanEvent.SpawnParticles)
     */
    public static void banOrConvert(LevelAccessor accessor, BlockPos pos) {
        if (accessor instanceof ServerLevel serverLevel) {
            double x = pos.getX() + 0.5;
            double y = pos.getY() + 1.0;
            double z = pos.getZ() + 0.5;
            for (int i = 0; i < 10; i++) {
                serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, x, y, z, 1, 0.0,0.0, 0.0, 0.0F);
            }
            serverLevel.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }

    /**
     * Caches all Icestone freezing recipes, checks if a block is in the cache, and sends a {@link AetherGameEvents#ICESTONE_FREEZABLE_UPDATE} game event update from that block.
     * The game event is used to let Icestone blocks know to freeze another block in a performance-efficient way.
     * @param accessor The {@link LevelAccessor} that the block is in.
     * @param pos The {@link BlockPos}
     * @see com.aetherteam.aether.event.listeners.RecipeListener#onNeighborNotified(BlockEvent.NeighborNotifyEvent)
     */
    public static void sendIcestoneFreezableUpdateEvent(LevelAccessor accessor, BlockPos pos) {
        if (accessor instanceof Level level && !level.isClientSide())  {
            BlockState oldBlockState = level.getBlockState(pos);
            FreezingBlock.cacheRecipes(level);
            if (FreezingBlock.matchesCache(oldBlockState.getBlock(), oldBlockState) != null) {
                level.gameEvent(null, AetherGameEvents.ICESTONE_FREEZABLE_UPDATE.get(), pos);
            }
        }
    }

    /**
     * Prevents freezing blocks at a position from Icestone if that position is marked to have delayed freezing.
     * @param accessor The {@link LevelAccessor} that the block is in.
     * @param sourcePos The {@link BlockPos} of the source of the freezing.
     * @param pos The {@link BlockPos} of the block to freeze.
     * @return Whether freezing a block should be prevented, as a {@link Boolean}.
     * @see com.aetherteam.aether.event.listeners.RecipeListener#onBlockFreeze(FreezeEvent.FreezeFromBlock)
     */
    public static boolean preventBlockFreezing(LevelAccessor accessor, BlockPos sourcePos, BlockPos pos) {
        if (accessor.getBlockEntity(sourcePos) instanceof IcestoneBlockEntity blockEntity) {
            for (Map.Entry<BlockPos, Integer> entry : blockEntity.getLastBrokenPositions().entrySet()) {
                if (entry.getKey().equals(pos) && entry.getValue() > 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
