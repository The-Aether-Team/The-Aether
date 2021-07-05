package com.gildedgames.aether.common.item.miscellaneous;

import com.gildedgames.aether.common.inventory.container.LoreBookContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class LoreBookItem extends Item
{
    public LoreBookItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isClientSide()) {
            NetworkHooks.openGui((ServerPlayerEntity) playerIn,
                    new SimpleNamedContainerProvider((id, inventory, player) -> LoreBookContainer.create(id, inventory),
                            new TranslationTextComponent("container.aether.book_of_lore")));
        }
        return super.use(worldIn, playerIn, handIn);
    }
}
