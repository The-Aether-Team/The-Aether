package com.aetherteam.aether.integration.jade;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.dungeon.DoorwayBlock;
import com.aetherteam.aether.block.dungeon.TrappedBlock;
import com.aetherteam.aether.block.dungeon.TreasureDoorwayBlock;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.registries.ForgeRegistries;
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
			if (player.isCreative() || player.isSpectator())
				return accessor;
			IWailaClientRegistration client = VanillaPlugin.CLIENT_REGISTRATION;
			//trapped dungeon blocks show up as their normal dungeon blocks
			if (target.getBlock() instanceof TrappedBlock trapped) {
				return client.blockAccessor().from(target).blockState(trapped.getFacadeBlock()).build();
			//both doorways show up as locked dungeon blocks, since you won't see them if a dungeon is completed anyway
			} else if (target.getBlock() instanceof DoorwayBlock door) {
				return client.blockAccessor().from(target).blockState(this.getLockedDungeonBlock(ForgeRegistries.BLOCKS.getKey(door).getPath()).defaultBlockState()).build();
			} else if (target.getBlock() instanceof TreasureDoorwayBlock door) {
				return client.blockAccessor().from(target).blockState(this.getLockedDungeonBlock(ForgeRegistries.BLOCKS.getKey(door).getPath()).defaultBlockState()).build();
			//mimics show up as normal chests. There's not a single way to tell the difference between these and normal chests from the tooltip.
			} else if (target.getBlock() == AetherBlocks.CHEST_MIMIC.get()) {
				return client.blockAccessor().from(target).serverData(this.createFakeChestData()).blockState(Blocks.CHEST.defaultBlockState()).build();
			}
		}
		return accessor;
	}

	//adds the "inventory not generated" text to the mimic's tooltip
	private CompoundTag createFakeChestData() {
		CompoundTag tag = new CompoundTag();
		tag.putBoolean("Loot", true);
		return tag;
	}

	//converts doorway blocks to their appropriate locked blocks
	@Nullable
	private Block getLockedDungeonBlock(String name) {
		if (name.startsWith("boss_doorway_")) {
			return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(Aether.MODID, "locked_" + name.substring(13)));
		} else if (name.startsWith("treasure_doorway_")) {
			return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(Aether.MODID, "locked_" + name.substring(17)));
		}
		return null;
	}
}
