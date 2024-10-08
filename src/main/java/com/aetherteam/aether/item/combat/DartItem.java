package com.aetherteam.aether.item.combat;

import com.aetherteam.aether.entity.projectile.dart.AbstractDart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class DartItem extends Item {
    private final Supplier<? extends EntityType<?>> dartEntityType;

    public DartItem(Supplier<? extends EntityType<?>> dartEntityType, Properties properties) {
        super(properties);
        this.dartEntityType = dartEntityType;
    }

    /**
     * Creates Dart with setup using shooter entity.
     *
     * @param level   {@link Level} of shooter entity.
     * @param shooter {@link LivingEntity} shooting dart.
     * @return {@link AbstractDart} entity created from dart entity type.
     * @see DartItem#createDart(Level)
     */
    @Nullable
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
     * Creates Dart from given dart entity type.
     *
     * @param level The corresponding {@link Level}.
     * @return {@link AbstractDart} entity created from dart entity type.
     */
    @Nullable
    public AbstractDart createDart(Level level) {
        Entity entity = this.getDartEntityType().get().create(level);
        if (entity instanceof AbstractDart dart) {
            return dart;
        } else {
            return null;
        }
    }

    /**
     * @return A {@link Supplier Supplier&lt;? extends EntityType&lt;?&gt;&gt;} that gives the entity type correlating with the projectile that can be created by this dart.
     */
    public Supplier<? extends EntityType<?>> getDartEntityType() {
        return this.dartEntityType;
    }
}
