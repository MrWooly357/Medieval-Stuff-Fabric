package net.mrwooly357.medievalstuff.entity.model;

import net.minecraft.client.model.ModelPart;
import org.joml.Vector3f;

import java.util.Optional;

public interface AnimatedEntityModel {

    Vector3f TEMPORARY_VECTOR = new Vector3f();


    ModelPart getRoot();

    Optional<ModelPart> getChild(String id);
}
