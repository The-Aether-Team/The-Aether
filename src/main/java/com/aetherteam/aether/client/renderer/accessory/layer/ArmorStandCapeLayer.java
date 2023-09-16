package com.aetherteam.aether.client.renderer.accessory.layer;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.accessory.model.CapeModel;
import com.aetherteam.aether.item.accessories.cape.CapeItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmorStandModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Optional;

/**
 * [CODE COPY] - {@link net.minecraft.client.renderer.entity.layers.CapeLayer}.<br><br>
 * Modified to check for capes in the Armor Stand's slots, as well as remove rotational fields and instead keep rotations constant.
 */
public class ArmorStandCapeLayer extends RenderLayer<ArmorStand, ArmorStandModel> {
    private final CapeModel cape;

    public ArmorStandCapeLayer(RenderLayerParent<ArmorStand, ArmorStandModel> renderer) {
        super(renderer);
        this.cape = new CapeModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.CAPE));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, ArmorStand livingEntity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        String identifier = AetherConfig.COMMON.use_curios_menu.get() ? "back" : "aether_cape";
        LazyOptional<ICuriosItemHandler> lazyHandler = CuriosApi.getCuriosInventory(livingEntity);
        if (lazyHandler.isPresent() && lazyHandler.resolve().isPresent()) {
            ICuriosItemHandler handler = lazyHandler.resolve().get();
            Optional<ICurioStacksHandler> stacksHandler = handler.getStacksHandler(identifier);
            if (stacksHandler.isPresent()) {
                IDynamicStackHandler stackHandler = stacksHandler.get().getCosmeticStacks();
                if (0 < stackHandler.getSlots()) {
                    ItemStack itemStack = stackHandler.getStackInSlot(0);
                    if (!itemStack.isEmpty()) {
                        if (itemStack.getItem() instanceof CapeItem capeItem) {
                            ResourceLocation texture = capeItem.getCapeTexture();
                            if (!livingEntity.isInvisible() && texture != null) {
                                ItemStack itemstack = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
                                if (!itemstack.is(Items.ELYTRA)) {
                                    poseStack.pushPose();
                                    poseStack.translate(0.0F, 0.0F, 0.0925F);
                                    poseStack.mulPose(Axis.XP.rotationDegrees(3.0F));
                                    poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
                                    poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
                                    VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entitySolid(texture));
                                    this.cape.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                                    poseStack.popPose();
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
