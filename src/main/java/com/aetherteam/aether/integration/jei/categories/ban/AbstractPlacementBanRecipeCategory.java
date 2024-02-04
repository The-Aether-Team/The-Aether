package com.aetherteam.aether.integration.jei.categories.ban;

//import com.aetherteam.aether.integration.jei.categories.BiomeTooltip;
//import com.aetherteam.aether.recipe.recipes.ban.AbstractPlacementBanRecipe;
//import com.aetherteam.nitrogen.integration.jei.BlockStateRenderer;
//import com.aetherteam.nitrogen.integration.jei.FluidStateRenderer;
//import com.aetherteam.nitrogen.integration.jei.categories.AbstractRecipeCategory;
//import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
//import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
//import com.aetherteam.nitrogen.recipe.BlockStateRecipeUtil;
//import mezz.jei.api.constants.VanillaTypes;
//import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
//import mezz.jei.api.gui.drawable.IDrawable;
//import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
//import mezz.jei.api.helpers.IGuiHelper;
//import mezz.jei.api.helpers.IPlatformFluidHelper;
//import mezz.jei.api.recipe.IFocusGroup;
//import mezz.jei.api.recipe.RecipeIngredientRole;
//import mezz.jei.api.recipe.RecipeType;
//import mezz.jei.common.platform.Services;
//import mezz.jei.common.util.Translator;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.Font;
//import net.minecraft.client.gui.GuiGraphics;
//import net.minecraft.core.BlockPos;
//import net.minecraft.network.chat.Component;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.level.BlockGetter;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.LiquidBlock;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.block.state.properties.Property;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.function.Predicate;
//
//public abstract class AbstractPlacementBanRecipeCategory<T, S extends Predicate<T>, R extends AbstractPlacementBanRecipe<T, S>> extends AbstractRecipeCategory<R> implements BiomeTooltip {
//    protected final IPlatformFluidHelper<?> fluidHelper;
//    private final IDrawable slot;
//
//    public AbstractPlacementBanRecipeCategory(IGuiHelper guiHelper, String id, ResourceLocation uid, IDrawable background, IDrawable icon, RecipeType<R> recipeType, IPlatformFluidHelper<?> fluidHelper) {
//        super(id, uid, background, icon, recipeType);
//        this.fluidHelper = fluidHelper;
//        this.slot = guiHelper.getSlotDrawable();
//    }
//
//    @Override
//    public Component getTitle() {
//        return Component.translatable("gui.aether.jei." + this.id);
//    }
//
//    @Override
//    public void setRecipe(IRecipeLayoutBuilder builder, R recipe, IFocusGroup focusGroup) {
//        BlockStateIngredient bypassBlockIngredient = recipe.getBypassBlock();
//        BlockPropertyPair[] pairs = bypassBlockIngredient.getPairs();
//        if (pairs != null) {
//            List<Object> ingredients = this.setupIngredients(pairs);
//            builder.addSlot(RecipeIngredientRole.INPUT, 99, 1).addIngredientsUnsafe(ingredients)
//                    .setCustomRenderer(Services.PLATFORM.getFluidHelper().getFluidIngredientType(), new FluidStateRenderer(Services.PLATFORM.getFluidHelper()))
//                    .setCustomRenderer(VanillaTypes.ITEM_STACK, new BlockStateRenderer(pairs));
//        }
//    }
//
//    /**
//     * Warning for "deprecation" is suppressed because the non-sensitive version of {@link net.minecraft.world.level.block.Block#getCloneItemStack(BlockGetter, BlockPos, BlockState)} is needed in this context.
//     */
//    @SuppressWarnings("deprecation")
//    protected List<Object> setupIngredients(BlockPropertyPair[] pairs) {
//        List<Object> ingredients = new ArrayList<>();
//        if (Minecraft.getInstance().level != null) {
//            for (BlockPropertyPair pair : pairs) {
//                if (pair.block() instanceof LiquidBlock liquidBlock) {
//                    ingredients.add(this.fluidHelper.create(liquidBlock.getFluid(), 1000));
//                } else {
//                    BlockState state = pair.block().defaultBlockState();
//                    for (Map.Entry<Property<?>, Comparable<?>> propertyEntry : pair.properties().entrySet()) {
//                        state = BlockStateRecipeUtil.setHelper(propertyEntry, state);
//                    }
//                    ItemStack stack = pair.block().getCloneItemStack(Minecraft.getInstance().level, BlockPos.ZERO, state);
//                    stack = stack.isEmpty() ? new ItemStack(Blocks.STONE) : stack;
//                    ingredients.add(stack);
//                }
//            }
//        }
//        return ingredients;
//    }
//
//    @Override
//    public void draw(R recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
//        if (recipe.getBypassBlock() == null || recipe.getBypassBlock().isEmpty()) {
//            this.slot.draw(guiGraphics, 49, 0);
//        } else {
//            this.slot.draw(guiGraphics);
//            this.slot.draw(guiGraphics, 98, 0);
//            String text = Translator.translateToLocalFormatted("gui.aether.jei.bypass");
//            Font font = Minecraft.getInstance().font;
//            guiGraphics.drawString(font, text, 24, 5, 0xFF808080);
//        }
//    }
//
//    protected void populateAdditionalInformation(R recipe, List<Component> tooltip) {
//        if (Minecraft.getInstance().level != null) {
//            this.populateBiomeInformation(recipe.getBiomeKey(), recipe.getBiomeTag(), tooltip);
//        }
//    }
//}
