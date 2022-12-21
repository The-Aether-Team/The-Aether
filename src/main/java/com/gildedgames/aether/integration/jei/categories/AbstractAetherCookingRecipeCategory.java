//package com.gildedgames.aether.integration.jei.categories;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import mezz.jei.api.gui.drawable.IDrawable;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.Font;
//import net.minecraft.network.chat.Component;
//import net.minecraft.world.item.crafting.AbstractCookingRecipe;
//
//public abstract class AbstractAetherCookingRecipeCategory {
//    protected void drawExperience(AbstractCookingRecipe recipe, PoseStack poseStack, int y, IDrawable background) {
//        float experience = recipe.getExperience();
//        if (experience > 0) {
//            Component experienceString = Component.translatable("gui.jei.category.smelting.experience", experience);
//            Minecraft minecraft = Minecraft.getInstance();
//            Font fontRenderer = minecraft.font;
//            int stringWidth = fontRenderer.width(experienceString);
//            fontRenderer.draw(poseStack, experienceString, background.getWidth() - stringWidth, y, 0xFF808080);
//        }
//    }
//
//    protected void drawCookTime(AbstractCookingRecipe recipe, PoseStack poseStack, int y, IDrawable background) {
//        int cookTime = recipe.getCookingTime();
//        if (cookTime > 0) {
//            int cookTimeSeconds = cookTime / 20;
//            Component timeString = Component.translatable("gui.jei.category.smelting.time.seconds", cookTimeSeconds);
//            Minecraft minecraft = Minecraft.getInstance();
//            Font fontRenderer = minecraft.font;
//            int stringWidth = fontRenderer.width(timeString);
//            fontRenderer.draw(poseStack, timeString, background.getWidth() - stringWidth, y, 0xFF808080);
//        }
//    }
//}
