package net.mrwooly357.medievalstuff.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.mrwooly357.medievalstuff.attachment.MSAttachmentTypes;
import net.mrwooly357.medievalstuff.attachment.custom.ComboData;
import net.mrwooly357.medievalstuff.entity.custom.AnimatedEntity;
import net.mrwooly357.medievalstuff.entity.custom.WeaponUser;
import net.mrwooly357.medievalstuff.entity.model.AnimatedEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements WeaponUser, AnimatedEntity {

    @Unique
    private final Map<Animation, Pair<AnimationState, Float>> animations = new HashMap<>();


    @Inject(method = "tick", at = @At("TAIL"))
    private void injectTick(CallbackInfo ci) {
        if (!((LivingEntity) (Object) this).getWorld().isClient())
            WeaponUser.tick(this);
    }

    @Override
    public ComboData getComboData() {
        return ((LivingEntity) (Object) this).getAttachedOrCreate(MSAttachmentTypes.COMBO_DATA);
    }

    @Override
    public void setComboData(int combo, int ticksAfterPrevious, int remainingComboTicks) {
        ((LivingEntity) (Object) this).setAttached(MSAttachmentTypes.COMBO_DATA, new ComboData(combo, ticksAfterPrevious, remainingComboTicks));
    }

    @Override
    public boolean isAnimationPlaying(Animation animation) {
        return Optional.ofNullable(animations.get(animation)).map(pair -> pair.getFirst().isRunning()).orElse(false);
    }

    @Override
    public void startPlayingAnimation(Animation animation, float speedMultiplier) {
        animations.put(animation, Pair.of(new AnimationState(), speedMultiplier));
        animations.get(animation).getFirst().start(((LivingEntity) (Object) this).age);
    }

    @Override
    public void updateAnimations(float progress, AnimatedEntityModel model) {
        animations.forEach((animation, pair) -> {
            AnimationState state = pair.getFirst();
            state.update(progress, pair.getSecond());
            state.run(state1 -> {
                float runningSeconds = AnimationHelper.getRunningSeconds(animation, state.getTimeRunning());

                for (Map.Entry<String, List<Transformation>> entry : animation.boneAnimations().entrySet()) {
                    model.getChild(entry.getKey()).ifPresent(part1 -> entry.getValue().forEach(transformation -> {
                        Keyframe[] keyframes = transformation.keyframes();
                        int i = Math.max(0, MathHelper.binarySearch(0, keyframes.length, index -> runningSeconds <= keyframes[index].timestamp()) - 1);
                        int j = Math.min(keyframes.length - 1, i + 1);
                        Keyframe keyframe = keyframes[i];
                        Keyframe keyframe1 = keyframes[j];
                        float f;

                        if (j != i) {
                            float timestamp = keyframe.timestamp();
                            f = MathHelper.clamp((runningSeconds - timestamp) / (keyframe1.timestamp() - timestamp), 0.0F, 1.0F);
                        } else
                            f = 0.0F;

                        keyframe1.interpolation().apply(AnimatedEntityModel.TEMPORARY_VECTOR, f, keyframes, i, j, 1.0F);
                        transformation.target().apply(part1, AnimatedEntityModel.TEMPORARY_VECTOR);
                    }));
                }
            });
        });
    }
}
