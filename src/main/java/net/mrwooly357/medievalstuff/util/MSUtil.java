package net.mrwooly357.medievalstuff.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.mrwooly357.medievalstuff.MedievalStuff;

public final class MSUtil {

    public static final Codec<Box> BOX_CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.DOUBLE.fieldOf("min_x").forGetter(box -> box.minX),
                    Codec.DOUBLE.fieldOf("min_y").forGetter(box -> box.minY),
                    Codec.DOUBLE.fieldOf("min_z").forGetter(box -> box.minZ),
                    Codec.DOUBLE.fieldOf("max_x").forGetter(box -> box.maxX),
                    Codec.DOUBLE.fieldOf("max_y").forGetter(box -> box.maxY),
                    Codec.DOUBLE.fieldOf("max_z").forGetter(box -> box.maxZ)
            )
                    .apply(instance, Box::new)
    );
    public static final Codec<SpawnReason> SPAWN_REASON_CODEC = Codec.STRING.xmap(SpawnReason::valueOf, Enum::name);

    private MSUtil() {}


    public static Identifier id(String path) {
        return Identifier.of(MedievalStuff.MOD_ID, path);
    }
}
