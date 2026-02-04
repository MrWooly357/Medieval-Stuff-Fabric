package net.mrwooly357.medievalstuff.attachment;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.attachment.custom.AttackData;
import net.mrwooly357.medievalstuff.attachment.custom.ComboData;
import net.mrwooly357.medievalstuff.util.MSUtil;

import java.util.function.Consumer;

public final class MSAttachmentTypes {

    public static final AttachmentType<AttackData> ATTACK_DATA = register(
            "attack_data", builder -> builder
                    .initializer(() -> new AttackData(0, 0))
                    .persistent(AttackData.CODEC)
                    .syncWith(AttackData.PACKET_CODEC, AttachmentSyncPredicate.targetOnly())
    );
    public static final AttachmentType<ComboData> COMBO_DATA = register(
            "combo_data", builder -> builder
                    .initializer(() -> new ComboData(0, Integer.MAX_VALUE, 0))
                    .persistent(ComboData.CODEC)
                    .syncWith(ComboData.PACKET_CODEC, AttachmentSyncPredicate.targetOnly())
    );

    private MSAttachmentTypes() {}


    private static <A> AttachmentType<A> register(String id, Consumer<AttachmentRegistry.Builder<A>> builder) {
        return AttachmentRegistry.create(MSUtil.id(id), builder);
    }

    public static void initialize() {
        MedievalStuff.logInitialization("attachment types");
    }
}
