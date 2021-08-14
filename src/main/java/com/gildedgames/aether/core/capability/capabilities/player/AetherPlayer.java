package com.gildedgames.aether.core.capability.capabilities.player;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.entity.miscellaneous.CloudMinionEntity;
import com.gildedgames.aether.common.entity.miscellaneous.ColdParachuteEntity;
import com.gildedgames.aether.common.entity.miscellaneous.GoldenParachuteEntity;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;

import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.client.*;
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
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AetherPlayer implements IAetherPlayer
{
	private final PlayerEntity player;

	private static final UUID LIFE_SHARD_HEALTH_ID = UUID.fromString("E11710C8-4247-4CB6-B3B5-729CB34CFC1A");

	private boolean canGetPortal = true;

	public boolean isInAetherPortal = false;
	public int aetherPortalTimer = 0;
	public float prevPortalAnimTime, portalAnimTime = 0.0F;

	private boolean isHitting;

	private boolean isMoving;

	private boolean isJumping;

	private int goldenDartCount = 0;
	private int poisonDartCount = 0;
	private int enchantedDartCount = 0;
	private int removeGoldenDartTime;
	private int removePoisonDartTime;
	private int removeEnchantedDartTime;

	private int remedyMaximum = 0;
	private int remedyTimer = 0;

	private int projectileImpactedMaximum = 0;
	private int projectileImpactedTimer = 0;

	private List<CloudMinionEntity> cloudMinions = new ArrayList<>(2);

	private float savedHealth = 0.0F;
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
		nbt.putFloat("SavedHealth", this.getSavedHealth());
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
			this.canGetPortal = nbt.getBoolean("CanGetPortal");
		}
		if (nbt.contains("RemedyMaximum")) {
			this.remedyMaximum = nbt.getInt("RemedyMaximum");
		}
		if (nbt.contains("RemedyTimer")) {
			this.remedyTimer = nbt.getInt("RemedyTimer");
		}
		if (nbt.contains("ProjectileImpactedMaximum")) {
			this.projectileImpactedMaximum = nbt.getInt("ProjectileImpactedMaximum");
		}
		if (nbt.contains("ProjectileImpactedTimer")) {
			this.projectileImpactedTimer = nbt.getInt("ProjectileImpactedTimer");
		}
		if (nbt.contains("SavedHealth")) {
			this.savedHealth = nbt.getFloat("SavedHealth");
		}
		if (nbt.contains("LifeShardCount")) {
			this.lifeShardCount = nbt.getInt("LifeShardCount");
		}
	}

	@Override
	public void copyFrom(IAetherPlayer other, boolean isWasDeath) {
		if (!isWasDeath) {
			this.setRemedyMaximum(other.getRemedyMaximum());
			this.setRemedyTimer(other.getRemedyTimer());
			this.setProjectileImpactedMaximum(other.getProjectileImpactedMaximum());
			this.setProjectileImpactedTimer(other.getProjectileImpactedTimer());
		}
		this.setCanGetPortal(other.canGetPortal());
		this.setLifeShardCount(other.getLifeShardCount());
	}

	@Override
	public void sync() {
		this.sendRemedyPacket(this.remedyMaximum, this.remedyTimer);
		this.sendProjectileImpactedPacket(this.projectileImpactedMaximum, this.projectileImpactedTimer);
		this.sendLifeShardPacket(this.lifeShardCount);
	}

	@Override
	public void onUpdate() {
		this.handleAetherPortal();
		this.activateParachute();
		this.handleRemoveDarts();
		this.tickDownRemedy();
		this.tickDownProjectileImpact();
		this.checkToRemoveCloudMinions();
		this.handleSavedHealth();
		this.handleLifeShardModifier();
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
			if (player.level.isClientSide) {
				this.portalAnimTime += 0.0125F;
				if(this.portalAnimTime > 1.0F) {
					this.portalAnimTime = 1.0F;
				}
			}
			this.isInAetherPortal = false;
		}
		else {
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
		mc.getSoundManager().play(SimpleSound.forLocalAmbience(AetherSoundEvents.BLOCK_AETHER_PORTAL_TRIGGER.get(), this.getPlayer().random.nextFloat() * 0.4F + 0.8F, 0.25F));
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
							ColdParachuteEntity parachuteEntity = AetherEntityTypes.COLD_PARACHUTE.get().create(world);
							if (parachuteEntity != null) {
								parachuteEntity.setPos(player.getX(), player.getY() - 1.0D, player.getZ());
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
				} else if (inventory.contains(new ItemStack(AetherItems.GOLDEN_PARACHUTE.get()))) {
					for (ItemStack stack : inventory.items) {
						Item item = stack.getItem();
						if (item == AetherItems.GOLDEN_PARACHUTE.get()) {
							GoldenParachuteEntity parachuteEntity = AetherEntityTypes.GOLDEN_PARACHUTE.get().create(world);
							if (parachuteEntity != null) {
								parachuteEntity.setPos(player.getX(), player.getY() - 1.0D, player.getZ());
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
	}

	private void handleRemoveDarts() {
		int goldenDarts = this.getGoldenDartCount();
		if (goldenDarts > 0) {
			if (this.removeGoldenDartTime <= 0) {
				this.removeGoldenDartTime = 20 * (30 - goldenDarts);
			}

			--this.removeGoldenDartTime;
			if (this.removeGoldenDartTime <= 0) {
				this.setGoldenDartCount(goldenDarts - 1);
			}
		}
		int poisonDarts = this.getPoisonDartCount();
		if (poisonDarts > 0) {
			if (this.removePoisonDartTime <= 0) {
				this.removePoisonDartTime = 20 * (30 - poisonDarts);
			}

			--this.removePoisonDartTime;
			if (this.removePoisonDartTime <= 0) {
				this.setPoisonDartCount(poisonDarts - 1);
			}
		}
		int enchantedDarts = this.getEnchantedDartCount();
		if (enchantedDarts > 0) {
			if (this.removeEnchantedDartTime <= 0) {
				this.removeEnchantedDartTime = 20 * (30 - enchantedDarts);
			}

			--this.removeEnchantedDartTime;
			if (this.removeEnchantedDartTime <= 0) {
				this.setEnchantedDartCount(enchantedDarts - 1);
			}
		}
	}

	private void tickDownRemedy() {
		if (this.remedyTimer > 0) {
			this.remedyTimer--;
		} else {
			this.remedyMaximum = 0;
			this.remedyTimer = 0;
		}
	}

	private void tickDownProjectileImpact() {
		if (this.projectileImpactedTimer > 0) {
			this.projectileImpactedTimer--;
		} else {
			this.projectileImpactedMaximum = 0;
			this.projectileImpactedTimer = 0;
		}
	}

	private void checkToRemoveCloudMinions() {
		this.getCloudMinionEntities().removeIf(cloudMinion -> !cloudMinion.isAlive());
	}

	private void handleSavedHealth() {
		if (this.getSavedHealth() > 0.0F) {
			ModifiableAttributeInstance health = this.getPlayer().getAttribute(Attributes.MAX_HEALTH);
			if (health != null && health.hasModifier(this.getLifeShardHealthAttributeModifier())) {
				if (this.getSavedHealth() >= this.getPlayer().getMaxHealth()) {
					this.getPlayer().setHealth(this.getPlayer().getMaxHealth());
				} else {
					this.getPlayer().setHealth(this.getSavedHealth());
				}
				this.setSavedHealth(0.0F);
			}
		}
	}

	private void handleLifeShardModifier() {
		ModifiableAttributeInstance health = this.getPlayer().getAttribute(Attributes.MAX_HEALTH);
		AttributeModifier LIFE_SHARD_HEALTH = this.getLifeShardHealthAttributeModifier();
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
	public void setHitting(boolean isHitting) {
		this.isHitting = isHitting;
	}

	@Override
	public boolean isHitting() {
		return this.isHitting;
	}

	@Override
	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	@Override
	public boolean isMoving() {
		return this.isMoving;
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
	public void setGoldenDartCount(int count) {
		this.sendGoldenDartPacket(count);
		this.goldenDartCount = count;
	}

	@Override
	public int getGoldenDartCount() {
		this.sendGoldenDartPacket(this.goldenDartCount);
		return this.goldenDartCount;
	}

	@Override
	public void setPoisonDartCount(int count) {
		this.sendPoisonDartPacket(count);
		this.poisonDartCount = count;
	}

	@Override
	public int getPoisonDartCount() {
		this.sendPoisonDartPacket(this.poisonDartCount);
		return this.poisonDartCount;
	}

	@Override
	public void setEnchantedDartCount(int count) {
		this.sendEnchantedDartPacket(count);
		this.enchantedDartCount = count;
	}

	@Override
	public int getEnchantedDartCount() {
		this.sendEnchantedDartPacket(this.enchantedDartCount);
		return this.enchantedDartCount;
	}

	@Override
	public void setRemedyMaximum(int remedyMaximum) {
		this.sendRemedyPacket(remedyMaximum, this.remedyTimer);
		this.remedyMaximum = remedyMaximum;
	}

	@Override
	public int getRemedyMaximum() {
		this.sendRemedyPacket(this.remedyMaximum, this.remedyTimer);
		return remedyMaximum;
	}

	@Override
	public void setRemedyTimer(int timer) {
		this.sendRemedyPacket(this.remedyMaximum, timer);
		this.remedyTimer = timer;
	}

	@Override
	public int getRemedyTimer() {
		this.sendRemedyPacket(this.remedyMaximum, this.remedyTimer);
		return this.remedyTimer;
	}

	@Override
	public void setProjectileImpactedMaximum(int projectileImpactedMaximum) {
		this.sendProjectileImpactedPacket(projectileImpactedMaximum, this.projectileImpactedTimer);
		this.projectileImpactedMaximum = projectileImpactedMaximum;
	}

	@Override
	public int getProjectileImpactedMaximum() {
		this.sendProjectileImpactedPacket(this.projectileImpactedMaximum, this.projectileImpactedTimer);
		return this.projectileImpactedMaximum;
	}

	@Override
	public void setProjectileImpactedTimer(int projectileImpactedTimer) {
		this.sendProjectileImpactedPacket(this.projectileImpactedMaximum, projectileImpactedTimer);
		this.projectileImpactedTimer = projectileImpactedTimer;
	}

	@Override
	public int getProjectileImpactedTimer() {
		this.sendProjectileImpactedPacket(this.projectileImpactedMaximum, this.projectileImpactedTimer);
		return this.projectileImpactedTimer;
	}

	@Override
	public void setCloudMinions(CloudMinionEntity cloudMinionRight, CloudMinionEntity cloudMinionLeft) {
		this.sendCloudMinionPacket(cloudMinionRight, cloudMinionLeft);
		this.cloudMinions.add(0, cloudMinionRight);
		this.cloudMinions.add(1, cloudMinionLeft);
	}

	@Override
	public List<CloudMinionEntity> getCloudMinionEntities() {
		return this.cloudMinions;
	}

	@Override
	public void setSavedHealth(float health) {
		this.savedHealth = health;
	}

	@Override
	public float getSavedHealth() {
		return this.savedHealth;
	}

	@Override
	public void addToLifeShardCount(int amountToAdd) {
		int newAmount = this.lifeShardCount + amountToAdd;
		this.sendLifeShardPacket(newAmount);
		this.lifeShardCount = newAmount;
	}

	@Override
	public void setLifeShardCount(int amount) {
		this.sendLifeShardPacket(amount);
		this.lifeShardCount = amount;
	}

	@Override
	public int getLifeShardCount() {
		this.sendLifeShardPacket(this.lifeShardCount);
		return this.lifeShardCount;
	}

	@Override
	public int getLifeShardLimit() {
		return AetherConfig.COMMON.maximum_life_shards.get();
	}

	@Override
	public AttributeModifier getLifeShardHealthAttributeModifier() {
		return new AttributeModifier(LIFE_SHARD_HEALTH_ID, "Life Shard health increase", this.getLifeShardCount() * 2.0F, AttributeModifier.Operation.ADDITION);
	}

	private void sendGoldenDartPacket(int count) {
		if (this.getPlayer() instanceof ServerPlayerEntity && !this.getPlayer().level.isClientSide) {
			AetherPacketHandler.sendToPlayer(new GoldenDartCountPacket(this.getPlayer().getId(), count), (ServerPlayerEntity) this.getPlayer());
		}
	}

	private void sendPoisonDartPacket(int count) {
		if (this.getPlayer() instanceof ServerPlayerEntity && !this.getPlayer().level.isClientSide) {
			AetherPacketHandler.sendToPlayer(new PoisonDartCountPacket(this.getPlayer().getId(), count), (ServerPlayerEntity) this.getPlayer());
		}
	}

	private void sendEnchantedDartPacket(int count) {
		if (this.getPlayer() instanceof ServerPlayerEntity && !this.getPlayer().level.isClientSide) {
			AetherPacketHandler.sendToPlayer(new EnchantedDartCountPacket(this.getPlayer().getId(), count), (ServerPlayerEntity) this.getPlayer());
		}
	}

	private void sendRemedyPacket(int maximum, int timer) {
		if (this.getPlayer() instanceof ServerPlayerEntity && !this.getPlayer().level.isClientSide) {
			AetherPacketHandler.sendToPlayer(new SetRemedyPacket(this.getPlayer().getId(), maximum, timer), (ServerPlayerEntity) this.getPlayer());
		}
	}

	private void sendProjectileImpactedPacket(int maximum, int timer) {
		if (this.getPlayer() instanceof ServerPlayerEntity && !this.getPlayer().level.isClientSide) {
			AetherPacketHandler.sendToPlayer(new SetProjectileImpactedPacket(this.getPlayer().getId(), maximum, timer), (ServerPlayerEntity) this.getPlayer());
		}
	}

	private void sendCloudMinionPacket(CloudMinionEntity cloudMinionRight, CloudMinionEntity cloudMinionLeft) {
		if (this.getPlayer() instanceof ServerPlayerEntity && !this.getPlayer().level.isClientSide) {
			AetherPacketHandler.sendToPlayer(new CloudMinionPacket(this.getPlayer().getId(), cloudMinionRight.getId(), cloudMinionLeft.getId()), (ServerPlayerEntity) this.getPlayer());
		}
	}

	private void sendLifeShardPacket(int amount) {
		if (this.getPlayer() instanceof ServerPlayerEntity && !this.getPlayer().level.isClientSide) {
			AetherPacketHandler.sendToPlayer(new SetLifeShardPacket(this.getPlayer().getId(), amount), (ServerPlayerEntity) this.getPlayer());
		}
	}
}
