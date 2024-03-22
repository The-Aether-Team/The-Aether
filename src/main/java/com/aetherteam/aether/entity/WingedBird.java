package com.aetherteam.aether.entity;

/**
 * Interface for bird mobs with wings to handle wing rotation animation.
 */
public interface WingedBird extends NotGrounded {
    float getWingRotation();

    void setWingRotation(float rot);

    float getPrevWingRotation();

    void setPrevWingRotation(float rot);

    float getWingDestPos();

    void setWingDestPos(float pos);

    float getPrevWingDestPos();

    void setPrevWingDestPos(float pos);

    /**
     * Animates the mob's wings depending on whether it is on the ground or not.
     */
    default void animateWings() {
        this.setPrevWingRotation(this.getWingRotation());
        this.setPrevWingDestPos(this.getWingDestPos());
        if (!this.isEntityOnGround()) {
            this.setWingDestPos(this.getWingDestPos() + 0.45F);
            this.setWingDestPos(Math.min(1.0F, Math.max(0.01F, this.getWingDestPos())));
        } else {
            this.setWingDestPos(0.0F);
            this.setWingRotation(0.0F);
        }
        this.setWingRotation(this.getWingRotation() + 3.0F);
    }
}
