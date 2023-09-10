package com.aetherteam.aether.integration.crafttweaker.actions;

import com.blamejared.crafttweaker.api.action.base.IRuntimeAction;

import java.util.function.BiConsumer;

public record AddFuelAction<T>(BiConsumer<T, Integer> applyFunction, T item, int burnTime) implements IRuntimeAction {
    @Override
    public void apply() {
        this.applyFunction().accept(this.item(), this.burnTime());
    }

    @Override
    public String describe() {
        return String.format("Adding fuel: %s with duration: %s", this.item().toString(), this.burnTime());
    }
}