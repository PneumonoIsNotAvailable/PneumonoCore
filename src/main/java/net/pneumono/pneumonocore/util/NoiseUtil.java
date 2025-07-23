package net.pneumono.pneumonocore.util;

@SuppressWarnings("unused")
public final class NoiseUtil {
    private static final int[] defaultPermutation = {
            151,160,137, 91, 90, 15,131, 13,201, 95, 96, 53,194,233,  7,225,
            140, 36,103, 30, 69,142,  8, 99, 37,240, 21, 10, 23,190,  6,148,
            247,120,234, 75,  0, 26,197, 62, 94,252,219,203,117, 35, 11, 32,
             57,177, 33, 88,237,149, 56, 87,174, 20,125,136,171,168, 68,175,
             74,165, 71,134,139, 48, 27,166, 77,146,158,231, 83,111,229,122,
             60,211,133,230,220,105, 92, 41, 55, 46,245, 40,244,102,143, 54,
             65, 25, 63,161,  1,216, 80, 73,209, 76,132,187,208, 89, 18,169,
            200,196,135,130,116,188,159, 86,164,100,109,198,173,186,  3, 64,
             52,217,226,250,124,123,  5,202, 38,147,118,126,255, 82, 85,212,
            207,206, 59,227, 47, 16, 58, 17,182,189, 28, 42,223,183,170,213,
            119,248,152,  2, 44,154,163, 70,221,153,101,155,167, 43,172,  9,
            129, 22, 39,253, 19, 98,108,110, 79,113,224,232,178,185,112,104,
            218,246, 97,228,251, 34,242,193,238,210,144, 12,191,179,162,241,
             81, 51,145,235,249, 14,239,107, 49,192,214, 31,181,199,106,157,
            184, 84,204,176,115,121, 50, 45,127,  4,150,254,138,236,205, 93,
            222,114, 67, 29, 24, 72,243,141,128,195, 78, 66,215, 61,156,180,
            151,160,137, 91, 90, 15,131, 13,201, 95, 96, 53,194,233,  7,225,
            140, 36,103, 30, 69,142,  8, 99, 37,240, 21, 10, 23,190,  6,148,
            247,120,234, 75,  0, 26,197, 62, 94,252,219,203,117, 35, 11, 32,
             57,177, 33, 88,237,149, 56, 87,174, 20,125,136,171,168, 68,175,
             74,165, 71,134,139, 48, 27,166, 77,146,158,231, 83,111,229,122,
             60,211,133,230,220,105, 92, 41, 55, 46,245, 40,244,102,143, 54,
             65, 25, 63,161,  1,216, 80, 73,209, 76,132,187,208, 89, 18,169,
            200,196,135,130,116,188,159, 86,164,100,109,198,173,186,  3, 64,
             52,217,226,250,124,123,  5,202, 38,147,118,126,255, 82, 85,212,
            207,206, 59,227, 47, 16, 58, 17,182,189, 28, 42,223,183,170,213,
            119,248,152,  2, 44,154,163, 70,221,153,101,155,167, 43,172,  9,
            129, 22, 39,253, 19, 98,108,110, 79,113,224,232,178,185,112,104,
            218,246, 97,228,251, 34,242,193,238,210,144, 12,191,179,162,241,
             81, 51,145,235,249, 14,239,107, 49,192,214, 31,181,199,106,157,
            184, 84,204,176,115,121, 50, 45,127,  4,150,254,138,236,205, 93,
            222,114, 67, 29, 24, 72,243,141,128,195, 78, 66,215, 61,156,180
    };

    public static double noise2D(double x, double y, int[] permutation) {
        int X = (int)Math.floor(x) & 255;
        int Y = (int)Math.floor(y) & 255;
        x -= Math.floor(x);
        y -= Math.floor(y);
        double u = fade(x);
        double v = fade(y);
        int A = permutation[X  ]+Y, AA = permutation[A], AB = permutation[A+1];
        int B = permutation[X+1]+Y, BA = permutation[B], BB = permutation[B+1];

        return lerp(
                v,
                lerp(
                        u,
                        grad2D(permutation[AA], x, y),
                        grad2D(permutation[BA], x-1, y)
                ),
                lerp(
                        u,
                        grad2D(permutation[AB], x, y-1),
                        grad2D(permutation[BB], x-1, y-1)
                )
        );
    }

    private static double grad2D(int hash, double x, double y) {
        int h = hash & 15;
        double u = h<8 ? x : y,
                v = h<4 ? y : h==12||h==14 ? x : 0;
        return ((h&1) == 0 ? u : -u) + ((h&2) == 0 ? v : -v);
    }

    public static double noise3D(double x, double y, double z, int[] permutation) {
        int noiseUnitX = (int)Math.floor(x) & 255;
        int noiseUnitY = (int)Math.floor(y) & 255;
        int noiseUnitZ = (int)Math.floor(z) & 255;
        double xWithinUnit = x - Math.floor(x);
        double yWithinUnit = y - Math.floor(y);
        double zWithinUnit = z - Math.floor(z);
        double u = fade(xWithinUnit);
        double v = fade(yWithinUnit);
        double w = fade(zWithinUnit);
        int A = permutation[noiseUnitX  ]+noiseUnitY, AA = permutation[A]+noiseUnitZ, AB = permutation[A+1]+noiseUnitZ;
        int B = permutation[noiseUnitX+1]+noiseUnitY, BA = permutation[B]+noiseUnitZ, BB = permutation[B+1]+noiseUnitZ;

        return lerp(w,
                lerp(
                        v,
                        lerp(
                                u,
                                grad3D(permutation[AA], xWithinUnit, yWithinUnit, zWithinUnit),
                                grad3D(permutation[BA], xWithinUnit-1, yWithinUnit, zWithinUnit)
                        ),
                        lerp(
                                u,
                                grad3D(permutation[AB], xWithinUnit, yWithinUnit-1, zWithinUnit),
                                grad3D(permutation[BB], xWithinUnit-1, yWithinUnit-1, zWithinUnit)
                        )
                ),
                lerp(
                        v,
                        lerp(
                                u,
                                grad3D(permutation[AA+1], xWithinUnit, yWithinUnit, zWithinUnit-1),
                                grad3D(permutation[BA+1], xWithinUnit-1, yWithinUnit, zWithinUnit-1)
                        ),
                        lerp(
                                u,
                                grad3D(permutation[AB+1], xWithinUnit, yWithinUnit-1, zWithinUnit-1),
                                grad3D(permutation[BB+1], xWithinUnit-1, yWithinUnit-1, zWithinUnit-1)
                        )
                )
        );
    }

    private static double grad3D(int hash, double x, double y, double z) {
        int h = hash & 15;
        double u = h<8 ? x : y,
                v = h<4 ? y : h==12||h==14 ? x : z;
        return ((h&1) == 0 ? u : -u) + ((h&2) == 0 ? v : -v);
    }

    private static double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    private static double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }
}
