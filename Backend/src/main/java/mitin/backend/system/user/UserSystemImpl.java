package mitin.backend.system.user;

import mitin.backend.system.UserSystem;
import mitin.backend.system.user.model.Gender;
import mitin.backend.system.user.model.User;

import java.util.*;

public class UserSystemImpl implements UserSystem {

    private List<User> users;

    private static UserSystemImpl userSystem = null;


    private UserSystemImpl() {
        users = new LinkedList<>();
    }

    //Singleton pattern
    public static UserSystem createUserSystem() {
        if (userSystem == null) {
            userSystem = new UserSystemImpl();
            return userSystem;
        }
        return userSystem;
    }


    @Override
    public boolean hasUserChat(Long chatId) {
        return getUser(chatId).isPresent();
    }

    @Override
    public User startRegistry(Long chatId) {
        User user = new User(chatId);
        users.add(user);
        return user;
    }

    @Override
    public List<String> getUnregisterParameters(Long chatId) {
        User user = getUser(chatId).get();
        return getNullUserParameters(user);
    }

    @Override
    public void setUserActivity(Long chatId, int activity) {
        Optional<User> user = getUser(chatId);
        if (user.isPresent()) {
            user.get().setActivity(activity);
        } else {
            throw new RuntimeException("Такого юзера нет");
        }
    }

    @Override
    public void setUserAge(Long chatId, int age) {
        Optional<User> user = getUser(chatId);
        if (user.isPresent()) {
            user.get().setAge(age);
        } else {
            throw new RuntimeException("Такого юзера нет");
        }
    }

    @Override
    public void setUserHeight(Long chatId, int height) {
        Optional<User> user = getUser(chatId);
        if (user.isPresent()) {
            user.get().setHeight(height);
        } else {
            throw new RuntimeException("Такого юзера нет");
        }
    }

    @Override
    public void setUserWeight(Long chatId, int weight) {
        Optional<User> user = getUser(chatId);
        if (user.isPresent()) {
            user.get().setWeight(weight);
        } else {
            throw new RuntimeException("Такого юзера нет");
        }
    }

    @Override
    public void setUserGender(Long chatId, String usrParamValue) {
        Optional<User> user = getUser(chatId);
        if (user.isPresent()) {
            Gender gender = null;
            if (usrParamValue.equals("Мужской")) {
                gender = Gender.MALE;
            } else if (usrParamValue.equals("Женский")) {
                gender = Gender.FEMALE;
            }
            user.get().setSex(gender);
        } else {
            throw new RuntimeException("Такого юзера нет");
        }
    }

    @Override
    public void removeUser(Long chatId) {
        users.remove(users.stream().filter(u -> u.getChatId().equals(chatId)).findAny().get());
    }

    @Override
    public Map<String, String> getUserParameters(Long chatId) {
        Optional<User> optionalUser = getUser(chatId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return Map.of("Активность", user.getActivity().toString(),
                    "Вес", user.getWeight().toString(),
                    "Рост", user.getHeight().toString(),
                    "Возраст", user.getAge().toString(),
                    "Пол", user.getSex().toString());
        }
        throw new RuntimeException("Такого юзера нет");
    }

    private List<String> getNullUserParameters(User user) {
        List<String> nullParameters = new LinkedList<>();
        if (user.getActivity() == null) {
            nullParameters.add("Активность");
        }
        if (user.getAge() == null) {
            nullParameters.add("Возраст");
        }
        if (user.getHeight() == null) {
            nullParameters.add("Рост");
        }
        if (user.getWeight() == null) {
            nullParameters.add("Вес");
        }
        if (user.getSex() == null) {
            nullParameters.add("Пол");
        }

        return nullParameters;
    }

    private Optional<User> getUser(Long chatId) {
        return users.stream().filter(user -> user.getChatId().equals(chatId)).findAny();
    }
}
