package net.mrwooly357.medievalstuff.event;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.mrwooly357.medievalstuff.attachment.MSAttachmentTypes;
import net.mrwooly357.medievalstuff.attachment.custom.ComboData;
import net.mrwooly357.medievalstuff.util.NumberFormats;

public final class MSHudRenderEvents {

    private MSHudRenderEvents() {}


    public static void initialize() {
        HudRenderCallback.EVENT.register((context, renderTickCounter) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (!client.options.hudHidden) {
                ClientPlayerEntity player = client.player;

                if (player != null) {
                    ComboData weaponUserData = player.getAttached(MSAttachmentTypes.COMBO_DATA);
                    int combo = weaponUserData.combo();
                    int remainingComboTicks = weaponUserData.remainingComboTicks();

                    if (combo > 0)
                        context.drawText(client.textRenderer, Text.literal(String.valueOf(combo)), 332, 178, 0xFFFFFFFF, false);

                    if (remainingComboTicks > 0)
                        context.drawText(client.textRenderer, Text.literal(NumberFormats.A_AA.format(remainingComboTicks / 20.0F)), 332, 188, 0xFFFFFFFF, false);
                }
            }
        });
    }
}
