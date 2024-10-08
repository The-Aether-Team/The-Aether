package com.aetherteam.aether.recipe.recipes.item;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.recipe.AetherBookCategory;
import com.aetherteam.aether.recipe.AetherRecipeSerializers;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SingleRecipeInput;

public class AltarRepairRecipe extends AbstractAetherCookingRecipe {
    public final Ingredient ingredient;

    public AltarRepairRecipe(String group, Ingredient ingredient, int repairTime) {
        super(AetherRecipeTypes.ENCHANTING.get(), group, AetherBookCategory.ENCHANTING_REPAIR, ingredient, ingredient.getItems()[0], 0.0F, repairTime);
        this.ingredient = ingredient;
    }

    /**
     * @param inventory The crafting {@link SingleRecipeInput}.
     * @return The original {@link ItemStack} ingredient, because repairing always outputs the same item as the input.
     */
    @Override
    public ItemStack assemble(SingleRecipeInput inventory, HolderLookup.Provider provider) {
        return this.ingredient.getItems()[0];
    }

    /**
     * @return The original {@link ItemStack} ingredient for Recipe Book display, because repairing always outputs the same item as the input.
     */
    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.ingredient.getItems()[0];
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(AetherBlocks.ALTAR.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AetherRecipeSerializers.REPAIRING.get();
    }

    public static class Serializer implements RecipeSerializer<AltarRepairRecipe> {
        private static final MapCodec<AltarRepairRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Codec.STRING.optionalFieldOf("group", "").forGetter(AbstractCookingRecipe::getGroup),
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter((recipe) -> recipe.ingredient),
                Codec.INT.fieldOf("repairTime").orElse(500).forGetter((recipe) -> recipe.cookingTime)
        ).apply(instance, AltarRepairRecipe::new));

        @Override
        public MapCodec<AltarRepairRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AltarRepairRecipe> streamCodec() {
            return null;
        }

        public AltarRepairRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            String group = buffer.readUtf();
            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            int cookingTime = buffer.readVarInt();
            return new AltarRepairRecipe(group, ingredient, cookingTime);
        }

        public void toNetwork(RegistryFriendlyByteBuf buffer, AltarRepairRecipe recipe) {
            buffer.writeUtf(recipe.group);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient);
            buffer.writeVarInt(recipe.cookingTime);
        }
    }
}
