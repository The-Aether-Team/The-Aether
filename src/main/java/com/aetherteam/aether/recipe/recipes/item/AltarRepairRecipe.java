package com.aetherteam.aether.recipe.recipes.item;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.recipe.AetherBookCategory;
import com.aetherteam.aether.recipe.AetherRecipeSerializers;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nullable;

public class AltarRepairRecipe extends AbstractAetherCookingRecipe {
    public final Ingredient ingredient;

    public AltarRepairRecipe(ResourceLocation id, String group, AetherBookCategory category, Ingredient ingredient, int repairTime) {
        super(AetherRecipeTypes.ENCHANTING.get(), id, group, category, ingredient, ingredient.getItems()[0], 0.0F, repairTime);
        this.ingredient = ingredient;
    }

    /**
     * @param inventory The crafting {@link Container}.
     * @return The original {@link ItemStack} ingredient, because repairing always outputs the same item as the input.
     */
    @Override
    public ItemStack assemble(Container inventory) {
        return this.ingredient.getItems()[0];
    }

    /**
     * @return The original {@link ItemStack} ingredient for Recipe Book display, because repairing always outputs the same item as the input.
     */
    @Override
    public ItemStack getResultItem() {
        return this.ingredient.getItems()[0];
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(AetherBlocks.ALTAR.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AetherRecipeSerializers.ENCHANTING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return AetherRecipeTypes.ENCHANTING.get();
    }

    public static class Serializer implements RecipeSerializer<AltarRepairRecipe> {
        @Override
        public AltarRepairRecipe fromJson(ResourceLocation id, JsonObject json) {
            String group = GsonHelper.getAsString(json, "group", "");
            AetherBookCategory aetherBookCategory = AetherBookCategory.CODEC.byName(GsonHelper.getAsString(json, "category", AetherBookCategory.UNKNOWN.name()));
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "ingredient"));
            int cookingTime = GsonHelper.getAsInt(json, "repairTime", 500);
            return new AltarRepairRecipe(id, group, aetherBookCategory, ingredient, cookingTime);
        }

        @Nullable
        @Override
        public AltarRepairRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            String group = buffer.readUtf();
            AetherBookCategory aetherBookCategory = buffer.readEnum(AetherBookCategory.class);
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            int cookingTime = buffer.readVarInt();
            return new AltarRepairRecipe(id, group, aetherBookCategory, ingredient, cookingTime);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, AltarRepairRecipe recipe) {
            buffer.writeUtf(recipe.group);
            buffer.writeEnum(recipe.aetherCategory());
            recipe.ingredient.toNetwork(buffer);
            buffer.writeVarInt(recipe.cookingTime);
        }
    }
}
