package com.gildedgames.aether.item.combat.loot;

import com.gildedgames.aether.client.AetherSoundEvents;
import com.gildedgames.aether.entity.projectile.weapon.HammerProjectile;
import com.gildedgames.aether.item.AetherItemGroups;
import com.gildedgames.aether.item.combat.AetherItemTiers;
import com.gildedgames.aether.item.AetherItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class HammerOfNotchItem extends SwordItem {
    public HammerOfNotchItem() {
        super(AetherItemTiers.HAMMER_OF_NOTCH, 3, -2.4F, new Item.Properties().rarity(AetherItems.AETHER_LOOT).tab(AetherItemGroups.AETHER_WEAPONS));
    }

    /**
     * Spawns a hammer projectile when right-clicking, setting a cooldown of 200 ticks for the item and taking off 1 durability.
     * @param level The level of the user.
     * @param player The entity using this item.
     * @param hand The hand in which the item is being used.
     * @return Success (the item is swung).
     */
    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, @Nonnull InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);
        if (!level.isClientSide()) {
            if (!player.getAbilities().instabuild) {
                player.getCooldowns().addCooldown(this, 200);
                heldStack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
            }
            HammerProjectile hammerProjectile = new HammerProjectile(player, level);
            hammerProjectile.shoot(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            if (heldStack.getHoverName().getString().equalsIgnoreCase("hammer of jeb")) {
                hammerProjectile.setIsJeb(true); // Handles Hammer of Jeb texture for the projectile.
            }
            level.addFreshEntity(hammerProjectile);
        }
        level.playLocalSound(player.getX(), player.getY(), player.getZ(), AetherSoundEvents.ITEM_HAMMER_OF_NOTCH_SHOOT.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (player.getRandom().nextFloat() * 0.4F + 0.8F), false);
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.success(heldStack);
    }
}
