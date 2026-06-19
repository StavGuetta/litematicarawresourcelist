package dev.StavGuetta.litematicarawresourcelist;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LitematicaRawResourceListClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("litematicarawresourcelist");

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing Litematica Raw Resource List (Client)");
    }
}
