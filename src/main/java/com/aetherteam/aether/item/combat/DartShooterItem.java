package com.aetherteam.aether.item.combat;

import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.entity.projectile.dart.AbstractDart;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class DartShooterItem extends ProjectileWeaponItem { //implements Vanishable
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
            ItemStack itemStack = player.getProjectile(stack);
            if (!itemStack.isEmpty()) {
                net.neoforged.neoforge.event.EventHooks.onArrowLoose(stack, level, player, 0, !itemStack.isEmpty());

                List<ItemStack> list = draw(stack, itemStack, player);
                if (level instanceof ServerLevel serverlevel && !list.isEmpty()) {
                    this.shoot(serverlevel, player, player.getUsedItemHand(), stack, list, 3.1F, 1.2F, false, null);
                }

                level.playSound(null, player.getX(), player.getY(), player.getZ(), AetherSoundEvents.ITEM_DART_SHOOTER_SHOOT.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
                player.awardStat(Stats.ITEM_USED.get(this));
            }
        }
        return stack;
    }

    @Override
    protected void shootProjectile(LivingEntity shooter, Projectile projectile, int index, float velocity, float inaccuracy, float angle, @Nullable LivingEntity target) {
        projectile.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), 0.0F, velocity, inaccuracy);
    }

    @Override
    protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit) {
        DartItem dartItem = ammo.getItem() instanceof DartItem dart ? dart : (DartItem) this.getDartType().get();
        AbstractDart dart = dartItem.createDart(level, ammo, shooter, weapon);
        if (dart != null) {
            dart.setNoGravity(true); // Darts have no gravity.
            return this.customDart(dart, ammo, weapon);
        }
        return super.createProjectile(level, shooter, weapon, ammo, isCrit);
    }

    public AbstractDart customDart(AbstractDart dart, ItemStack projectileStack, ItemStack weaponStack) {
        return dart;
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
