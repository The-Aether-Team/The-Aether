package com.gildedgames.aether.api;

import com.gildedgames.aether.mixin.mixins.accessor.I18nAccessor;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;

import java.util.ArrayList;
import java.util.List;

public class TriviaGenerator {
    private final RandomSource random = RandomSource.create();
    private final List<Component> trivia = new ArrayList<>();
    private int index;

    public TriviaGenerator() { }

    public void generateTriviaList() {
        for (String string : I18nAccessor.getLanguage().getLanguageData().keySet()) {
            if (string.startsWith("aether.pro_tips.line.")) {
                this.getTrivia().add(Component.translatable(string));
            }
        }
    }

    public void randomizeTriviaIndex() {
        if (!this.getTrivia().isEmpty()) {
            this.index = this.random.nextInt(this.getTrivia().size());
        }
    }

    public Component getTriviaLine() {
        if (this.getTriviaComponent() != null) {
            return Component.translatable("gui.aether.pro_tip").append(Component.literal(" ").append(this.getTriviaComponent()));
        }
        return null;
    }

    private Component getTriviaComponent() {
        if (!this.getTrivia().isEmpty()) {
            return this.getTrivia().get(this.index);
        }
        return null;
    }

    public List<Component> getTrivia() {
        return this.trivia;
    }
}
