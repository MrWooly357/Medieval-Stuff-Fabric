package net.mrwooly357.medievalstuff.command.custom;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.RegistryKeyArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContextParameters;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContextTypes;
import net.mrwooly357.medievalstuff.registry.MSRegistryKeys;

public final class SpawnCommand {

    public static final DynamicCommandExceptionType INVALID_KEY_EXCEPTION = new DynamicCommandExceptionType(
            key -> Text.stringifiedTranslatable("command.medievalstuff.spawn.invalid_key", key)
    );

    private SpawnCommand() {}


    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        dispatcher.register(
                CommandManager.literal("spawn")
                        .then(
                                CommandManager.argument("table", RegistryKeyArgumentType.registryKey(MSRegistryKeys.SPAWN_TABLE))
                                        .then(
                                                CommandManager.argument("offset", Vec3ArgumentType.vec3())
                                                        .executes(context -> {
                                                            ServerCommandSource source = context.getSource();
                                                            registryAccess.getWrapperOrThrow(MSRegistryKeys.SPAWN_TABLE)
                                                                    .getOptional(RegistryKeyArgumentType.getKey(context, "table", MSRegistryKeys.SPAWN_TABLE, INVALID_KEY_EXCEPTION)).orElseThrow().value()
                                                                    .generateEntities(
                                                                            SpawnContext.builder()
                                                                                    .parameter(SpawnContextParameters.ORIGIN, Vec3ArgumentType.getVec3(context, "offset"))
                                                                                    .build(SpawnContextTypes.GENERIC, source.getWorld())
                                                                    );

                                                            return Command.SINGLE_SUCCESS;
                                                        })
                                        )
                        )
        );
    }
}
