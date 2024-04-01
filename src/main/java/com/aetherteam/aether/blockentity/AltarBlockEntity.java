package com.aetherteam.aether.blockentity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.api.AetherDataMaps;
import com.aetherteam.aether.inventory.menu.AltarMenu;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class AltarBlockEntity extends AbstractAetherFurnaceBlockEntity {

    public AltarBlockEntity(BlockPos pos, BlockState state) {
        super(AetherBlockEntityTypes.ALTAR.get(), pos, state, AetherRecipeTypes.ENCHANTING.get());
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("menu." + Aether.MODID + ".altar");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory playerInventory) {
        return new AltarMenu(id, playerInventory, this, this.dataAccess);
    }

    @Override
    protected int getBurnDuration(ItemStack fuelStack) {
        if (!fuelStack.isEmpty()) {
            var datamap = fuelStack.getItem().builtInRegistryHolder().getData(AetherDataMaps.ALTAR_FUEL);
            if (datamap != null) {
                return datamap.burnTime();
            }
        }
        return 0;
    }
}