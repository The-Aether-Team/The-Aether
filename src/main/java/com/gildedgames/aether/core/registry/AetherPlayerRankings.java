package com.gildedgames.aether.core.registry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.event.events.RankingEvent;
import com.gildedgames.aether.core.api.registers.PlayerRanking;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

public class AetherPlayerRankings
{
    private static final Map<UUID, Set<PlayerRanking>> RANKS;

    static {
        Map<UUID, Set<PlayerRanking>> ranks = Maps.newHashMap();
        MinecraftForge.EVENT_BUS.post(new RankingEvent(ranks));
        for (Map.Entry<UUID, Set<PlayerRanking>> entry : ranks.entrySet()) {
            entry.setValue(ImmutableSet.copyOf(entry.getValue()));
        }
        RANKS = ImmutableMap.copyOf(ranks);
    }

    @Mod.EventBusSubscriber(modid = Aether.MODID)
    public static class Registration
    {
        @SubscribeEvent
        public static void registerRanks(RankingEvent event) {
            //TODO: Should be reviewed.
            event.addRank("13655ac1-584d-4785-b227-650308195121", PlayerRanking.GILDED_GAMES); //kingbdogz
            event.addRank("3c0e4411-3421-40bd-b092-056d3e99b98a", PlayerRanking.GILDED_GAMES); //Oz Payn
            event.addRank("6fb2f965-6b57-46de-9ef3-0ef4c9b9bdc6", PlayerRanking.GILDED_GAMES); //Hugo Payn
            event.addRank("dc4cf9b2-f601-4eb4-9436-2924836b9f42", PlayerRanking.GILDED_GAMES); //Jaryt Bustard
            event.addRank("c0643897-c500-4f61-a62a-8051801562a9", PlayerRanking.GILDED_GAMES); //Raetro
            event.addRank("58a5d694-a8a6-4605-ab33-d6904107ad5f", PlayerRanking.GILDED_GAMES); //bconlon
            event.addRank("353a859b-ba16-4e6a-8f63-9a8c79ab0071", PlayerRanking.GILDED_GAMES); //quek
            event.addRank("c3e6871e-8e60-490a-8a8d-2bbe35ad1604", PlayerRanking.GILDED_GAMES); //Raptor
            event.addRank("78c7f290-62aa-4afa-9d9a-f8e6b2f85206", PlayerRanking.GILDED_GAMES); //NAPPUS
            event.addRank("d475af59-d73c-42be-90ed-f1a78f10d452", PlayerRanking.GILDED_GAMES); //bumble_dani
            event.addRank("168fd38e-d43c-4130-95e4-d7501e569892", PlayerRanking.GILDED_GAMES); //Mercedes
            event.addRank("d5570878-6f70-42ea-a311-6370965d2a2b", PlayerRanking.GILDED_GAMES); //Overspace

            event.addRank("b5ee3d5d-2ad7-4642-b9d4-6b041ad600a4", PlayerRanking.RETIRED_GILDED_GAMES); //Emile van Krieken
            event.addRank("6e8be0ba-e4bb-46af-aea8-2c1f5eec5bc2", PlayerRanking.RETIRED_GILDED_GAMES); //Brendan Freeman
            event.addRank("2afd6a1d-1531-4985-a104-399c0c19351d", PlayerRanking.RETIRED_GILDED_GAMES); //Brandon Potts
            event.addRank("ffb94179-dd54-400d-9ece-834720cd7be9", PlayerRanking.RETIRED_GILDED_GAMES); //JellySquid
            event.addRank("68fef942-0c9f-45b9-8b72-6d94a7d08b8e", PlayerRanking.RETIRED_GILDED_GAMES); //Chelsea

            event.addRank("1d680bb6-2a9a-4f25-bf2f-a1af74361d69", PlayerRanking.MODDING_LEGACY); //Bailey
            event.addRank("4bfb28a3-005d-4fc9-9238-a55c6c17b575", PlayerRanking.MODDING_LEGACY); //Jon Lachney

            event.addRank("6a0e8505-1556-4ee9-bec0-6af32f05888d", PlayerRanking.RETIRED_MODDING_LEGACY); //115kino
            event.addRank("5f112332-0993-4f52-a5ab-9a55dc3173cb", PlayerRanking.RETIRED_MODDING_LEGACY); //JorgeQ

            event.addRank("6f8be24f-03f3-4288-9218-16c9ecc08c8f", PlayerRanking.CONTRIBUTOR); //Jonathing
            event.addRank("c15c4d6d-9a80-4d6b-9eda-770859b5ed91", PlayerRanking.CONTRIBUTOR); //Everett1999
            event.addRank("4f0e8dd5-caf4-4d88-bfa4-1b0f1e13779f", PlayerRanking.CONTRIBUTOR); //Indianajaune
            event.addRank("6c249311-f939-4e66-9f31-49b753bfb14b", PlayerRanking.CONTRIBUTOR); //InsomniaKitten
            event.addRank("2b5187c9-dc5d-480e-ab6f-e884e92fce45", PlayerRanking.CONTRIBUTOR); //ItzDennisz
            //Reetam
            //Burning Cactus
            //Kelvin
            //Fabian

            event.addRank("cf51ef47-04a8-439a-aa41-47d871b0b837", PlayerRanking.TESTER); //Kito
            event.addRank("93822537-d79f-4672-b9a8-04aae16131d2", PlayerRanking.TESTER); //KidoftheForest
            event.addRank("f2914dae-441a-4254-aa5c-2ec4d917b7a6", PlayerRanking.TESTER); //Jesterguy
            event.addRank("869aed85-9dc0-4187-92d7-6064c202a844", PlayerRanking.TESTER); //SunflowerAspen
            event.addRank("8ab9311e-6b8d-4633-80d5-e1798b1c6a96", PlayerRanking.TESTER); //Silver_David
            event.addRank("c4fa4377-5147-43bd-b571-e0e0db46e4f6", PlayerRanking.TESTER); //Anabree

            event.addRank("5f820c39-5883-4392-b174-3125ac05e38c", PlayerRanking.CELEBRITY); //CaptainSparklez
            event.addRank("0c063bfd-3521-413d-a766-50be1d71f00e", PlayerRanking.CELEBRITY); //AntVenom
            event.addRank("20073cb8-a092-47e2-9a60-bca856e62faf", PlayerRanking.CELEBRITY); //ChimneySwift
            event.addRank("8d945389-6105-4a8d-8be7-088da387d173", PlayerRanking.CELEBRITY); //ClashJTM
            event.addRank("0e305085-6ef0-4e46-a7b0-18e78827c44b", PlayerRanking.CELEBRITY); //Mr360Games
        }
    }

    public static PlayerRanking getPrimaryRankOf(UUID uuid) {
        return getRanksOf(uuid).stream().max(Comparator.naturalOrder()).orElse(PlayerRanking.NONE);
    }

    public static Set<PlayerRanking> getRanksOf(UUID uuid) {
        return RANKS.getOrDefault(uuid, Collections.emptySet());
    }

    public static boolean hasHalo(UUID uuid) {
        return getPrimaryRankOf(uuid).hasHalo();
    }

    public static boolean hasDevGlow(UUID uuid) {
        return getPrimaryRankOf(uuid).hasDevGlow() || uuid.toString().equals("cf51ef47-04a8-439a-aa41-47d871b0b837");
    }

    public static boolean isPatron(UUID uuid) {
        //TODO:
        return false;
    }

    public static boolean isMojang(UUID uuid) {
        //TODO:
        /*
        if (this.getPlayer() instanceof ClientPlayerEntity) {
            ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity) this.getPlayer();
            Aether.LOGGER.info(clientPlayerEntity.getCloakTextureLocation());
            //minecraft:skins/ea963f1b7d7c510da28800a770882d0c4b0aee6d = mojang cape. this is client only though.........
        }
         */
        return false;
    }

}
