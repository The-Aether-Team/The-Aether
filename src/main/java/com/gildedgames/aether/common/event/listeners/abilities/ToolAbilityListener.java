package com.gildedgames.aether.common.event.listeners.abilities;

import com.gildedgames.aether.common.registry.AetherLoot;
import com.gildedgames.aether.common.registry.AetherTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class ToolAbilityListener
{
    @SubscribeEvent
    public static void doGoldenOakStripping(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        ItemStack stack = event.getItemStack();
        if (stack.getItem() instanceof AxeItem) {
            BlockState blockState = world.getBlockState(event.getPos());
            if (blockState.getBlock().is(AetherTags.Blocks.GOLDEN_OAK_LOGS)) {
                if (world.getServer() != null) {
                    Vector3d vector = event.getHitVec().getLocation();
                    LootContext.Builder lootContext = new LootContext.Builder((ServerWorld) world)
                            .withParameter(LootParameters.BLOCK_STATE, blockState)
                            .withParameter(LootParameters.ORIGIN, vector)
                            .withParameter(LootParameters.TOOL, stack);
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
