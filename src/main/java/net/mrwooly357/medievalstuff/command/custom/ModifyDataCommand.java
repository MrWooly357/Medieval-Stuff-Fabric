package net.mrwooly357.medievalstuff.command.custom;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.command.argument.NbtCompoundArgumentType;
import net.minecraft.command.argument.RegistryKeyArgumentType;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.mrwooly357.medievalstuff.registry.MSRegistries;
import net.mrwooly357.medievalstuff.registry.MSRegistryKeys;

import java.util.function.Supplier;

public final class ModifyDataCommand {

    private static final DynamicCommandExceptionType INVALID_KEY_EXCEPTION = new DynamicCommandExceptionType(
            key -> Text.stringifiedTranslatable("command.medievalstuff.modify_data.invalid_key", key)
    );

    private ModifyDataCommand() {}


    @SuppressWarnings("unchecked")
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("modify_data")
                        .then(
                                CommandManager.argument("type", RegistryKeyArgumentType.registryKey(MSRegistryKeys.ATTACHMENT_TYPE))
                                        .then(
                                                CommandManager.literal("set")
                                                        .then(
                                                                CommandManager.argument("data", NbtCompoundArgumentType.nbtCompound())
                                                                        .executes(context -> {
                                                                            AttachmentType<Object> type = (AttachmentType<Object>) MSRegistries.ATTACHMENT_TYPE.get(RegistryKeyArgumentType.getKey(context, "type", MSRegistryKeys.ATTACHMENT_TYPE, INVALID_KEY_EXCEPTION));
                                                                            Codec<?> codec = type.persistenceCodec();

                                                                            if (codec != null) {
                                                                                context.getSource().getPlayer().setAttached(type, codec.decode(NbtOps.INSTANCE, NbtCompoundArgumentType.getNbtCompound(context, "data")).result().orElseThrow().getFirst());

                                                                                return Command.SINGLE_SUCCESS;
                                                                            } else
                                                                                return 0;
                                                                        })
                                                        )
                                        )
                                        .then(
                                                CommandManager.literal("reset")
                                                        .executes(context -> {
                                                            AttachmentType<Object> type =
                                                                    (AttachmentType<Object>) MSRegistries.ATTACHMENT_TYPE.get(RegistryKeyArgumentType.getKey(context, "type", MSRegistryKeys.ATTACHMENT_TYPE, INVALID_KEY_EXCEPTION));
                                                            Supplier<?> initializer = type.initializer();

                                                            if (initializer != null) {
                                                                context.getSource().getPlayer().setAttached(type, initializer.get());

                                                                return Command.SINGLE_SUCCESS;
                                                            } else
                                                                return 0;
                                                        })
                                        )
                        )
        );
    }
}
