package com.gildedgames.aether.common.item.materials;

import com.gildedgames.aether.core.capability.interfaces.IEternalDay;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class SkyrootStickItem extends Item
{
    public SkyrootStickItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return 100;
    }

    @Override
    public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
        if (p_77659_1_ instanceof ServerWorld) {
            ServerWorld world = (ServerWorld) p_77659_1_;
            MinecraftServer server = world.getServer();
            for (ServerWorld serverworld : server.getAllLevels()) {
                IEternalDay.get(serverworld).ifPresent(eternalDay -> eternalDay.setEternalDay(false));
            }
        }
        return super.use(p_77659_1_, p_77659_2_, p_77659_3_);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if (entity.level instanceof ServerWorld) {
            ServerWorld world = (ServerWorld) entity.level;
            MinecraftServer server = world.getServer();
            for (ServerWorld serverworld : server.getAllLevels()) {
                IEternalDay.get(serverworld).ifPresent(eternalDay -> eternalDay.setEternalDay(true));
                IEternalDay.get(serverworld).ifPresent(eternalDay -> eternalDay.setAetherTime(18000));
                IEternalDay.get(serverworld).ifPresent(eternalDay -> eternalDay.setCheckTime(true));
            }
        }
        return super.onEntitySwing(stack, entity);
    }
}
