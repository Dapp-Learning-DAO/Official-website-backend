package com.dl.officialsite.bot.telegram;

public class TelegramMarkdownV2Escaper {

    private static final String[] SPECIAL_CHARACTERS = {"\\", "_", "*", "[", "]", "(", ")", "~", "`", ">", "#", "+", "-", "=", "|", "{",
        "}", ".", "!"};

    /**
     * Escapes characters in a string according to Telegram's MarkdownV2 requirements.
     *
     * @param text The text to be escaped.
     * @return The escaped text.
     */
    public static String escapeText(String text) {
        // Characters to be escaped in MarkdownV2. Note that backslash must be escaped first.

        for (String specialCharacter : SPECIAL_CHARACTERS) {
            text = text.replace(specialCharacter, "\\" + specialCharacter);
        }

        return text;
    }

}