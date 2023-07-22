package com.aetherteam.aether.recipe.serializer;

import com.aetherteam.aether.recipe.AetherBookCategory;
import com.aetherteam.aether.recipe.recipes.item.AbstractAetherCookingRecipe;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

/**
 * [VANILLA COPY] - {@link SimpleCookingSerializer}.<br><br>
 * Cleaned up.
 */
public class AetherCookingSerializer<T extends AbstractAetherCookingRecipe> implements RecipeSerializer<T> {
    private final int defaultCookingTime;
    private final AetherCookingSerializer.CookieBaker<T> factory;

    public AetherCookingSerializer(AetherCookingSerializer.CookieBaker<T> factory, int defaultCookingTime) {
        this.defaultCookingTime = defaultCookingTime;
        this.factory = factory;
    }

    @Override
    public T fromJson(ResourceLocation id, JsonObject json) {
        String group = GsonHelper.getAsString(json, "group", "");
        AetherBookCategory aetherBookCategory = AetherBookCategory.CODEC.byName(GsonHelper.getAsString(json, "category", null), AetherBookCategory.UNKNOWN);
        JsonElement ingredientJson = GsonHelper.isArrayNode(json, "ingredient") ? GsonHelper.getAsJsonArray(json, "ingredient") : GsonHelper.getAsJsonObject(json, "ingredient");
        Ingredient ingredient = Ingredient.fromJson(ingredientJson);

        ItemStack result;
        if (!json.has("result")) {
            throw new JsonSyntaxException("Missing result, expected to find a string or object");
        }
        if (json.get("result").isJsonObject()) {
            result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
        } else {
            String resultString = GsonHelper.getAsString(json, "result");
            ResourceLocation resultLocation = new ResourceLocation(resultString);
            result = new ItemStack(ForgeRegistries.ITEMS.getValue(resultLocation));
            if (result.isEmpty()) {
                throw new IllegalStateException("Item: " + resultString + " does not exist");
            }
        }

        float experience = GsonHelper.getAsFloat(json, "experience", 0.0F);
        int cookingTime = GsonHelper.getAsInt(json, "cookingtime", this.defaultCookingTime);

        return this.factory.create(id, group, aetherBookCategory, ingredient, result, experience, cookingTime);
    }

    @Nullable
    @Override
    public T fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
        String group = buffer.readUtf();
        AetherBookCategory aetherBookCategory = buffer.readEnum(AetherBookCategory.class);
        Ingredient ingredient = Ingredient.fromNetwork(buffer);
        ItemStack result = buffer.readItem();
        float experience = buffer.readFloat();
        int cookingTime = buffer.readVarInt();
        return this.factory.create(id, group, aetherBookCategory, ingredient, result, experience, cookingTime);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, T recipe) {
        buffer.writeUtf(recipe.getGroup());
        buffer.writeEnum(recipe.aetherCategory());
        recipe.getIngredients().get(0).toNetwork(buffer);
        buffer.writeItem(recipe.getResult());
        buffer.writeFloat(recipe.getExperience());
        buffer.writeVarInt(recipe.getCookingTime());
    }

    public interface CookieBaker<T extends AbstractAetherCookingRecipe> {
        T create(ResourceLocation id, String group, AetherBookCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime);
    }
}