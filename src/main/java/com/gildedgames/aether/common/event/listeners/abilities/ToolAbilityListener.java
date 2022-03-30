package com.gildedgames.aether.common.event.listeners.abilities;

import com.gildedgames.aether.common.item.tools.abilities.GravititeTool;
import com.gildedgames.aether.common.item.tools.abilities.HolystoneTool;
import com.gildedgames.aether.common.item.tools.abilities.ZaniteTool;
import com.gildedgames.aether.common.registry.AetherLoot;
import com.gildedgames.aether.common.registry.AetherTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class ToolAbilityListener {
    @SubscribeEvent
    public static void doGoldenOakStripping(PlayerInteractEvent.RightClickBlock event) { //TODO: Potentially  switch to use popResources
        Level level = event.getWorld();
        ItemStack stack = event.getItemStack();
        if (stack.getItem() instanceof AxeItem) {
            BlockState blockState = level.getBlockState(event.getPos());
            if (blockState.is(AetherTags.Blocks.GOLDEN_OAK_LOGS)) {
                if (level.getServer() != null) {
                    Vec3 vector = event.getHitVec().getLocation();
                    LootContext.Builder lootContext = new LootContext.Builder((ServerLevel) level)
                            .withParameter(LootContextParams.BLOCK_STATE, blockState)
                            .withParameter(LootContextParams.ORIGIN, vector)
                            .withParameter(LootContextParams.TOOL, stack);
                    LootTable loottable = level.getServer().getLootTables().get(AetherLoot.STRIP_GOLDEN_OAK);
                    List<ItemStack> list = loottable.getRandomItems(lootContext.create(AetherLoot.STRIPPING));

                    for (ItemStack itemstack : list) {
                        ItemEntity itementity = new ItemEntity(level, vector.x(), vector.y(), vector.z(), itemstack);
                        level.addFreshEntity(itementity);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void doHolystoneAbility(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Level level = player.getLevel();
        BlockPos blockPos = event.getPos();
        ItemStack itemStack = player.getMainHandItem();
        HolystoneTool.dropAmbrosium(player, level, blockPos, itemStack);
    }

    @SubscribeEvent
    public static void doZaniteAbility(PlayerEvent.BreakSpeed event) {
        Player player = event.getPlayer();
        ItemStack itemStack = player.getMainHandItem();
        event.setNewSpeed(ZaniteTool.increaseSpeed(itemStack, event.getOriginalSpeed()));
    }

    @SubscribeEvent
    public static void doGravititeAbility(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getWorld();
        BlockPos blockPos = event.getPos();
        ItemStack itemStack = event.getItemStack();
        BlockState blockState = level.getBlockState(blockPos);
        Player player = event.getPlayer();
        InteractionHand interactionHand = event.getHand();
        if (GravititeTool.floatBlock(level, blockPos, itemStack, blockState, player, interactionHand)) {
            event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
            event.setCanceled(true);
        }
    }
}
