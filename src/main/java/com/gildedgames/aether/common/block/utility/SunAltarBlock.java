package com.gildedgames.aether.common.block.utility;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.block.entity.SunAltarBlockEntity;
import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.capability.AetherCapabilities;
import com.gildedgames.aether.core.capability.interfaces.IAetherTime;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;

import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class SunAltarBlock extends BaseEntityBlock {
	public SunAltarBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new SunAltarBlockEntity(pPos, pState);
	}

	@Nonnull
	@Override
	public InteractionResult use(@Nonnull BlockState state, Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
		if (level.isClientSide) {
			return InteractionResult.SUCCESS;
		} else {
			if (!player.hasPermissions(AetherConfig.COMMON.admin_sun_altar.get() ? 4 : 0)) {
				player.displayClientMessage(new TranslatableComponent(Aether.MODID + ".sun_altar.no_permission"), true);
				return InteractionResult.FAIL;
			}
			IAetherTime aetherTime = level.getCapability(AetherCapabilities.AETHER_TIME_CAPABILITY).orElse(null);
			if(aetherTime != null) {
				if(!aetherTime.getEternalDay() || AetherConfig.COMMON.disable_eternal_day.get()) {
					this.openScreen(level, pos, player);
					return InteractionResult.CONSUME;
				} else {
					player.displayClientMessage(new TranslatableComponent(Aether.MODID + ".sun_altar.in_control"), true);
					return InteractionResult.FAIL;
				}
			} else {
				player.displayClientMessage(new TranslatableComponent(Aether.MODID + ".sun_altar.no_power"), true);
				return InteractionResult.FAIL;
			}
		}
	}

	protected void openScreen(Level level, @Nonnull BlockPos pos, @Nonnull Player player) {
		if (!level.isClientSide) {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if (blockEntity instanceof SunAltarBlockEntity sunAltar) {
				((ServerPlayer)player).connection.send(ClientboundBlockEntityDataPacket.create(sunAltar));
			}
		}
	}

	@Override
	public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
		if (pStack.hasCustomHoverName()) {
			BlockEntity blockentity = pLevel.getBlockEntity(pPos);
			if (blockentity instanceof SunAltarBlockEntity sunAltar) {
				sunAltar.setCustomName(pStack.getHoverName());
			}
		}

	}

	@Override
	public RenderShape getRenderShape(BlockState pState) {
		return RenderShape.MODEL;
	}

}
