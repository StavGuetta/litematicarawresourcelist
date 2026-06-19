package dev.StavGuetta.litematicarawresourcelist.api;

import net.minecraft.world.level.block.state.BlockState;
import java.util.Map;

/**
 * An explicit extension point for a future feature that compares
 * built-so-far state in the world against the schematic.
 */
public interface ProgressTracker {
    /**
     * Calculates the remaining blocks needed by comparing the current world
     * state to the expected schematic state.
     * 
     * The default implementation is a no-op that simply assumes nothing has
     * been built and returns the full schematic requirements.
     */
    default Map<BlockState, Integer> getRemainingBlocks(SchematicSource schematic) {
        return schematic.getBlockCounts();
    }
}
