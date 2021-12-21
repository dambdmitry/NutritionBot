package mitin.frontend.telegram;

import mitin.frontend.telegram.constant.bot.Constant;
import mitin.frontend.telegram.engine.Engine;
import mitin.frontend.telegram.engine.UnregisteredUserCommand;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TelegramBot extends TelegramLongPollingBot {

    private BotEngine bot = new Engine();

    @Override
    public String getBotUsername() {
        return Constant.botName;
    }

    @Override
    public String getBotToken() {
        return Constant.botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()){
            handleCallback(update.getCallbackQuery());
        }
        else if (update.hasMessage()){
            handleMessage(update.getMessage());
        }
    }

    public static void main(String[] args) throws TelegramApiException {
        TelegramBot telegramBot = new TelegramBot();
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(telegramBot);
    }

    private void handleCallback(CallbackQuery callbackQuery) {
        SendMessage response = null;
        response = bot.executeCallback(callbackQuery);
        try {
            execute(response);
        } catch (TelegramApiException e) {
            System.out.println("Callback failed");
        }
    }

    private void handleMessage(Message message){
        SendMessage response = null;

        if (bot.isRegisteredUser(message.getChatId())){
            response = bot.executeMessage(message);
        } else {
            UnregisteredUserCommand cmd = new UnregisteredUserCommand();
            response = cmd.executeRegistration(message);
        }
        try {
            execute(response);
            if (bot.hasUserPhoto(message.getChatId())){
                SendPhoto photo = bot.getPhotoForUser(message.getChatId());
                execute(photo);
                bot.setNullPhoto(message.getChatId());
            }
        } catch (TelegramApiException e) {
            System.out.println("Message failed");
        }
    }
}
