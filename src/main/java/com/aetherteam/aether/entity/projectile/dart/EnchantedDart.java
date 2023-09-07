package com.aetherteam.aether.entity.projectile.dart;

import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class EnchantedDart extends AbstractDart {
    public EnchantedDart(EntityType<? extends EnchantedDart> type, Level level) {
        super(type, level, AetherItems.ENCHANTED_DART);
        this.setBaseDamage(6.0);
    }

    public EnchantedDart(Level level) {
        super(AetherEntityTypes.ENCHANTED_DART.get(), level, AetherItems.ENCHANTED_DART);
        this.setBaseDamage(6.0);
    }
}
