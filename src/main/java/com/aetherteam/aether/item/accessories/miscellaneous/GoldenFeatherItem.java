package com.aetherteam.aether.item.accessories.miscellaneous;

import com.aetherteam.aether.inventory.container.AccessoryContainer;
import com.aetherteam.aether.item.accessories.AccessoryItem;
import com.aetherteam.aether.item.accessories.abilities.SlowFallAccessory;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GoldenFeatherItem extends AccessoryItem implements SlowFallAccessory {
    public GoldenFeatherItem(Properties properties) {
        super(properties, AccessoryContainer.SlotType.ACCESSORY);
    }

    @Override
    public void tick(ItemStack stack, Level level, LivingEntity entity, InteractionHand hand) {
        this.handleSlowFall(entity);
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer) {
        return true;
    }
}
