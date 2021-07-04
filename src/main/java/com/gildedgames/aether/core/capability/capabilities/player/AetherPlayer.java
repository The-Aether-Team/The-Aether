package com.gildedgames.aether.core.capability.capabilities.player;

import com.gildedgames.aether.common.entity.miscellaneous.ParachuteEntity;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;

import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.client.SetLifeShardPacket;
import com.gildedgames.aether.core.network.packet.client.SetProjectileImpactedPacket;
import com.gildedgames.aether.core.network.packet.client.SetRemedyPacket;
import com.gildedgames.aether.core.registry.AetherParachuteTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.UUID;

public class AetherPlayer implements IAetherPlayer
{
	private final PlayerEntity player;

	private static final UUID LIFE_SHARD_HEALTH_ID = UUID.fromString("E11710C8-4247-4CB6-B3B5-729CB34CFC1A");

	private boolean canGetPortal = true;

	public boolean isInAetherPortal = false;
	public int aetherPortalTimer = 0;
	public float prevPortalAnimTime, portalAnimTime = 0.0F;

	private boolean isJumping;

	private int remedyMaximum = 0;
	private int remedyTimer = 0;

	private int projectileImpactedMaximum = 0;
	private int projectileImpactedTimer = 0;

	private int lifeShardCount = 0;
	
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
		nbt.putInt("RemedyMaximum", this.getRemedyMaximum());
		nbt.putInt("RemedyTimer", this.getRemedyTimer());
		nbt.putInt("ProjectileImpactedMaximum", this.getProjectileImpactedMaximum());
		nbt.putInt("ProjectileImpactedTimer", this.getProjectileImpactedTimer());
		nbt.putInt("LifeShardCount", this.getLifeShardCount());

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
		if (nbt.contains("RemedyMaximum")) {
			this.setRemedyMaximum(nbt.getInt("RemedyMaximum"));
		}
		if (nbt.contains("RemedyTimer")) {
			this.setRemedyTimer(nbt.getInt("RemedyTimer"));
		}
		if (nbt.contains("ProjectileImpactedMaximum")) {
			this.setProjectileImpactedMaximum(nbt.getInt("ProjectileImpactedMaximum"));
		}
		if (nbt.contains("ProjectileImpactedTimer")) {
			this.setProjectileImpactedTimer(nbt.getInt("ProjectileImpactedTimer"));
		}
		if (nbt.contains("LifeShardCount")) {
			this.setLifeShardCount(nbt.getInt("LifeShardCount"));
		}
	}
	
	@Override
	public void copyFrom(IAetherPlayer other) {
		this.setCanGetPortal(other.canGetPortal());
		this.setRemedyMaximum(other.getRemedyMaximum());
		this.setRemedyTimer(other.getRemedyTimer());
		this.setProjectileImpactedMaximum(other.getProjectileImpactedMaximum());
		this.setProjectileImpactedTimer(other.getProjectileImpactedTimer());
		this.setLifeShardCount(other.getLifeShardCount());
	}

	@Override
	public void copyHealth(IAetherPlayer other, boolean wasDeath) {
		if (!wasDeath) {
			this.getPlayer().setHealth(other.getPlayer().getHealth());
		} else {
			if (this.getPlayer().getHealth() == 20.0F) {
				this.getPlayer().setHealth(this.getPlayer().getMaxHealth());
			}
		}
	}

	@Override
	public void sync() {
		if (!this.getPlayer().level.isClientSide) {
			AetherPacketHandler.sendToPlayer(new SetRemedyPacket(this.getRemedyMaximum(), this.getRemedyTimer()), (ServerPlayerEntity) this.getPlayer());
			AetherPacketHandler.sendToPlayer(new SetProjectileImpactedPacket(this.getProjectileImpactedMaximum(), this.getProjectileImpactedTimer()), (ServerPlayerEntity) this.getPlayer());
			AetherPacketHandler.sendToPlayer(new SetLifeShardPacket(this.getLifeShardCount()), (ServerPlayerEntity) this.getPlayer());
		}
	}

	@Override
	public void onUpdate() {
		handleAetherPortal();
		activateParachute();
		tickDownRemedy();
		tickDownProjectileImpact();
		handleLifeShardModifier();
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

	private void tickDownRemedy() {
		if (!this.getPlayer().level.isClientSide) {
			if (this.remedyTimer > 0) {
				this.remedyTimer--;
			} else {
				this.remedyMaximum = 0;
				this.remedyTimer = 0;
			}
		}
	}

	private void tickDownProjectileImpact() {
		if (!this.getPlayer().level.isClientSide) {
			if (this.projectileImpactedTimer > 0) {
				this.projectileImpactedTimer--;
			} else {
				this.projectileImpactedMaximum = 0;
				this.projectileImpactedTimer = 0;
			}
		}
	}

	private void handleLifeShardModifier() {
		ModifiableAttributeInstance health = this.getPlayer().getAttribute(Attributes.MAX_HEALTH);
		AttributeModifier LIFE_SHARD_HEALTH = new AttributeModifier(LIFE_SHARD_HEALTH_ID, "Life Shard health increase", this.lifeShardCount * 2.0F, AttributeModifier.Operation.ADDITION);
		if (health != null) {
			if (health.hasModifier(LIFE_SHARD_HEALTH)) {
				health.removeModifier(LIFE_SHARD_HEALTH);
			}
			health.addTransientModifier(LIFE_SHARD_HEALTH);
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

	@Override
	public void setJumping(boolean isJumping) {
		this.isJumping = isJumping;
	}

	@Override
	public boolean isJumping() {
		return this.isJumping;
	}

	@Override
	public void setRemedyMaximum(int remedyMaximum) {
		this.remedyMaximum = remedyMaximum;
	}

	@Override
	public int getRemedyMaximum() {
		return remedyMaximum;
	}

	@Override
	public void setRemedyTimer(int timer) {
		this.remedyTimer = timer;
	}

	@Override
	public int getRemedyTimer() {
		return this.remedyTimer;
	}

	@Override
	public void setProjectileImpactedMaximum(int projectileImpactedMaximum) {
		this.projectileImpactedMaximum = projectileImpactedMaximum;
	}

	@Override
	public int getProjectileImpactedMaximum() {
		return projectileImpactedMaximum;
	}

	@Override
	public void setProjectileImpactedTimer(int projectileImpactedTimer) {
		this.projectileImpactedTimer = projectileImpactedTimer;
	}

	@Override
	public int getProjectileImpactedTimer() {
		return projectileImpactedTimer;
	}

	@Override
	public void addToLifeShardCount(int amountToAdd) {
		this.lifeShardCount += amountToAdd;
		handleLifeShardModifier();
	}

	@Override
	public void setLifeShardCount(int amount) {
		this.lifeShardCount = amount;
		handleLifeShardModifier();
	}

	@Override
	public int getLifeShardLimit() {
		return AetherConfig.COMMON.maximum_life_shards.get();
	}

	@Override
	public int getLifeShardCount() {
		return this.lifeShardCount;
	}
}
