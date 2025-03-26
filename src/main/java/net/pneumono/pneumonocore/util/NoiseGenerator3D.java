package net.pneumono.pneumonocore.util;

import net.minecraft.util.math.random.Random;
import net.minecraft.util.math.random.RandomSplitter;

@SuppressWarnings("unused")
public class NoiseGenerator3D extends NoiseGenerator {
    public NoiseGenerator3D(int[] permutation) {
        super(permutation);
    }

    public NoiseGenerator3D(Random random) {
        super(random);
    }

    public NoiseGenerator3D(RandomSplitter splitter, String name) {
        super(splitter, name);
    }

    public NoiseGenerator3D(long seed) {
        super(seed);
    }

    public NoiseGenerator3D() {
        super();
    }

    public double layeredNoise(double x, double y, double z, int layers) {
        return layeredStretchedNoise(x, y, z, 1, 1, layers);
    }

    public double layeredStretchedNoise(double x, double y, double z, double horizontalStretch, double verticalStretch, int layers) {
        if (layers <= 0) {
            throw new IllegalArgumentException("Number of layers cannot be less than 1");
        }
        double noise = 0;
        for (int layer = 1; layer <= layers; ++layer) {
            noise += (stretchedNoise(x, y, z, horizontalStretch / layer, verticalStretch / layer) / layer);
        }
        return noise;
    }

    public double stretchedNoise(double x, double y, double z, double horizontalStretch, double verticalStretch) {
        return noise(x / horizontalStretch, y / verticalStretch, z / horizontalStretch);
    }

    public double noise(double x, double y, double z) {
        return NoiseUtil.noise3D(x, y, z, getPermutation());
    }
}
