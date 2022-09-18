package com.gildedgames.aether.item.combat;

import com.gildedgames.aether.item.AetherItemGroups;
import com.gildedgames.aether.item.combat.abilities.weapon.SkyrootWeapon;
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

    @SubscribeEvent
    public static void trackEntityDeath(LivingDeathEvent event) {
        LivingEntity livingEntity = event.getEntity();
        DamageSource damageSource = event.getSource();
        SkyrootWeapon.entityKilledBySkyroot(livingEntity, damageSource);
    }

    @SubscribeEvent
    public static void doSkyrootDoubleDrops(LivingDropsEvent event) {
        LivingEntity livingEntity = event.getEntity();
        DamageSource damageSource = event.getSource();
        Collection<ItemEntity> drops = event.getDrops();
        SkyrootWeapon.entityDropsBySkyroot(livingEntity, damageSource, drops);
    }
}
