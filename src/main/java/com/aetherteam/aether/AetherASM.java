package com.aetherteam.aether;

import io.github.fabricators_of_create.porting_lib.asm.ASMUtils;
import me.shedaniel.mm.api.ClassTinkerers;

public class AetherASM implements Runnable {
    @Override
    public void run() {
        ClassTinkerers.enumBuilder(ASMUtils.mapC("class_5421").replace('.', '/'))
                .addEnum("ALTAR")
                .addEnum("FREEZER")
                .addEnum("INCUBATOR")
                .build();
    }
}
