package com.aetherteam.aetherfabric.mixin.client;

import com.aetherteam.aetherfabric.pond.LanguageExtension;
import net.minecraft.locale.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Mixin(Language.class)
public abstract class LanguageMixin implements LanguageExtension {
    @Unique
    private Map<String, String> languageData = new HashMap<>();

    @Override
    public Map<String, String> getLanguageData() {
        return Collections.unmodifiableMap(languageData);
    }

    @Override
    public void setLanguageData(Map<String, String> data) {
        this.languageData = data;
    }
}
