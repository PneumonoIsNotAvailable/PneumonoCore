package net.pneumono.pneumonocore.datagen.enums;

public enum ConditionType {
    AND,
    OR,
    NOT,
    CONFIG;

    public static ConditionType fromString(String string) {
        return switch (string.toUpperCase()) {
            case "AND" -> AND;
            case "OR" -> OR;
            case "NOT" -> NOT;
            default -> CONFIG;
        };
    }
}
