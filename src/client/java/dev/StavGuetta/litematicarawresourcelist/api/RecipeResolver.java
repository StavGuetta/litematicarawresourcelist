package dev.StavGuetta.litematicarawresourcelist.api;

import net.minecraft.world.item.Item;

/**
 * A pluggable interface for resolving an item into its raw recipe tree.
 * Allows modded recipe sources (like Stonecutting, Smithing, or custom machines)
 * to be integrated without altering core logic.
 */
public interface RecipeResolver {
    /**
     * Resolves an Item into a MaterialNode. If the item cannot be crafted,
     * it should return a MaterialNode.Leaf. If it can be crafted, it returns
     * a MaterialNode.Composite.
     */
    MaterialNode resolve(Item item);
}
