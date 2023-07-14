package com.aetherteam.aether.entity.monster;

import com.aetherteam.aether.client.particle.AetherParticleTypes;
import com.aetherteam.aether.loot.AetherLoot;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PassiveWhirlwind extends AbstractWhirlwind {
    public PassiveWhirlwind(EntityType<? extends PassiveWhirlwind> type, Level level) {
        super(type, level);
    }

    @Override
    public SpawnGroupData finalizeSpawn(@Nonnull ServerLevelAccessor level, @Nonnull DifficultyInstance difficulty, @Nonnull MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag tag) {
        this.lifeLeft = this.random.nextInt(512) + 512;
        return super.finalizeSpawn(level, difficulty, reason, spawnData, tag);
    }

    @Nonnull
    @Override
    public InteractionResult mobInteract(Player player, @Nonnull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.getItem() instanceof DyeItem dyeItem && player.isCreative()) {
            this.setColorData(dyeItem.getDyeColor().getMaterialColor().col);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void updateParticles() {
        for (int i = 0; i < 2; i++) {
            double d1 = this.getX() + this.random.nextDouble() * 0.25;
            double d4 = getY() + getBbHeight() + 0.125;
            double d7 = this.getZ() + this.random.nextDouble() * 0.25;
            float f = this.random.nextFloat() * 360;
            this.level.addParticle(AetherParticleTypes.PASSIVE_WHIRLWIND.get(), d1, d4 - 0.25, d7, -Math.sin(0.01745329F * f) * 0.75, 0.125, Math.cos(0.01745329F * f) * 0.75);
        }
    }

    @Override
    public ResourceLocation getLootLocation() {
        return AetherLoot.WHIRLWIND_JUNK;
    }

    @Override
    public int getDefaultColor() {
        return 16777215;
    }
}
