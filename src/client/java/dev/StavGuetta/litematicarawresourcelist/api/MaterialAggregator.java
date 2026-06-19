package dev.StavGuetta.litematicarawresourcelist.api;

import net.minecraft.world.item.Item;
import java.util.Map;

/**
 * Combines a block count and recipe resolution into a single flattened "raw materials required" report.
 */
public interface MaterialAggregator {
    /**
     * Computes the aggregated materials required.
     * Both the unfiltered and survival-filtered views are computed simultaneously.
     *
     * @param source The schematic providing the blocks.
     * @param resolver The resolver mapping items to their recipe trees.
     * @param filter The filter identifying survival-obtainable items.
     * @return A single record containing both views.
     */
    AggregationResult aggregate(SchematicSource source, RecipeResolver resolver, SurvivalObtainability filter);
    
    /**
     * A record holding both views of the aggregated materials.
     */
    record AggregationResult(
        Map<Item, Integer> allMaterials,
        Map<Item, Integer> survivalOnlyMaterials
    ) {}
}
