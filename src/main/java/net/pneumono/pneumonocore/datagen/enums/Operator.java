package net.pneumono.pneumonocore.datagen.enums;

public enum Operator {
    EQUAL,
    LESS,
    GREATER,
    LESS_OR_EQUAL,
    GREATER_OR_EQUAL;

    public static Operator fromString(String string) {
        return switch (string.toUpperCase()) {
            case "LESS" -> LESS;
            case "GREATER" -> GREATER;
            case "LESS_OR_EQUAL" -> LESS_OR_EQUAL;
            case "GREATER_OR_EQUAL" -> GREATER_OR_EQUAL;
            default -> EQUAL;
        };
    }
}
