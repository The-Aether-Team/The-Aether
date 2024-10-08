package com.aetherteam.aether.recipe.recipes.ban;

import com.aetherteam.aether.event.AetherEventDispatch;
import com.aetherteam.aether.recipe.AetherRecipeSerializers;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import com.aetherteam.aether.recipe.serializer.PlacementBanRecipeSerializer;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.BlockStateRecipeUtil;
import com.aetherteam.nitrogen.recipe.input.BlockStateRecipeInput;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class BlockBanRecipe extends AbstractPlacementBanRecipe<BlockState, BlockStateIngredient, BlockStateRecipeInput> {
    public BlockBanRecipe(Either<ResourceKey<Biome>, TagKey<Biome>> biome, Optional<BlockStateIngredient> bypassBlock, BlockStateIngredient ingredient) {
        super(AetherRecipeTypes.BLOCK_PLACEMENT_BAN.get(), biome, bypassBlock, ingredient);
    }

    public BlockBanRecipe(Either<ResourceKey<Biome>, TagKey<Biome>> biome, Optional<BlockStateIngredient> bypassBlock) {
        this(biome, bypassBlock, BlockStateIngredient.EMPTY);
    }

    /**
     * Checks if the recipe matches the given parameters using {@link AbstractPlacementBanRecipe#matches(Level, BlockPos, Object)}.<br><br>
     * Then checks an event hook through {@link AetherEventDispatch#isBlockPlacementBanned(LevelAccessor, BlockPos, BlockState)}.<br><br>
     * Before calling {@link AetherEventDispatch#onPlacementSpawnParticles(LevelAccessor, BlockPos, Direction, ItemStack, BlockState)} to spawn particles on block ban.
     *
     * @param level The {@link Level} the recipe is performed in.
     * @param pos   The {@link BlockPos} the recipe is performed at.
     * @param state The {@link BlockState} being used that is being checked.
     * @return Whether the given {@link BlockState} is banned from placement.
     */
    public boolean banBlock(Level level, BlockPos pos, BlockState state) {
        if (this.matches(level, pos.below(), state)) {
            if (AetherEventDispatch.isBlockPlacementBanned(level, pos, state)) {
                AetherEventDispatch.onPlacementSpawnParticles(level, pos, null, null, state);
                return true;
            }
        }
        return false;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AetherRecipeSerializers.BLOCK_PLACEMENT_BAN.get();
    }

    public static class Serializer extends PlacementBanRecipeSerializer<BlockState, BlockStateIngredient, BlockStateRecipeInput, BlockBanRecipe> {
        public Serializer() {
            super(BlockBanRecipe::new);
        }

        @Override
        public MapCodec<BlockBanRecipe> codec() {
            return RecordCodecBuilder.mapCodec(inst -> inst.group(
                    BlockStateRecipeUtil.KEY_CODEC.fieldOf("biome").forGetter(BlockBanRecipe::getBiome),
                    BlockStateIngredient.CODEC.optionalFieldOf("bypass").forGetter(BlockBanRecipe::getBypassBlock),
                    BlockStateIngredient.CODEC.fieldOf("ingredient").forGetter(BlockBanRecipe::getIngredient)
            ).apply(inst, this.getFactory()));
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, BlockBanRecipe> streamCodec() {
            return StreamCodec.of(this::toNetwork, this::fromNetwork);
        }

        public BlockBanRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            Either<ResourceKey<Biome>, TagKey<Biome>> biome = BlockStateRecipeUtil.STREAM_CODEC.decode(buffer);
            Optional<BlockStateIngredient> bypassBlock = buffer.readOptional((buf) -> BlockStateIngredient.CONTENTS_STREAM_CODEC.decode(buffer));
            BlockStateIngredient ingredient = BlockStateIngredient.CONTENTS_STREAM_CODEC.decode(buffer);
            return new BlockBanRecipe(biome, bypassBlock, ingredient);
        }

        public void toNetwork(RegistryFriendlyByteBuf buffer, BlockBanRecipe recipe) {
            super.toNetwork(buffer, recipe);
            BlockStateIngredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getIngredient());
        }
    }
}
