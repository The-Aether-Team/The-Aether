package com.aetherteam.aether.loot.modifiers;

import com.aetherteam.aether.utils.FabricUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.fabricators_of_create.porting_lib.loot.IGlobalLootModifier;
import io.github.fabricators_of_create.porting_lib.loot.LootModifier;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ArmorItem;
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

import java.util.List;
import java.util.Map;

public class GlovesLootModifier extends LootModifier {
    public static final Codec<GlovesLootModifier> CODEC = RecordCodecBuilder.create(instance -> codecStart(instance)
            .and(ItemStack.CODEC.fieldOf("gloves").forGetter(modifier -> modifier.glovesStack))
            .and(ArmorMaterials.CODEC.fieldOf("armor_material").forGetter(modifier -> modifier.armorMaterial))
            .apply(instance, GlovesLootModifier::new));

    public final ItemStack glovesStack;
    public final ArmorMaterials armorMaterial;

    public GlovesLootModifier(LootItemCondition[] conditionsIn, ItemStack glovesStack, ArmorMaterials armorMaterial) {
        super(conditionsIn);
        this.glovesStack = glovesStack;
        this.armorMaterial = armorMaterial;
    }

    /**
     * Randomly replaces {@link ArmorItem} {@link ItemStack}s in loot with gloves of the equivalent {@link ArmorMaterials} with a 1/10 chance for each armor item,
     * with the gloves provided by {@link GlovesLootModifier#glovesStack} and the material provided by {@link GlovesLootModifier#armorMaterial}.
     * During replacement, the gloves will retain any enchantments from the respective armor piece.
     * @param lootStacks Result items from a loot table as an {@link ObjectArrayList} of {@link ItemStack}s.
     * @param context The {@link LootContext}.
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
                        for (Map.Entry<Enchantment, Integer> enchantmentInfo : FabricUtils.getAllEnchantments(armorStack).entrySet()) {
                            Enchantment enchantment = enchantmentInfo.getKey();
                            int level = enchantmentInfo.getValue();
                            cost = Math.max(cost, enchantment.getMinCost(level));
                            if (!isTreasure) {
                                isTreasure = enchantment.isTreasureOnly();
                            }
                            if (FabricUtils.canApplyAtEnchantingTable(gloves, enchantment)) {
                                gloves.enchant(enchantment, enchantmentInfo.getValue());
                            }
                        }
                        if (!FabricUtils.getAllEnchantments(armorStack).isEmpty() && FabricUtils.getAllEnchantments(gloves).isEmpty()) {
                            EnchantmentHelper.enchantItem(randomSource, gloves, cost, isTreasure);
                        }
                        if (FabricUtils.getAllEnchantments(armorStack).isEmpty() || !FabricUtils.getAllEnchantments(gloves).isEmpty()) {
                            lootStacks.replaceAll((stack) -> stack.equals(armorStack) ? gloves : stack);
                        }
                    }
                }
            }
        }
        return lootStacks;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return GlovesLootModifier.CODEC;
    }
}