package net.mrwooly357.medievalstuff;

import net.fabricmc.api.ClientModInitializer;
import net.mrwooly357.medievalstuff.command.MSCommands;
import net.mrwooly357.medievalstuff.event.MSHudRenderEvents;

public final class MSClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        MSCommands.initializeClient();
        MSHudRenderEvents.initialize();
    }
}
