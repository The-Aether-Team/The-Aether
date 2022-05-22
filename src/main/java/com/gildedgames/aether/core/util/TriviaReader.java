package com.gildedgames.aether.core.util;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TriviaReader {
    private final Random random = new Random();
    private final List<Component> trivia = new ArrayList<>();
    private int index;

    public TriviaReader() { }

    public void generateTriviaList() {
        for (String string : I18n.language.getLanguageData().keySet()) {
            if (string.startsWith("aether.pro_tips.line.")) {
                this.getTrivia().add(new TranslatableComponent(string));
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
            return new TranslatableComponent("gui.aether.pro_tip").append(new TextComponent(" ").append(this.getTriviaComponent()));
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
