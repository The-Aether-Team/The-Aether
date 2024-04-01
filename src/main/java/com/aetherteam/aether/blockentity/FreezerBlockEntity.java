package com.aetherteam.aether.blockentity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.data.resources.registries.AetherDataMaps;
import com.aetherteam.aether.inventory.menu.FreezerMenu;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class FreezerBlockEntity extends AbstractAetherFurnaceBlockEntity {

    public FreezerBlockEntity(BlockPos pos, BlockState state) {
        super(AetherBlockEntityTypes.FREEZER.get(), pos, state, AetherRecipeTypes.FREEZING.get());
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("menu." + Aether.MODID + ".freezer");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory playerInventory) {
        return new FreezerMenu(id, playerInventory, this, this.dataAccess);
    }

    @Override
    protected int getBurnDuration(ItemStack fuelStack) {
        if (!fuelStack.isEmpty()) {
            var datamap = fuelStack.getItem().builtInRegistryHolder().getData(AetherDataMaps.FREEZER_FUEL);
            if (datamap != null) {
                return datamap.burnTime();
            }
        }
        return 0;
    }
}
