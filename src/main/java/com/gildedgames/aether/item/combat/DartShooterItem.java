package com.gildedgames.aether.item.combat;

import com.gildedgames.aether.client.AetherSoundEvents;
import com.gildedgames.aether.entity.projectile.dart.AbstractDart;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;

import java.util.function.Predicate;
import java.util.function.Supplier;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nonnull;

public class DartShooterItem extends ProjectileWeaponItem implements Vanishable {
    private final Supplier<Item> dartType;

    public DartShooterItem(Supplier<Item> dartType, Properties properties) {
        super(properties);
        this.dartType = dartType;
    }

    /**
     * Rearranged version of {@link net.minecraft.world.item.BowItem#use(Level, Player, InteractionHand)}.
     * @param level The level of the user.
     * @param player The entity using this item.
     * @param hand The hand in which the item is being used.
     * @return Consume (cause the item to bob down then up in hand) if the player has ammo or is in creative, or fail (do nothing) if those conditions aren't meant, or use the result of the Forge event hook if there is one.
     */
    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, Player player, @Nonnull InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);
        boolean hasAmmo = !player.getProjectile(heldStack).isEmpty();

        InteractionResultHolder<ItemStack> result = ForgeEventFactory.onArrowNock(heldStack, level, player, hand, hasAmmo);
        if (result == null) {
            if (player.getAbilities().instabuild || hasAmmo) {
                player.startUsingItem(hand);
                return InteractionResultHolder.consume(heldStack);
            } else {
                return InteractionResultHolder.fail(heldStack);
            }
        } else {
            return result;
        }
    }

    /**
     * Based somewhat on {@link net.minecraft.world.item.BowItem#releaseUsing(ItemStack, Level, LivingEntity, int)}, although we use {@link Item#finishUsingItem(ItemStack, Level, LivingEntity)} instead to allow for the dart shooter to shoot continuously without releasing.
     * @param stack The stack in use.
     * @param level The level of the user.
     * @param user The entity using the stack.
     * @return The used stack.
     */
    @Nonnull
    @Override
    public ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull LivingEntity user) {
        if (user instanceof Player player) {
            ItemStack ammoItem = player.getProjectile(stack); // Gets matching ammo stack from inventory according to DartShooterItem#getAllSupportedProjectiles().

            boolean creativeOrShooterIsInfinite = player.getAbilities().instabuild || stack.getEnchantmentLevel(Enchantments.INFINITY_ARROWS) > 0; // Note: Dart shooters can't be enchanted with infinity in survival, but we still implement the behavior.
            boolean stillHasAmmo = !ammoItem.isEmpty() || creativeOrShooterIsInfinite;

            ForgeEventFactory.onArrowLoose(stack, level, player, 0, stillHasAmmo);

            if (stillHasAmmo) { // Seems to be a failsafe check; under normal circumstances this should already be true because of the checks in DartShooterItem#use().
                if (ammoItem.isEmpty()) {
                    ammoItem = new ItemStack(this.getDartType().get()); // Another failsafe to create a stack if somehow the ammoItem is empty at this stage of the code; under normal circumstances this seems to never get reached.
                }
                boolean creativeOrDartIsInfinite = player.getAbilities().instabuild || (ammoItem.getItem() instanceof DartItem dartItem && dartItem.isInfinite(stack));

                if (!level.isClientSide()) {
                    DartItem dartItem = (DartItem) (ammoItem.getItem() instanceof DartItem dart ? dart : this.getDartType().get());
                    AbstractDart dart = dartItem.createDart(level, player);
                    dart = customDart(dart);
                    dart.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.0F, 1.0F);
                    dart.setNoGravity(true); // Darts have no gravity.

                    int powerModifier = stack.getEnchantmentLevel(Enchantments.POWER_ARROWS);
                    if (powerModifier > 0) {
                        dart.setBaseDamage(dart.getBaseDamage() + powerModifier * 0.5 + 0.5);
                    }

                    int punchModifier = stack.getEnchantmentLevel(Enchantments.PUNCH_ARROWS);
                    if (punchModifier > 0) {
                        dart.setKnockback(punchModifier);
                    }

                    if (creativeOrDartIsInfinite || player.getAbilities().instabuild) {
                        dart.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    }

                    level.addFreshEntity(dart);
                }
                level.playSound(null, player.getX(), player.getY(), player.getZ(), AetherSoundEvents.ITEM_DART_SHOOTER_SHOOT.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

                if (!creativeOrDartIsInfinite && !player.getAbilities().instabuild) {
                    ammoItem.shrink(1);
                    if (ammoItem.isEmpty()) {
                        player.getInventory().removeItem(ammoItem);
                    }
                }
                player.awardStat(Stats.ITEM_USED.get(this));
            }
        }
        return stack;
    }

    /**
     * The dart shooter has a very short usage duration to make it almost instant but still play the usage animation; any shorter duration breaks the animation.
     * @param stack The stack in use.
     * @return The usage duration in ticks.
     */
    @Override
    public int getUseDuration(@Nonnull ItemStack stack) {
        return 4;
    }

    @Nonnull
    @Override
    public UseAnim getUseAnimation(@Nonnull ItemStack stack) {
        return UseAnim.BOW;
    }

    @Nonnull
    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return (stack) -> stack.is(this.getDartType().get());
    }

    public AbstractDart customDart(AbstractDart dart) {
        return dart;
    }

    /**
     * @return The block range that an entity's AI is capable of targeting to shoot with this weapon.
     */
    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }

    @Override
    public boolean isEnchantable(@Nonnull ItemStack stack) {
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return ImmutableSet.of(Enchantments.POWER_ARROWS, Enchantments.PUNCH_ARROWS).contains(enchantment);
    }

    /**
     * @return The dart item that this dart shooter is capable of using as ammo.
     */
    public Supplier<Item> getDartType() {
        return this.dartType;
    }
}