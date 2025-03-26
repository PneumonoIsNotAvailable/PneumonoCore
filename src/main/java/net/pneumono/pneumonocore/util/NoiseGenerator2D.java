package net.pneumono.pneumonocore.util;

import net.minecraft.util.math.random.Random;
import net.minecraft.util.math.random.RandomSplitter;

@SuppressWarnings("unused")
public class NoiseGenerator2D extends NoiseGenerator {
    public NoiseGenerator2D(int[] permutation) {
        super(permutation);
    }

    public NoiseGenerator2D(Random random) {
        super(random);
    }

    public NoiseGenerator2D(RandomSplitter splitter, String name) {
        super(splitter, name);
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
