package net.mrwooly357.medievalstuff.attachment;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.attachment.custom.AttackPreparationData;
import net.mrwooly357.medievalstuff.attachment.custom.ComboData;
import net.mrwooly357.medievalstuff.registry.MSRegistries;
import net.mrwooly357.medievalstuff.util.MSUtil;

import java.util.function.Consumer;

public final class MSAttachmentTypes {

    public static final AttachmentType<AttackPreparationData> ATTACK_PREPARATION_DATA = register(
            "attack_preparation_data", builder -> builder
                    .initializer(() -> new AttackPreparationData(0))
                    .persistent(AttackPreparationData.CODEC)
                    .syncWith(AttackPreparationData.PACKET_CODEC, AttachmentSyncPredicate.targetOnly())
    );
    public static final AttachmentType<ComboData> COMBO_DATA = register(
            "combo_data", builder -> builder
                    .initializer(() -> new ComboData(0, Integer.MAX_VALUE, 0))
                    .persistent(ComboData.CODEC)
                    .syncWith(ComboData.PACKET_CODEC, AttachmentSyncPredicate.targetOnly())
    );

    private MSAttachmentTypes() {}


    private static <A> AttachmentType<A> register(String id, Consumer<AttachmentRegistry.Builder<A>> builder) {
        Identifier id1 = MSUtil.id(id);

        return Registry.register(MSRegistries.ATTACHMENT_TYPE, id1, AttachmentRegistry.create(id1, builder));
    }

    public static void initialize() {
        MedievalStuff.logInitialization("attachment types");
    }
}
