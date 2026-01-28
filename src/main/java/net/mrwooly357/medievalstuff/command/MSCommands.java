package net.mrwooly357.medievalstuff.command;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.command.custom.SpawnCommand;

public final class MSCommands {

    private MSCommands() {}


    public static void initializeServer() {
        MedievalStuff.logInitialization("server commands");
        registerServer((dispatcher, registryAccess, environment) -> SpawnCommand.register(dispatcher, registryAccess));
    }

    public static void initializeClient() {
        MedievalStuff.logInitialization("client commands");
    }

    private static void registerServer(CommandRegistrationCallback callback) {
        CommandRegistrationCallback.EVENT.register(callback);
    }

    private static void registerClient(ClientCommandRegistrationCallback callback) {
        ClientCommandRegistrationCallback.EVENT.register(callback);
    }
}
