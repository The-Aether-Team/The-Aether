package com.gildedgames.aether.core.capability.capabilities.player;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.entity.miscellaneous.CloudMinionEntity;
import com.gildedgames.aether.common.entity.miscellaneous.ColdParachuteEntity;
import com.gildedgames.aether.common.entity.miscellaneous.GoldenParachuteEntity;
import com.gildedgames.aether.common.entity.passive.AerbunnyEntity;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;

import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.client.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AetherPlayer implements IAetherPlayer
{
	private final Player player;

	private static final UUID LIFE_SHARD_HEALTH_ID = UUID.fromString("E11710C8-4247-4CB6-B3B5-729CB34CFC1A");

	private boolean canGetPortal = true;

	public boolean isInAetherPortal = false;
	public int aetherPortalTimer = 0;
	public float prevPortalAnimTime, portalAnimTime = 0.0F;

	private boolean isHitting;
	private boolean isMoving;
	private boolean isJumping;

	private static final EntityDataAccessor<Integer> DATA_GOLDEN_DART_COUNT_ID = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> DATA_POISON_DART_COUNT_ID = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> DATA_ENCHANTED_DART_COUNT_ID = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);
	private int removeGoldenDartTime;
	private int removePoisonDartTime;
	private int removeEnchantedDartTime;

	private static final EntityDataAccessor<Integer> DATA_REMEDY_MAXIMUM_ID = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> DATA_REMEDY_TIMER_ID = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);

	private static final EntityDataAccessor<Integer> DATA_IMPACTED_MAXIMUM_ID = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> DATA_IMPACTED_TIMER_ID = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);

	private static final EntityDataAccessor<Optional<UUID>> DATA_AERBUNNY_ID = SynchedEntityData.defineId(Player.class, EntityDataSerializers.OPTIONAL_UUID);
	private int aerbunnyCheck;

	private final List<CloudMinionEntity> cloudMinions = new ArrayList<>(2);

	private float savedHealth = 0.0F;
	private static final EntityDataAccessor<Integer> DATA_LIFE_SHARD_ID = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);
	
	public AetherPlayer(Player player) {
		this.player = player;
		this.defineSynchedData();
	}
	
	@Override
	public Player getPlayer() {
		return this.player;
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		nbt.putBoolean("CanGetPortal", this.canGetPortal());
		nbt.putInt("RemedyMaximum", this.getRemedyMaximum());
		nbt.putInt("RemedyTimer", this.getRemedyTimer());
		nbt.putInt("ProjectileImpactedMaximum", this.getProjectileImpactedMaximum());
		nbt.putInt("ProjectileImpactedTimer", this.getProjectileImpactedTimer());
		if (this.getAerbunny() != null) {
			nbt.putUUID("Aerbunny", this.getAerbunny());
		}
		nbt.putFloat("SavedHealth", this.getSavedHealth());
		nbt.putInt("LifeShardCount", this.getLifeShardCount());

		//(leftover reference code)
		//Set<AetherRank> ranks = AetherRankings.getRanksOf(this.player.getUniqueID());
//		if (ranks.stream().anyMatch(AetherRank::hasHalo)) {
//			nbt.putBoolean("Halo", this.shouldRenderHalo);
//		}
		//this.accessories.writeToNBT(nbt);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
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
		if (nbt.contains("Aerbunny")) {
			this.setAerbunny(nbt.getUUID("Aerbunny"));
		}
		if (nbt.contains("SavedHealth")) {
			this.setSavedHealth(nbt.getFloat("SavedHealth"));
		}
		if (nbt.contains("LifeShardCount")) {
			this.setLifeShardCount(nbt.getInt("LifeShardCount"));
		}
	}

	@Override
	public void defineSynchedData() {
		this.getPlayer().getEntityData().define(DATA_GOLDEN_DART_COUNT_ID, 0);
		this.getPlayer().getEntityData().define(DATA_POISON_DART_COUNT_ID, 0);
		this.getPlayer().getEntityData().define(DATA_ENCHANTED_DART_COUNT_ID, 0);
		this.getPlayer().getEntityData().define(DATA_REMEDY_MAXIMUM_ID, 0);
		this.getPlayer().getEntityData().define(DATA_REMEDY_TIMER_ID, 0);
		this.getPlayer().getEntityData().define(DATA_IMPACTED_MAXIMUM_ID, 0);
		this.getPlayer().getEntityData().define(DATA_IMPACTED_TIMER_ID, 0);
		this.getPlayer().getEntityData().define(DATA_AERBUNNY_ID, Optional.empty());
		this.getPlayer().getEntityData().define(DATA_LIFE_SHARD_ID, 0);
	}

	@Override
	public void onLogin() {
		this.handleGivePortal();
		this.remountAerbunny();
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
	public void onUpdate() {
		this.handleAetherPortal();
		this.activateParachute();
		this.handleRemoveDarts();
		this.tickDownRemedy();
		this.tickDownProjectileImpact();
		this.checkToRemoveAerbunny();
		this.checkToRemoveCloudMinions();
		this.handleSavedHealth();
		this.handleLifeShardModifier();
	}

	private void handleGivePortal() {
		if (AetherConfig.COMMON.start_with_portal.get()) {
			this.givePortalItem();
		} else {
			this.setCanGetPortal(false);
		}
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
					if (mc.screen instanceof AbstractContainerScreen) {
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
				if (this.portalAnimTime > 0.0F) {
					this.portalAnimTime -= 0.05F;
				}

				if (this.portalAnimTime < 0.0F) {
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
		mc.getSoundManager().play(SimpleSoundInstance.forLocalAmbience(AetherSoundEvents.BLOCK_AETHER_PORTAL_TRIGGER.get(), this.getPlayer().getRandom().nextFloat() * 0.4F + 0.8F, 0.25F));
	}

	private void activateParachute() {
		Player player = this.getPlayer();
		Inventory inventory = this.getPlayer().getInventory();
		Level level = player.level;
		if (!player.isCreative() && !player.isShiftKeyDown()) {
			if (player.getDeltaMovement().y() < -1.5D) {
				if (inventory.contains(new ItemStack(AetherItems.COLD_PARACHUTE.get()))) {
					for (ItemStack stack : inventory.items) {
						Item item = stack.getItem();
						if (item == AetherItems.COLD_PARACHUTE.get()) {
							ColdParachuteEntity coldParachute = AetherEntityTypes.COLD_PARACHUTE.get().create(level);
							if (coldParachute != null) {
								coldParachute.setPos(player.getX(), player.getY() - 1.0D, player.getZ());
								if (!level.isClientSide) {
									level.addFreshEntity(coldParachute);
									player.startRiding(coldParachute);
									stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
								}
								coldParachute.spawnExplosionParticle();
								break;
							}
						}
					}
				} else if (inventory.contains(new ItemStack(AetherItems.GOLDEN_PARACHUTE.get()))) {
					for (ItemStack stack : inventory.items) {
						Item item = stack.getItem();
						if (item == AetherItems.GOLDEN_PARACHUTE.get()) {
							GoldenParachuteEntity goldenParachute = AetherEntityTypes.GOLDEN_PARACHUTE.get().create(level);
							if (goldenParachute != null) {
								goldenParachute.setPos(player.getX(), player.getY() - 1.0D, player.getZ());
								if (!level.isClientSide) {
									level.addFreshEntity(goldenParachute);
									player.startRiding(goldenParachute);
									stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
								}
								goldenParachute.spawnExplosionParticle();
								break;
							}
						}
					}
				}
			}
		}
	}

	private void handleRemoveDarts() {
		if (!this.getPlayer().level.isClientSide) {
			if (this.getGoldenDartCount() > 0) {
				if (this.removeGoldenDartTime <= 0) {
					this.removeGoldenDartTime = 20 * (30 - this.getGoldenDartCount());
				}

				--this.removeGoldenDartTime;
				if (this.removeGoldenDartTime <= 0) {
					this.setGoldenDartCount(this.getGoldenDartCount() - 1);
				}
			}
			if (this.getPoisonDartCount() > 0) {
				if (this.removePoisonDartTime <= 0) {
					this.removePoisonDartTime = 20 * (30 - this.getPoisonDartCount());
				}

				--this.removePoisonDartTime;
				if (this.removePoisonDartTime <= 0) {
					this.setPoisonDartCount(this.getPoisonDartCount() - 1);
				}
			}
			if (this.getEnchantedDartCount() > 0) {
				if (this.removeEnchantedDartTime <= 0) {
					this.removeEnchantedDartTime = 20 * (30 - this.getEnchantedDartCount());
				}

				--this.removeEnchantedDartTime;
				if (this.removeEnchantedDartTime <= 0) {
					this.setEnchantedDartCount(this.getEnchantedDartCount() - 1);
				}
			}
		}
	}

	private void tickDownRemedy() {
		if (this.getRemedyTimer() > 0) {
			this.setRemedyTimer(this.getRemedyTimer() - 1);
		} else {
			this.setRemedyMaximum(0);
			this.setRemedyTimer(0);
		}
	}

	private void tickDownProjectileImpact() {
		if (this.getProjectileImpactedTimer() > 0) {
			this.setProjectileImpactedTimer(this.getProjectileImpactedTimer() - 1);
		} else {
			this.setProjectileImpactedMaximum(0);
			this.setProjectileImpactedTimer(0);
		}
	}

	//TODO: Can we just kill the aerbunny, save all its data, and respawn it when the player rejoins? Or is that too much to track.
	private void remountAerbunny() {
		if (this.getAerbunny() != null && this.getPlayer().level instanceof ServerLevel serverLevel && serverLevel.getEntity(this.getAerbunny()) instanceof AerbunnyEntity aerbunny) {
			aerbunny.startRiding(this.getPlayer());
			if (this.getPlayer() instanceof ServerPlayer serverPlayer) {
				AetherPacketHandler.sendToPlayer(new RemountAerbunnyPacket(this.getPlayer().getId(), aerbunny.getId()), serverPlayer);
			}
		}
	}

	private void checkToRemoveAerbunny() {
		if (this.getAerbunny() != null && this.getPlayer().level instanceof ServerLevel serverLevel && serverLevel.getEntity(this.getAerbunny()) instanceof AerbunnyEntity aerbunny) {
			if (aerbunny.getVehicle() == null) {
				this.aerbunnyCheck++;
			} else {
				this.aerbunnyCheck = 0;
			}
			if (this.aerbunnyCheck == 50) {
				this.setAerbunny(null);
				this.aerbunnyCheck = 0;
			}
		}
	}

	private void checkToRemoveCloudMinions() {
		this.getCloudMinions().removeIf(cloudMinion -> !cloudMinion.isAlive());
	}

	private void handleSavedHealth() {
		if (this.getSavedHealth() > 0.0F) {
			AttributeInstance health = this.getPlayer().getAttribute(Attributes.MAX_HEALTH);
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
		AttributeInstance health = this.getPlayer().getAttribute(Attributes.MAX_HEALTH);
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
		this.getPlayer().getEntityData().set(DATA_GOLDEN_DART_COUNT_ID, count);
	}

	@Override
	public int getGoldenDartCount() {
		return this.getPlayer().getEntityData().get(DATA_GOLDEN_DART_COUNT_ID);
	}

	@Override
	public void setPoisonDartCount(int count) {
		this.getPlayer().getEntityData().set(DATA_POISON_DART_COUNT_ID, count);
	}

	@Override
	public int getPoisonDartCount() {
		return this.getPlayer().getEntityData().get(DATA_POISON_DART_COUNT_ID);
	}

	@Override
	public void setEnchantedDartCount(int count) {
		this.getPlayer().getEntityData().set(DATA_ENCHANTED_DART_COUNT_ID, count);
	}

	@Override
	public int getEnchantedDartCount() {
		return this.getPlayer().getEntityData().get(DATA_ENCHANTED_DART_COUNT_ID);
	}

	@Override
	public void setRemedyMaximum(int remedyMaximum) {
		this.getPlayer().getEntityData().set(DATA_REMEDY_MAXIMUM_ID, remedyMaximum);
	}

	@Override
	public int getRemedyMaximum() {
		return this.getPlayer().getEntityData().get(DATA_REMEDY_MAXIMUM_ID);
	}

	@Override
	public void setRemedyTimer(int timer) {
		this.getPlayer().getEntityData().set(DATA_REMEDY_TIMER_ID, timer);
	}

	@Override
	public int getRemedyTimer() {
		return this.getPlayer().getEntityData().get(DATA_REMEDY_TIMER_ID);
	}

	@Override
	public void setProjectileImpactedMaximum(int projectileImpactedMaximum) {
		this.getPlayer().getEntityData().set(DATA_IMPACTED_MAXIMUM_ID, projectileImpactedMaximum);
	}

	@Override
	public int getProjectileImpactedMaximum() {
		return this.getPlayer().getEntityData().get(DATA_IMPACTED_MAXIMUM_ID);
	}

	@Override
	public void setProjectileImpactedTimer(int projectileImpactedTimer) {
		this.getPlayer().getEntityData().set(DATA_IMPACTED_TIMER_ID, projectileImpactedTimer);
	}

	@Override
	public int getProjectileImpactedTimer() {
		return this.getPlayer().getEntityData().get(DATA_IMPACTED_TIMER_ID);
	}

	@Override
	public void setAerbunny(UUID uuid) {
		this.getPlayer().getEntityData().set(DATA_AERBUNNY_ID, Optional.ofNullable(uuid));
	}

	@Override
	public UUID getAerbunny() {
		return this.getPlayer().getEntityData().get(DATA_AERBUNNY_ID).orElse(null);
	}

	@Override
	public void setCloudMinions(CloudMinionEntity cloudMinionRight, CloudMinionEntity cloudMinionLeft) {
		this.sendCloudMinionPacket(cloudMinionRight, cloudMinionLeft);
		this.cloudMinions.add(0, cloudMinionRight);
		this.cloudMinions.add(1, cloudMinionLeft);
	}

	@Override
	public List<CloudMinionEntity> getCloudMinions() {
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
		int newAmount = this.getLifeShardCount() + amountToAdd;
		this.getPlayer().getEntityData().set(DATA_LIFE_SHARD_ID, newAmount);
	}

	@Override
	public void setLifeShardCount(int amount) {
		this.getPlayer().getEntityData().set(DATA_LIFE_SHARD_ID, amount);
	}

	@Override
	public int getLifeShardCount() {
		return this.getPlayer().getEntityData().get(DATA_LIFE_SHARD_ID);
	}

	@Override
	public int getLifeShardLimit() {
		return AetherConfig.COMMON.maximum_life_shards.get();
	}

	@Override
	public AttributeModifier getLifeShardHealthAttributeModifier() {
		return new AttributeModifier(LIFE_SHARD_HEALTH_ID, "Life Shard health increase", this.getLifeShardCount() * 2.0F, AttributeModifier.Operation.ADDITION);
	}

	private void sendCloudMinionPacket(CloudMinionEntity cloudMinionRight, CloudMinionEntity cloudMinionLeft) {
		if (this.getPlayer() instanceof ServerPlayer serverPlayer && !this.getPlayer().level.isClientSide) {
			AetherPacketHandler.sendToPlayer(new CloudMinionPacket(this.getPlayer().getId(), cloudMinionRight.getId(), cloudMinionLeft.getId()), serverPlayer);
		}
	}
}
