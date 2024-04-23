package com.aetherteam.aether.integration.jade;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.dungeon.DoorwayBlock;
import com.aetherteam.aether.block.dungeon.TrappedBlock;
import com.aetherteam.aether.block.dungeon.TreasureDoorwayBlock;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.HitResult;
import net.zestyblaze.lootr.registry.LootrBlockInit;
import org.jetbrains.annotations.Nullable;
import snownee.jade.addon.vanilla.VanillaPlugin;
import snownee.jade.api.*;

@WailaPlugin
public class AetherJadePlugin implements IWailaPlugin {
	@Override
	public void registerClient(IWailaClientRegistration registration) {
		registration.addRayTraceCallback(this::registerAetherOverrides);
	}

	@Nullable
	public Accessor<?> registerAetherOverrides(HitResult hitResult, @Nullable Accessor<?> accessor, @Nullable Accessor<?> originalAccessor) {
		if (accessor instanceof BlockAccessor target) {
			Player player = accessor.getPlayer();
			if (player.isCreative() || player.isSpectator()) {
				return accessor;
			}
			IWailaClientRegistration client = VanillaPlugin.CLIENT_REGISTRATION;
			if (target.getBlock() instanceof TrappedBlock trapped) { // Trapped dungeon blocks show up as their normal dungeon blocks
				return client.blockAccessor().from(target).blockState(trapped.getFacadeBlock()).build();
			} else if (target.getBlock() instanceof DoorwayBlock door) { // Both doorways show up as locked dungeon blocks, since you won't see them if a dungeon is completed anyway
				ResourceLocation doorLocation = BuiltInRegistries.BLOCK.getKey(door);
				if (doorLocation != null) {
					Block doorBlock = this.getLockedDungeonBlock(doorLocation.getPath());
					if (doorBlock != null) {
						return client.blockAccessor().from(target).blockState(doorBlock.defaultBlockState()).build();
					}
				}
			} else if (target.getBlock() instanceof TreasureDoorwayBlock door) {
				ResourceLocation doorLocation = BuiltInRegistries.BLOCK.getKey(door);
				if (doorLocation != null) {
					Block doorBlock = this.getLockedDungeonBlock(doorLocation.getPath());
					if (doorBlock != null) {
						return client.blockAccessor().from(target).blockState(doorBlock.defaultBlockState()).build();
					}
				}
			} else if (target.getBlock() == AetherBlocks.CHEST_MIMIC.get()) { // Mimics show up as normal chests. There's not a single way to tell the difference between these and normal chests from the tooltip.
				if (FabricLoader.getInstance().isModLoaded("lootr")) { // Disguise as Lootr Loot Chest
					return client.blockAccessor().from(target).serverData(this.createFakeChestData(target)).blockState(LootrBlockInit.CHEST.defaultBlockState()).build();
				} else {
					return client.blockAccessor().from(target).serverData(this.createFakeChestData(target)).blockState(Blocks.CHEST.defaultBlockState()).build();
				}
			}
		}
		return accessor;
	}

	/**
	 * Adds the "inventory not generated" text to the mimic's tooltip
	 */
	private CompoundTag createFakeChestData(BlockAccessor target) {
		CompoundTag tag = new CompoundTag();
		if (!target.getServerData().isEmpty()) {
			tag.putBoolean("Loot", true);
		}
		return tag;
	}

	/**
	 * Converts doorway blocks to their appropriate locked blocks
	 */
	@Nullable
	private Block getLockedDungeonBlock(String name) {
		if (name.startsWith("boss_doorway_")) {
			return BuiltInRegistries.BLOCK.get(new ResourceLocation(Aether.MODID, "locked_" + name.substring(13)));
		} else if (name.startsWith("treasure_doorway_")) {
			return BuiltInRegistries.BLOCK.get(new ResourceLocation(Aether.MODID, "locked_" + name.substring(17)));
		}
		return null;
	}
}
