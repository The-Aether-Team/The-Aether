package com.aetherteam.aether.item.combat;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.entity.projectile.dart.AbstractDart;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class DartShooterItem extends ProjectileWeaponItem implements Vanishable {
    private final Supplier<? extends Item> dartType;

    public DartShooterItem(Supplier<? extends Item> dartType, Properties properties) {
        super(properties);
        this.dartType = dartType;
    }

    /**
     * Rearranged version of {@link net.minecraft.world.item.BowItem#use(Level, Player, InteractionHand)}.
     *
     * @param level  The {@link Level} of the user.
     * @param player The {@link Player} using this item.
     * @param hand   The {@link InteractionHand} in which the item is being used.
     * @return Consume (cause the item to bob down then up in hand) if the player has ammo or is in creative, or fail (do nothing) if those conditions aren't met, or use the result of the Forge event hook if there is one.
     * This is an {@link InteractionResultHolder InteractionResultHolder&lt;ItemStack&gt;}.
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);
        boolean hasAmmo = !player.getProjectile(heldStack).isEmpty();

        InteractionResultHolder<ItemStack> result = EventHooks.onArrowNock(heldStack, level, player, hand, hasAmmo);
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
     * Based somewhat on {@link net.minecraft.world.item.BowItem#releaseUsing(ItemStack, Level, LivingEntity, int)}, although we use {@link Item#finishUsingItem(ItemStack, Level, LivingEntity)} instead to allow for the Dart Shooter to shoot continuously without releasing.
     *
     * @param stack The {@link ItemStack} in use.
     * @param level The {@link Level} of the user.
     * @param user  The {@link LivingEntity} using the stack.
     * @return The used {@link ItemStack}.
     */
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
        if (user instanceof Player player) {
            ItemStack ammoItem = player.getProjectile(stack); // Gets matching ammo stack from inventory according to DartShooterItem#getAllSupportedProjectiles().

            boolean creativeOrShooterIsInfinite = player.getAbilities().instabuild || stack.getEnchantmentLevel(level.holderOrThrow(Enchantments.INFINITY)) > 0; // Note: Dart shooters can't be enchanted with Infinity in survival, but we still implement the behavior.
            boolean stillHasAmmo = !ammoItem.isEmpty() || creativeOrShooterIsInfinite;

            EventHooks.onArrowLoose(stack, level, player, 0, stillHasAmmo);

            if (stillHasAmmo) { // Seems to be a failsafe check; under normal circumstances this should already be true because of the checks in DartShooterItem#use().
                if (ammoItem.isEmpty()) {
                    ammoItem = new ItemStack(this.getDartType().get()); // Another failsafe to create a stack if somehow the ammoItem is empty at this stage of the code; under normal circumstances this seems to never get reached.
                }
                boolean creativeOrDartIsInfinite = player.getAbilities().instabuild || (ammoItem.getItem() instanceof DartItem dartItem && dartItem.isInfinite(stack));

                if (!level.isClientSide()) {
                    DartItem dartItem = (DartItem) (ammoItem.getItem() instanceof DartItem dart ? dart : this.getDartType().get());
                    AbstractDart dart = dartItem.createDart(level, player);
                    if (dart != null) {
                        dart = this.customDart(dart);
                        dart.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3.1F, 1.2F);
                        dart.setNoGravity(true); // Darts have no gravity.

                        int powerModifier = stack.getEnchantmentLevel(level.holderOrThrow(Enchantments.POWER));
                        if (powerModifier > 0) {
                            dart.setBaseDamage(dart.getBaseDamage() + powerModifier * 0.1 + 0.1);
                        }

                        int punchModifier = stack.getEnchantmentLevel(level.holderOrThrow(Enchantments.PUNCH));
                        if (punchModifier > 0) {
                            dart.setKnockback(punchModifier);
                        }

                        if (creativeOrDartIsInfinite || player.getAbilities().instabuild) {
                            dart.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                        }

                        level.addFreshEntity(dart);
                    } else {
                        Aether.LOGGER.warn("Failed to create dart from Dart Shooter");
                    }
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
     * The Dart Shooter has a very short usage duration to make it almost instant but still play the usage animation; any shorter duration breaks the animation.
     *
     * @param stack The {@link ItemStack} in use.
     * @return The usage duration in ticks as an {@link Integer}.
     */
    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 10;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return (stack) -> stack.is(this.getDartType().get());
    }

    public AbstractDart customDart(AbstractDart dart) {
        return dart;
    }

    /**
     * @return The block range as an {@link Integer} that an entity's AI is capable of targeting to shoot with this weapon.
     */
    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isPrimaryItemFor(ItemStack stack, Holder<Enchantment> enchantment) {
        return ImmutableSet.of(Enchantments.POWER, Enchantments.PUNCH).contains(enchantment.getKey());
    }

    /**
     * @return A {@link Supplier Supplier&lt;? extends Item&gt;} that gives the Dart item that this Dart Shooter is capable of using as ammo.
     */
    public Supplier<? extends Item> getDartType() {
        return this.dartType;
    }
}
