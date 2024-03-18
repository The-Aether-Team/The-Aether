package com.aetherteam.aether.event;

import com.google.common.base.Preconditions;
import io.github.fabricators_of_create.porting_lib.core.event.BaseEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * PlacementBanEvent is fired when an event involving placement banning occurs.<br>
 * If a method utilizes this {@link BaseEvent} as its parameter, the method will receive every child event of this class.<br>
 * <br>
 */
public class PlacementBanEvent extends BaseEvent {
	public static final Event<PlacementBanCallback> PLACEMENT_BAN = EventFactory.createWithPhases(PlacementBanCallback.class, callbacks -> event -> {
		for (PlacementBanCallback e : callbacks)
			e.onPlacementBan(event);
	}, AetherEvents.LOWEST, Event.DEFAULT_PHASE);
	public static final Event<PlacementBanCheckItemCallback> CHECK_ITEM = EventFactory.createWithPhases(PlacementBanCheckItemCallback.class, callbacks -> event -> {
		for (PlacementBanCheckItemCallback e : callbacks)
			e.onPlacementBanCheckItem(event);
	}, AetherEvents.LOWEST, Event.DEFAULT_PHASE);
	public static final Event<PlacementBanCheckBlockCallback> CHECK_BLOCK = EventFactory.createWithPhases(PlacementBanCheckBlockCallback.class, callbacks -> event -> {
		for (PlacementBanCheckBlockCallback e : callbacks)
			e.onPlacementBanCheckBlock(event);
	}, AetherEvents.LOWEST, Event.DEFAULT_PHASE);
	public static final Event<PlacementBanParticlesCallback> SPAWN_PARTICLES = EventFactory.createWithPhases(PlacementBanParticlesCallback.class, callbacks -> event -> {
		for (PlacementBanParticlesCallback e : callbacks)
			e.onPlacementBanSpawnParticles(event);
	}, AetherEvents.LOWEST, Event.DEFAULT_PHASE);

	@Override
	public void sendEvent() {
		PLACEMENT_BAN.invoker().onPlacementBan(this);
	}

	/**
	 * PlacementBanEvent.CheckItem is fired after an item that can be banned is used, but before its placement has been prevented.
	 * <br>
	 * This event is not cancelable. <br>
	 * <br>
	 * This event does not have a result. <br>
	 * <br>
	 * This event is fired on both {@link EnvType sides}.
	 */
	public static class CheckItem extends PlacementBanEvent {
		private boolean banned = true;
		private final LevelAccessor level;
		private final BlockPos pos;
		private final ItemStack itemStack;

		/**
		 * @param level The {@link LevelAccessor} that the ban occurs in.
		 * @param pos The {@link BlockPos} the ban occurs at.
		 * @param itemStack The {@link ItemStack} to be banned.
		 */
		public CheckItem(LevelAccessor level, BlockPos pos, ItemStack itemStack) {
			this.level = Preconditions.checkNotNull(level, "Null world in PlacementBanEvent");
			this.pos = Preconditions.checkNotNull(pos, "Null position in PlacementBanEvent");
			this.itemStack = Preconditions.checkNotNull(itemStack, "Null itemStack in PlacementBanEvent");
		}

		/**
		 * @return The {@link LevelAccessor} that the ban occurs in.
		 */
		public LevelAccessor getLevel() {
			return this.level;
		}

		/**
		 * @return The {@link BlockPos} the ban occurs at.
		 */
		public BlockPos getPos() {
			return this.pos;
		}

		/**
		 * @return The {@link ItemStack} to be banned.
		 */
		public ItemStack getItemStack() {
			return this.itemStack;
		}

		/**
		 * @return Whether the item placement is banned, as a {@link Boolean}.
		 */
		public boolean isBanned() {
			return this.banned;
		}

		/**
		 * Sets whether the item placement is banned.
		 * @param banned The ban value as a {@link Boolean}.
		 */
		public void setBanned(boolean banned) {
			this.banned = banned;
		}

		@Override
		public void sendEvent() {
			CHECK_ITEM.invoker().onPlacementBanCheckItem(this);
		}
	}

	/**
	 * PlacementBanEvent.CheckItem is fired after a block that can be banned is placed, but before its placement has been prevented.
	 * <br>
	 * This event is not cancelable. <br>
	 * <br>
	 * This event does not have a result.<br>
	 * <br>
	 * This event is only fired on the {@link EnvType#SERVER} side.
	 */
	public static class CheckBlock extends PlacementBanEvent {
		private boolean banned = true;
		private final LevelAccessor level;
		private final BlockPos pos;
		private final BlockState blockState;

		/**
		 * @param level The {@link LevelAccessor} that the ban occurs in.
		 * @param pos The {@link BlockPos} the ban occurs at.
		 * @param blockState The {@link BlockState} to be banned.
		 */
		public CheckBlock(LevelAccessor level, BlockPos pos, BlockState blockState) {
			this.level = Preconditions.checkNotNull(level, "Null world in PlacementBanEvent");
			this.pos = Preconditions.checkNotNull(pos, "Null position in PlacementBanEvent");
			this.blockState = Preconditions.checkNotNull(blockState, "Null blockState in PlacementBanEvent");
		}

		/**
		 * @return The {@link LevelAccessor} that the ban occurs in.
		 */
		public LevelAccessor getLevel() {
			return this.level;
		}

		/**
		 * @return The {@link BlockPos} the ban occurs at.
		 */
		public BlockPos getPos() {
			return this.pos;
		}

		/**
		 * @return The {@link BlockState} to be banned.
		 */
		public BlockState getBlockState() {
			return this.blockState;
		}

		/**
		 * @return Whether the block placement is banned, as a {@link Boolean}.
		 */
		public boolean isBanned() {
			return this.banned;
		}

		/**
		 * Sets whether the block placement is banned.
		 * @param banned The ban value as a {@link Boolean}.
		 */
		public void setBanned(boolean banned) {
			this.banned = banned;
		}

		@Override
		public void sendEvent() {
			CHECK_BLOCK.invoker().onPlacementBanCheckBlock(this);
		}
	}

	/**
	 * PlacementBanEvent.SpawnParticles is fired after a placement ban has occurred.
	 * <br>
	 * This event is cancelable.<br>
	 * If the event is not canceled, the particles will spawn.
	 * <br>
	 * This event does not have a result.<br>
	 * <br>
	 * This event is fired on both {@link EnvType sides}.<br>
	 * <br>
	 * If this event is canceled, the particles will not be spawned.
	 */
	public static class SpawnParticles extends PlacementBanEvent {
		private final LevelAccessor level;
		private final BlockPos pos;
		@Nullable
		private final Direction face;
		@Nullable
		private final ItemStack itemStack;
		@Nullable
		private final BlockState blockState;

		/**
		 * @param level The {@link LevelAccessor} to spawn the particles in.
		 * @param pos The {@link BlockPos} to spawn the particles at.
		 * @param face The {@link Direction} of the face the particles are spawning from.
		 * @param stack The {@link ItemStack} being banned.
		 * @param state The {@link BlockState} being banned.
		 */
		public SpawnParticles(LevelAccessor level, BlockPos pos, @Nullable Direction face, @Nullable ItemStack stack, @Nullable BlockState state) {
			this.level = Preconditions.checkNotNull(level, "Null world in PlacementBanEvent");
			this.pos = Preconditions.checkNotNull(pos, "Null position in PlacementBanEvent");
			this.face = face;
			this.itemStack = stack;
			this.blockState = state;
		}

		/**
		 * @return The {@link LevelAccessor} to spawn the particles in.
		 */
		public LevelAccessor getLevel() {
			return this.level;
		}

		/**
		 * @return The {@link BlockPos} to spawn the particles at.
		 */
		public BlockPos getPos() {
			return this.pos;
		}

		/**
		 * This method is {@link Nullable}. It is marked null for block placement bans.
		 * @return The {@link Direction} of the face the particles are spawning from.
		 */
		@Nullable
		public Direction getFace() {
			return this.face;
		}

		/**
		 * This method is {@link Nullable}. It is marked null for block placement bans.
		 * @return The {@link ItemStack} being banned.
		 */
		@Nullable
		public ItemStack getItemStack() {
			return this.itemStack;
		}

		/**
		 * This method is {@link Nullable}. It is marked null for item placement bans.
		 * @return The {@link BlockState} being banned.
		 */
		@Nullable
		public BlockState getBlockState() {
			return this.blockState;
		}

		@Override
		public void sendEvent() {
			SPAWN_PARTICLES.invoker().onPlacementBanSpawnParticles(this);
		}
	}

	@FunctionalInterface
	public interface PlacementBanCallback {
		void onPlacementBan(PlacementBanEvent event);
	}

	@FunctionalInterface
	public interface PlacementBanCheckItemCallback {
		void onPlacementBanCheckItem(CheckItem event);
	}

	@FunctionalInterface
	public interface PlacementBanCheckBlockCallback {
		void onPlacementBanCheckBlock(CheckBlock event);
	}

	@FunctionalInterface
	public interface PlacementBanParticlesCallback {
		void onPlacementBanSpawnParticles(SpawnParticles event);
	}
}
