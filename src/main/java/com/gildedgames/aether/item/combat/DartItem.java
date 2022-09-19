package com.gildedgames.aether.item.combat;

import com.gildedgames.aether.entity.projectile.dart.AbstractDart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class DartItem extends Item {
    private final Supplier<? extends EntityType<?>> dartEntityType;

    public DartItem(Supplier<? extends EntityType<?>> dartEntityType, Properties properties) {
        super(properties);
        this.dartEntityType = dartEntityType;
    }

    /**
     * Creates dart with setup using shooter entity.
     * @see DartItem#createDart(Level)
     * @param level Level of shooter entity.
     * @param shooter Entity shooting dart.
     * @return Dart entity created from dart entity type.
     */
    public AbstractDart createDart(Level level, LivingEntity shooter) {
        AbstractDart dart = this.createDart(level);
        if (dart != null) {
            dart.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
            dart.setOwner(shooter);
            return dart;
        } else {
            return null;
        }
    }

    /**
     * Creates dart from given dart entity type.
     * @param level The corresponding level.
     * @return Dart entity created from dart entity type.
     */
    public AbstractDart createDart(Level level) {
        Entity entity = this.getDartEntityType().get().create(level);
        if (entity instanceof AbstractDart dart) {
            return dart;
        } else {
            return null;
        }
    }

    /**
     * Based on {@link net.minecraft.world.item.ArrowItem#isInfinite(ItemStack, ItemStack, Player)}. Checks if the dart item is allowed to be infinite, if the dart shooter item has the infinity enchantment and the dart item is specifically DartItem and not a subclass.
     * @param dartShooter The dart shooter stack.
     * @return Whether the dart item ammo is allowed to be infinite.
     */
    public boolean isInfinite(ItemStack dartShooter) {
        int enchant = dartShooter.getEnchantmentLevel(Enchantments.INFINITY_ARROWS);
        return enchant > 0 && this.getClass() == DartItem.class;
    }

    /**
     * @return The entity type correlating with the projectile that can be created by this dart.
     */
    public Supplier<? extends EntityType<?>> getDartEntityType() {
        return this.dartEntityType;
    }
}
