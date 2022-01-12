package com.gildedgames.aether.common.entity.monster;

import com.gildedgames.aether.client.registry.AetherParticleTypes;
import com.gildedgames.aether.common.registry.AetherLoot;
import com.gildedgames.aether.core.registry.AetherPlayerRankings;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class PassiveWhirlwind extends Whirlwind {
    public PassiveWhirlwind(EntityType<? extends Whirlwind> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        this.lifeLeft = this.random.nextInt(512) + 512;
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    /**
     * This method is called in aiStep to handle the item drop behavior of the whirlwind.
     * This method should only be called on the logical server!
     */
    protected void handleDrops() {
        if(this.random.nextInt(4) == 0) {
            LootContext.Builder builder = new LootContext.Builder((ServerLevel) this.level)
                    .withParameter(LootContextParams.ORIGIN, this.position())
                    .withParameter(LootContextParams.THIS_ENTITY, this);
            LootTable lootTable = this.level.getServer().getLootTables().get(AetherLoot.WHIRLWIND_JUNK);
            List<ItemStack> list = lootTable.getRandomItems(builder.create(LootContextParamSets.SELECTOR));
            for (ItemStack itemstack : list) {
                this.spawnAtLocation(itemstack, 1);
            }
        }
    }

    /**
     * This method is called when a player right-clicks the entity.
     */
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);
        if (heldItem.getItem() instanceof DyeItem && !AetherPlayerRankings.getRanksOf(player.getUUID()).isEmpty()) {
            this.setColorData(((DyeItem) heldItem.getItem()).getDyeColor().getMaterialColor().col);

            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void updateParticles() {
        for(int k = 0; k < 2; k++) {
            double d1 = this.getX() + this.random.nextDouble() * 0.25;
            double d4 = getY() + getBbHeight() + 0.125;
            double d7 = this.getZ() + this.random.nextDouble() * 0.25;
            float f = this.random.nextFloat() * 360;
            this.level.addParticle(AetherParticleTypes.PASSIVE_WHIRLWIND.get(), d1, d4 - 0.25, d7, -Math.sin(0.01745329F * f) * 0.75, 0.125, Math.cos(0.01745329F * f) * 0.75);
        }
    }

    @Override
    public int getDefaultColor() {
        return 0xFFFFFF;
    }
}
