package com.aetherteam.aether.integration.rei.categories.block;

//import com.aetherteam.aether.block.AetherBlocks;
//import com.aetherteam.aether.integration.rei.AetherREIServerPlugin;
//import com.aetherteam.aether.item.AetherItems;
//import com.aetherteam.aether.recipe.recipes.block.AccessoryFreezableRecipe;
//import com.aetherteam.aether.recipe.recipes.block.AmbrosiumRecipe;
//import com.aetherteam.aether.recipe.recipes.block.IcestoneFreezableRecipe;
//import com.aetherteam.nitrogen.integration.rei.categories.block.AbstractBlockStateRecipeCategory;
//import com.aetherteam.nitrogen.integration.rei.displays.BlockStateRecipeDisplay;
//import com.aetherteam.nitrogen.recipe.recipes.AbstractBlockStateRecipe;
//import me.shedaniel.rei.api.client.gui.Renderer;
//import me.shedaniel.rei.api.common.category.CategoryIdentifier;
//import me.shedaniel.rei.api.common.util.EntryStacks;
//import net.minecraft.network.chat.Component;
//
//public class AetherBlockStateRecipeCategory<R extends AbstractBlockStateRecipe> extends AbstractBlockStateRecipeCategory<R> {
//    protected AetherBlockStateRecipeCategory(String id, CategoryIdentifier<BlockStateRecipeDisplay<R>> uid, int width, int height, Renderer icon) {
//        super(id, uid, width, height, icon);
//    }
//
//    @Override
//    public Component getTitle() {
//        return Component.translatable("gui.aether.jei." + this.id);
//    }
//
//    public static AetherBlockStateRecipeCategory<AccessoryFreezableRecipe> accessoryFreezable() {
//        return new AetherBlockStateRecipeCategory<>("accessory_freezable", AetherREIServerPlugin.ACCESSORY_FREEZABLE,84, 28, EntryStacks.of(AetherItems.ICE_RING.get()));
//    }
//
//    public static AetherBlockStateRecipeCategory<IcestoneFreezableRecipe> icestoneFreezable() {
//        return new AetherBlockStateRecipeCategory<>("icestone_freezable", AetherREIServerPlugin.ICESTONE_FREEZABLE,84, 28, EntryStacks.of(AetherBlocks.ICESTONE.get()));
//    }
//
//    public static AetherBlockStateRecipeCategory<AmbrosiumRecipe> ambrosium() {
//        return new AetherBlockStateRecipeCategory<>("ambrosium_enchanting", AetherREIServerPlugin.AMBROSIUM_ENCHANTING,84, 28, EntryStacks.of(AetherItems.AMBROSIUM_SHARD.get()));
//    }
//}
