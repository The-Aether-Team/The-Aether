package com.aetherteam.aether.item.combat.loot;

import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.aether.item.combat.AetherItemTiers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.neoforged.neoforge.event.entity.EntityStruckByLightningEvent;

public class LightningSwordItem extends SwordItem {
    public LightningSwordItem() {
        super(AetherItemTiers.LIGHTNING, new Item.Properties().rarity(AetherItems.AETHER_LOOT).attributes(SwordItem.createAttributes(AetherItemTiers.LIGHTNING, 3.0F, -2.4F)));
    }

    /**
     * Summon lightning at the position of the target if the attacker attacked with full strength as determined by {@link EquipmentUtil#isFullStrength(LivingEntity)}.<br><br>
     * {@link com.aetherteam.aether.event.listeners.abilities.WeaponAbilityListener#onLightningStrike(EntityStruckByLightningEvent)} prevents the attacker from being injured by the lightning.
     *
     * @param stack    The {@link ItemStack} used to hurt the target
     * @param target   The hurt {@link LivingEntity}.
     * @param attacker The attacking {@link LivingEntity}.
     * @return Whether the enemy was hurt or not, as a {@link Boolean}.
     */
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (EquipmentUtil.isFullStrength(attacker)) {
            LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(attacker.level());
            if (lightningBolt != null) {
                lightningBolt.getData(AetherDataAttachments.LIGHTNING_TRACKER).setOwner(attacker);
                lightningBolt.setPos(target.getX(), target.getY(), target.getZ());
                attacker.level().addFreshEntity(lightningBolt);
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
