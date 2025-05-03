package com.aetherteam.aether.client.renderer.entity.model;

import com.aetherteam.aether.client.renderer.entity.state.MoaRenderState;
import net.minecraft.client.model.geom.ModelPart;

public class MoaModel extends BipedBirdModel<MoaRenderState> {

    public MoaModel(ModelPart root) {
        super(root);
    }

    @Override
    public void setupAnim(MoaRenderState moa) {
        super.setupAnim(moa);
        if (moa.sitting) {
            this.jaw.xRot = 0.0F;
        } else {
            this.jaw.xRot = 0.35F;
        }
        this.rightLeg.visible = moa.renderLegs;
        this.leftLeg.visible = moa.renderLegs;
    }
}
