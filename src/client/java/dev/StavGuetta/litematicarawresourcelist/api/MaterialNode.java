package dev.StavGuetta.litematicarawresourcelist.api;

import net.minecraft.world.item.Item;
import java.util.List;

/**
 * Represents a node in a recursively-resolved crafting tree.
 * 
 * A sealed interface restricts which classes can implement it. In this case,
 * only Leaf and Composite can implement MaterialNode.
 */
public sealed interface MaterialNode permits MaterialNode.Leaf, MaterialNode.Composite {
    
    Item item();
    
    /**
     * A Leaf node represents a raw/natural item with no further recipe 
     * (e.g., logs, ores, dirt).
     */
    record Leaf(Item item) implements MaterialNode {}
    
    /**
     * A Composite node represents an item produced via a recipe.
     * 
     * @param item The resulting item.
     * @param recipeYield How many of the item are produced per craft (e.g., 4 for Planks).
     * @param ingredients The items needed to perform the craft once.
     */
    record Composite(Item item, int recipeYield, List<IngredientCount> ingredients) implements MaterialNode {}
    
    /**
     * A helper record to store an ingredient and the quantity required.
     */
    record IngredientCount(MaterialNode node, int count) {}
}
