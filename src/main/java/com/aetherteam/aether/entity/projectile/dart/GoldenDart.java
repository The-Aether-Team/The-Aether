package com.aetherteam.aether.entity.projectile.dart;

import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class GoldenDart extends AbstractDart {
    public GoldenDart(EntityType<? extends GoldenDart> type, Level level) {
        super(type, level, AetherItems.GOLDEN_DART);
        this.setBaseDamage(1.0);
    }

    public GoldenDart(Level level) {
        super(AetherEntityTypes.GOLDEN_DART.get(), level, AetherItems.GOLDEN_DART);
        this.setBaseDamage(1.0);
    }
}
