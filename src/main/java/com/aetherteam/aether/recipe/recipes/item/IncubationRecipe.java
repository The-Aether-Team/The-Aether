package com.aetherteam.aether.recipe.recipes.item;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.recipe.AetherRecipeSerializers;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class IncubationRecipe implements Recipe<Container> {
    protected final RecipeType<?> type;
    protected final String group;
    protected final Ingredient ingredient;
    protected final EntityType<?> entity;
    protected final CompoundTag tag;
    protected final int incubationTime;

    public IncubationRecipe(String group, Ingredient ingredient, EntityType<?> entity, CompoundTag tag, int incubationTime) {
        this.type = AetherRecipeTypes.INCUBATION.get();
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
        private static final Codec<IncubationRecipe> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter(IncubationRecipe::getGroup),
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter((recipe) -> recipe.ingredient),
                BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("entity").forGetter((recipe) -> recipe.entity),
                CompoundTag.CODEC.fieldOf("tag").orElse(null).forGetter((recipe) -> recipe.tag),
                Codec.INT.fieldOf("incubationtime").orElse(500).forGetter((recipe) -> recipe.incubationTime)
        ).apply(instance, IncubationRecipe::new));

        @Nullable
        @Override
        public IncubationRecipe fromNetwork(FriendlyByteBuf buffer) {
            String group = buffer.readUtf();
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            EntityType<?> entityType = EntityType.byString(buffer.readUtf()).orElseThrow(() -> new JsonSyntaxException("Entity type cannot be found"));
            CompoundTag tag = null;
            if (buffer.readBoolean()) {
                tag = buffer.readNbt();
            }
            int incubationTime = buffer.readVarInt();
            return new IncubationRecipe(group, ingredient, entityType, tag, incubationTime);
        }

        @Override
        public Codec<IncubationRecipe> codec() {
            return CODEC;
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
