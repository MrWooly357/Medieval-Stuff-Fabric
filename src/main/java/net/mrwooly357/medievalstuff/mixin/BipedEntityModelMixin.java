package net.mrwooly357.medievalstuff.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.entity.custom.AnimatedEntity;
import net.mrwooly357.medievalstuff.entity.model.AnimatedEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.function.Function;

@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin<LE extends LivingEntity> implements AnimatedEntityModel {

    @Unique
    private ModelPart root;


    @Override
    public ModelPart getRoot() {
        return root;
    }

    @Override
    public Optional<ModelPart> getChild(String id) {
        ModelPart root = getRoot();

        return id.equals(EntityModelPartNames.RODS) ? Optional.of(root) : root.traverse()
                .filter(part -> part.hasChild(id)).findFirst()
                .map(part -> part.getChild(id));
    }

    @Inject(method = "<init>(Lnet/minecraft/client/model/ModelPart;Ljava/util/function/Function;)V", at = @At("TAIL"))
    private void injectConstructor(ModelPart root, Function<Identifier, RenderLayer> renderLayerFactory, CallbackInfo ci) {
        this.root = root;
    }

    @Inject(method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V", at = @At("HEAD"))
    private void injectSetAnglesHead(LE entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, CallbackInfo ci) {
        getRoot().traverse().forEach(ModelPart::resetTransform);
    }

    @Inject(method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
    private void injectSetAnglesTail(LE entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, CallbackInfo ci) {
        ((AnimatedEntity) entity).updateAnimations(entity.age + MinecraftClient.getInstance().getRenderTickCounter().getTickDelta(true), this);
    }
}
