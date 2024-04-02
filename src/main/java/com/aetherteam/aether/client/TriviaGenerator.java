package com.aetherteam.aether.client;

import com.aetherteam.aether.mixin.mixins.client.accessor.I18nAccessor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.RandomSource;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TriviaGenerator {
    private final RandomSource random = RandomSource.create();
    private final List<Component> trivia = new ArrayList<>();
    private int index;

    public TriviaGenerator() {
    }

    /**
     * Generates a list of "Pro Tip" entries to display on the loading screen.
     * The trivia is gathered from all language file entries starting with "aether.pro_tips.line."
     */
    public void generateTriviaList() {
        for (String string : I18nAccessor.aether$getLanguage().getLanguageData().keySet()) {
            if (string.startsWith("aether.pro_tips.line.")) {
                this.getTrivia().add(Component.translatable(string));
            }
        }
    }

    /**
     * Chooses a random index for selecting a trivia line from.
     */
    public void randomizeTriviaIndex() {
        if (!this.getTrivia().isEmpty()) {
            this.index = this.random.nextInt(this.getTrivia().size());
        }
    }

    /**
     * @return A constructed trivia line {@link Component} appended with "Pro Tip:", for display on the loading screen.
     */
    @Nullable
    public Component getTriviaLine() {
        if (this.getTriviaComponent() != null) {
            Component triviaComponent = this.getTriviaComponent();
            MutableComponent prefixComponent = Component.translatable("gui.aether.pro_tip").withStyle(triviaComponent.getStyle());
            return prefixComponent.append(Component.literal(" ").append(triviaComponent));
        }
        return null;
    }

    /**
     * @return A {@link Component} for the trivia line at the current index.
     */
    @Nullable
    private Component getTriviaComponent() {
        if (!this.getTrivia().isEmpty()) {
            return this.getTrivia().get(this.index);
        }
        return null;
    }

    /**
     * @return A {@link List} of all the stored trivia line {@link Component}s.
     */
    public List<Component> getTrivia() {
        return this.trivia;
    }
}
