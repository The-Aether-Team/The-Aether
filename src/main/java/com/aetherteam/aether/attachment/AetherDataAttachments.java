package com.aetherteam.aether.attachment;

import com.aetherteam.aether.Aether;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;


public class AetherDataAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Aether.MODID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<AetherPlayerAttachment>> AETHER_PLAYER = ATTACHMENTS.register("aether_player", () -> AttachmentType.builder(AetherPlayerAttachment::new).serialize(AetherPlayerAttachment.CODEC).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<MobAccessoryAttachment>> MOB_ACCESSORY = ATTACHMENTS.register("mob_accessory", () -> AttachmentType.builder(MobAccessoryAttachment::new).serialize(MobAccessoryAttachment.CODEC).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<PhoenixArrowAttachment>> PHOENIX_ARROW = ATTACHMENTS.register("phoenix_arrow", () -> AttachmentType.builder(PhoenixArrowAttachment::new).serialize(PhoenixArrowAttachment.CODEC).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<LightningTrackerAttachment>> LIGHTNING_TRACKER = ATTACHMENTS.register("lightning_tracker", () -> AttachmentType.builder(LightningTrackerAttachment::new).serialize(LightningTrackerAttachment.CODEC).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<DroppedItemAttachment>> DROPPED_ITEM = ATTACHMENTS.register("dropped_item", () -> AttachmentType.builder(DroppedItemAttachment::new).serialize(DroppedItemAttachment.CODEC).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<AetherTimeAttachment>> AETHER_TIME = ATTACHMENTS.register("aether_time", () -> AttachmentType.builder(AetherTimeAttachment::new).serialize(AetherTimeAttachment.CODEC).build());
}
