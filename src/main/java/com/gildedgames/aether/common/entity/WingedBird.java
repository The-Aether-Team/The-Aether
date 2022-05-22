package com.gildedgames.aether.common.entity;

public interface WingedBird {
    float getWingRotation();
    void setWingRotation(float rot);
    float getPrevWingRotation();
    void setPrevWingRotation(float rot);

    float getDestPos();
    void setDestPos(float pos);
    float getPrevDestPos();
    void setPrevDestPos(float pos);
}
