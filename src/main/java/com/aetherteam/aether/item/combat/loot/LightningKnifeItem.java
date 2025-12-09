package com.aetherteam.aether.item.combat.loot;

import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.entity.projectile.weapon.ThrownLightningKnife;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class LightningKnifeItem extends Item implements ProjectileItem {
    public LightningKnifeItem() {
        super(new Item.Properties().rarity(AetherItems.AETHER_LOOT).stacksTo(16));
    }

    /**
     * Spawns a thrown Lightning Knife projectile when right-clicking, consuming the item.<br><br>
     * {@link com.aetherteam.aether.event.listeners.abilities.WeaponAbilityListener#onLightningStrike(Entity, LightningBolt, MutableBoolean)} prevents the attacker from being injured by the lightning.
     *
     * @param level  The {@link Level} of the user.
     * @param player The {@link Player} using this item.
     * @param hand   The {@link InteractionHand} in which the item is being used.
     * @return Success (the item is swung). This is an {@link InteractionResultHolder InteractionResultHolder&lt;ItemStack&gt;}.
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);
        if (!level.isClientSide()) {
            if (!player.getAbilities().instabuild && heldStack.nitrogen_fabric$getEnchantmentLevel(level.registryAccess().nitrogen_fabric$holderOrThrow(Enchantments.INFINITY)) == 0) { // Note: Lightning knives can't be enchanted with Infinity in survival, but we still implement the behavior.
                heldStack.shrink(1);
            }
            ThrownLightningKnife lightningKnife = new ThrownLightningKnife(player, level);
            lightningKnife.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.8F, 1.0F);
            level.addFreshEntity(lightningKnife);
        }
        level.playLocalSound(player.getX(), player.getY(), player.getZ(), AetherSoundEvents.ITEM_LIGHTNING_KNIFE_SHOOT.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 0.8F), false);
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.success(heldStack);
    }

    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        ThrownLightningKnife lightningKnife = new ThrownLightningKnife(level);
        lightningKnife.setPos(pos.x(), pos.y(), pos.z());
        return lightningKnife;
    }

    @Override
    public DispenseConfig createDispenseConfig() {
        return DispenseConfig.builder().uncertainty(1.5F).build();
    }
}
