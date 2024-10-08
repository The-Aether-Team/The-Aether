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

import javax.annotation.Nullable;

public class PassiveWhirlwind extends AbstractWhirlwind {
    public PassiveWhirlwind(EntityType<? extends PassiveWhirlwind> type, Level level) {
        super(type, level);
    }

    /**
     * Sets the Whirlwind's lifespan.<br><br>
     * Warning for "deprecation" is suppressed because this is fine to override.
     *
     * @param level      The {@link ServerLevelAccessor} where the entity is spawned.
     * @param difficulty The {@link DifficultyInstance} of the game.
     * @param reason     The {@link MobSpawnType} reason.
     * @param spawnData  The {@link SpawnGroupData}.
     * @param tag        The {@link CompoundTag} to apply to this entity.
     * @return The {@link SpawnGroupData} to return.
     */
    @Override
    @SuppressWarnings("deprecation")
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
        this.setLifeLeft(this.getRandom().nextInt(512) + 512);
        return spawnData;
    }

    /**
     * Allows creative mode players to color Passive Whirlwinds, as an Easter Egg.
     *
     * @param player The interacting {@link Player}.
     * @param hand   The {@link InteractionHand}.
     * @return The {@link InteractionResult}.
     */
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.getItem() instanceof DyeItem dyeItem && player.isCreative()) {
            this.setColorData(dyeItem.getDyeColor().getMapColor().col);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void spawnParticles() {
        for (int i = 0; i < 2; i++) {
            double d1 = this.getX() + this.getRandom().nextDouble() * 0.25;
            double d4 = getY() + getBbHeight() + 0.125;
            double d7 = this.getZ() + this.getRandom().nextDouble() * 0.25;
            float f = this.getRandom().nextFloat() * 360;
            this.level().addParticle(AetherParticleTypes.PASSIVE_WHIRLWIND.get(), d1, d4 - 0.25, d7, -Math.sin(0.0175F * f) * 0.75, 0.125, Math.cos(0.0175F * f) * 0.75);
        }
    }

    @Override
    public ResourceLocation getLootLocation() {
        return AetherLoot.WHIRLWIND_JUNK.location();
    }

    @Override
    public int getDefaultColor() {
        return 16777215;
    }
}
