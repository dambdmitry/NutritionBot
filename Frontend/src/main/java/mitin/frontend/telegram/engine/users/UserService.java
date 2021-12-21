package mitin.frontend.telegram.engine.users;

import mitin.backend.system.UserSystem;
import mitin.backend.system.user.UserSystemImpl;
import mitin.frontend.telegram.constant.user.UserParameters;
import mitin.frontend.telegram.engine.users.storage.TelegramUser;

import java.util.*;

public class UserService {
    private UserSystem system;

    private List<TelegramUser> users;

    private static UserService userService = null;

    private UserService() {
        system = UserSystemImpl.createUserSystem();
        users = new LinkedList<>();
    }

    public static UserService createUserSystem() {
        return Objects.requireNonNullElseGet(userService, UserService::new);
    }

    public boolean checkUnregisterUser(Long chatId) {
        return system.hasUser(chatId);
    }

    public void registerUser(Long chatId) {
        users.add(new TelegramUser(system.createUser(chatId)));
    }

    public List<String> getUserUnregisterParameters(Long chatId) {
        return system.getUnregisterParameters(chatId);
    }

    public TelegramUser getTgUser(Long chatId) {
        Optional<TelegramUser> optUser = users.stream()
                .filter(u -> u.getChatId().equals(chatId)).findAny();

        if (optUser.isPresent()) {
            return optUser.get();
        }

        throw new RuntimeException("В стеке нет такого пользователся");

    }

    public void setUserParamValue(Long chatId, String usrParamValue) throws Exception {
        TelegramUser user = getTgUser(chatId);
        if (user.getPrevMessage() == null) {
            throw new RuntimeException("Выберете параметр");
        }

        if (user.getPrevMessage().equals(UserParameters.ACTIVITY)) {
            int activity;
            try {
                activity = Integer.parseInt(usrParamValue);
            } catch (NumberFormatException e) {
                throw new Exception("Введите число от 1 до 10");
            }
            if (activity > 10 || activity < 1) {
                throw new Exception("Введите число от 1 до 10");
            }
            system.setUserActivity(chatId, activity);
        } else if (user.getPrevMessage().equals(UserParameters.AGE)) {
            int age;
            try {
                age = Integer.parseInt(usrParamValue);
            } catch (NumberFormatException e) {
                throw new Exception("Введите число");
            }
            if (age < 12 || age > 120) {
                throw new Exception("Прошу прощения, я не могу работать с таким возрастом");
            }
            system.setUserAge(chatId, age);
        } else if (user.getPrevMessage().equals(UserParameters.HEIGHT)) {
            int height;
            try {
                height = Integer.parseInt(usrParamValue);
            } catch (NumberFormatException e) {
                throw new Exception("Введите число");
            }
            if (height < 120 || height > 260) {
                throw new Exception("Прошу прощения, я не могу работать с таким ростом");
            }
            system.setUserHeight(chatId, height);
        } else if (user.getPrevMessage().equals(UserParameters.WEIGHT)) {
            int weight;
            try {
                weight = Integer.parseInt(usrParamValue);
            } catch (NumberFormatException e) {
                throw new Exception("Введите число");
            }
            if (weight < 35 || weight > 260) {
                throw new Exception("Прошу прощения, я не могу работать с таким весом");
            }
            system.setUserWeight(chatId, weight);
        } else if (user.getPrevMessage().equals(UserParameters.GENDER)) {
            if (!usrParamValue.equals("Мужской") && !usrParamValue.equals("Женский")) {
                throw new Exception("Выберете на клавиатуре мужской или женский");
            }
            if (system.getUnregisterParameters(chatId).contains("Пол")) {
                system.setUserGender(chatId, usrParamValue);
            } else {
                throw new Exception("Параметр пол уже зарегистрирован");
            }
        }
        user.setPrevMessage(null);
    }

    public boolean hasUserUnregisteredParams(Long chatId) {
        return !system.getUnregisterParameters(chatId).isEmpty();
    }

    public void deleteUser(Long chatId) {
        users.remove(users.stream().filter(u -> u.getChatId().equals(chatId)).findAny().get());
        system.removeUser(chatId);
    }

    public boolean hasUserParam(Long chatId, String key) {
        List<String> unregisterParameters = system.getUnregisterParameters(chatId);
        return !unregisterParameters.contains(key);
    }

    public Map<String, String> getUser(Long chatId) {
        return system.getUserParameters(chatId);
    }
}
