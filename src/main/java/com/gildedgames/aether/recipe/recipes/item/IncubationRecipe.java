package com.gildedgames.aether.recipe.recipes.item;

import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.recipe.AetherRecipeSerializers;
import com.gildedgames.aether.recipe.AetherRecipeTypes;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;

import javax.annotation.Nonnull;

public class IncubationRecipe implements Recipe<Container> {
    protected final RecipeType<?> type;
    protected final ResourceLocation id;
    protected final String group;
    protected final Ingredient ingredient;
    protected final EntityType<?> entity;
    protected final CompoundTag tag;
    protected final int incubationTime;

    public IncubationRecipe(ResourceLocation id, String group, Ingredient ingredient, EntityType<?> entity, CompoundTag tag, int incubationTime) {
        this.type = AetherRecipeTypes.INCUBATION.get();
        this.id = id;
        this.group = group;
        this.ingredient = ingredient;
        this.entity = entity;
        this.tag = tag;
        this.incubationTime = incubationTime;
    }

    @Override
    public boolean matches(Container menu, @Nonnull Level level) {
        return this.ingredient.test(menu.getItem(0));
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull Container menu) {
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ItemStack getResultItem() {
        return this.ingredient.getItems()[0];
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }
    public int getIncubationTime() {
        return this.incubationTime;
    }

    @Nonnull
    public EntityType<?> getEntity() {
        return this.entity;
    }

    public CompoundTag getTag() {
        return this.tag;
    }

    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonNullList = NonNullList.create();
        nonNullList.add(this.ingredient);
        return nonNullList;
    }

    @Nonnull
    @Override
    public String getGroup() {
        return this.group;
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Nonnull
    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(AetherBlocks.INCUBATOR.get());
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return AetherRecipeSerializers.INCUBATION.get();
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {
        return this.type;
    }

    public static class Serializer implements RecipeSerializer<IncubationRecipe> {
        @Nonnull
        public IncubationRecipe fromJson(@Nonnull ResourceLocation recipeLocation, @Nonnull JsonObject jsonObject) {
            String group = GsonHelper.getAsString(jsonObject, "group", "");
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "ingredient"));
            EntityType<?> entityType = EntityType.byString(GsonHelper.getAsString(jsonObject, "entity")).orElseThrow(() -> new JsonSyntaxException("Entity type cannot be found"));
            CompoundTag tag = null;
            if (jsonObject.has("tag")) {
                tag = CraftingHelper.getNBT(jsonObject.get("tag"));
            }
            int incubationTime = GsonHelper.getAsInt(jsonObject, "incubationtime", 5700);
            return new IncubationRecipe(recipeLocation, group, ingredient, entityType, tag, incubationTime);
        }

        public IncubationRecipe fromNetwork(@Nonnull ResourceLocation recipeLocation, FriendlyByteBuf buffer) {
            String group = buffer.readUtf();
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            EntityType<?> entityType = EntityType.byString(buffer.readUtf()).orElseThrow(() -> new JsonSyntaxException("Entity type cannot be found"));
            CompoundTag tag = null;
            if (buffer.readBoolean()) {
                tag = buffer.readNbt();
            }
            int incubationTime = buffer.readVarInt();
            return new IncubationRecipe(recipeLocation, group, ingredient, entityType, tag, incubationTime);
        }

        public void toNetwork(FriendlyByteBuf buffer, IncubationRecipe recipe) {
            buffer.writeUtf(recipe.group);
            recipe.ingredient.toNetwork(buffer);
            buffer.writeUtf(EntityType.getKey(recipe.getEntity()).toString());
            if (recipe.tag != null) {
                buffer.writeBoolean(true);
                buffer.writeNbt(recipe.tag);
            } else {
                buffer.writeBoolean(false);
            }
            buffer.writeVarInt(recipe.getIncubationTime());
        }
    }
}
