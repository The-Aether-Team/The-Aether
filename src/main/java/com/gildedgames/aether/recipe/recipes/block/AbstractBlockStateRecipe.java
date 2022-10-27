package com.gildedgames.aether.recipe.recipes.block;

import com.gildedgames.aether.recipe.BlockPropertyPair;
import com.gildedgames.aether.recipe.BlockStateIngredient;
import com.gildedgames.aether.util.BlockStateRecipeUtil;
import net.minecraft.commands.CommandFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import java.util.Map;

public abstract class AbstractBlockStateRecipe implements BlockStateRecipe {
    protected final RecipeType<?> type;
    protected final ResourceLocation id;
    protected final BlockStateIngredient ingredient;
    protected final BlockPropertyPair result;
    protected final CommandFunction.CacheableFunction mcfunction;

    public AbstractBlockStateRecipe(RecipeType<?> type, ResourceLocation id, BlockStateIngredient ingredient, BlockPropertyPair result, CommandFunction.CacheableFunction mcfunction) {
        this.type = type;
        this.id = id;
        this.ingredient = ingredient;
        this.result = result;
        this.mcfunction = mcfunction;
    }

    public boolean set(Level level, BlockPos pos, BlockState oldState) {
        if (this.matches(level, pos, oldState)) {
            BlockState newState = this.getResultState(oldState);
            level.setBlockAndUpdate(pos, newState);
            var serverLevel = (ServerLevel) level;
            var server = serverLevel.getServer();
            this.mcfunction.get(server.getFunctions()).ifPresent(command -> {
                var context = server.getFunctions().getGameLoopSender()
                        .withPosition(Vec3.atBottomCenterOf(pos))
                        .withLevel(serverLevel);
                server.getFunctions().execute(command, context);
            });
            return true;
        }
        return false;
    }

    public boolean matches(Level level, BlockPos pos, BlockState state) {
        return this.getIngredient().test(state);
    }

    @Override
    public BlockState getResultState(BlockState originalState) {
        BlockState resultState = this.getResult().block().withPropertiesOf(originalState);
        for (Map.Entry<Property<?>, Comparable<?>> propertyEntry : this.getResult().properties().entrySet()) {
            resultState = BlockStateRecipeUtil.setHelper(propertyEntry, resultState);
        }
        return resultState;
    }

    @Override
    public BlockStateIngredient getIngredient() {
        return this.ingredient;
    }

    @Override
    public BlockPropertyPair getResult() {
        return this.result;
    }

    @Override
    public CommandFunction.CacheableFunction getMcfunction() {
        return this.mcfunction;
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {
        return this.type;
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return this.id;
    }
}
