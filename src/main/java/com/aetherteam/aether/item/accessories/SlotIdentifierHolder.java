package com.aetherteam.aether.item.accessories;


/**
 * Functional interface whose only defines an accessory slot identifier for an item.
 * <p>
 * Should be used to avoid hard-coding specific identifiers
 * and enable easy changing and addition of new accessory slot identifiers.
 *
 * @author Alexandra
 */
@FunctionalInterface
public interface SlotIdentifierHolder {
    /**
     * @implNote May be best to pair with a static method to get an identifier if no instance methods are needed.
     * @return The string used as an identifier for an accessory slot.
     */
    String getIdentifier();
}
