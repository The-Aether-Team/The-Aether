package com.aetherteam.aether.block.utility;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.blockentity.SunAltarBlockEntity;
import com.aetherteam.aether.command.SunAltarWhitelist;
import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.aetherteam.aether.network.packet.clientbound.OpenSunAltarPacket;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.network.PacketDistributor;

public class SunAltarBlock extends BaseEntityBlock {

    public static final MapCodec<SunAltarBlock> CODEC = simpleCodec(SunAltarBlock::new);

    public SunAltarBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SunAltarBlockEntity(pos, state);
    }

    /**
     * Controls when the Sun Altar can be used and interacted with.<br><br>
     * Warning for "deprecation" is suppressed because the method is fine to override.
     *
     * @param state  The {@link BlockState} of the block.
     * @param level  The {@link Level} the block is in.
     * @param pos    The {@link BlockPos} of the block.
     * @param player The {@link Player} interacting with the block.
     * @param hand   The {@link InteractionHand} the player interacts with.
     * @param hit    The {@link BlockHitResult} of the interaction.
     * @return The {@link InteractionResult} of the interaction.
     */
    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide()) {
            if (AetherConfig.SERVER.sun_altar_whitelist.get() && !player.hasPermissions(4) && !SunAltarWhitelist.INSTANCE.isWhiteListed(player.getGameProfile())) { // Prevents non-operator or non-whitelisted players from using the Sun Altar on servers
                player.displayClientMessage(Component.translatable(Aether.MODID + ".sun_altar.no_permission"), true); // Player doesn't have permission to use the Sun Altar.
            } else {
                if (AetherConfig.SERVER.sun_altar_dimensions.get().contains(level.dimension().location().toString())) {
                    if (level.hasData(AetherDataAttachments.AETHER_TIME)) { // Checks if the level has the capability used for Aether time, which determines if the Sun Altar has control over the time of a dimension.
                        if (!level.getData(AetherDataAttachments.AETHER_TIME).isEternalDay()) { // Checks if the time is locked into eternal day or not.
                            this.openScreen(level, pos, player, AetherDimensions.AETHER_TICKS_PER_DAY);
                        } else {
                            player.displayClientMessage(Component.translatable(Aether.MODID + ".sun_altar.in_control"), true); // Sun Spirit is still in control of the realm.
                        }
                    } else {
                        this.openScreen(level, pos, player, 24000);
                    }
                } else {
                    player.displayClientMessage(Component.translatable(Aether.MODID + ".sun_altar.no_power"), true); // Sun Altar has no power in the dimension.
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    protected void openScreen(Level level, BlockPos pos, Player player, int timeScale) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof SunAltarBlockEntity sunAltar) {
                PacketDistributor.sendToPlayer(new OpenSunAltarPacket(sunAltar.getName(), timeScale), serverPlayer);
            }
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof SunAltarBlockEntity sunAltar) {
                sunAltar.setCustomName(stack.getHoverName());
                sunAltar.setChanged();
            }
        }
    }

    /**
     * Warning for "deprecation" is suppressed because the method is fine to override.
     */
    @SuppressWarnings("deprecation")
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
