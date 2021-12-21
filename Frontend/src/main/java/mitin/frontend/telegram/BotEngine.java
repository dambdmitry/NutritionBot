package mitin.frontend.telegram;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface BotEngine {
    /**
     * @param chatId идентификатор пользователя
     * @return зарегистрирован ли этот пользователь.
     */
    boolean isRegisteredUser(Long chatId);
    /**
     * @param message сообщение, присланное пользователем
     * @return является ли это сообщение командой, начинающейся на /.
     */
    boolean isCommand(Message message);
    /**
     * @param message сообщение, присланное пользователем
     * @return ответ пользователю на команду, начинающуюся на /.
     */
    SendMessage executeCommand(Message message);
    /**
     * @param message сообщение, присланное пользователем
     * @return ответ пользователю на нажатие кнопки на клавиатуре.
     */
    SendMessage executeCallback(CallbackQuery message);
    /**
     * @param message сообщение, присланное пользователем
     * @return ответ пользователю на его текстовое сообщение
     */
    SendMessage executeMessage(Message message);
    /**
     * @param chatId идентификатор пользователя
     * @return есть ли в локальном хранилище фото, которое должно отправиться пользователю.
     */
    boolean hasUserPhoto(Long chatId);
    /**
     * @param chatId идентификатор пользователя
     * @return фото из локального хранилища, которое должно отправиться пользователю.
     */
    SendPhoto getPhotoForUser(Long chatId);
    /**
     * @param chatId идентификатор пользователя
     * удалить фото из локального хранилища пользователя.
     */
    void setNullPhoto(Long chatId);
}
