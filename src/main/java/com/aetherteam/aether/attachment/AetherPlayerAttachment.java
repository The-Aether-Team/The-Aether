package com.aetherteam.aether.attachment;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.client.sound.PortalTriggerSoundInstance;
import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.aetherteam.aether.effect.AetherEffects;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.miscellaneous.CloudMinion;
import com.aetherteam.aether.entity.miscellaneous.Parachute;
import com.aetherteam.aether.entity.passive.Aerbunny;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.aether.item.miscellaneous.ParachuteItem;
import com.aetherteam.aether.network.packet.AetherPlayerSyncPacket;
import com.aetherteam.aether.network.packet.clientbound.CloudMinionPacket;
import com.aetherteam.aether.network.packet.clientbound.RemountAerbunnyPacket;
import com.aetherteam.aether.perk.CustomizationsOptions;
import com.aetherteam.aether.perk.data.ClientDeveloperGlowPerkData;
import com.aetherteam.aether.perk.data.ClientHaloPerkData;
import com.aetherteam.aether.perk.data.ClientMoaSkinPerkData;
import com.aetherteam.aether.perk.data.ServerPerkData;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.BasePacket;
import com.aetherteam.nitrogen.network.PacketRelay;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Triple;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Capability class for handling {@link Player} behavior for the Aether.
 *
 * @see com.aetherteam.aether.event.hooks.CapabilityHooks.AetherPlayerHooks
 */
public class AetherPlayerAttachment implements INBTSynchable {
    private static final UUID LIFE_SHARD_HEALTH_ID = UUID.fromString("E11710C8-4247-4CB6-B3B5-729CB34CFC1A");

    private boolean canGetPortal = true;
    private boolean canSpawnInAether = true;

    public boolean isInAetherPortal = false;
    public int aetherPortalTimer = 0;
    public float prevPortalAnimTime, portalAnimTime = 0.0F;

    private boolean isHitting;
    private boolean isMoving;
    private boolean isJumping;
    private boolean isGravititeJumpActive;
    private boolean seenSunSpiritDialogue;

    private int goldenDartCount;
    private int poisonDartCount;
    private int enchantedDartCount;
    private int removeGoldenDartTime;
    private int removePoisonDartTime;
    private int removeEnchantedDartTime;

    private int remedyStartDuration;

    private int impactedMaximum;
    private int impactedTimer;

    private boolean performVampireHealing;

    @Nullable
    private Aerbunny mountedAerbunny;
    private Optional<CompoundTag> mountedAerbunnyTag = Optional.empty();

    private Optional<UUID> lastRiddenMoa = Optional.empty();

    private final List<CloudMinion> cloudMinions = new ArrayList<>(2);

    private int wingRotationO;
    private int wingRotation;

    private int invisibilityAttackCooldown;
    private boolean attackedWithInvisibility;
    private boolean invisibilityEnabled = true;
    private boolean wearingInvisibilityCloak;

    private static final int FLIGHT_TIMER_MAX = 52;
    private static final float FLIGHT_MODIFIER_MAX = 15.0F;
    private int flightTimer;
    private float flightModifier = 1.0F;

    private double neptuneSubmergeLength;
    private double phoenixSubmergeLength;

    private static final int OBSIDIAN_TIMER_MAX = 20;
    private int obsidianConversionTime;

    private float savedHealth = 0.0F;
    private int lifeShards;

    private static final ResourceLocation LOGOMARKS = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "logomarks");
    private static final Style DISCORD = Style.EMPTY.withColor(5793266).withUnderlined(true).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/aethermod")).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal("https://discord.gg/aethermod")));
    private static final Style PATREON = Style.EMPTY.withColor(16728653).withUnderlined(true).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.patreon.com/TheAetherTeam")).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal("https://www.patreon.com/TheAetherTeam")));
    private boolean canShowPatreonMessage = true;
    private int loginsUntilPatreonMessage = -1;

    /**
     * Stores the following methods as able to be synced between client and server and vice-versa.
     */
    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
        Map.entry("setHitting", Triple.of(Type.BOOLEAN, (object) -> this.setHitting((boolean) object), this::isHitting)),
        Map.entry("setMoving", Triple.of(Type.BOOLEAN, (object) -> this.setMoving((boolean) object), this::isMoving)),
        Map.entry("setJumping", Triple.of(Type.BOOLEAN, (object) -> this.setJumping((boolean) object), this::isJumping)),
        Map.entry("setGravititeJumpActive", Triple.of(Type.BOOLEAN, (object) -> this.setGravititeJumpActive((boolean) object), this::isGravititeJumpActive)),
        Map.entry("setGoldenDartCount", Triple.of(Type.INT, (object) -> this.setGoldenDartCount((int) object), this::getGoldenDartCount)),
        Map.entry("setPoisonDartCount", Triple.of(Type.INT, (object) -> this.setPoisonDartCount((int) object), this::getPoisonDartCount)),
        Map.entry("setEnchantedDartCount", Triple.of(Type.INT, (object) -> this.setEnchantedDartCount((int) object), this::getEnchantedDartCount)),
        Map.entry("setRemedyStartDuration", Triple.of(Type.INT, (object) -> this.setRemedyStartDuration((int) object), this::getRemedyStartDuration)),
        Map.entry("setAttackedWithInvisibility", Triple.of(Type.BOOLEAN, (object) -> this.setAttackedWithInvisibility((boolean) object), this::attackedWithInvisibility)),
        Map.entry("setInvisibilityEnabled", Triple.of(Type.BOOLEAN, (object) -> this.setInvisibilityEnabled((boolean) object), this::isInvisibilityEnabled)),
        Map.entry("setWearingInvisibilityCloak", Triple.of(Type.BOOLEAN, (object) -> this.setWearingInvisibilityCloak((boolean) object), this::isWearingInvisibilityCloak)),
        Map.entry("setLifeShardCount", Triple.of(Type.INT, (object) -> this.setLifeShardCount((int) object), this::getLifeShardCount)),
        Map.entry("setLastRiddenMoa", Triple.of(Type.UUID, (object) -> this.setLastRiddenMoa((UUID) object), this::getLastRiddenMoa)),
        Map.entry("setShouldSyncBetweenClients", Triple.of(Type.BOOLEAN, (object) -> this.setShouldSyncBetweenClients((boolean) object), this::shouldSyncBetweenClients))
    );
    private boolean shouldSyncAfterJoin;
    private boolean shouldSyncBetweenClients;

    public static final Codec<AetherPlayerAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.BOOL.fieldOf("can_get_portal").forGetter(AetherPlayerAttachment::canGetPortal),
        Codec.BOOL.fieldOf("can_spawn_in_aether").forGetter(AetherPlayerAttachment::canSpawnInAether),
        Codec.FLOAT.fieldOf("saved_health").forGetter(AetherPlayerAttachment::getSavedHealth),
        Codec.INT.fieldOf("life_shard_count").forGetter(AetherPlayerAttachment::getLifeShardCount),
        Codec.BOOL.fieldOf("seen_sun_spirit").forGetter(AetherPlayerAttachment::hasSeenSunSpiritDialogue),
        Codec.INT.fieldOf("remedy_start_duration").forGetter(AetherPlayerAttachment::getRemedyStartDuration),
        CompoundTag.CODEC.optionalFieldOf("mounted_aerbunny").forGetter(AetherPlayerAttachment::getMountedAerbunnyTag),
        UUIDUtil.CODEC.optionalFieldOf("last_ridden_moa").forGetter(o -> o.lastRiddenMoa),
        Codec.BOOL.fieldOf("show_patreon_message").forGetter(o -> o.canShowPatreonMessage),
        Codec.INT.fieldOf("logins_until_patreon_message").forGetter(o -> o.loginsUntilPatreonMessage)
    ).apply(instance, AetherPlayerAttachment::new));

    public AetherPlayerAttachment() {

    }

    public AetherPlayerAttachment(boolean portal, boolean spawnInAether, float savedHealth, int lifeShards, boolean seenSunSpirit, int remedyDuration, Optional<CompoundTag> mountedAerbunnyTag, Optional<UUID> lastRiddenMoa, boolean showPatreonMessage, int loginUntilMessage) {
        this.setCanGetPortal(portal);
        this.setCanSpawnInAether(spawnInAether);
        this.setSavedHealth(savedHealth);
        this.setLifeShardCount(lifeShards);
        this.setSeenSunSpiritDialogue(seenSunSpirit);
        this.setRemedyStartDuration(remedyDuration);
        this.setMountedAerbunnyTag(mountedAerbunnyTag);
        this.setLastRiddenMoa(lastRiddenMoa.orElse(null));
        this.canShowPatreonMessage = showPatreonMessage;
        this.loginsUntilPatreonMessage = loginUntilMessage;
    }

    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return this.synchableFunctions;
    }

    /**
     * Handles functions when the player logs out of a world from {@link net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent}.
     */
    public void onLogout(Player player) {
        this.removeAerbunny();
        this.handleLogoutSavedHealth(player);
    }

    /**
     * Handles functions when the player logs in to a world from {@link net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent}.
     */
    public void onLogin(Player player) {
        this.handleGivePortal(player);
        this.remountAerbunny(player);
        this.handlePatreonMessage(player);
        this.shouldSyncAfterJoin = true;
        ServerPerkData.MOA_SKIN_INSTANCE.syncFromServer(player);
        ServerPerkData.HALO_INSTANCE.syncFromServer(player);
        ServerPerkData.DEVELOPER_GLOW_INSTANCE.syncFromServer(player);
    }

    /**
     * Handles functions when the player joins a world from {@link net.neoforged.neoforge.event.entity.EntityJoinLevelEvent}.
     */
    public void onJoinLevel(Player player) {
        if (player.level().isClientSide()) {
            CustomizationsOptions.INSTANCE.load();
            this.setSynched(player.getId(), Direction.SERVER, "setShouldSyncBetweenClients", true);
        }
    }

    /**
     * Used to correct data between instances of the capability from {@link net.neoforged.neoforge.event.entity.player.PlayerEvent.Clone}. <br>
     * This attachment copies all data by default, this is here to reset certain values that dont need to persist on death.
     *
     * @param wasDeath A {@link Boolean} for whether this copying is from death. If false, the copying is from entering the End Portal.
     */
    public void handleRespawn(boolean wasDeath) {
        if (wasDeath) {
            this.setRemedyStartDuration(0);
        }
        this.shouldSyncAfterJoin = true;
    }

    /**
     * Handles functions when the player ticks from {@link net.neoforged.neoforge.event.entity.living.LivingEvent.LivingTickEvent}
     */
    public void onUpdate(Player player) {
        this.syncAfterJoin(player);
        this.syncClients(player);
        this.handleAetherPortal(player);
        this.activateParachute(player);
        this.handleRemoveDarts(player);
        this.removeRemedyDuration(player);
        this.tickDownProjectileImpact(player);
        this.handleWingRotation(player);
        this.handleAttackCooldown(player);
        this.handleVampireHealing(player);
        this.checkToRemoveAerbunny(player);
        this.checkToRemoveCloudMinions();
        this.handleSavedHealth(player);
        this.handleLifeShardModifier(player);
        ClientMoaSkinPerkData.INSTANCE.syncFromClient(player);
        ClientHaloPerkData.INSTANCE.syncFromClient(player);
        ClientDeveloperGlowPerkData.INSTANCE.syncFromClient(player);
    }

    private void syncAfterJoin(Player player) {
        if (this.shouldSyncAfterJoin) {
            this.forceSync(player.getId(), INBTSynchable.Direction.CLIENT);
            this.shouldSyncAfterJoin = false;
        }
    }

    private void syncClients(Player player) {
        if (this.shouldSyncBetweenClients()) {
            if (!player.level().isClientSide()) {
                MinecraftServer server = player.level().getServer();
                if (server != null) {
                    PlayerList playerList = server.getPlayerList();
                    for (ServerPlayer serverPlayer : playerList.getPlayers()) {
                        if (!serverPlayer.getUUID().equals(player.getUUID())) {
                            player.getData(AetherDataAttachments.AETHER_PLAYER).forceSync(player.getId(), INBTSynchable.Direction.CLIENT);
                        }
                    }
                }
            }
            this.setShouldSyncBetweenClients(false);
        }
    }

    public void setCanSpawnInAether(boolean canSpawnInAether) {
        this.canSpawnInAether = canSpawnInAether;
    }

    /**
     * @return Whether the player will spawn in the Aether dimension on first join, as a {@link Boolean}.
     */
    public boolean canSpawnInAether() {
        return this.canSpawnInAether;
    }

    /**
     * Gives the player an Aether Portal Frame item on login if the {@link AetherConfig.Common#start_with_portal} config is enabled.
     */
    private void handleGivePortal(Player player) {
        if (AetherConfig.COMMON.start_with_portal.get()) {
            this.givePortalItem(player);
        } else {
            this.setCanGetPortal(false);
        }
    }

    /**
     * Increments or decrements the Aether portal timer depending on if the player is inside an Aether portal.
     * On the client, this will also help to set the portal overlay.
     */
    private void handleAetherPortal(Player player) {
        if (player.level().isClientSide()) {
            this.prevPortalAnimTime = this.portalAnimTime;
            Minecraft minecraft = Minecraft.getInstance();
            if (this.isInAetherPortal) {
                if (minecraft.screen != null && !minecraft.screen.isPauseScreen()) {
                    if (minecraft.screen instanceof AbstractContainerScreen) {
                        player.closeContainer();
                    }
                    minecraft.setScreen(null);
                }

                if (this.getPortalAnimTime() == 0.0F) {
                    this.playPortalSound(minecraft, player);
                }
            }
        }

        if (this.isInPortal()) {
            ++this.aetherPortalTimer;
            if (player.level().isClientSide()) {
                this.portalAnimTime += 0.0125F;
                if (this.getPortalAnimTime() > 1.0F) {
                    this.portalAnimTime = 1.0F;
                }
            }
            this.isInAetherPortal = false;
        } else {
            if (player.level().isClientSide()) {
                if (this.getPortalAnimTime() > 0.0F) {
                    this.portalAnimTime -= 0.05F;
                }

                if (this.getPortalAnimTime() < 0.0F) {
                    this.portalAnimTime = 0.0F;
                }
            }
            if (this.getPortalTimer() > 0) {
                this.aetherPortalTimer -= 4;
            }
        }
    }

    /**
     * Plays the portal entry sound on the client.
     */
    @OnlyIn(Dist.CLIENT)
    private void playPortalSound(Minecraft minecraft, Player player) {
        minecraft.getSoundManager().play(PortalTriggerSoundInstance.forLocalAmbience(player, AetherSoundEvents.BLOCK_AETHER_PORTAL_TRIGGER.get(), player.getRandom().nextFloat() * 0.4F + 0.8F, 0.25F));
    }

    /**
     * Activates any parachute that the player has in their inventory when falling fast enough.
     * Checks for deployable parachutes from {@link AetherTags.Items#DEPLOYABLE_PARACHUTES}.
     */
    private void activateParachute(Player player) {
        Inventory inventory = player.getInventory();
        Level level = player.level();
        if (!player.isCreative() && !player.isShiftKeyDown() && !player.isFallFlying() && !player.isPassenger()) {
            if (player.getDeltaMovement().y() < -1.5) {
                if (inventory.contains(AetherTags.Items.DEPLOYABLE_PARACHUTES)) {
                    for (ItemStack stack : inventory.items) {
                        if (stack.getItem() instanceof ParachuteItem parachuteItem) {
                            Parachute parachute = parachuteItem.getParachuteEntity().get().create(level);
                            if (parachute != null) {
                                parachute.setPos(player.getX(), player.getY() - 1.0, player.getZ());
                                parachute.setDeltaMovement(player.getDeltaMovement());
                                if (!level.isClientSide()) {
                                    level.addFreshEntity(parachute);
                                    player.startRiding(parachute);
                                    stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                                }
                                parachute.spawnExplosionParticle();
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Slowly removes darts that are rendered as stuck on the player by {@link com.aetherteam.aether.client.renderer.player.layer.DartLayer}.
     */
    private void handleRemoveDarts(Player player) {
        if (!player.level().isClientSide()) {
            if (this.getGoldenDartCount() > 0) {
                if (this.removeGoldenDartTime <= 0) {
                    this.removeGoldenDartTime = 20 * (30 - this.getGoldenDartCount());
                }

                --this.removeGoldenDartTime;
                if (this.removeGoldenDartTime <= 0) {
                    this.setSynched(player.getId(), INBTSynchable.Direction.CLIENT, "setGoldenDartCount", this.getGoldenDartCount() - 1);
                }
            }
            if (this.getPoisonDartCount() > 0) {
                if (this.removePoisonDartTime <= 0) {
                    this.removePoisonDartTime = 20 * (30 - this.getPoisonDartCount());
                }

                --this.removePoisonDartTime;
                if (this.removePoisonDartTime <= 0) {
                    this.setSynched(player.getId(), INBTSynchable.Direction.CLIENT, "setPoisonDartCount", this.getPoisonDartCount() - 1);
                }
            }
            if (this.getEnchantedDartCount() > 0) {
                if (this.removeEnchantedDartTime <= 0) {
                    this.removeEnchantedDartTime = 20 * (30 - this.getEnchantedDartCount());
                }

                --this.removeEnchantedDartTime;
                if (this.removeEnchantedDartTime <= 0) {
                    this.setSynched(player.getId(), INBTSynchable.Direction.CLIENT, "setEnchantedDartCount", this.getEnchantedDartCount() - 1);
                }
            }
        }
    }

    private void removeRemedyDuration(Player player) {
        if (this.remedyStartDuration > 0) {
            if (!player.hasEffect(AetherEffects.REMEDY.get())) {
                this.remedyStartDuration = 0;
            }
        }
    }

    /**
     * Decreases the opacity of the Shield of Repulsion overlay vignette.
     */
    private void tickDownProjectileImpact(Player player) {
        if (player.level().isClientSide()) {
            if (this.getProjectileImpactedTimer() > 0) {
                this.setProjectileImpactedTimer(this.getProjectileImpactedTimer() - 1);
            } else {
                this.setProjectileImpactedMaximum(0);
                this.setProjectileImpactedTimer(0);
            }
        }
    }

    /**
     * Handles the rotation for the Valkyrie Armor wings layer renderer at {@link com.aetherteam.aether.client.renderer.player.layer.PlayerWingsLayer}.
     */
    private void handleWingRotation(Player player) {
        if (player.level().isClientSide()) {
            this.wingRotationO = this.getWingRotation();
            if (EquipmentUtil.hasFullValkyrieSet(player)) {
                this.wingRotation = player.tickCount;
            } else {
                this.wingRotation = 0;
            }
        }
    }

    /**
     * Decreases the attack cooldown after a player has attacked. This is used for when the player has attacked while wearing an Invisibility Cloak.
     */
    private void handleAttackCooldown(Player player) {
        if (!player.level().isClientSide()) {
            if (this.attackedWithInvisibility()) {
                --this.invisibilityAttackCooldown;
                if (this.invisibilityAttackCooldown <= 0) {
                    this.setSynched(player.getId(), INBTSynchable.Direction.CLIENT, "setAttackedWithInvisibility", false);
                }
            } else {
                this.invisibilityAttackCooldown = AetherConfig.SERVER.invisibility_visibility_time.get();
            }
        }
    }

    /**
     * Used for healing the player with a Vampire Blade. This method exists to get around a bug with heart rendering.
     */
    private void handleVampireHealing(Player player) {
        if (!player.level().isClientSide() && this.performVampireHealing()) {
            player.heal(1.0F);
            this.setVampireHealing(false);
        }
    }

    /**
     * Checks whether the capability should stop tracking a mounted Aerbunny.
     */
    private void checkToRemoveAerbunny(Player player) {
        if (this.getMountedAerbunny() != null && (!this.getMountedAerbunny().isAlive() || !player.isAlive())) {
            this.setMountedAerbunny(null);
        }
    }

    /**
     * Removes an Aerbunny from the world and stores it to NBT for the capability. This is used when a player logs out with an Aerbunny.
     */
    private void removeAerbunny() {
        if (this.getMountedAerbunny() != null) {
            Aerbunny aerbunny = this.getMountedAerbunny();
            CompoundTag nbt = new CompoundTag();
            aerbunny.save(nbt);
            this.setMountedAerbunnyTag(Optional.of(nbt));
            aerbunny.stopRiding();
            aerbunny.setRemoved(Entity.RemovalReason.UNLOADED_WITH_PLAYER);
        }
    }

    /**
     * Remounts an Aerbunny to the player if there exists stored NBT when joining the world.
     */
    private void remountAerbunny(Player player) {
        if (this.getMountedAerbunnyTag().isPresent()) {
            if (!player.level().isClientSide()) {
                Aerbunny aerbunny = new Aerbunny(AetherEntityTypes.AERBUNNY.get(), player.level());
                aerbunny.load(this.getMountedAerbunnyTag().get());
                player.level().addFreshEntity(aerbunny);
                aerbunny.startRiding(player);
                this.setMountedAerbunny(aerbunny);
                if (player instanceof ServerPlayer serverPlayer) {
                    PacketRelay.sendToPlayer(new RemountAerbunnyPacket(player.getId(), aerbunny.getId()), serverPlayer);
                }
            }
            this.setMountedAerbunnyTag(null);
        }
    }

    /**
     * Checks whether the capability should stop tracking Cloud Minions.
     */
    private void checkToRemoveCloudMinions() {
        this.getCloudMinions().removeIf(cloudMinion -> !cloudMinion.isAlive());
    }

    /**
     * Used when capability data is copied. This restores any extra health the players had from Life Shards before the copy occurred.
     */
    private void handleSavedHealth(Player player) {
        if (this.getSavedHealth() > 0.0F) {
            AttributeInstance health = player.getAttribute(Attributes.MAX_HEALTH);
            if (health != null && health.hasModifier(this.getLifeShardHealthAttributeModifier())) {
                player.setHealth(Math.min(this.getSavedHealth(), player.getMaxHealth()));
                this.setSavedHealth(0.0F);
            }
        }
    }

    /**
     * Sets up the attribute modifier for extra Life Shard hearts.
     */
    private void handleLifeShardModifier(Player player) {
        if (!player.level().isClientSide()) {
            AttributeInstance health = player.getAttribute(Attributes.MAX_HEALTH);
            AttributeModifier lifeShardHealth = this.getLifeShardHealthAttributeModifier();
            if (health != null) {
                if (health.hasModifier(lifeShardHealth)) {
                    health.removeModifier(lifeShardHealth.getId());
                }
                health.addTransientModifier(lifeShardHealth);
            }
        }
    }

    /**
     * Sets the player's extra saved health on logout.
     */
    private void handleLogoutSavedHealth(Player player) {
        AttributeInstance health = player.getAttribute(Attributes.MAX_HEALTH);
        if (health != null && health.hasModifier(this.getLifeShardHealthAttributeModifier())) {
            this.setSavedHealth(player.getHealth());
        }
    }

    /**
     * Sends a message linking the mod's Patreon and Discord, as long as the {@link AetherConfig.Common#show_patreon_message} config is not disabled.
     * The message will only be able to show up 1 or 2 logins after the player has defeated a boss, and after it sends it will not be able to show up again on any world.
     */
    private void handlePatreonMessage(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            if (AetherConfig.COMMON.show_patreon_message.get() && this.canShowPatreonMessage) {
                if (this.loginsUntilPatreonMessage < 0) {
                    if (serverPlayer.level().dimension() == AetherDimensions.AETHER_LEVEL
                        && (serverPlayer.getStats().getValue(Stats.ENTITY_KILLED.get(AetherEntityTypes.SLIDER.get())) > 0
                        || serverPlayer.getStats().getValue(Stats.ENTITY_KILLED.get(AetherEntityTypes.VALKYRIE_QUEEN.get())) > 0
                        || serverPlayer.getStats().getValue(Stats.ENTITY_KILLED.get(AetherEntityTypes.SUN_SPIRIT.get())) > 0)) {
                        this.loginsUntilPatreonMessage = serverPlayer.getRandom().nextInt(2);
                    }
                }
                if (this.loginsUntilPatreonMessage == 0) {
                    this.sendPatreonMessage(serverPlayer);
                    this.canShowPatreonMessage = false;
                    AetherConfig.COMMON.show_patreon_message.set(false);
                    AetherConfig.COMMON.show_patreon_message.save();
                } else if (this.loginsUntilPatreonMessage > 0) {
                    --this.loginsUntilPatreonMessage;
                }
            } else if (!AetherConfig.COMMON.show_patreon_message.get()) {
                this.canShowPatreonMessage = false;
            }
        }
    }

    /**
     * Handles the component setup for the Patreon message.
     *
     * @param serverPlayer The {@link ServerPlayer} to send the message to.
     */
    private void sendPatreonMessage(ServerPlayer serverPlayer) {
        Component component = Component.translatable("gui.aether.patreon.message");
        List<String> unlinkedBodyArray = Arrays.stream(component.getString().split("(?=(%s1))|(?<=(%s1))|(?=(%s2))|(?<=(%s2))|(?=(%s3))|(?<=(%s3))")).toList();
        List<MutableComponent> bodyArray = unlinkedBodyArray.stream().map((string) ->
            switch (string) {
                case "%s1" -> Component.literal("The Aether").setStyle(Style.EMPTY.withColor(8445183).withItalic(true));
                case "%s2" ->
                    Component.literal("").append(Component.literal("! ").setStyle(DISCORD.withFont(LOGOMARKS))).append(Component.literal("Discord").setStyle(DISCORD));
                case "%s3" ->
                    Component.literal("").append(Component.literal(", ").setStyle(PATREON.withFont(LOGOMARKS))).append(Component.literal("Patreon").setStyle(PATREON));
                default -> Component.literal(string);
            }).toList();
        MutableComponent message = Component.literal("");
        bodyArray.forEach(message::append);
        serverPlayer.sendSystemMessage(message);
        Component note = Component.translatable("gui.aether.patreon.note").setStyle(Style.EMPTY.withColor(7631988).withItalic(true));
        serverPlayer.sendSystemMessage(note);
    }

    /**
     * Gives the player an Aether Portal Frame item.
     */
    public void givePortalItem(Player player) {
        if (this.canGetPortal()) {
            player.addItem(new ItemStack(AetherItems.AETHER_PORTAL_FRAME.get()));
            this.setCanGetPortal(false);
        }
    }

    public void setCanGetPortal(boolean canGetPortal) {
        this.canGetPortal = canGetPortal;
    }

    /**
     * @return Whether the player can get the Aether Portal Frame item, as a {@link Boolean}.
     */
    public boolean canGetPortal() {
        return this.canGetPortal;
    }

    public void setInPortal(boolean inPortal) {
        this.isInAetherPortal = inPortal;
    }

    /**
     * @return Whether the player is in an Aether Portal, as a {@link Boolean}.
     */
    public boolean isInPortal() {
        return this.isInAetherPortal;
    }

    public void setPortalTimer(int timer) {
        this.aetherPortalTimer = timer;
    }

    /**
     * @return The {@link Integer} timer for how long the player has stood in a portal.
     */
    public int getPortalTimer() {
        return this.aetherPortalTimer;
    }

    /**
     * @return The {@link Float} timer for the portal vignette animation time.
     */
    public float getPortalAnimTime() {
        return this.portalAnimTime;
    }

    /**
     * @return The previous {@link Float} for the portal animation timer.
     */
    public float getPrevPortalAnimTime() {
        return this.prevPortalAnimTime;
    }

    public void setHitting(boolean isHitting) {
        this.isHitting = isHitting;
    }

    /**
     * @return Whether the player is hitting, as a {@link Boolean}.
     */
    public boolean isHitting() {
        return this.isHitting;
    }

    public void setMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    /**
     * @return Whether the player is moving, as a {@link Boolean}.
     */
    public boolean isMoving() {
        return this.isMoving;
    }

    public void setJumping(boolean isJumping) {
        this.isJumping = isJumping;
    }

    /**
     * @return Whether the player is jumping, as a {@link Boolean}.
     */
    public boolean isJumping() {
        return this.isJumping;
    }

    public void setGravititeJumpActive(boolean isGravititeJumpActive) {
        this.isGravititeJumpActive = isGravititeJumpActive;
    }

    /**
     * @return Whether the gravitite jump ability is active, as a {@link Boolean}.
     */
    public boolean isGravititeJumpActive() {
        return this.isGravititeJumpActive;
    }

    public void setSeenSunSpiritDialogue(boolean seenDialogue) {
        this.seenSunSpiritDialogue = seenDialogue;
    }

    /**
     * @return Whether the player has already seen the Sun Spirit's dialogue, as a {@link Boolean}.
     */
    public boolean hasSeenSunSpiritDialogue() {
        return this.seenSunSpiritDialogue;
    }

    public void setGoldenDartCount(int count) {
        this.goldenDartCount = count;
    }

    /**
     * @return An {@link Integer} for how many Golden Darts are stuck in the player.
     */
    public int getGoldenDartCount() {
        return this.goldenDartCount;
    }

    public void setPoisonDartCount(int count) {
        this.poisonDartCount = count;
    }

    /**
     * @return An {@link Integer} for how many Poison Darts are stuck in the player.
     */
    public int getPoisonDartCount() {
        return this.poisonDartCount;
    }

    public void setEnchantedDartCount(int count) {
        this.enchantedDartCount = count;
    }

    /**
     * @return An {@link Integer} for how many Enchanted Darts are stuck in the player.
     */
    public int getEnchantedDartCount() {
        return this.enchantedDartCount;
    }

    public void setRemedyStartDuration(int duration) {
        this.remedyStartDuration = duration;
    }

    /**
     * @return An {@link Integer} for the original time duration of the remedy effect.
     */
    public int getRemedyStartDuration() {
        return this.remedyStartDuration;
    }

    public void setProjectileImpactedMaximum(int projectileImpactedMaximum) {
        this.impactedMaximum = projectileImpactedMaximum;
    }

    /**
     * @return An {@link Integer} for the max time duration of the Shield of Repulsion vignette.
     */
    public int getProjectileImpactedMaximum() {
        return this.impactedMaximum;
    }

    public void setProjectileImpactedTimer(int projectileImpactedTimer) {
        this.impactedTimer = projectileImpactedTimer;
    }

    /**
     * @return The {@link Integer} timer for how long until the Shield of Repulsion vignette disappears.
     */
    public int getProjectileImpactedTimer() {
        return this.impactedTimer;
    }

    public void setVampireHealing(boolean performVampireHealing) {
        this.performVampireHealing = performVampireHealing;
    }

    /**
     * @return Whether to heal from attacking with the Vampire Blade, as a {@link Boolean}.
     */
    public boolean performVampireHealing() {
        return this.performVampireHealing;
    }

    public void setMountedAerbunny(@Nullable Aerbunny mountedAerbunny) {
        this.mountedAerbunny = mountedAerbunny;
    }

    /**
     * @return The {@link Aerbunny} currently mounted to the player
     */
    @Nullable
    public Aerbunny getMountedAerbunny() {
        return this.mountedAerbunny;
    }

    public void setMountedAerbunnyTag(Optional<CompoundTag> mountedAerbunnyTag) {
        this.mountedAerbunnyTag = mountedAerbunnyTag;
    }

    /**
     * @return The {@link CompoundTag} data for the Aerbunny currently mounted to the player.
     */
    public Optional<CompoundTag> getMountedAerbunnyTag() {
        return this.mountedAerbunnyTag;
    }

    public void setLastRiddenMoa(@Nullable UUID lastRiddenMoa) {
        this.lastRiddenMoa = Optional.ofNullable(lastRiddenMoa);
    }

    /**
     * @return The {@link UUID} for the last ridden Moa.
     */
    @Nullable
    public UUID getLastRiddenMoa() {
        return this.lastRiddenMoa.orElse(null);
    }

    public void setCloudMinions(Player player, CloudMinion cloudMinionRight, CloudMinion cloudMinionLeft) {
        this.sendCloudMinionPacket(player, cloudMinionRight, cloudMinionLeft);
        this.cloudMinions.add(0, cloudMinionRight);
        this.cloudMinions.add(1, cloudMinionLeft);
    }

    /**
     * @return The currently summoned {@link CloudMinion}s.
     */
    public List<CloudMinion> getCloudMinions() {
        return this.cloudMinions;
    }

    /**
     * @return An {@link Integer} for the last wing rotation value.
     */
    public int getWingRotationO() {
        return this.wingRotationO;
    }


    /**
     * @return An {@link Integer} for the wing rotation value.
     */
    public int getWingRotation() {
        return this.wingRotation;
    }

    public void setAttackedWithInvisibility(boolean attacked) {
        this.attackedWithInvisibility = attacked;
    }

    /**
     * @return Whether the player attacked, as a {@link Boolean}.
     */
    public boolean attackedWithInvisibility() {
        return this.attackedWithInvisibility;
    }

    public void setInvisibilityEnabled(boolean enabled) {
        this.invisibilityEnabled = enabled;
    }

    /**
     * @return Whether the player's invisibility is enabled when wearing an Invisibility Cloak, as a {@link Boolean}.
     */
    public boolean isInvisibilityEnabled() {
        return this.invisibilityEnabled;
    }

    public void setWearingInvisibilityCloak(boolean wearing) {
        this.wearingInvisibilityCloak = wearing;
    }

    /**
     * @return Whether the player is wearing an Invisibility Cloak.
     */
    public boolean isWearingInvisibilityCloak() {
        return this.wearingInvisibilityCloak;
    }

    /**
     * @return An {@link Integer} for the maximum flight duration.
     */
    public int getFlightTimerMax() {
        return FLIGHT_TIMER_MAX;
    }

    /**
     * @return A {@link Float} for the maximum flight speed modifier.
     */
    public float getFlightModifierMax() {
        return FLIGHT_MODIFIER_MAX;
    }

    public void setFlightTimer(int timer) {
        this.flightTimer = timer;
    }

    /**
     * @return The {@link Integer} timer for how long the player has to fly.
     */
    public int getFlightTimer() {
        return this.flightTimer;
    }

    public void setFlightModifier(float modifier) {
        this.flightModifier = modifier;
    }

    /**
     * @return The {@link Float} modifier for the player's flight speed.
     */
    public float getFlightModifier() {
        return this.flightModifier;
    }

    public void setSavedHealth(float health) {
        this.savedHealth = health;
    }

    /**
     * @return The {@link Float} for player health stored between capability copying.
     */
    public float getSavedHealth() {
        return this.savedHealth;
    }

    public void setNeptuneSubmergeLength(double length) {
        this.neptuneSubmergeLength = length;
    }

    /**
     * @return A {@link Double} for how long the player has been submerged in water while wearing Neptune Armor.
     */
    public double getNeptuneSubmergeLength() {
        return this.neptuneSubmergeLength;
    }

    public void setPhoenixSubmergeLength(double length) {
        this.phoenixSubmergeLength = length;
    }

    /**
     * @return A {@link Double} for how long the player has been submerged in lava while wearing Phoenix Armor.
     */
    public double getPhoenixSubmergeLength() {
        return this.phoenixSubmergeLength;
    }

    /**
     * @return An {@link Integer} for how long Phoenix Armor takes to convert into Obsidian Armor.
     */
    public int getObsidianConversionTimerMax() {
        return OBSIDIAN_TIMER_MAX;
    }

    public void setObsidianConversionTime(int time) {
        this.obsidianConversionTime = time;
    }

    /**
     * @return An {@link Integer} timer for how long until Phoenix Armor converts into Obsidian Armor.
     */
    public int getObsidianConversionTime() {
        return this.obsidianConversionTime;
    }

    public void setLifeShardCount(int amount) {
        this.lifeShards = amount;
    }

    /**
     * @return An {@link Integer} for how many Life Shards the player has used.
     */
    public int getLifeShardCount() {
        return this.lifeShards;
    }

    /**
     * @return An {@link Integer} for the maximum amount of Life Shards the player can use, from {@link AetherConfig.Server#maximum_life_shards}.
     */
    public int getLifeShardLimit() {
        return AetherConfig.SERVER.maximum_life_shards.get();
    }

    /**
     * @return The Life Shard health {@link AttributeModifier}.
     */
    public AttributeModifier getLifeShardHealthAttributeModifier() {
        return new AttributeModifier(LIFE_SHARD_HEALTH_ID, "Life Shard health increase", this.getLifeShardCount() * 2.0F, AttributeModifier.Operation.ADDITION);
    }

    /**
     * Tracks the player's summoned Cloud Minions on the client.
     *
     * @param cloudMinionRight The right {@link CloudMinion}.
     * @param cloudMinionLeft  The left {@link CloudMinion}.
     */
    private void sendCloudMinionPacket(Player player, CloudMinion cloudMinionRight, CloudMinion cloudMinionLeft) {
        if (player instanceof ServerPlayer serverPlayer && !player.level().isClientSide) {
            PacketRelay.sendToPlayer(new CloudMinionPacket(player.getId(), cloudMinionRight.getId(), cloudMinionLeft.getId()), serverPlayer);
        }
    }

    /**
     * @return Whether the capability should sync server values to nearby clients.
     */
    private boolean shouldSyncBetweenClients() {
        return this.shouldSyncBetweenClients;
    }

    private void setShouldSyncBetweenClients(boolean shouldSyncBetweenClients) {
        this.shouldSyncBetweenClients = shouldSyncBetweenClients;
    }

    @Override
    public BasePacket getSyncPacket(int entityID, String key, Type type, Object value) {
        return new AetherPlayerSyncPacket(entityID, key, type, value);
    }
}
