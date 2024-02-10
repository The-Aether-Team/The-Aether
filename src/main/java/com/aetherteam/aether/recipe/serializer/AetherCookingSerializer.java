package com.aetherteam.aether.recipe.serializer;

import com.aetherteam.aether.recipe.AetherBookCategory;
import com.aetherteam.aether.recipe.recipes.item.AbstractAetherCookingRecipe;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.neoforged.neoforge.common.crafting.CraftingHelper;

/**
 * [CODE COPY] - {@link SimpleCookingSerializer}.<br><br>
 * Cleaned up.
 */
public class AetherCookingSerializer<T extends AbstractAetherCookingRecipe> implements RecipeSerializer<T> {
    private final int defaultCookingTime;
    private final AetherCookingSerializer.CookieBaker<T> factory;
    private final Codec<T> codec;

    public AetherCookingSerializer(AetherCookingSerializer.CookieBaker<T> factory, int defaultCookingTime) {
        this.defaultCookingTime = defaultCookingTime;
        this.factory = factory;
        this.codec = RecordCodecBuilder.create((p_300831_) -> {
            return p_300831_.group(ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter((p_300832_) -> {
                return p_300832_.getGroup();
            }), AetherBookCategory.CODEC.fieldOf("category").forGetter((p_300828_) -> {
                return p_300828_.aetherCategory();
            }), Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter((p_300833_) -> {
                return p_300833_.getIngredients().get(0);
            }), CraftingHelper.smeltingResultCodec().fieldOf("result").forGetter((p_300827_) -> {
                return p_300827_.getResult();
            }), Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter((p_300826_) -> {
                return p_300826_.getExperience();
            }), Codec.INT.fieldOf("cookingtime").orElse(defaultCookingTime).forGetter((p_300834_) -> {
                return p_300834_.getCookingTime();
            })).apply(p_300831_, factory::create);
        });
    }

    @Override
    public Codec<T> codec() {
        return this.codec;
    }

    @Override
    public T fromNetwork(FriendlyByteBuf friendlyByteBuf) {
        String group = friendlyByteBuf.readUtf();
        AetherBookCategory aetherBookCategory = friendlyByteBuf.readEnum(AetherBookCategory.class);
        Ingredient ingredient = Ingredient.fromNetwork(friendlyByteBuf);
        ItemStack result = friendlyByteBuf.readItem();
        float experience = friendlyByteBuf.readFloat();
        int cookingTime = friendlyByteBuf.readVarInt();
        return this.factory.create(group, aetherBookCategory, ingredient, result, experience, cookingTime);
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
        T create(String group, AetherBookCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime);
    }
}