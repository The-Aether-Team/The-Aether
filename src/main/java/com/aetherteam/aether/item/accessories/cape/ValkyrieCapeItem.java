package com.aetherteam.aether.item.accessories.cape;

import com.aetherteam.aether.item.accessories.abilities.SlowFallAccessory;
import dev.emi.trinkets.api.SlotReference;
import io.github.fabricators_of_create.porting_lib.item.WalkOnSnowItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ValkyrieCapeItem extends CapeItem implements SlowFallAccessory, WalkOnSnowItem {
    public ValkyrieCapeItem(Properties properties) {
        super("valkyrie_cape", properties);
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
