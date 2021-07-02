package com.gildedgames.aether.core.capability.capabilities.player;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.entity.miscellaneous.ParachuteEntity;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;

import com.gildedgames.aether.core.registry.AetherParachuteTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AetherPlayer implements IAetherPlayer
{
	private final PlayerEntity player;

	public boolean isInAetherPortal = false;
	public int aetherPortalTimer = 0;
	public float prevPortalAnimTime, portalAnimTime = 0.0F;

	private boolean canGetPortal = true;
	private boolean isJumping;
	
	public AetherPlayer(PlayerEntity player) {
		this.player = player;
	}
	
	@Override
	public PlayerEntity getPlayer() {
		return this.player;
	}
	
	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT nbt = new CompoundNBT();
		nbt.putBoolean("CanGetPortal", this.canGetPortal());

		//Set<AetherRank> ranks = AetherRankings.getRanksOf(this.player.getUniqueID());
//		if (ranks.stream().anyMatch(AetherRank::hasHalo)) {
//			nbt.putBoolean("Halo", this.shouldRenderHalo);
//		}
		//this.accessories.writeToNBT(nbt);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		if (nbt.contains("CanGetPortal")) {
			this.setCanGetPortal(nbt.getBoolean("CanGetPortal"));
		}
	}
	
	@Override
	public void copyFrom(IAetherPlayer other) {
		this.setCanGetPortal(other.canGetPortal());
	}

	@Override
	public void onUpdate() {
		handleAetherPortal();
		activateParachute();
	}

	/**
	 * Increments or decrements the Aether portal timer depending on whether or not the player is inside an Aether portal.
	 * On the client, this will also help to set the portal overlay.
	 */
	private void handleAetherPortal() {
		if (player.level.isClientSide) {
			this.prevPortalAnimTime = this.portalAnimTime;
			Minecraft mc = Minecraft.getInstance();
			if (this.isInAetherPortal) {
				if (mc.screen != null && !mc.screen.isPauseScreen()) {
					if (mc.screen instanceof ContainerScreen) {
						player.closeContainer();
					}

					mc.setScreen(null);
				}

				if (this.portalAnimTime == 0.0F) {
					playPortalSound(mc);
				}
			}
		}

		if (this.isInAetherPortal) {
			++this.aetherPortalTimer;
			if(player.level.isClientSide) {
				this.portalAnimTime += 0.0125F;
				if(this.portalAnimTime > 1.0F) {
					this.portalAnimTime = 1.0F;
				}
			}
			this.isInAetherPortal = false;
		}
		else{
			if (player.level.isClientSide) {
				if (this.portalAnimTime > 0.0F)
				{
					this.portalAnimTime -= 0.05F;
				}

				if (this.portalAnimTime < 0.0F)
				{
					this.portalAnimTime = 0.0F;
				}
			}
			if (this.aetherPortalTimer > 0) {
				this.aetherPortalTimer -= 4;
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	private void playPortalSound(Minecraft mc) {
		mc.getSoundManager().play(SimpleSound.forLocalAmbience(SoundEvents.PORTAL_TRIGGER, this.getPlayer().random.nextFloat() * 0.4F + 0.8F, 0.25F));
	}

	private void activateParachute() {
		PlayerEntity player = this.getPlayer();
		PlayerInventory inventory = this.getPlayer().inventory;
		World world = player.level;
		if (!player.isCreative() && !player.isShiftKeyDown()) {
			if (player.getDeltaMovement().y() < -1.5D) {
				if (inventory.contains(new ItemStack(AetherItems.COLD_PARACHUTE.get()))) {
					for (ItemStack stack : inventory.items) {
						Item item = stack.getItem();
						if (item == AetherItems.COLD_PARACHUTE.get()) {
							ParachuteEntity parachuteEntity = new ParachuteEntity(world, player.getX(), player.getY() - 1.0D, player.getZ());
							parachuteEntity.setParachuteType(AetherParachuteTypes.COLD_PARACHUTE);
							if (!world.isClientSide) {
								world.addFreshEntity(parachuteEntity);
								player.startRiding(parachuteEntity);
								stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(Hand.MAIN_HAND));
							}
							parachuteEntity.spawnExplosionParticle();
							break;
						}
					}
				} else if (inventory.contains(new ItemStack(AetherItems.GOLDEN_PARACHUTE.get()))) {
					for (ItemStack stack : inventory.items) {
						Item item = stack.getItem();
						if (item == AetherItems.GOLDEN_PARACHUTE.get()) {
							ParachuteEntity parachuteEntity = new ParachuteEntity(world, player.getX(), player.getY() - 1.0D, player.getZ());
							parachuteEntity.setParachuteType(AetherParachuteTypes.GOLDEN_PARACHUTE);
							if (!world.isClientSide) {
								world.addFreshEntity(parachuteEntity);
								player.startRiding(parachuteEntity);
								stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(Hand.MAIN_HAND));
							}
							parachuteEntity.spawnExplosionParticle();
							break;
						}
					}
				}
			}
		}
	}

	@Override
	public void givePortalItem() {
		if (this.canGetPortal()) {
			this.getPlayer().addItem(new ItemStack(AetherItems.AETHER_PORTAL_FRAME.get()));
			this.setCanGetPortal(false);
		}
	}

	@Override
	public void setCanGetPortal(boolean canGetPortal) {
		this.canGetPortal = canGetPortal;
	}

	@Override
	public boolean canGetPortal() {
		return this.canGetPortal;
	}

	@Override
	public void setJumping(boolean isJumping) {
		this.isJumping = isJumping;
	}

	@Override
	public boolean isJumping() {
		return this.isJumping;
	}

	@Override
	public void setInPortal(boolean inPortal) {
		this.isInAetherPortal = inPortal;
	}

	@Override
	public boolean isInPortal() {
		return this.isInAetherPortal;
	}

	@Override
	public void addPortalTime(int time) {
		this.aetherPortalTimer += time;
	}

	@Override
	public void setPortalTimer(int timer) {
		this.aetherPortalTimer = timer;
	}

	@Override
	public int getPortalTimer() {
		return this.aetherPortalTimer;
	}

	@Override
	public float getPortalAnimTime() {
		return this.portalAnimTime;
	}

	@Override
	public float getPrevPortalAnimTime() {
		return this.prevPortalAnimTime;
	}
}
