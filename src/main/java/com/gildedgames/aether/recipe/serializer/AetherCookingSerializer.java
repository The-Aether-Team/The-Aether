package com.gildedgames.aether.recipe.serializer;

import com.gildedgames.aether.recipe.AetherBookCategory;
import com.gildedgames.aether.recipe.recipes.item.AbstractAetherCookingRecipe;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

public class AetherCookingSerializer<T extends AbstractAetherCookingRecipe> implements RecipeSerializer<T> {
    private final int defaultCookingTime;
    private final AetherCookingSerializer.CookieBaker<T> factory;

    public AetherCookingSerializer(AetherCookingSerializer.CookieBaker<T> factory, int defaultCookingTime) {
        this.defaultCookingTime = defaultCookingTime;
        this.factory = factory;
    }

    public T fromJson(ResourceLocation id, JsonObject json) {
        String group = GsonHelper.getAsString(json, "group", "");
        AetherBookCategory aetherBookCategory = AetherBookCategory.CODEC.byName(GsonHelper.getAsString(json, "category", null), AetherBookCategory.MISC);
        JsonElement ingredientJson = GsonHelper.isArrayNode(json, "ingredient") ? GsonHelper.getAsJsonArray(json, "ingredient") : GsonHelper.getAsJsonObject(json, "ingredient");
        Ingredient ingredient = Ingredient.fromJson(ingredientJson);
        // Forge: Check if primitive string to keep vanilla or a object which can contain a count field.
        if (!json.has("result")) {
            throw new com.google.gson.JsonSyntaxException("Missing result, expected to find a string or object");
        }
        ItemStack result;
        if (json.get("result").isJsonObject()) {
            result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
        } else {
            String resultString = GsonHelper.getAsString(json, "result");
            ResourceLocation resultLocation = new ResourceLocation(resultString);
            result = new ItemStack(BuiltInRegistries.ITEM.getOptional(resultLocation).orElseThrow(() -> new IllegalStateException("Item: " + resultString + " does not exist")));
        }
        float experience = GsonHelper.getAsFloat(json, "experience", 0.0F);
        int cookingTime = GsonHelper.getAsInt(json, "cookingtime", this.defaultCookingTime);
        return this.factory.create(id, group, aetherBookCategory, ingredient, result, experience, cookingTime);
    }

    public T fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
        String group = buffer.readUtf();
        AetherBookCategory aetherBookCategory = buffer.readEnum(AetherBookCategory.class);
        Ingredient ingredient = Ingredient.fromNetwork(buffer);
        ItemStack result = buffer.readItem();
        float experience = buffer.readFloat();
        int cookingTime = buffer.readVarInt();
        return this.factory.create(id, group, aetherBookCategory, ingredient, result, experience, cookingTime);
    }

    public void toNetwork(FriendlyByteBuf buffer, T recipe) {
        buffer.writeUtf(recipe.getGroup());
        buffer.writeEnum(recipe.aetherCategory());
        recipe.getIngredients().get(0).toNetwork(buffer);
        buffer.writeItem(recipe.getResultItem());
        buffer.writeFloat(recipe.getExperience());
        buffer.writeVarInt(recipe.getCookingTime());
    }

    public interface CookieBaker<T extends AbstractAetherCookingRecipe> {
        T create(ResourceLocation id, String group, AetherBookCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime);
    }
}