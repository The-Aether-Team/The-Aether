package com.aetherteam.aether.recipe.recipes.item;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.recipe.AetherBookCategory;
import com.aetherteam.aether.recipe.AetherRecipeSerializers;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nullable;

public class AltarRepairRecipe extends AbstractAetherCookingRecipe {
    public final Ingredient ingredient;

    public AltarRepairRecipe(String group, AetherBookCategory category, Ingredient ingredient, int repairTime) {
        super(AetherRecipeTypes.ENCHANTING.get(), group, category, ingredient, ingredient.getItems()[0], 0.0F, repairTime);
        this.ingredient = ingredient;
    }

    /**
     * @param inventory The crafting {@link Container}.
     * @return The original {@link ItemStack} ingredient, because repairing always outputs the same item as the input.
     */
    @Override
    public ItemStack assemble(Container inventory, RegistryAccess registryAccess) {
        return this.ingredient.getItems()[0];
    }

    /**
     * @return The original {@link ItemStack} ingredient for Recipe Book display, because repairing always outputs the same item as the input.
     */
    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
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
        private static final Codec<AltarRepairRecipe> CODEC = RecordCodecBuilder.create((p_296927_) -> {
            return p_296927_.group(ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter((p_300832_) -> {
                return p_300832_.getGroup();
            }), AetherBookCategory.CODEC.fieldOf("category").forGetter((p_296920_) -> {
                return AetherBookCategory.CODEC.byName(p_296920_.group);
            }), Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter((p_296923_) -> {
                return p_296923_.ingredient;
            }), Codec.INT.fieldOf("repairTime").orElse(500).forGetter((p_296923_) -> {
                return p_296923_.cookingTime;
            })).apply(p_296927_, AltarRepairRecipe::new);
        });

        @Override
        public Codec<AltarRepairRecipe> codec() {
            return CODEC;
        }

        @Nullable
        @Override
        public AltarRepairRecipe fromNetwork(FriendlyByteBuf buffer) {
            String group = buffer.readUtf();

            AetherBookCategory aetherBookCategory = buffer.readEnum(AetherBookCategory.class);
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            int cookingTime = buffer.readVarInt();
            return new AltarRepairRecipe(group, aetherBookCategory, ingredient, cookingTime);
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
