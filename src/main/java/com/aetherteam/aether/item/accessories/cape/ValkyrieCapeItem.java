package com.aetherteam.aether.item.accessories.cape;

import com.aetherteam.aether.item.accessories.abilities.SlowFallAccessory;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ValkyrieCapeItem extends CapeItem implements SlowFallAccessory {
    public ValkyrieCapeItem(Properties properties) {
        super("valkyrie_cape", properties);
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        this.handleSlowFall(reference.entity());
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer) {
        return true;
    }
}
