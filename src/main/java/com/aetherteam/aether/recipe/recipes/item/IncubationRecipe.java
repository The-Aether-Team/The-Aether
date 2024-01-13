package com.aetherteam.aether.recipe.recipes.item;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.recipe.AetherRecipeSerializers;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;

import org.jetbrains.annotations.Nullable;

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
    public boolean matches(Container menu, Level level) {
        return this.ingredient.test(menu.getItem(0));
    }

    /**
     * @return An empty {@link ItemStack}, as there is no item output.
     */
    @Override
    public ItemStack assemble(Container menu, RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    /**
     * @return The original {@link ItemStack} ingredient for Recipe Book display.
     */
    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.ingredient.getItems()[0];
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    public int getIncubationTime() {
        return this.incubationTime;
    }

    public EntityType<?> getEntity() {
        return this.entity;
    }

    public CompoundTag getTag() {
        return this.tag;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonNullList = NonNullList.create();
        nonNullList.add(this.ingredient);
        return nonNullList;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(AetherBlocks.INCUBATOR.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AetherRecipeSerializers.INCUBATION.get();
    }

    @Override
    public RecipeType<?> getType() {
        return this.type;
    }

    public static class Serializer implements RecipeSerializer<IncubationRecipe> {
        @Override
        public IncubationRecipe fromJson(ResourceLocation recipeLocation, JsonObject jsonObject) {
            String group = GsonHelper.getAsString(jsonObject, "group", "");
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "ingredient"));
            EntityType<?> entityType = EntityType.byString(GsonHelper.getAsString(jsonObject, "entity")).orElseThrow(() -> new JsonSyntaxException("Entity type cannot be found"));
            CompoundTag tag = null;
            if (jsonObject.has("tag")) {
                tag = CraftingHelper.getNBT(jsonObject.get("tag"));
            }
            int incubationTime = GsonHelper.getAsInt(jsonObject, "incubationtime", 2500);
            return new IncubationRecipe(recipeLocation, group, ingredient, entityType, tag, incubationTime);
        }

        @Nullable
        @Override
        public IncubationRecipe fromNetwork(ResourceLocation recipeLocation, FriendlyByteBuf buffer) {
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

        @Override
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
