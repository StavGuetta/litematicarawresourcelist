package dev.StavGuetta.litematicarawresourcelist.api;

import net.minecraft.world.item.Item;

/**
 * A classifier that flags whether a given block/item can reasonably be 
 * obtained or placed in survival mode.
 */
public interface SurvivalObtainability {
    /**
     * Determines if an item is survival-obtainable.
     * 
     * Default logic relies on the item having a creative-menu entry, checked against
     * an explicit user-editable deny-list JSON file for exceptions.
     */
    boolean isObtainable(Item item);
    
    /**
     * Reloads the deny-list from the user-editable JSON configuration file.
     */
    void reloadConfig();
}
