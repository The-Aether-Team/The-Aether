package com.aetherteam.aether.client.renderer.entity.sub;

import com.aetherteam.aether.client.gui.screen.perks.MoaSkinsScreen;
import com.aetherteam.aether.client.renderer.entity.model.MoaModel;
import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.aether.perk.data.ClientMoaSkinPerkData;
import com.aetherteam.aether.perk.types.MoaData;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.Map;
import java.util.UUID;

public interface MoaSubRenderer {
    void render(MoaModel parentModel, MoaModel layerModel, PoseStack poseStack, MultiBufferSource buffer, int packedLight, Moa moa, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch);

    default void createParticles(ClientLevel level, Moa moa) {

    }

    static void tickParticles(Moa moa) {
        if (moa.level() instanceof ClientLevel level) {
            UUID lastRiderUUID = moa.getLastRider();
            UUID moaUUID = moa.getMoaUUID();
            Map<UUID, MoaData> userSkinsData = ClientMoaSkinPerkData.INSTANCE.getClientPerkData();
            if (Minecraft.getInstance().screen instanceof MoaSkinsScreen moaSkinsScreen && moaSkinsScreen.getSelectedSkin() != null && moaSkinsScreen.getPreviewMoa() != null && moaSkinsScreen.getPreviewMoa().getMoaUUID() != null && moaSkinsScreen.getPreviewMoa().getMoaUUID().equals(moaUUID) && moaSkinsScreen.getSelectedSkin().getSubRenderer() != null) {
                moaSkinsScreen.getSelectedSkin().getSubRenderer().createParticles(level, moa);
            } else if (userSkinsData.containsKey(lastRiderUUID) && userSkinsData.get(lastRiderUUID).moaSkin() != null && userSkinsData.get(lastRiderUUID).moaUUID() != null && userSkinsData.get(lastRiderUUID).moaUUID().equals(moaUUID) && userSkinsData.get(lastRiderUUID).moaSkin().getSubRenderer() != null) {
                userSkinsData.get(lastRiderUUID).moaSkin().getSubRenderer().createParticles(level, moa);
            }
        }
    }

    default ResourceLocation getAnimationFrame(ResourceLocation texture, int tickCount) {
        if (this.getAnimationData() != null) {
            int frame = Mth.floor(((float) tickCount / this.getAnimationData().delay()) % this.getAnimationData().frames());
            String textureString = texture.toString();
            textureString = textureString.replace(".png", "_" + frame + ".png");
            return ResourceLocation.parse(textureString);
        }
        return texture;
    }

    default AnimationData getAnimationData() {
        return null;
    }

    record AnimationData(int frames, int delay) {

    }
}
