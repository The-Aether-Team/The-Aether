package com.aether.item;

import com.aether.entity.projectile.LightningKnifeEntity;
import com.aether.util.AetherSoundEvents;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class LightningKnifeItem extends Item {

	public LightningKnifeItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand hand) {
		ItemStack heldItem = playerIn.getHeldItem(hand);
		
		if (!playerIn.isCreative() && EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, heldItem) == 0) {
			heldItem.shrink(1);
		}
		
		worldIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), AetherSoundEvents.ENTITY_PROJECTILE_SHOOT, SoundCategory.PLAYERS, 1.0f, 1.0f / (random.nextFloat() * 0.4f + 0.8f));
		
		if (!worldIn.isRemote) {
			LightningKnifeEntity lightningKnife = new LightningKnifeEntity(playerIn, worldIn);
			lightningKnife.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0f, 0.5f, 1.0f);
			worldIn.addEntity(lightningKnife);
		}
		
		return ActionResult.resultSuccess(heldItem);
	}

}
