package mitin.frontend.telegram.engine;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface BotEngine {
    boolean isRegisteredUser(Long chatId);

    boolean isCommand(Message message);

    SendMessage executeCommand(Message message);

    SendMessage executeCallback(CallbackQuery message);

    SendMessage executeMessage(Message message);

    boolean hasUserPhoto(Long chatId);

    SendPhoto getPhotoForUser(Long chatId);

    void setNullPhoto(Long chatId);
}
