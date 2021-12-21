package mitin.frontend.telegram.engine;

import mitin.frontend.telegram.constant.communication.Commands;
import mitin.frontend.telegram.constant.communication.Response;
import mitin.frontend.telegram.engine.api.BotApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import java.util.List;
import java.util.Optional;

public class UnregisteredUserCommand {


    public UnregisteredUserCommand() {
    }

    public SendMessage executeRegistration(Message message) {
        Long chatId = message.getChatId();
        Optional<String> command = getCommandFromMessage(message);
        if (command.isPresent()){
            if (command.get().equals(Commands.START)){
                return BotApi.createSendMessageWithReplyKeyboard(chatId, Response.START, BotApi.createReplyKeyboard(BotApi.createButtonsFromList(List.of("Регистрация"))));
            }
        }
        return tryRegister(chatId);
    }

    private Optional<String> getCommandFromMessage(Message message) {
        Optional<MessageEntity> commandEntity = message.getEntities().stream()
                .filter(entity -> entity.getType().equals(Commands.BOT_COMMAND_IDENTIFIER))
                .findAny();
        String command = null;
        if (commandEntity.isPresent()){
            command = message.getText()
                    .substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
        }
        return Optional.ofNullable(command);
    }

    private SendMessage tryRegister(Long chatId) {
        return BotApi.createSendMessageWithReplyKeyboard(chatId, Response.UNREGISTERED_USER_RESPONSE, BotApi.createReplyKeyboard(BotApi.createButtonsFromList(List.of("Регистрация"))));
    }
}
