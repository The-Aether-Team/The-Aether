package com.aetherteam.aether.integration.jei.categories.ban;

import com.aetherteam.aether.integration.jei.BlockStateRenderer;
import com.aetherteam.aether.integration.jei.FluidStateRenderer;
import com.aetherteam.aether.integration.jei.categories.AbstractAetherRecipeCategory;
import com.aetherteam.aether.recipe.BlockPropertyPair;
import com.aetherteam.aether.recipe.BlockStateIngredient;
import com.aetherteam.aether.recipe.recipes.ban.AbstractPlacementBanRecipe;
import com.aetherteam.aether.recipe.BlockStateRecipeUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.common.platform.Services;
import mezz.jei.common.util.Translator;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public abstract class AbstractPlacementBanRecipeCategory<T, S extends Predicate<T>, R extends AbstractPlacementBanRecipe<T, S>> extends AbstractAetherRecipeCategory<R> {
    protected final IPlatformFluidHelper<?> fluidHelper;
    private final IDrawable slot;

    public AbstractPlacementBanRecipeCategory(IGuiHelper guiHelper, String id, ResourceLocation uid, IDrawable background, IDrawable icon, RecipeType<R> recipeType, IPlatformFluidHelper<?> fluidHelper) {
        super(id, uid, background, icon, recipeType);
        this.fluidHelper = fluidHelper;
        this.slot = guiHelper.getSlotDrawable();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, R recipe, IFocusGroup focusGroup) {
        BlockStateIngredient bypassBlockIngredient = recipe.getBypassBlock();
        BlockPropertyPair[] pairs = bypassBlockIngredient.getPairs();

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
        builder.addSlot(RecipeIngredientRole.INPUT, 99, 1).addIngredientsUnsafe(ingredients)
                .setCustomRenderer(Services.PLATFORM.getFluidHelper().getFluidIngredientType(), new FluidStateRenderer(Services.PLATFORM.getFluidHelper())).setCustomRenderer(VanillaTypes.ITEM_STACK, new BlockStateRenderer(pairs));
    }

    @Override
    public void draw(R recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
        if (recipe.getBypassBlock() == null || recipe.getBypassBlock().isEmpty()) {
            this.slot.draw(poseStack, 49, 0);
        } else {
            this.slot.draw(poseStack);
            this.slot.draw(poseStack, 98, 0);
            String text = Translator.translateToLocalFormatted("gui.aether.jei.bypass");
            Minecraft minecraft = Minecraft.getInstance();
            Font font = minecraft.font;
            font.draw(poseStack, text, 24, 5, 0xFF808080);
        }
    }

    protected void populateAdditionalInformation(R recipe, List<Component> tooltip) {
        if (recipe.getBiomeKey() != null || recipe.getBiomeTag() != null) {
            tooltip.add(Component.translatable("gui.aether.jei.biome.ban.tooltip").withStyle(ChatFormatting.GRAY));
            if (recipe.getBiomeKey() != null) {
                tooltip.add(Component.translatable("gui.aether.jei.biome.tooltip.biome").withStyle(ChatFormatting.DARK_GRAY));
                tooltip.add(Component.literal(recipe.getBiomeKey().location().toString()).withStyle(ChatFormatting.DARK_GRAY));
            } else if (recipe.getBiomeTag() != null) {
                tooltip.add(Component.translatable("gui.aether.jei.biome.tooltip.tag").withStyle(ChatFormatting.DARK_GRAY));
                tooltip.add(Component.literal("#" + recipe.getBiomeTag().location()).withStyle(ChatFormatting.DARK_GRAY));

                tooltip.add(Component.translatable("gui.aether.jei.biome.tooltip.biomes").withStyle(ChatFormatting.DARK_GRAY));
                Minecraft.getInstance().level.registryAccess().registryOrThrow(Registries.BIOME).getTagOrEmpty(recipe.getBiomeTag()).forEach((biomeHolder) -> tooltip.add(Component.literal(biomeHolder.unwrapKey().get().location().toString()).withStyle(ChatFormatting.DARK_GRAY)));
            }
        }
    }
}
