package net.pneumono.pneumonocore.util;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;

@SuppressWarnings("unused")
public class NoiseGenerator2D extends NoiseGenerator {
    public NoiseGenerator2D(int[] permutation) {
        super(permutation);
    }

    public NoiseGenerator2D(RandomSource random) {
        super(random);
    }

    public NoiseGenerator2D(PositionalRandomFactory randomFactory, String name) {
        super(randomFactory, name);
    }

    public NoiseGenerator2D(long seed) {
        super(seed);
    }

    public NoiseGenerator2D() {
        super();
    }

    public double layeredNoise(double x, double z, int layers) {
        return layeredStretchedNoise(x, z, 1, layers);
    }

    public double layeredStretchedNoise(double x, double z, double horizontalStretch, int layers) {
        if (layers <= 0) {
            throw new IllegalArgumentException("Number of layers cannot be less than 1");
        }
        double noise = 0;
        for (int layer = 1; layer <= layers; ++layer) {
            noise += (stretchedNoise(x, z, horizontalStretch / layer) / layer);
        }
        return noise;
    }

    public double stretchedNoise(double x, double z, double horizontalStretch) {
        return noise(x / horizontalStretch, z / horizontalStretch);
    }

    public double noise(double x, double z) {
        return NoiseUtil.noise2D(x, z, getPermutation());
    }
}
