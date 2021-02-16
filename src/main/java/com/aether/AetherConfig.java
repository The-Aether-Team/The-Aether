package com.aether;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import org.apache.commons.lang3.tuple.Pair;

public class AetherConfig
{
    public static class Common {
        public final ConfigValue<Boolean> testCommon;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("test");
            testCommon = builder.define("This is a test config value", true);
            builder.pop();
        }
    }

    public static class Client {
        public final ConfigValue<Boolean> testClient;

        public Client(ForgeConfigSpec.Builder builder) {
            builder.push("testClient");
            testClient = builder.define("This is a test config value, but for client", true);
            builder.pop();
        }
    }

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final Client CLIENT;

    static {
        final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();

        final Pair<Client, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = clientSpecPair.getRight();
        CLIENT = clientSpecPair.getLeft();
    }
}
