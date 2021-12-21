package mitin.backend.system;

import mitin.backend.system.user.model.User;

import java.util.List;
import java.util.Map;

public interface UserSystem {

    /**
     * @param chatId идентификатор пользователя
     * @return имеется ли такой пользователь в системе
     */
    boolean hasUser(Long chatId);

    /**
     * @param chatId идентификатор пользователя
     * @return созданный пользователь
     */
    User createUser(Long chatId);

    /**
     * @param chatId идентификатор пользователя
     * @return список незарегистрированных параметров пользователя
     */
    List<String> getUnregisterParameters(Long chatId);

    /**
     * Методы установки параметра сущности User
     */
    void setUserActivity(Long chatId, int activity);

    void setUserAge(Long chatId, int age);

    void setUserHeight(Long chatId, int height);

    void setUserWeight(Long chatId, int weight);

    void setUserGender(Long chatId, String usrParamValue);

    /**
     * @param chatId идентификатор пользователя
     *               Удаляет пользователя из системы бота.
     */
    void removeUser(Long chatId);

    /**
     * @param chatId идентификатор пользователя
     * @return таблица ключ-значение, ключ - название параметра, значение - значение параметра
     */
    Map<String, String> getUserParameters(Long chatId);

}
