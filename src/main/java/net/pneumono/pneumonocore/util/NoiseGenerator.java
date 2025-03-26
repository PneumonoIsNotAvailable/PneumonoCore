package net.pneumono.pneumonocore.util;

import net.minecraft.util.math.random.Random;
import net.minecraft.util.math.random.RandomSeed;
import net.minecraft.util.math.random.RandomSplitter;

import java.util.ArrayList;
import java.util.List;

public abstract class NoiseGenerator {
    private final int[] permutation;

    public NoiseGenerator(int[] permutation) {
        this.permutation = permutation;
    }

    public NoiseGenerator(Random random) {
        this(createPermutation(random));
    }

    public NoiseGenerator(RandomSplitter splitter, String name) {
        this(splitter.split(name));
    }

    public NoiseGenerator(long seed) {
        this(Random.create(seed));
    }

    public NoiseGenerator() {
        this(RandomSeed.getSeed());
    }

    public static int[] createPermutation(Random random) {
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 256; ++i) {
            integers.add(i);
        }
        int[] values = new int[512];
        for (int i = 0; i < 256; ++i) {
            Integer integer = integers.get(random.nextInt(integers.size()));
            values[i] = integer;
            values[i + 256] = integer;
            integers.remove(integer);
        }
        return values;
    }

    public int[] getPermutation() {
        return permutation;
    }
}
