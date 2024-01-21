package net.pneumono.pneumonocore.util;

import net.minecraft.util.math.BlockPos;

@SuppressWarnings("unused")
public class PneumonoMathHelper {
    public static double toRadians(double degrees) {
        return degrees * Math.PI / 180;
    }

    public static double toDegrees(double radians) {
        return radians * 180 / Math.PI;
    }

    public static double horizontalDistanceBetween(BlockPos pos1, BlockPos pos2) {
        double xDifference = Math.abs(pos1.getX() - pos2.getX());
        double zDifference = Math.abs(pos1.getZ() - pos2.getZ());
        return Math.sqrt((xDifference * xDifference) + (zDifference * zDifference));
    }

    public static int round(double d) {
        return (int)d + 0.5 > d ? (int)d : (int)d + 1;
    }

    public static int round(float f) {
        return (int)f + 0.5 > f ? (int)f : (int)f + 1;
    }
}
