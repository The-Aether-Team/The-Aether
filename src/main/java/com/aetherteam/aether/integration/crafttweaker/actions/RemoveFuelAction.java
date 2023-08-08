package com.aetherteam.aether.integration.crafttweaker.actions;

import com.blamejared.crafttweaker.api.action.base.IUndoableAction;

import java.util.function.BiConsumer;

public class RemoveFuelAction<T> implements IUndoableAction {
    private final BiConsumer<T, Integer> applyFunction;
    private final BiConsumer<T, Integer> undoFunction;
    private final T item;
    private final int burnTime;

    public RemoveFuelAction(BiConsumer<T, Integer> applyFunction, BiConsumer<T, Integer> undoFunction, T item, int burnTime) {
        this.applyFunction = applyFunction;
        this.undoFunction = undoFunction;
        this.item = item;
        this.burnTime = burnTime;
    }

    @Override
    public void apply() {
        this.applyFunction.accept(this.item, this.burnTime);
    }

    @Override
    public void undo() {
        this.undoFunction.accept(this.item, this.burnTime);
    }

    @Override
    public String describe() {
        return String.format("Removing fuel: %s with duration: %s", this.item.toString(), this.burnTime);
    }

    @Override
    public String describeUndo() {
        return String.format("Adding fuel: %s with duration: %s", this.item.toString(), this.burnTime);
    }

    @Override
    public String systemName() {
        return "The Aether";
    }
}
