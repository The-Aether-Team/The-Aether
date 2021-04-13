package com.gildedgames.aether.core.capability.capabilities.player;

import com.gildedgames.aether.common.entity.block.ParachuteEntity;
import com.gildedgames.aether.core.api.registers.ParachuteType;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;

import com.gildedgames.aether.core.registry.AetherParachuteTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class AetherPlayer implements IAetherPlayer
{
	private final PlayerEntity player;

	private ParachuteEntity parachute = null;

	public boolean isInAetherPortal = false;
	public int aetherPortalTimer = 0;
	public float prevPortalAnimTime, portalAnimTime = 0.0F;

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
		if (this.getParachute() != null) {
			nbt.putString("Parachute", this.getParachute().getParachuteType().getRegistryName().toString());
		}

		//Set<AetherRank> ranks = AetherRankings.getRanksOf(this.player.getUniqueID());
//		if (ranks.stream().anyMatch(AetherRank::hasHalo)) {
//			nbt.putBoolean("Halo", this.shouldRenderHalo);
//		}
		//this.accessories.writeToNBT(nbt);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		if (nbt.contains("Parachute")) {
			this.setParachute(AetherParachuteTypes.PARACHUTES.get(new ResourceLocation(nbt.getString("Parachute"))));
		}
	}
	
	@Override
	public void copyFrom(IAetherPlayer other) {
		CompoundNBT nbt = new CompoundNBT();
		//this.shouldRenderGlow = other.shouldRenderGlow();
	}

	// TODO
	@Override
	public void onUpdate() {
		handleAetherPortal();
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

	@Override
	public void setParachute(ParachuteType parachuteType) {
		if (this.getParachute() != null) {
			this.getParachute().kill();
			this.parachute = null;
		}

		if (parachuteType != null) {
			ParachuteEntity parachute = (ParachuteEntity) parachuteType.getParachuteType().create(this.getPlayer().level);
			this.parachute = parachute;

			if (parachute != null) {
				parachute.setPlayer(this.getPlayer().getId());
				parachute.setParachuteType(parachuteType.getRegistryName().toString());
				parachute.absMoveTo(this.getPlayer().getX(), this.getPlayer().getY() - 1.0, this.getPlayer().getZ(), 0.0F, 0.0F);
				this.getPlayer().level.addFreshEntity(parachute);
				parachute.spawnExplosionParticle();
			}
		} else {
			this.parachute = null;
		}
	}

	@Override
	public ParachuteEntity getParachute() {
		return this.parachute;
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
