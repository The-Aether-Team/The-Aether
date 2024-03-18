package com.aetherteam.aether.item.accessories.miscellaneous;

import com.aetherteam.aether.item.accessories.AccessoryItem;
import com.aetherteam.aether.item.accessories.abilities.SlowFallAccessory;
import dev.emi.trinkets.api.SlotReference;
import io.github.fabricators_of_create.porting_lib.item.WalkOnSnowItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class GoldenFeatherItem extends AccessoryItem implements SlowFallAccessory, WalkOnSnowItem {
    public GoldenFeatherItem(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(ItemStack stack, SlotReference slotContext, LivingEntity entity) {
        this.handleSlowFall(entity);
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer) {
        return true;
    }
}
