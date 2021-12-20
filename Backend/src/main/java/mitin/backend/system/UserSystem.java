package mitin.backend.system;

import mitin.backend.system.user.model.User;

import java.util.List;
import java.util.Map;

public interface UserSystem {

    boolean hasUserChat(Long chatId);

    User startRegistry(Long chatId);

    List<String> getUnregisterParameters(Long chatId);

    void setUserActivity(Long chatId, int activity);

    void setUserAge(Long chatId, int age);

    void setUserHeight(Long chatId, int height);

    void setUserWeight(Long chatId, int weight);

    void setUserGender(Long chatId, String usrParamValue);

    void removeUser(Long chatId);

    Map<String, String> getUserParameters(Long chatId);


}
