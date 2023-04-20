package com.aetherteam.aether.entity;

public interface WingedBird extends NotGrounded {
    float getWingRotation();
    void setWingRotation(float rot);
    float getPrevWingRotation();
    void setPrevWingRotation(float rot);

    float getDestPos();
    void setDestPos(float pos);
    float getPrevDestPos();
    void setPrevDestPos(float pos);

    default void animateWings() {
        this.setPrevWingRotation(this.getWingRotation());
        this.setPrevDestPos(this.getDestPos());
        if (!this.isEntityOnGround()) {
            this.setDestPos(this.getDestPos() + 0.45F);
            this.setDestPos(Math.min(1.0F, Math.max(0.01F, this.getDestPos())));
        } else {
            this.setDestPos(0.0F);
            this.setWingRotation(0.0F);
        }
        this.setWingRotation(this.getWingRotation() + 3.0F);
    }
}
