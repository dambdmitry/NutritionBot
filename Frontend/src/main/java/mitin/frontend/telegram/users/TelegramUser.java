package mitin.frontend.telegram.users;

import mitin.backend.system.diet.model.diet.DietType;
import mitin.backend.system.user.model.User;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

public class TelegramUser {
    private User user;
    private Long chatId;
    private String prevMessage;
    private SendPhoto photo;
    private DietType currentDietType;
    private String prevMenu;

    public String getPrevMenu() {
        return prevMenu;
    }

    public void setPrevMenu(String prevMenu) {
        this.prevMenu = prevMenu;
    }

    public DietType getCurrentDietType() {
        return currentDietType;
    }

    public void setCurrentDietType(DietType currentDietType) {
        this.currentDietType = currentDietType;
    }

    public SendPhoto getPhoto() {
        return photo;
    }

    public void setPhoto(SendPhoto photo) {
        this.photo = photo;
    }

    public TelegramUser(User user) {
        this.user = user;
        chatId = user.getChatId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TelegramUser that = (TelegramUser) o;

        return chatId != null ? chatId.equals(that.chatId) : that.chatId == null;
    }

    @Override
    public int hashCode() {
        return chatId != null ? chatId.hashCode() : 0;
    }

    public Long getChatId() {
        return chatId;
    }

    public String getPrevMessage() {
        return prevMessage;
    }

    public void setPrevMessage(String prevMessage) {
        this.prevMessage = prevMessage;
    }
}
