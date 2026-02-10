package net.mrwooly357.medievalstuff.block.custom.spawner.dark;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public final class DarkSpawnerBlockEntityRenderer implements BlockEntityRenderer<DarkSpawnerBlockEntity> {


    @Override
    public void render(DarkSpawnerBlockEntity darkSpawner, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {}
}
