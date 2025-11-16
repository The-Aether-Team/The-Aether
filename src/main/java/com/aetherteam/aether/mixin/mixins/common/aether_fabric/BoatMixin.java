package com.aetherteam.aether.mixin.mixins.common.aether_fabric;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherItems;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Boat.class)
public abstract class BoatMixin {
    @Shadow
    public abstract Boat.Type getVariant();

    @WrapMethod(method = "getDropItem")
    private Item aetherFabric$adjustBoatItemDrop(Operation<Item> original) {
        return (this.getVariant().getName().equals("aether:skyroot"))
            ? AetherItems.SKYROOT_BOAT.get()
            : original.call();
    }

    @WrapOperation(method = "checkFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/vehicle/Boat;spawnAtLocation(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/entity/item/ItemEntity;", ordinal = 1))
    private ItemEntity aetherFabric$adjustStickSpawn(Boat instance, ItemLike itemLike, Operation<ItemEntity> original) {
        return original.call(instance, (instance.getVariant().getName().equals("aether:skyroot")) ? AetherItems.SKYROOT_STICK : itemLike);
    }

    @Mixin(Boat.Type.class)
    public static abstract class TypeMixin {

//        @Invoker("<init>")
//        public static Boat.Type aetherFabric$invokeNew(String internalName, int ordinal, Block baseBlock, String name) {
//            throw new IllegalStateException("How did this mixin stub get called conc");
//        }
//
//        @Final
//        @Shadow
//        @Mutable
//        private static Boat.Type[] $VALUES;
//
//        @Inject(method = "<clinit>", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/vehicle/Boat$Type;$VALUES:[Lnet/minecraft/world/entity/vehicle/Boat$Type;", shift = At.Shift.AFTER, opcode = Opcodes.PUTSTATIC))
//        private static void aetherFabric$addSkyRootBoat(CallbackInfo ci) {
//            var boatTypes = new Boat.Type[$VALUES.length + 1];
//            System.arraycopy($VALUES, 0, boatTypes, 0, $VALUES.length);
//
//            boatTypes[boatTypes.length - 1] = TypeMixin.aetherFabric$invokeNew("AETHER_SKYROOT", Boat.Type.values().length, null, "aether:skyroot");
//
//            $VALUES = boatTypes;
//        }

        @Shadow
        public abstract String getName();

        @WrapMethod(method = "getPlanks")
        private Block aetherFabric$adjustPlanks(Operation<Block> original) {
            return (this.getName().equals("aether:skyroot"))
                ? AetherBlocks.SKYROOT_PLANKS.get()
                : original.call();
        }
    }
}
