package com.aether.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TieredItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;

public class ValkyrieLanceItem extends TieredItem implements IValkyrieToolItem {
    private final int attackDamage;
    private final float attackSpeed;
    /** Modifiers applied when the item is in the mainhand of a user. */
    private Multimap<Attribute, AttributeModifier> attributeModifiers;

    public ValkyrieLanceItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, builder);
        this.attackDamage = (int) (attackDamageIn + tier.getAttackDamage());
        this.attackSpeed = attackSpeedIn;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = ImmutableMultimap.builder();
        attributeBuilder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
        attributeBuilder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)attackSpeedIn, AttributeModifier.Operation.ADDITION));
        this.attributeModifiers = attributeBuilder.build();
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damageItem(1, attacker, (entity) -> {
            entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        });
        return true;
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (state.getBlockHardness(worldIn, pos) != 0.0F) {
            stack.damageItem(2, entityLiving, (entity) -> {
                entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
        }

        return true;
    }

    public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        return !player.isCreative();
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", this.attackSpeed, AttributeModifier.Operation.ADDITION));
        builder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(reachModifierUUID, "Tool modifier", this.getReachDistanceModifier(), AttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
        return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(equipmentSlot);
    }
}
