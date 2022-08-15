package com.gildedgames.aether.command;

import com.gildedgames.aether.capability.player.AetherPlayer;
import com.gildedgames.aether.network.AetherPacketHandler;
import com.gildedgames.aether.network.packet.client.HealthResetPacket;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.Collection;

public class CapabilityPlayerCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("aether")
                .then(Commands.literal("capability")
                        .then(Commands.literal("player").requires((commandSourceStack) -> commandSourceStack.hasPermission(2))
                                .then(Commands.literal("life_shards")
                                        .then(Commands.literal("set")
                                                .then(Commands.argument("targets", GameProfileArgument.gameProfile())
                                                        .suggests((context, builder) -> {
                                                            PlayerList playerlist = context.getSource().getServer().getPlayerList();
                                                            return SharedSuggestionProvider.suggest(playerlist.getPlayers().stream().map((player) -> player.getGameProfile().getName()), builder);
                                                        }).then(Commands.argument("value", IntegerArgumentType.integer(0, 10)).executes((context) -> setLifeShards(context.getSource(), GameProfileArgument.getGameProfiles(context, "targets"), IntegerArgumentType.getInteger(context, "value"))))
                                                )
                                        )
                                )
                        )
                )
        );
    }

    private static int setLifeShards(CommandSourceStack source, Collection<GameProfile> gameProfiles, int value) {
        ServerLevel level = source.getLevel();
        PlayerList playerList = source.getServer().getPlayerList();
        for (GameProfile gameProfile : gameProfiles) {
            ServerPlayer player = playerList.getPlayer(gameProfile.getId());
            if (player != null) {
                AetherPlayer.get(player).ifPresent(aetherPlayer -> {
                    aetherPlayer.setLifeShardCount(value);
                    AttributeInstance attribute = player.getAttribute(Attributes.MAX_HEALTH);
                    if (attribute != null) {
                        attribute.removeModifier(aetherPlayer.getLifeShardHealthAttributeModifier());
                    }
                    player.setHealth(player.getMaxHealth());
                    AetherPacketHandler.sendToNear(new HealthResetPacket(player.getId(), value), player.getX(), player.getY(), player.getZ(), 5.0, level.dimension());
                    source.sendSuccess(Component.translatable("commands.aether.capability.player.life_shards.set", player.getDisplayName(), value), true);
                });
            }
        }
        return 1;
    }
}
