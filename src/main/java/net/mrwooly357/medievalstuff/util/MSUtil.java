package net.mrwooly357.medievalstuff.util;

import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.MedievalStuff;

public final class MSUtil {

    private MSUtil() {}


    public static Identifier id(String path) {
        return Identifier.of(MedievalStuff.MOD_ID, path);
    }
}
