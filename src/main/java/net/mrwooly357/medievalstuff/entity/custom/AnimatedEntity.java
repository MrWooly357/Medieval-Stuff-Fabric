package net.mrwooly357.medievalstuff.entity.custom;

import net.minecraft.client.render.entity.animation.Animation;
import net.mrwooly357.medievalstuff.entity.model.AnimatedEntityModel;

public interface AnimatedEntity {


    boolean isAnimationPlaying(Animation animation);

    void startPlayingAnimation(Animation animation, float speedMultiplier);

    void updateAnimations(float progress, AnimatedEntityModel model);
}
