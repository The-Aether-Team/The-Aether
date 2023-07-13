package com.aetherteam.aether.integration.jei.categories.ban;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.integration.jei.BlockStateRenderer;
import com.aetherteam.aether.integration.jei.FluidStateRenderer;
import com.aetherteam.aether.recipe.BlockPropertyPair;
import com.aetherteam.aether.recipe.BlockStateIngredient;
import com.aetherteam.aether.recipe.recipes.ban.BlockBanRecipe;
import com.aetherteam.aether.recipe.BlockStateRecipeUtil;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.common.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BlockBanRecipeCategory extends AbstractPlacementBanRecipeCategory<BlockState, BlockStateIngredient, BlockBanRecipe>  {
    public static final ResourceLocation UID = new ResourceLocation(Aether.MODID, "block_placement_ban");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Aether.MODID, "textures/gui/menu/jei_render.png");
    public static final RecipeType<BlockBanRecipe> RECIPE_TYPE = RecipeType.create(Aether.MODID, "block_placement_ban", BlockBanRecipe.class);

    public BlockBanRecipeCategory(IGuiHelper guiHelper, IPlatformFluidHelper<?> fluidHelper) {
        super(guiHelper, "block_placement_ban", UID,
                guiHelper.createBlankDrawable(116, 18),
                guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Blocks.TORCH)),
                RECIPE_TYPE, fluidHelper);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BlockBanRecipe recipe, IFocusGroup focusGroup) {
        BlockStateIngredient ingredient = recipe.getIngredient();
        BlockPropertyPair[] pairs = ingredient.getPairs();
        List<Object> ingredients = new ArrayList<>();
        for (BlockPropertyPair pair : pairs) {
            if (pair.block() instanceof LiquidBlock liquidBlock) {
                ingredients.add(this.fluidHelper.create(liquidBlock.getFluid(), 1000));
            } else {
                BlockState state = pair.block().defaultBlockState();
                for (Map.Entry<Property<?>, Comparable<?>> propertyEntry : pair.properties().entrySet()) {
                    state = BlockStateRecipeUtil.setHelper(propertyEntry, state);
                }
                ItemStack stack = pair.block().getCloneItemStack(Minecraft.getInstance().level, BlockPos.ZERO, state);
                stack = stack.isEmpty() ? new ItemStack(Blocks.STONE) : stack;
                ingredients.add(stack);
            }
        }
        if (recipe.getBypassBlock() == null || recipe.getBypassBlock().isEmpty()) {
            builder.addSlot(RecipeIngredientRole.INPUT, 50, 1).addIngredientsUnsafe(ingredients)
                    .setCustomRenderer(Services.PLATFORM.getFluidHelper().getFluidIngredientType(), new FluidStateRenderer(Services.PLATFORM.getFluidHelper())).setCustomRenderer(VanillaTypes.ITEM_STACK, new BlockStateRenderer(pairs));
        } else {
            builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).addIngredientsUnsafe(ingredients)
                    .setCustomRenderer(Services.PLATFORM.getFluidHelper().getFluidIngredientType(), new FluidStateRenderer(Services.PLATFORM.getFluidHelper())).setCustomRenderer(VanillaTypes.ITEM_STACK, new BlockStateRenderer(pairs));
        }
        super.setRecipe(builder, recipe, focusGroup);
    }
}
