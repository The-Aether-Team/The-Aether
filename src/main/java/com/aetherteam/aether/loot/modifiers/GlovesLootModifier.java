package com.aetherteam.aether.loot.modifiers;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

import java.util.List;

public class GlovesLootModifier extends LootModifier {
    public static final MapCodec<GlovesLootModifier> CODEC = RecordCodecBuilder.mapCodec(instance -> codecStart(instance)
            .and(ItemStack.CODEC.fieldOf("gloves").forGetter(modifier -> modifier.glovesStack))
            .and(BuiltInRegistries.ARMOR_MATERIAL.holderByNameCodec().fieldOf("armor_material").forGetter(modifier -> modifier.armorMaterial))
            .apply(instance, GlovesLootModifier::new));

    public final ItemStack glovesStack;
    public final Holder<ArmorMaterial> armorMaterial;

    public GlovesLootModifier(LootItemCondition[] conditionsIn, ItemStack glovesStack, Holder<ArmorMaterial> armorMaterial) {
        super(conditionsIn);
        this.glovesStack = glovesStack;
        this.armorMaterial = armorMaterial;
    }

    /**
     * Randomly replaces {@link ArmorItem} {@link ItemStack}s in loot with gloves of the equivalent {@link ArmorMaterials} with a 1/10 chance for each armor item,
     * with the gloves provided by {@link GlovesLootModifier#glovesStack} and the material provided by {@link GlovesLootModifier#armorMaterial}.
     * During replacement, the gloves will retain any enchantments from the respective armor piece.
     *
     * @param lootStacks Result items from a loot table as an {@link ObjectArrayList} of {@link ItemStack}s.
     * @param context    The {@link LootContext}.
     * @return A new {@link ObjectArrayList} of {@link ItemStack}s that a loot table will give.
     */
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> lootStacks, LootContext context) {
        RandomSource randomSource = context.getRandom();
        Vec3 vec3 = context.getParamOrNull(LootContextParams.ORIGIN);
        if (vec3 != null) {
            BlockPos pos = BlockPos.containing(vec3);
            BlockEntity blockEntity = context.getLevel().getBlockEntity(pos);
            if (blockEntity instanceof BaseContainerBlockEntity) {
                List<ItemStack> armorItems = lootStacks.stream().filter((itemStack) -> itemStack.getItem() instanceof ArmorItem armorItem && armorItem.getMaterial().equals(this.armorMaterial)).toList();
                for (ItemStack armorStack : armorItems) {
                    if (randomSource.nextInt(4) < 1) {
                        ItemStack gloves = this.glovesStack.copy();
                        int cost = 0;
                        boolean isTreasure = false;
                        for (Object2IntMap.Entry<Holder<Enchantment>> enchantmentInfo : armorStack.getAllEnchantments().entrySet()) {
                            Holder<Enchantment> enchantment = enchantmentInfo.getKey();
                            int level = enchantmentInfo.getValue();
                            cost = Math.max(cost, enchantment.value().getMinCost(level));
                            if (!isTreasure) {
                                isTreasure = enchantment.is(EnchantmentTags.TREASURE);
                            }
                            if (gloves.canApplyAtEnchantingTable(enchantment)) {
                                gloves.enchant(enchantment, enchantmentInfo.getValue());
                            }
                        }
                        if (!armorStack.getAllEnchantments().isEmpty() && gloves.getAllEnchantments().isEmpty()) {
                            EnchantmentHelper.enchantItem(randomSource, gloves, cost, isTreasure);
                        }
                        if (armorStack.getAllEnchantments().isEmpty() || !gloves.getAllEnchantments().isEmpty()) {
                            lootStacks.replaceAll((stack) -> stack.equals(armorStack) ? gloves : stack);
                        }
                    }
                }
            }
        }
        return lootStacks;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return GlovesLootModifier.CODEC;
    }
}
