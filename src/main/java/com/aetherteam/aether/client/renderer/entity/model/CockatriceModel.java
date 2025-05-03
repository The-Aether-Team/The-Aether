package com.aetherteam.aether.client.renderer.entity.model;

import com.aetherteam.aether.client.renderer.entity.state.BirdRenderState;
import net.minecraft.client.model.geom.ModelPart;

public class CockatriceModel extends BipedBirdModel<BirdRenderState> {
    public CockatriceModel(ModelPart root) {
        super(root);
    }

    @Override
    public void setupAnim(BirdRenderState cockatrice) {
        super.setupAnim(cockatrice);
        this.jaw.xRot = 0.35F;
    }
}
