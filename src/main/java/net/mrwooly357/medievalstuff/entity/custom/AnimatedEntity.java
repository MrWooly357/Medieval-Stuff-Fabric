package net.mrwooly357.medievalstuff.entity.custom;

import net.minecraft.client.render.entity.animation.Animation;
import net.mrwooly357.medievalstuff.entity.model.AnimatedEntityModel;

public interface AnimatedEntity {


    boolean isAnimationPlaying(Animation animation);

    void startAnimation(Animation animation, float speedMultiplier);

    void stopAnimation(Animation animation);

    void updateAnimations(float progress, AnimatedEntityModel model);
}
