package com.gildedgames.aether.item.combat;

import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.item.AetherItemGroups;
import com.gildedgames.aether.item.combat.abilities.weapon.SkyrootWeapon;
import com.gildedgames.aether.util.EquipmentUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.Collection;

@Mod.EventBusSubscriber
public class SkyrootSwordItem extends SwordItem implements SkyrootWeapon {
    public SkyrootSwordItem() {
        super(AetherItemTiers.SKYROOT, 3, -2.4F, new Item.Properties().tab(AetherItemGroups.AETHER_WEAPONS));
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return 200;
    }

    /**
     * @see SkyrootWeapon#entityKilledBySkyroot(LivingEntity)
     */
    @SubscribeEvent
    public static void trackEntityDeath(LivingDeathEvent event) {
        LivingEntity livingEntity = event.getEntity();
        DamageSource damageSource = event.getSource();
        SkyrootWeapon skyrootWeapon = canPerformAbility(livingEntity, damageSource);
        if (skyrootWeapon != null) {
            skyrootWeapon.entityKilledBySkyroot(livingEntity);
        }
    }

    /**
     * @see SkyrootWeapon#entityDropsBySkyroot(LivingEntity, Collection)
     */
    @SubscribeEvent
    public static void doSkyrootDoubleDrops(LivingDropsEvent event) {
        LivingEntity livingEntity = event.getEntity();
        DamageSource damageSource = event.getSource();
        Collection<ItemEntity> drops = event.getDrops();
        SkyrootWeapon skyrootWeapon = canPerformAbility(livingEntity, damageSource);
        if (skyrootWeapon != null) {
            skyrootWeapon.entityDropsBySkyroot(livingEntity, drops);
        }
    }

    /**
     * Basic checks to perform the ability if the source is living, the target can have their drops doubled, the item is a Skyroot weapon, and if the attacker attacked with full strength as determined by {@link EquipmentUtil#isFullStrength(LivingEntity)}.
     * @param target The killed {@link LivingEntity}.
     * @param source The attacking {@link DamageSource}.
     */
    private static SkyrootWeapon canPerformAbility(LivingEntity target, DamageSource source) {
        if (source.getDirectEntity() instanceof LivingEntity attacker) {
            if (EquipmentUtil.isFullStrength(attacker)) {
                if (!target.getType().is(AetherTags.Entities.NO_SKYROOT_DOUBLE_DROPS)) {
                    if (attacker.getMainHandItem().getItem() instanceof SkyrootWeapon skyrootWeapon) {
                        return skyrootWeapon;
                    }
                }
            }
        }
        return null;
    }
}
