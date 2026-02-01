package net.mrwooly357.medievalstuff.attachment;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.attachment.custom.WeaponUserData;
import net.mrwooly357.medievalstuff.util.MSUtil;

import java.util.function.Consumer;

public final class MSAttachmentTypes {

    public static final AttachmentType<WeaponUserData> WEAPON_USER_DATA = register(
            "weapon_user_data", builder -> builder
                    .initializer(() -> new WeaponUserData(0, 0))
                    .persistent(WeaponUserData.CODEC)
                    .syncWith(WeaponUserData.PACKET_CODEC, AttachmentSyncPredicate.targetOnly())
    );

    private MSAttachmentTypes() {}


    private static <A> AttachmentType<A> register(String id, Consumer<AttachmentRegistry.Builder<A>> builder) {
        return AttachmentRegistry.create(MSUtil.id(id), builder);
    }

    public static void initialize() {
        MedievalStuff.logInitialization("attachment types");
    }
}
