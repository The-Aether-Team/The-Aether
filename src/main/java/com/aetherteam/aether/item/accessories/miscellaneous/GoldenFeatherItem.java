package com.aetherteam.aether.item.accessories.miscellaneous;

import com.aetherteam.aether.item.accessories.AccessoryItem;
import com.aetherteam.aether.item.accessories.abilities.SlowFallAccessory;
import io.github.fabricators_of_create.porting_lib.item.WalkOnSnowItem;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class GoldenFeatherItem extends AccessoryItem implements SlowFallAccessory, WalkOnSnowItem {
    public GoldenFeatherItem(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(ItemStack stack, SlotReference slotContext) {
        this.handleSlowFall(slotContext.entity());
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer) {
        return true;
    }
}
