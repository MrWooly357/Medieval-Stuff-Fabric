package net.mrwooly357.medievalstuff.item.custom.weapon.halberd;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;
import net.minecraft.client.render.entity.model.EntityModelPartNames;

import java.util.function.Consumer;

public final class HalberdAnimations {

    public static final Animation HALBERD_SLASHING = create(
            2.0F, builder -> builder
                    .addBoneAnimation(EntityModelPartNames.HEAD, new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                            new Keyframe(0.75F, AnimationHelper.createRotationalVector(-11.25F, 22.5F, 0.0F), Transformation.Interpolations.CUBIC),
                            new Keyframe(1.0F, AnimationHelper.createRotationalVector(-11.25F, 22.5F, 0.0F), Transformation.Interpolations.CUBIC),
                            new Keyframe(1.25F, AnimationHelper.createRotationalVector(11.25F, -22.5F, 0.0F), Transformation.Interpolations.CUBIC),
                            new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                    ))
                    .addBoneAnimation(EntityModelPartNames.BODY, new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                            new Keyframe(0.75F, AnimationHelper.createRotationalVector(0.0F, 11.25F, 0.0F), Transformation.Interpolations.CUBIC),
                            new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 12.5F, 0.0F), Transformation.Interpolations.CUBIC),
                            new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.0F, -22.5F, 0.0F), Transformation.Interpolations.CUBIC),
                            new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                    ))
                    .addBoneAnimation(EntityModelPartNames.RIGHT_ARM, new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.createRotationalVector(-43.6371F, 25.4895F, -44.7683F), Transformation.Interpolations.CUBIC),
                            new Keyframe(0.75F, AnimationHelper.createRotationalVector(-133.441F, -40.7895F, -59.6388F), Transformation.Interpolations.CUBIC),
                            new Keyframe(1.0F, AnimationHelper.createRotationalVector(-133.441F, -40.7895F, -59.6388F), Transformation.Interpolations.CUBIC),
                            new Keyframe(1.25F, AnimationHelper.createRotationalVector(15.1624F, 35.3963F, -44.7191F), Transformation.Interpolations.CUBIC),
                            new Keyframe(2.0F, AnimationHelper.createRotationalVector(-43.6371F, 25.4895F, -44.7683F), Transformation.Interpolations.CUBIC)
                    ))
                    .addBoneAnimation(EntityModelPartNames.RIGHT_ARM, new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -2.0F), Transformation.Interpolations.CUBIC),
                            new Keyframe(0.75F, AnimationHelper.createTranslationalVector(-1.0F, 0.0F, 1.0F), Transformation.Interpolations.CUBIC),
                            new Keyframe(1.0F, AnimationHelper.createTranslationalVector(-1.0F, 0.0F, 1.0F), Transformation.Interpolations.CUBIC),
                            new Keyframe(1.25F, AnimationHelper.createTranslationalVector(2.0F, -1.0F, -5.0F), Transformation.Interpolations.CUBIC),
                            new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -2.0F), Transformation.Interpolations.CUBIC)
                    ))
                    .addBoneAnimation(EntityModelPartNames.LEFT_ARM, new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.createRotationalVector(-30.3612F, 40.7895F, -20.941F), Transformation.Interpolations.CUBIC),
                            new Keyframe(0.75F, AnimationHelper.createRotationalVector(-129.1768F, -19.4483F, -67.979F), Transformation.Interpolations.CUBIC),
                            new Keyframe(1.0F, AnimationHelper.createRotationalVector(-129.1768F, -19.4483F, -67.979F), Transformation.Interpolations.CUBIC),
                            new Keyframe(1.25F, AnimationHelper.createRotationalVector(46.559F, 40.7895F, -30.3612F), Transformation.Interpolations.CUBIC),
                            new Keyframe(2.0F, AnimationHelper.createRotationalVector(-30.3612F, 40.7895F, -20.941F), Transformation.Interpolations.CUBIC)
                    ))
                    .addBoneAnimation(EntityModelPartNames.LEFT_ARM, new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -2.0F, 2.0F), Transformation.Interpolations.CUBIC),
                            new Keyframe(0.75F, AnimationHelper.createTranslationalVector(1.0F, -3.0F, -2.0F), Transformation.Interpolations.CUBIC),
                            new Keyframe(1.0F, AnimationHelper.createTranslationalVector(1.0F, -3.0F, -2.0F), Transformation.Interpolations.CUBIC),
                            new Keyframe(1.25F, AnimationHelper.createTranslationalVector(-1.0F, -2.0F, 2.0F), Transformation.Interpolations.CUBIC),
                            new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, -2.0F, 2.0F), Transformation.Interpolations.CUBIC)
                    ))
                    .addBoneAnimation(EntityModelPartNames.RIGHT_ARM, new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                            new Keyframe(0.75F, AnimationHelper.createRotationalVector(0.0F, 11.25F, 0.0F), Transformation.Interpolations.CUBIC),
                            new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 11.25F, 0.0F), Transformation.Interpolations.CUBIC),
                            new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.0F, -22.5F, 0.0F), Transformation.Interpolations.CUBIC),
                            new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                    ))
                    .addBoneAnimation(EntityModelPartNames.LEFT_LEG, new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                            new Keyframe(0.75F, AnimationHelper.createRotationalVector(-5.123F, 21.9386F, -13.4936F), Transformation.Interpolations.CUBIC),
                            new Keyframe(1.0F, AnimationHelper.createRotationalVector(-5.123F, 21.9386F, -13.4936F), Transformation.Interpolations.CUBIC),
                            new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.0F, -10.0F, 0.0F), Transformation.Interpolations.CUBIC),
                            new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                    ))
    );

    private HalberdAnimations() {}


    private static Animation create(float length, Consumer<Animation.Builder> builder) {
        Animation.Builder builder1 = Animation.Builder.create(length);
        builder.accept(builder1);

        return builder1.build();
    }
}
