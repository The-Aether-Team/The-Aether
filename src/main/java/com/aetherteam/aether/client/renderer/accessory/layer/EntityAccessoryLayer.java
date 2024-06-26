package com.aetherteam.aether.client.renderer.accessory.layer;

import com.aetherteam.aether.AetherTags;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.slot.SlotReference;
import io.wispforest.accessories.client.AccessoriesRenderLayer;
import io.wispforest.accessories.client.MPOATVConstructingVertexConsumer;
import io.wispforest.accessories.client.PostEffectBuffer;
import io.wispforest.accessories.client.gui.AccessoriesScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.lwjgl.opengl.GL30;

import java.awt.*;
import java.util.Calendar;

/**
 * [CODE COPY] - {@link AccessoriesRenderLayer}.
 */
public class EntityAccessoryLayer extends AccessoriesRenderLayer<LivingEntity, EntityModel<LivingEntity>> {
    private static final PostEffectBuffer BUFFER = new PostEffectBuffer();

    public EntityAccessoryLayer(RenderLayerParent<LivingEntity, EntityModel<LivingEntity>> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        var capability = AccessoriesCapability.get(livingEntity);

        if (capability == null) return;

        var calendar = Calendar.getInstance();

        float scale = (float) (1 + (0.5 * (0.75 + (Math.sin(System.currentTimeMillis() / 250d)))));

        var renderingLines = AccessoriesScreen.HOLD_LINE_INFO;

        var useCustomerBuffer = AccessoriesScreen.IS_RENDERING_TARGETS;

        if(!renderingLines && !AccessoriesScreen.NOT_VERY_NICE_POSITIONS.isEmpty()) {
            AccessoriesScreen.NOT_VERY_NICE_POSITIONS.clear();
        }

        for (var entry : capability.getContainers().entrySet()) {
            var container = entry.getValue();

            var accessories = container.getAccessories();
            var cosmetics = container.getCosmeticAccessories();

            for (int i = 0; i < accessories.getContainerSize(); i++) {
                var stack = accessories.getItem(i);
                var cosmeticStack = cosmetics.getItem(i);

                if (!cosmeticStack.isEmpty()) stack = cosmeticStack;

                if (stack.isEmpty() && !stack.is(AetherTags.Items.ACCESSORIES))
                    continue; // Check if the Accessory is an Aether accessory using the tag.

                var renderer = AccessoriesRendererRegistry.getRender(stack);

                if (renderer == null || !renderer.shouldRender(container.shouldRender(i))) continue;

                poseStack.pushPose();

                var mpoatv = new MPOATVConstructingVertexConsumer();

                var bufferedGrabbedFlag = new MutableBoolean(false);

                MultiBufferSource innerBufferSource = (renderType) -> {
                    bufferedGrabbedFlag.setValue(true);

                    return useCustomerBuffer ?
                            VertexMultiConsumer.create(buffer.getBuffer(renderType), mpoatv) :
                            buffer.getBuffer(renderType);
                };

                renderer.render(stack, new SlotReference(container.getSlotName(), livingEntity, i), poseStack, getParentModel(), innerBufferSource, light, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);

                float[] colorValues = null;

                if (useCustomerBuffer && bufferedGrabbedFlag.getValue()) {
                    if (buffer instanceof MultiBufferSource.BufferSource bufferSource) {
                        if (AccessoriesScreen.HOVERED_SLOT_TYPE != null && AccessoriesScreen.HOVERED_SLOT_TYPE.equals(container.getSlotName() + i)) {
                            if (calendar.get(Calendar.MONTH) + 1 == 5 && calendar.get(Calendar.DATE) == 16) {
                                var hue = (float) ((System.currentTimeMillis() / 20d % 360d) / 360d);

                                var color = new Color(Mth.hsvToRgb(hue, 1, 1));

                                colorValues = new float[]{color.getRed() / 128f, color.getGreen() / 128f, color.getBlue() / 128f, 1};
                            } else {
                                colorValues = new float[]{scale, scale, scale, 1};
                            }
                        }

                        if (colorValues != null) {
                            BUFFER.beginWrite(true, GL30.GL_DEPTH_BUFFER_BIT);
                            bufferSource.endBatch();
                            BUFFER.endWrite();

                            BUFFER.draw(colorValues);

                            var frameBuffer = BUFFER.buffer();

                            GlStateManager._glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, frameBuffer.frameBufferId);
                            GL30.glBlitFramebuffer(0, 0, frameBuffer.width, frameBuffer.height, 0, 0, frameBuffer.width, frameBuffer.height, GL30.GL_DEPTH_BUFFER_BIT, GL30.GL_NEAREST);
                            Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
                        } else {
                            bufferSource.endBatch();
                        }
                    }

                    if (renderingLines && AccessoriesScreen.IS_RENDERING_LINE_TARGET) {
                        AccessoriesScreen.NOT_VERY_NICE_POSITIONS.put(container.getSlotName() + i, mpoatv.meanPos());
                    }
                }

                poseStack.popPose();
            }
        }
    }
}
