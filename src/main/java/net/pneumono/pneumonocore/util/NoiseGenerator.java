package net.pneumono.pneumonocore.util;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;
import net.minecraft.world.level.levelgen.RandomSupport;

import java.util.ArrayList;
import java.util.List;

public abstract class NoiseGenerator {
    private final int[] permutation;

    public NoiseGenerator(int[] permutation) {
        this.permutation = permutation;
    }

    public NoiseGenerator(RandomSource random) {
        this(createPermutation(random));
    }

    public NoiseGenerator(PositionalRandomFactory randomFactory, String name) {
        this(randomFactory.fromHashOf(name));
    }

    public NoiseGenerator(long seed) {
        this(RandomSource.create(seed));
    }

    public NoiseGenerator() {
        this(RandomSupport.generateUniqueSeed());
    }

    public static int[] createPermutation(RandomSource random) {
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
