package com.aetherteam.aether.perk.data;

import com.aetherteam.aether.perk.types.DeveloperGlow;
import com.aetherteam.aether.perk.types.Halo;
import com.aetherteam.aether.perk.types.MoaData;
import com.aetherteam.aether.perk.types.MoaSkins;
import com.google.common.collect.ImmutableMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PerkSavedData extends SavedData {
    public static final String FILE_NAME = "perks";
    private final Map<UUID, MoaData> storedSkinData = new HashMap<>();
    private final Map<UUID, Halo> storedHaloData = new HashMap<>();
    private final Map<UUID, DeveloperGlow> storedDeveloperGlowData = new HashMap<>();

    @Override
    public CompoundTag save(CompoundTag tag) {
        CompoundTag storedSkinDataTag = new CompoundTag();
        for (Map.Entry<UUID, MoaData> moaDataEntry : this.storedSkinData.entrySet()) {
            CompoundTag moaDataEntryTag = new CompoundTag();
            if (moaDataEntry.getValue().moaUUID() != null) {
                moaDataEntryTag.putUUID("MoaUUID", moaDataEntry.getValue().moaUUID());
            }
            if (moaDataEntry.getValue().moaSkin() != null) {
                moaDataEntryTag.putString("MoaSkin", moaDataEntry.getValue().moaSkin().getId());
            }
            storedSkinDataTag.put(moaDataEntry.getKey().toString(), moaDataEntryTag);
        }
        tag.put("StoredSkinData", storedSkinDataTag);

        CompoundTag storedHaloDataTag = new CompoundTag();
        for (Map.Entry<UUID, Halo> haloEntry : this.storedHaloData.entrySet()) {
            CompoundTag haloEntryTag = new CompoundTag();
            if (haloEntry.getValue().hexColor() != null) {
                haloEntryTag.putString("HexColor", haloEntry.getValue().hexColor());
            }
            storedHaloDataTag.put(haloEntry.getKey().toString(), haloEntryTag);
        }
        tag.put("StoredHaloData", storedHaloDataTag);

        CompoundTag storedDeveloperGlowDataTag = new CompoundTag();
        for (Map.Entry<UUID, DeveloperGlow> developerGlowEntry : this.storedDeveloperGlowData.entrySet()) {
            CompoundTag developerGlowTag = new CompoundTag();
            if (developerGlowEntry.getValue().hexColor() != null) {
                developerGlowTag.putString("HexColor", developerGlowEntry.getValue().hexColor());
            }
            storedDeveloperGlowDataTag.put(developerGlowEntry.getKey().toString(), developerGlowTag);
        }
        tag.put("StoredDeveloperGlowData", storedDeveloperGlowDataTag);

        return tag;
    }

    public static PerkSavedData load(CompoundTag tag) {
        PerkSavedData data = PerkSavedData.create();
        for (String key : tag.getAllKeys()) {
            if (key.equals("StoredSkinData")) {
                CompoundTag storedSkinDataTag = tag.getCompound(key);
                for (String storedSkinDataKey : storedSkinDataTag.getAllKeys()) {
                    CompoundTag moaDataEntryTag = storedSkinDataTag.getCompound(storedSkinDataKey);
                    UUID playerUUID = UUID.fromString(storedSkinDataKey);
                    UUID moaUUID = null;
                    String moaSkinId = null;
                    if (moaDataEntryTag.contains("MoaUUID")) {
                        moaUUID = moaDataEntryTag.getUUID("MoaUUID");
                    }
                    if (moaDataEntryTag.contains("MoaSkin")) {
                        moaSkinId = moaDataEntryTag.getString("MoaSkin");
                    }
                    MoaSkins.MoaSkin moaSkin = MoaSkins.getMoaSkins().get(moaSkinId);
                    data.storedSkinData.put(playerUUID, new MoaData(moaUUID, moaSkin));
                }
            }
            if (key.equals("StoredHaloData")) {
                CompoundTag storedHaloDataTag = tag.getCompound(key);
                for (String storedHaloDataKey : storedHaloDataTag.getAllKeys()) {
                    CompoundTag haloEntryTag = storedHaloDataTag.getCompound(storedHaloDataKey);
                    UUID playerUUID = UUID.fromString(storedHaloDataKey);
                    String hexColor = null;
                    if (haloEntryTag.contains("HexColor")) {
                        hexColor = haloEntryTag.getString("HexColor");
                    }
                    data.storedHaloData.put(playerUUID, new Halo(hexColor));
                }
            }
            if (key.equals("StoredDeveloperGlowData")) {
                CompoundTag storedDeveloperGlowDataTag = tag.getCompound(key);
                for (String storedDeveloperGlowDataKey : storedDeveloperGlowDataTag.getAllKeys()) {
                    CompoundTag developerGlowEntryTag = storedDeveloperGlowDataTag.getCompound(storedDeveloperGlowDataKey);
                    UUID playerUUID = UUID.fromString(storedDeveloperGlowDataKey);
                    String hexColor = null;
                    if (developerGlowEntryTag.contains("HexColor")) {
                        hexColor = developerGlowEntryTag.getString("HexColor");
                    }
                    data.storedDeveloperGlowData.put(playerUUID, new DeveloperGlow(hexColor));
                }
            }
        }
        return data;
    }

    public static PerkSavedData create() {
        return new PerkSavedData();
    }

    public static PerkSavedData compute(DimensionDataStorage dataStorage) {
       return dataStorage.computeIfAbsent(PerkSavedData::load, PerkSavedData::create, FILE_NAME);
    }

    Map<UUID, MoaData> getStoredSkinData() {
        return ImmutableMap.copyOf(this.storedSkinData);
    }

    void modifyStoredSkinData(UUID uuid, MoaData moaData) {
        this.storedSkinData.put(uuid, moaData);
        this.setDirty();
    }

    void removeStoredSkinData(UUID uuid) {
        this.storedSkinData.remove(uuid);
        this.setDirty();
    }

    Map<UUID, Halo> getStoredHaloData() {
        return ImmutableMap.copyOf(this.storedHaloData);
    }

    void modifyStoredHaloData(UUID uuid, Halo halo) {
        this.storedHaloData.put(uuid, halo);
        this.setDirty();
    }

    void removeStoredHaloData(UUID uuid) {
        this.storedHaloData.remove(uuid);
        this.setDirty();
    }

    Map<UUID, DeveloperGlow> getStoredDeveloperGlowData() {
        return ImmutableMap.copyOf(this.storedDeveloperGlowData);
    }

    void modifyStoredDeveloperGlowData(UUID uuid, DeveloperGlow developerGlow) {
        this.storedDeveloperGlowData.put(uuid, developerGlow);
        this.setDirty();
    }

    void removeStoredDeveloperGlowData(UUID uuid) {
        this.storedDeveloperGlowData.remove(uuid);
        this.setDirty();
    }
}
