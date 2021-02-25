package com.gildedgames.aether.event.handlers;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.registry.AetherItems;
import com.gildedgames.aether.registry.AetherLoot;
import com.gildedgames.aether.registry.AetherTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.*;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class AetherAbilityHandler
{
    @SubscribeEvent
    public static void doSkyrootDoubleDrops(LivingDropsEvent event) {
        if (!(event.getSource() instanceof EntityDamageSource)) {
            return;
        }

        LivingEntity entity = event.getEntityLiving();
        EntityDamageSource source = (EntityDamageSource) event.getSource();

        if (!(source.getImmediateSource() instanceof PlayerEntity)) {
            return;
        }

        PlayerEntity player = (PlayerEntity) source.getImmediateSource();
        ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
        Item item = stack.getItem();

        if (item == AetherItems.SKYROOT_SWORD.get() && !entity.getType().isContained(AetherTags.Entities.NO_SKYROOT_DOUBLE_DROPS)) {
            ArrayList<ItemEntity> newDrops = new ArrayList<>(event.getDrops().size());
            for (ItemEntity drop : event.getDrops()) {
                ItemStack droppedStack = drop.getItem();
                if (!droppedStack.getItem().isIn(AetherTags.Items.NO_SKYROOT_DOUBLE_DROPS)) {
                    newDrops.add(new ItemEntity(entity.world, drop.getPosX(), drop.getPosY(), drop.getPosZ(), droppedStack.copy()));
                }
            }
            event.getDrops().addAll(newDrops);
        }
    }

    @SubscribeEvent
    public static void doGoldenOakStripping(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        ItemStack stack = event.getItemStack();
        if (stack.getItem() instanceof AxeItem) {
            BlockState blockState = world.getBlockState(event.getPos());
            if (blockState.getBlock().isIn(AetherTags.Blocks.GOLDEN_OAK_LOGS)) {
                if (world.getServer() != null) {
                    Vector3d vector = event.getHitVec().getHitVec();
                    LootContext.Builder lootContext = new LootContext.Builder((ServerWorld) world)
                            .withParameter(LootParameters.BLOCK_STATE, blockState)
                            .withParameter(LootParameters.field_237457_g_, vector)
                            .withParameter(LootParameters.TOOL, stack);
                    LootTable loottable = world.getServer().getLootTableManager().getLootTableFromLocation(AetherLoot.STRIP_GOLDEN_OAK);
                    List<ItemStack> list = loottable.generate(lootContext.build(AetherLoot.STRIPPING));

                    for(ItemStack itemstack : list) {
                        ItemEntity itementity = new ItemEntity(world, vector.getX(), vector.getY(), vector.getZ(), itemstack);
                        world.addEntity(itementity);
                    }
                }
            }
        }
    }
}
