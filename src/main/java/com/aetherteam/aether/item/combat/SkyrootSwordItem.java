package com.aetherteam.aether.item.combat;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.item.combat.abilities.weapon.SkyrootWeapon;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.fml.common.Mod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = Aether.MODID)
public class SkyrootSwordItem extends SwordItem implements SkyrootWeapon {
    public SkyrootSwordItem() {
        super(AetherItemTiers.SKYROOT, 3, -2.4F, new Item.Properties());
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return 200;
    }
}
