package com.aetherteam.aether.integration.rei.categories.item;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.integration.rei.AetherREIServerPlugin;
import com.aetherteam.aether.recipe.recipes.item.AltarRepairRecipe;
import com.aetherteam.aether.recipe.recipes.item.EnchantingRecipe;
import com.aetherteam.aether.recipe.recipes.item.FreezingRecipe;
import com.aetherteam.aether.recipe.recipes.item.IncubationRecipe;
import com.aetherteam.nitrogen.integration.rei.categories.AbstractRecipeCategory;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.REIRuntime;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.WidgetWithBounds;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import org.apache.commons.lang3.mutable.MutableDouble;

import java.text.DecimalFormat;
import java.util.List;
import java.util.function.Supplier;

public class AetherCookingRecipeCategory<R extends Recipe<?>> extends AbstractRecipeCategory<AetherCookingRecipeDisplay<R>> {
    public static final ResourceLocation ALTAR_TEXTURE = new ResourceLocation(Aether.MODID, "textures/gui/menu/altar.png");
    public static final ResourceLocation FREEZER_TEXTURE = new ResourceLocation(Aether.MODID, "textures/gui/menu/freezer.png");
    public static final ResourceLocation INCUBATOR_TEXTURE = new ResourceLocation(Aether.MODID, "textures/gui/menu/incubator.png");

    protected final Supplier<WidgetWithBounds> fuelIndicator;
    protected final Supplier<WidgetWithBounds> animatedProgressArrow;

    public AetherCookingRecipeCategory(String id, CategoryIdentifier<AetherCookingRecipeDisplay<R>> categoryIdentifier, int width, int height, Renderer icon, ResourceLocation texture) {
        this(id, categoryIdentifier, width, height, icon, texture, () -> animatedArrow(texture, 100));
    }

    public AetherCookingRecipeCategory(String id, CategoryIdentifier<AetherCookingRecipeDisplay<R>> categoryIdentifier, int width, int height, Renderer icon, ResourceLocation texture, Supplier<WidgetWithBounds> animatedProgressArrow) {
        super(id, categoryIdentifier, width, height, icon);

        this.fuelIndicator = () -> fuelIndicator(texture);
        this.animatedProgressArrow = animatedProgressArrow;
    }

    public static AetherCookingRecipeCategory<AltarRepairRecipe> altarRepair() {
        return new AetherCookingRecipeCategory<>("altar.repairing", AetherREIServerPlugin.ALTAR_REPAIR, 140, 39, EntryStacks.of(AetherBlocks.ALTAR.get()), ALTAR_TEXTURE);
    }

    public static AetherCookingRecipeCategory<EnchantingRecipe> altarEnchanting() {
        return new AetherCookingRecipeCategory<>("altar.enchanting", AetherREIServerPlugin.ALTAR_ENCHANTING, 140, 39, EntryStacks.of(AetherBlocks.ALTAR.get()), ALTAR_TEXTURE);
    }

    public static AetherCookingRecipeCategory<FreezingRecipe> freezing() {
        return new AetherCookingRecipeCategory<>("freezing", AetherREIServerPlugin.FREEZING, 140, 39, EntryStacks.of(AetherBlocks.FREEZER.get()), FREEZER_TEXTURE);
    }

    public static AetherCookingRecipeCategory<IncubationRecipe> incubating() {
        return new AetherCookingRecipeCategory<>("incubating", AetherREIServerPlugin.INCUBATING, 88, 54, EntryStacks.of(AetherBlocks.INCUBATOR.get()), INCUBATOR_TEXTURE, () -> {
            var lastTick = new MutableDouble(0);

            var widgetBound = new Rectangle(8, -13, 10, 54);

            return Widgets.wrapRenderer(widgetBound, new Renderer() {
                @Override
                public void render(PoseStack poseStack, Rectangle bounds, int mouseX, int mouseY, float delta) {
                    RenderSystem.setShaderTexture(0, INCUBATOR_TEXTURE);
                    lastTick.getAndAdd(delta);

                    if  (lastTick.getValue() > 5700) {
                        lastTick.setValue(0);
                    }

                    var textureLength = 54;
                    var scissorOffset = (int) Math.round(textureLength * (lastTick.getValue() / 5700));

                    Gui.blit(poseStack, bounds.x, bounds.y, 103, 16, 9, 54);
                    Gui.enableScissor(bounds.x, bounds.y + textureLength - scissorOffset, bounds.x + 10, bounds.y + (textureLength * 2) - scissorOffset);
                    Gui.blit(poseStack, bounds.x, bounds.y, 179, 16, 10, 54);
                    Gui.disableScissor();
                }

                @Override
                public int getZ() {
                    return 0;
                }

                @Override
                public void setZ(int z) {

                }
            });
        });
    }

    private static WidgetWithBounds fuelIndicator(ResourceLocation texture) {
        return Widgets.wrapRenderer(new Rectangle(14, 13), new Renderer() {
            @Override
            public void render(PoseStack poseStack, Rectangle bounds, int mouseX, int mouseY, float delta) {
                RenderSystem.setShaderTexture(0, texture);
                Gui.blit(poseStack, bounds.x, bounds.y, 176, 0, 14, 13);
            }

            @Override
            public int getZ() {
                return 0;
            }

            @Override
            public void setZ(int z) {

            }
        });
    }

    private static WidgetWithBounds animatedArrow(ResourceLocation texture, int burnTime) {
        var lastTick = new MutableDouble(0);

        return Widgets.wrapRenderer(new Rectangle(23, 16), new Renderer() {
            @Override
            public void render(PoseStack poseStack, Rectangle bounds, int mouseX, int mouseY, float delta) {

                lastTick.getAndAdd(delta);

                if (lastTick.getValue() > burnTime) {
                    lastTick.setValue(0);
                }

                var xOffset = 23 - (int) Math.round(23 * (lastTick.getValue() / burnTime));

                var blankArrow = REIRuntime.getInstance().getDefaultDisplayTexture(false);

                RenderSystem.setShaderTexture(0, blankArrow);
                Gui.blit(poseStack, bounds.x, bounds.y, 106, 91, 24, 17);
                Gui.enableScissor(bounds.x - xOffset, bounds.y, bounds.x + 23 - xOffset, bounds.y + 16);
                RenderSystem.setShaderTexture(0, texture);
                Gui.blit(poseStack, bounds.x, bounds.y, 176, 14, 23, 16);
                Gui.disableScissor();
            }

            @Override
            public int getZ() {
                return 0;
            }

            @Override
            public void setZ(int z) {

            }
        });
    }

    @Override
    public List<Widget> setupDisplay(AetherCookingRecipeDisplay<R> display, Rectangle bounds) {
        var widgets = super.setupDisplay(display, bounds);
        var startPoint = new Point(bounds.getCenterX() - 41, bounds.y + 10);

        var df = new DecimalFormat("###.##");

        float experience = display.getExperience();
        int cookingTime = display.getCookingTime();

        Component cookInfo;
        if(experience > 0){
            cookInfo = Component.translatable("category.rei.cooking.time&xp", df.format(experience), df.format(cookingTime / 20d));
        } else {
            cookInfo = Component.translatable("category.rei.campfire.time", df.format(cookingTime / 20d));
        }

        var labelPoint = new Point(bounds.x + bounds.width - 5, bounds.y + 5);

        if (display.isIncubation()) {
            labelPoint.move(labelPoint.x, bounds.getCenterY() - Minecraft.getInstance().font.lineHeight / 2);
        }

        widgets.add(Widgets.createLabel(labelPoint, cookInfo).noShadow().rightAligned().color(0xFF404040, 0xFFBBBBBB));

        var arrowPoint = new Point(startPoint.x + 24, startPoint.y + 8);

        var arrowWidget = this.animatedProgressArrow.get();
        arrowWidget.getBounds().translate(arrowPoint.x, arrowPoint.y);
        widgets.add(arrowWidget);

        if (display.isIncubation()) {
            startPoint.translate(6, 5);
        }

        var fuelWidget = this.fuelIndicator.get();
        fuelWidget.getBounds().move(startPoint.x + 2, startPoint.y + 20);
        widgets.add(fuelWidget);

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 1, startPoint.y + 1))
                .entries(display.getInputEntries().get(0))
                .markInput());

        var outputEntries = display.getOutputEntries();

        if (outputEntries.size() > 0) {
            widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 61, startPoint.y + 9)));

            widgets.add(Widgets.createSlot(new Point(startPoint.x + 61, startPoint.y + 9))
                    .entries(outputEntries.get(0))
                    .disableBackground()
                    .markOutput());
        }

        return widgets;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("gui.aether.jei." + this.id);
    }
}
