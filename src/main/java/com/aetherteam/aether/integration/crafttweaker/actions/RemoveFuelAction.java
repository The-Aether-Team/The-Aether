package com.aetherteam.aether.integration.crafttweaker.actions;

import com.blamejared.crafttweaker.api.action.base.IRuntimeAction;

import java.util.function.Consumer;

public record RemoveFuelAction<T>(Consumer<T> applyFunction, T item) implements IRuntimeAction {
    @Override
    public void apply() {
        this.applyFunction().accept(this.item());
    }

    @Override
    public String describe() {
        return String.format("Removing fuel: %s", this.item().toString());
    }
}