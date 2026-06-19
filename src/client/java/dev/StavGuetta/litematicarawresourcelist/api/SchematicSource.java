package dev.StavGuetta.litematicarawresourcelist.api;

import net.minecraft.world.level.block.state.BlockState;
import java.util.Map;

/**
 * An abstraction over "where the block-state data came from".
 * This could be a directly parsed .litematic file or live data pulled from Litematica's placement.
 */
public interface SchematicSource {
    /**
     * Produces a normalized count of each block state required by the schematic.
     * 
     * Block states that do not affect material counting (e.g., stair orientation)
     * should be normalized so they group together. States that do affect materials
     * (e.g., waterlogged blocks needing a water bucket) should remain distinct.
     */
    Map<BlockState, Integer> getBlockCounts();
}
