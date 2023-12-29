package net.pneumono.pneumonocore.util;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class TextUtil {
    public static Text parseMarkdown(String content) {
        /*
        There's a pretty good chance this method is in violation of the Geneva Convention,
        so I've explained everything thoroughly, and I'm desperately hoping that makes it understandable.
         */

        // Creates a list of all Formatting Characters
        List<Character> formattingChars = new ArrayList<>();
        formattingChars.add('*');
        formattingChars.add('_');
        formattingChars.add('~');
        formattingChars.add('\\');

        // Creates a list of unprocessed characters
        List<Character> characters = new ArrayList<>();
        for (char c : content.toCharArray()) {
            characters.add(c);
        }

        // Uses the previous list to create a list of characters, all of which have been labelled either as a Formatting Character or not one
        List<UnformattedCharacter> unformattedCharacters = new ArrayList<>();
        boolean isNextCancelled = false;
        for (char character : characters) {
            /*
            For each character: if this character was not cancelled, and is a Formatting Character, label it as such. If it is also a \, cancel the next character
             */
            boolean isFormatting = false;
            boolean isCancelled = isNextCancelled;
            isNextCancelled = false;
            if (!isCancelled && formattingChars.contains(character)) {
                isFormatting = true;
                if (character == '\\') {
                    isNextCancelled = true;
                }
            }

            unformattedCharacters.add(new UnformattedCharacter(character, isFormatting, true));
        }

        // Uses the previous list to create a list of fully processed characters, each one with data about any formatting it might have
        List<FormattingCharacter> formattingCharacters = new ArrayList<>();
        // isNextCancelled is reused from the previous method
        isNextCancelled = false;
        boolean bold = false;
        boolean italic = false;
        boolean strikethrough = false;
        boolean underline = false;
        for (int i = 0; i < unformattedCharacters.size(); ++i) {
            /*
            For each character: if not cancelled/processed:
            IF it is a formatting character: go through the rest of the characters to find its accompanying character(s), and add the correct formatting value (and mark the accompanying character(s) as processed
             */
            UnformattedCharacter character = unformattedCharacters.get(i);
            UnformattedCharacter nextCharacter;
            if (i + 1 < unformattedCharacters.size()) {
                nextCharacter = unformattedCharacters.get(i + 1);
            } else {
                nextCharacter = null;
            }
            if (!isNextCancelled) {
                if (character.formattingChar) {
                    // pair is used as a boolean, except for in a for loop once where 1 is added to the starting value if the boolean is true (so it's easier to just store an int)
                    int isPair = 0;
                    if (nextCharacter != null && nextCharacter.formattingChar && character.character == nextCharacter.character) {
                        isNextCancelled = true;
                        isPair = 1;
                    }

                    boolean shouldFormat = character.end && (isPair == 0 || (nextCharacter.end));
                    if (!shouldFormat) {
                        for (int j = i + 1 + isPair; j < unformattedCharacters.size(); ++j) {
                            UnformattedCharacter checkedCharacter = unformattedCharacters.get(j);
                            if (checkedCharacter.formattingChar && checkedCharacter.character == character.character && (isPair == 0 || ((j + 1 < unformattedCharacters.size()) && unformattedCharacters.get(j + 1).character == nextCharacter.character))) {
                                unformattedCharacters.set(j, new UnformattedCharacter(checkedCharacter.character, true, true));
                                if (isPair == 1) {
                                    unformattedCharacters.set(j + 1, new UnformattedCharacter(unformattedCharacters.get(j + 1).character, true, true));
                                }
                                shouldFormat = true;
                                break;
                            }
                        }
                    }
                    if (shouldFormat) {
                        if (isPair == 1) {
                            switch (character.character) {
                                case '*' -> bold = !bold;
                                case '_' -> underline = !underline;
                                case '~' -> strikethrough = !strikethrough;
                            }
                        } else {
                            switch (character.character) {
                                case '*', '_' -> italic = !italic;
                            }
                        }
                    }
                } else {
                    formattingCharacters.add(new FormattingCharacter(character.character, bold, italic, strikethrough, underline));
                }
            } else {
                isNextCancelled = false;
            }
        }

        // Finally, uses the previous list to create one massive piece of text
        MutableText finalMessage = Text.empty();
        for (FormattingCharacter formattingCharacter : formattingCharacters) {
            MutableText formattedCharacter = Text.literal(String.valueOf(formattingCharacter.character));
            if (formattingCharacter.bold) {
                formattedCharacter.formatted(Formatting.BOLD);
            }
            if (formattingCharacter.italic) {
                formattedCharacter.formatted(Formatting.ITALIC);
            }
            if (formattingCharacter.strikethrough) {
                formattedCharacter.formatted(Formatting.STRIKETHROUGH);
            }
            if (formattingCharacter.underline) {
                formattedCharacter.formatted(Formatting.UNDERLINE);
            }
            finalMessage.append(formattedCharacter);
        }

        return finalMessage;
    }

    public record UnformattedCharacter(char character, boolean formattingChar, boolean end) {}
    public record FormattingCharacter(char character, boolean bold, boolean italic, boolean strikethrough, boolean underline) {}
}
