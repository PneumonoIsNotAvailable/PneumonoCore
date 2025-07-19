package net.pneumono.pneumonocore.util;

public class PneumonoCoreUtil {
    public static <T extends Enum<T>> T cycleEnum(T value) {
        T[] enumConstants = value.getDeclaringClass().getEnumConstants();
        for (int i = 0; i < enumConstants.length; ++i) {
            T enumConstant = enumConstants[i];
            if (enumConstant == value) {
                return enumConstants[i + 1 % enumConstants.length];
            }
        }
        return value;
    }
}
