package com.gildedgames.aether.common.recipe;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.common.registry.AetherRecipes;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

/**
 * Custom recipe class for data packs to make use of the incubator.
 */
public class IncubatorRecipe extends AbstractCookingRecipe {
    private final EntityType<?> entity;
    public IncubatorRecipe(ResourceLocation pId, String pGroup, Ingredient pIngredient, EntityType<?> pEntity, float pExperience, int pCookingTime) {
        super(AetherRecipes.RecipeTypes.INCUBATING, pId, pGroup, pIngredient, ItemStack.EMPTY, pExperience, pCookingTime);
        this.entity = pEntity;
    }

    @Override
    @Nonnull
    public ItemStack getToastSymbol() {
        return new ItemStack(AetherBlocks.INCUBATOR.get());
    }

    @Override
    @Nonnull
    public RecipeSerializer<?> getSerializer() {
        return AetherRecipes.INCUBATING.get();
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<IncubatorRecipe> {

        @Override
        @Nonnull
        public IncubatorRecipe fromJson(@Nonnull ResourceLocation pRecipeId, @Nonnull JsonObject pSerializedRecipe) {
            String group = GsonHelper.getAsString(pSerializedRecipe, "group", "");
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "egg"));
            EntityType<?> entity = EntityType.byString(GsonHelper.getAsString(pSerializedRecipe, "entity", "")).orElse(AetherEntityTypes.MOA.get());
            int incubatingTime = GsonHelper.getAsInt(pSerializedRecipe, "incubateTime", 5700);
            return new IncubatorRecipe(pRecipeId, group, ingredient, entity, 0.0F, incubatingTime);
        }

        @Nullable
        @Override
        public IncubatorRecipe fromNetwork(@Nonnull ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            String group = pBuffer.readUtf();
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            EntityType<?> entity = EntityType.byString(pBuffer.readUtf()).orElse(AetherEntityTypes.MOA.get());
            int incubatingTime = pBuffer.readVarInt();
            return new IncubatorRecipe(pRecipeId, group, ingredient, entity, 0.0F, incubatingTime);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, IncubatorRecipe pRecipe) {
            pBuffer.writeUtf(pRecipe.group);
            pRecipe.ingredient.toNetwork(pBuffer);
            pBuffer.writeUtf(pRecipe.entity.getRegistryName().toString());
            pBuffer.writeVarInt(pRecipe.cookingTime);
        }
    }
}
