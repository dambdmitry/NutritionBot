package mitin.frontend.telegram.engine.implementation.factory;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BotApi {

    public static SendMessage createSendMessage(Long chatId, String text) {
        return SendMessage.builder()
                .chatId(chatId.toString())
                .text(text)
                .build();
    }

    public static SendMessage createMarkdownSendMessage(Long chatId, String text) {
        return SendMessage.builder()
                .chatId(chatId.toString())
                .text(text)
                .parseMode("Markdown")
                .build();
    }

    public static SendPhoto createSendMessageWithPicture(Long chatId, String path) {
        SendPhoto photo = SendPhoto
                .builder()
                .chatId(chatId.toString())
                .photo(new InputFile(new java.io.File(path))).build();
        return photo;
    }

    public static SendMessage createSendMessageWithKeyboard(Long chatId, String text, List<KeyboardRow> keyboard, ReplyKeyboardMarkup keyboardMarkup) {
        keyboardMarkup.setKeyboard(keyboard);
        return SendMessage.builder()
                .chatId(chatId.toString())
                .replyMarkup(keyboardMarkup)
                .text(text)
                .build();
    }

    ;

    public static SendMessage createSendMessageWithReplyKeyboard(Long chatId, String text, InlineKeyboardMarkup keyboard) {
        return SendMessage.builder()
                .chatId(chatId.toString())
                .text(text)
                .replyMarkup(keyboard)
                .build();
    }

    public static List<List<InlineKeyboardButton>> createButtonsFromList(List<String> buttons) {
        List<List<InlineKeyboardButton>> result = new ArrayList<>();
        for (String button : buttons) {
            result.add(Arrays.asList(
                    InlineKeyboardButton.builder()
                            .text(button)
                            .callbackData(button)
                            .build()));
        }
        return result;
    }

    public static InlineKeyboardMarkup createReplyKeyboard(List<List<InlineKeyboardButton>> buttons) {
        return InlineKeyboardMarkup.builder()
                .keyboard(buttons)
                .build();
    }

    public static ReplyKeyboardMarkup createReplyKeyboardMarkup(boolean selective, boolean resize, boolean oneTime) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(selective);
        replyKeyboardMarkup.setResizeKeyboard(resize);
        replyKeyboardMarkup.setOneTimeKeyboard(oneTime);
        return replyKeyboardMarkup;
    }

    public static List<KeyboardRow> createKeyboard(List<String> buttons) {
        ArrayList<KeyboardRow> keyboard = new ArrayList<>();
        for (String button : buttons) {
            KeyboardRow row = new KeyboardRow();
            row.add(button);
            keyboard.add(row);
        }
        return keyboard;
    }

}
