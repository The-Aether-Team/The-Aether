package com.gildedgames.aether.common.event.listeners.abilities;

import com.gildedgames.aether.common.registry.AetherLoot;
import com.gildedgames.aether.common.registry.AetherTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class ToolAbilityListener
{
    @SubscribeEvent
    public static void doGoldenOakStripping(PlayerInteractEvent.RightClickBlock event) {
        Level world = event.getWorld();
        ItemStack stack = event.getItemStack();
        if (stack.getItem() instanceof AxeItem) {
            BlockState blockState = world.getBlockState(event.getPos());
            if (blockState.is(AetherTags.Blocks.GOLDEN_OAK_LOGS)) {
                if (world.getServer() != null) {
                    Vec3 vector = event.getHitVec().getLocation();
                    LootContext.Builder lootContext = new LootContext.Builder((ServerLevel) world)
                            .withParameter(LootContextParams.BLOCK_STATE, blockState)
                            .withParameter(LootContextParams.ORIGIN, vector)
                            .withParameter(LootContextParams.TOOL, stack);
                    LootTable loottable = world.getServer().getLootTables().get(AetherLoot.STRIP_GOLDEN_OAK);
                    List<ItemStack> list = loottable.getRandomItems(lootContext.create(AetherLoot.STRIPPING));

                    for(ItemStack itemstack : list) {
                        ItemEntity itementity = new ItemEntity(world, vector.x(), vector.y(), vector.z(), itemstack);
                        world.addFreshEntity(itementity);
                    }
                }
            }
        }
    }
}
