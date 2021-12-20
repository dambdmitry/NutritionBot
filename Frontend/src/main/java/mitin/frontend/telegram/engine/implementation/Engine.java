package mitin.frontend.telegram.engine.implementation;

import mitin.backend.system.diet.model.diet.DietType;
import mitin.backend.system.diet.model.menu.WeekDay;
import mitin.frontend.telegram.constant.*;
import mitin.frontend.telegram.diets.DietService;
import mitin.frontend.telegram.engine.BotEngine;
import mitin.frontend.telegram.engine.implementation.factory.BotApi;
import mitin.frontend.telegram.exception.CommandNotFoundException;
import mitin.frontend.telegram.users.TelegramUser;
import mitin.frontend.telegram.users.UserService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import java.util.*;

public class Engine implements BotEngine {

    private UserService service;
    private DietService dietService;

    public static final String REGISTER_USER = "Регистрация";

    public Engine() {
        service = UserService.createUserSystem();
        dietService = DietService.createDietService();
    }

    @Override
    public boolean isRegisteredUser(Long chatId) {
        return service.checkUnregisterUser(chatId);
    }

    @Override
    public boolean isCommand(Message message) {
        return isMessageCommand(message);
    }

    @Override
    public SendMessage executeCommand(Message message) {
        Long chatId = message.getChatId();
        if (isCommand(message)) {
            String command = getCommand(message);
            return getCommandExecution(command, chatId);
        } else {
            return BotApi.createSendMessage(chatId, Response.INCORRECT_COMMAND);
        }
    }


    @Override
    public SendMessage executeCallback(CallbackQuery callback) {
        Message message = callback.getMessage();
        Long chatId = message.getChatId();
        String key = callback.getData();

        if (key.equals(REGISTER_USER)) {
            return registerUser(chatId);
        } else if (key.equals("Мужской") || key.equals("Женский")) {
            return getUserParams(chatId, key);
        } else if (key.equals(DietTypes.WEIGHT_LOSS)) {
            if (dietService.hasUserTypedMenu(chatId, DietType.WEIGHT_LOSS)) {
                return BotApi.createSendMessage(chatId, Response.WEIGHT_LOSS_DIET_EXISTS);
            }
            dietService.createMenu(chatId, DietType.WEIGHT_LOSS);
            service.getTgUser(chatId).setPrevMenu(NavigationMenu.MAIN_MENU);
            service.getTgUser(chatId).setCurrentDietType(DietType.WEIGHT_LOSS);
            return BotApi
                    .createSendMessageWithKeyboard(chatId, Response.DIET_CREATED_SUCCESSFULLY,
                            BotApi
                                    .createKeyboard(DietMenu.MENU_COMPONENTS),
                            BotApi.createReplyKeyboardMarkup(true, true, false));
        } else if (key.equals(DietTypes.WEIGHT_GAIN)) {
            if (dietService.hasUserTypedMenu(chatId, DietType.WEIGHT_GAIN)) {
                return BotApi.createSendMessage(chatId, Response.WEIGHT_GAIN_DIET_EXISTS);
            }
            dietService.createMenu(chatId, DietType.WEIGHT_GAIN);
            service.getTgUser(chatId).setPrevMenu(NavigationMenu.MAIN_MENU);
            service.getTgUser(chatId).setCurrentDietType(DietType.WEIGHT_GAIN);
            return BotApi
                    .createSendMessageWithKeyboard(chatId, Response.DIET_CREATED_SUCCESSFULLY,
                            BotApi
                                    .createKeyboard(DietMenu.MENU_COMPONENTS),
                            BotApi.createReplyKeyboardMarkup(true, true, false));
        } else if (key.equals(DietTypes.NORMAL)) {
            if (dietService.hasUserTypedMenu(chatId, DietType.NORMAL)) {
                return BotApi.createSendMessage(chatId, Response.NORMAL_DIET_EXISTS);
            }
            dietService.createMenu(chatId, DietType.NORMAL);
            service.getTgUser(chatId).setPrevMenu(NavigationMenu.MAIN_MENU);
            service.getTgUser(chatId).setCurrentDietType(DietType.NORMAL);
            return BotApi
                    .createSendMessageWithKeyboard(chatId, Response.DIET_CREATED_SUCCESSFULLY,
                            BotApi
                                    .createKeyboard(DietMenu.MENU_COMPONENTS),
                            BotApi.createReplyKeyboardMarkup(true, true, false));
        } else if (isUserParameterForRegistry(key)) {
            return setUserParamMsg(chatId, key, null);
        } else {
            return BotApi.createSendMessage(chatId, Response.UNKNOWN_ERROR);
        }
    }


    @Override
    public SendMessage executeMessage(Message message) {
        Long chatId = message.getChatId();
        String data = message.getText();
        if (isCommand(message)) {
            return executeCommand(message);
        } else if (isFullyRegisteredUser(chatId)) {
            if (isMenuCommand(data, message.getChatId())) {
                return executeMenuCommand(chatId, data);
            } else {
                return BotApi.createSendMessage(chatId, Response.COMMAND_NOT_FOUND);
            }
        } else {
            return getUserParams(chatId, data);
        }
    }

    @Override
    public boolean hasUserPhoto(Long chatId) {
        TelegramUser tgUser = service.getTgUser(chatId);
        return tgUser.getPhoto() != null;
    }

    @Override
    public SendPhoto getPhotoForUser(Long chatId) {
        return service.getTgUser(chatId).getPhoto();
    }

    @Override
    public void setNullPhoto(Long chatId) {
        service.getTgUser(chatId).setPhoto(null);
    }

    private SendMessage registerUser(Long chatId) {
        if (isRegisteredUser(chatId)) {
            throw new RuntimeException("Такой пользователь уже есть");
        }
        service.registerUser(chatId);
        service.getTgUser(chatId).setPrevMessage(UserParameters.ACTIVITY);
        String text = "Оцените вашу ежедневную *активность* от 1 до 10";
        return BotApi.createMarkdownSendMessage(chatId, text);
    }

    private SendMessage setUserParamMsg(Long chatId, String parameter, String addition) {
        TelegramUser user = service.getTgUser(chatId);
        String text = addition == null ? "" : addition + "\n";
        if (service.hasUserParam(chatId, parameter)) {
            text += "Параметр " + parameter + " уже зарегистрирован";
        } else {
            if (parameter.equals(UserParameters.ACTIVITY)) {
                text += "Оцените вашу ежедневную *активность* от 1 до 10:";
                user.setPrevMessage(parameter);
            } else if (parameter.equals(UserParameters.AGE)) {
                text += "Введите ваш *возраст*:";
                user.setPrevMessage(parameter);
            } else if (parameter.equals(UserParameters.HEIGHT)) {
                text += "Введите ваш *рост*:";
                user.setPrevMessage(parameter);
            } else if (parameter.equals(UserParameters.WEIGHT)) {
                text += "Введите ваш *вес*:";
                user.setPrevMessage(parameter);
            } else if (parameter.equals(UserParameters.GENDER)) {
                text += "Ваш пол:";
                user.setPrevMessage(parameter);
                return BotApi.createSendMessageWithReplyKeyboard(chatId, text, BotApi.createReplyKeyboard(BotApi.createButtonsFromList(List.of("Мужской", "Женский"))));
            }
        }
        return BotApi.createMarkdownSendMessage(chatId, text);
    }


    private SendMessage getUserParams(Long chatId, String userParamValue) {
        String successResponse = "Параметер " + userParamValue + " успешно зарегистрирован";
        try {
            service.setUserParamValue(chatId, userParamValue);
            List<String> params = service.getUserUnregisterParameters(chatId);
            if (service.hasUserUnregisteredParams(chatId)) {
                return setUserParamMsg(chatId, params.get(0), successResponse);
            } else {
                return startUserMenu(chatId);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return BotApi.createSendMessage(chatId, e.getMessage());
        }

    }


    private mitin.backend.system.diet.model.menu.MealTime createMealTime(String key) {
        switch (key) {
            case MealTime.BREAKFAST:
                return mitin.backend.system.diet.model.menu.MealTime.BREAKFAST;
            case MealTime.FIRST_SNACK:
                return mitin.backend.system.diet.model.menu.MealTime.FIRST_SNACK;
            case MealTime.DINNER:
                return mitin.backend.system.diet.model.menu.MealTime.DINNER;
            case MealTime.SECOND_SNACK:
                return mitin.backend.system.diet.model.menu.MealTime.SECOND_SNACK;
            case MealTime.EVENING_MEAL:
                return mitin.backend.system.diet.model.menu.MealTime.SUPPER;
            default:
                return mitin.backend.system.diet.model.menu.MealTime.SUPPER;
        }
    }

    private WeekDay createWeekDay(String day) {
        switch (day) {
            case "Понедельник":
                return WeekDay.MONDAY;
            case "Вторник":
                return WeekDay.TUESDAY;
            case "Среда":
                return WeekDay.WEDNESDAY;
            case "Четверг":
                return WeekDay.THURSDAY;
            case "Пятница":
                return WeekDay.FRIDAY;
            case "Суббота":
                return WeekDay.SATURDAY;
            case "Воскресенье":
                return WeekDay.SUNDAY;
            default:
                return WeekDay.MONDAY;
        }
    }

    private SendMessage executeMenuCommand(Long chatId, String command) {
        boolean isMainMenu = Menu.MENU_COMPONENTS.contains(command);
        boolean isDietMenu = DietMenu.MENU_COMPONENTS.contains(command);
        boolean isMealTimeMenu = MealTime.MEALS.contains(command);
        boolean isNavigationMenu = NavigationMenu.NAVIGATION_COMPONENTS.contains(command);
        boolean isUserMenu = dietService.getUserDiets(chatId).contains(command);
        if (isNavigationMenu) {
            return executeNavigationMenuCommand(chatId, command);
        } else if (isDietMenu) {
            return executeDietMenuCommand(chatId, command);
        } else if (isMealTimeMenu) {
            return executeMealTimeMenu(chatId, command);
        } else if (isMainMenu) {
            return executeMainMenuCommand(chatId, command);
        } else if (isUserMenu) {
            return executeShowUserMenu(chatId, command);
        }
        return null;
    }

    private SendMessage executeShowUserMenu(Long chatId, String command) {
        if (command.equals(DietType.NORMAL.toString())) {
            service.getTgUser(chatId).setCurrentDietType(DietType.NORMAL);
        } else if (command.equals(DietType.WEIGHT_LOSS.toString())) {
            service.getTgUser(chatId).setCurrentDietType(DietType.WEIGHT_LOSS);
        } else if (command.equals(DietType.WEIGHT_GAIN.toString())) {
            service.getTgUser(chatId).setCurrentDietType(DietType.WEIGHT_GAIN);
        }
        service.getTgUser(chatId).setPrevMenu(NavigationMenu.DIET_SELECTION);
        return BotApi
                .createSendMessageWithKeyboard(chatId, Response.CHOOSE_DAY,
                        BotApi
                                .createKeyboard(DietMenu.MENU_COMPONENTS),
                        BotApi.createReplyKeyboardMarkup(true, true, false));
    }

    private SendMessage executeNavigationMenuCommand(Long chatId, String command) {
        if (NavigationMenu.GO_BACK.equals(command)) {
            TelegramUser tgUser = service.getTgUser(chatId);
            String prevMenu = tgUser.getPrevMenu();
            if (prevMenu.equals(NavigationMenu.DIET_SELECTION)) {
                tgUser.setPrevMenu(NavigationMenu.MAIN_MENU);
                List<String> userDiets = dietService.getUserDiets(chatId);
                userDiets.add(NavigationMenu.GO_BACK);
                userDiets.add(NavigationMenu.GO_MAIN_MENU);
                return BotApi.createSendMessageWithKeyboard(chatId, Response.CHOOSE_MENU, BotApi.createKeyboard(userDiets), BotApi.createReplyKeyboardMarkup(true, true, false));
            } else if (prevMenu.equals(NavigationMenu.WEEK_DAY)) {
                tgUser.setPrevMenu(NavigationMenu.DIET_SELECTION);
                return BotApi.createSendMessageWithKeyboard(chatId, Response.CHOOSE_DAY, BotApi.createKeyboard(DietMenu.MENU_COMPONENTS), BotApi.createReplyKeyboardMarkup(true, true, false));
            } else if (prevMenu.equals(NavigationMenu.MAIN_MENU)) {
                return BotApi.createSendMessageWithKeyboard(chatId, Response.MENU, BotApi.createKeyboard(Menu.MENU_COMPONENTS), BotApi.createReplyKeyboardMarkup(true, true, false));
            }
        }
        return BotApi.createSendMessageWithKeyboard(chatId, Response.MENU, BotApi.createKeyboard(Menu.MENU_COMPONENTS), BotApi.createReplyKeyboardMarkup(true, true, false));
    }

    private SendMessage executeMealTimeMenu(Long chatId, String time) {
        TelegramUser tgUser = service.getTgUser(chatId);
        String day = tgUser.getPrevMessage();
        DietType dietType = tgUser.getCurrentDietType();
        String nutrition = dietService.getMeal(chatId, createWeekDay(day), createMealTime(time), dietType);
        tgUser.setPhoto(BotApi
                .createSendMessageWithPicture(chatId, dietService.getMealPhoto(chatId, createWeekDay(day), createMealTime(time), dietType)));
        return BotApi.createSendMessage(chatId, nutrition);
    }

    private SendMessage executeDietMenuCommand(Long chatId, String command) {
        if (command.equals(DietMenu.MONDAY)) {
            TelegramUser user = service.getTgUser(chatId);
            user.setPrevMessage(DietMenu.MONDAY);
        } else if (command.equals(DietMenu.TUESDAY)) {
            TelegramUser user = service.getTgUser(chatId);
            user.setPrevMessage(DietMenu.TUESDAY);
        } else if (command.equals(DietMenu.WEDNESDAY)) {
            TelegramUser user = service.getTgUser(chatId);
            user.setPrevMessage(DietMenu.WEDNESDAY);
        } else if (command.equals(DietMenu.THURSDAY)) {
            TelegramUser user = service.getTgUser(chatId);
            user.setPrevMessage(DietMenu.THURSDAY);
        } else if (command.equals(DietMenu.FRIDAY)) {
            TelegramUser user = service.getTgUser(chatId);
            user.setPrevMessage(DietMenu.FRIDAY);
        } else if (command.equals(DietMenu.SATURDAY)) {
            TelegramUser user = service.getTgUser(chatId);
            user.setPrevMessage(DietMenu.SATURDAY);
        } else if (command.equals(DietMenu.SUNDAY)) {
            TelegramUser user = service.getTgUser(chatId);
            user.setPrevMessage(DietMenu.SUNDAY);
        } else if (command.equals(DietMenu.TODAY)) {
            WeekDay today = getToday();
            TelegramUser user = service.getTgUser(chatId);
            user.setPrevMessage(today.toString());
        }
        service.getTgUser(chatId).setPrevMenu(NavigationMenu.WEEK_DAY);
        return BotApi
                .createSendMessageWithKeyboard(chatId, Response.CHOOSE_MEAL_TIME, BotApi
                        .createKeyboard(MealTime.MEALS), BotApi.createReplyKeyboardMarkup(true, false, false));
    }

    private WeekDay getToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int dayNumber = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayNumber) {
            case 1:
                return WeekDay.SUNDAY;
            case 2:
                return WeekDay.MONDAY;
            case 3:
                return WeekDay.TUESDAY;
            case 4:
                return WeekDay.WEDNESDAY;
            case 5:
                return WeekDay.THURSDAY;
            case 6:
                return WeekDay.FRIDAY;
            case 7:
                return WeekDay.SATURDAY;
            default:
                return null;
        }
    }

    private SendMessage executeMainMenuCommand(Long chatId, String command) {
        if (command.equals(Menu.CREATE_MENU)) {
            return BotApi
                    .createSendMessageWithReplyKeyboard(chatId, Response.CHOOSE_DIET_TYPE, BotApi
                            .createReplyKeyboard(BotApi
                                    .createButtonsFromList(DietTypes.DIET_TYPES)));
        } else if (command.equals(Menu.MY_DIETS)) {
            List<String> userDiets = dietService.getUserDiets(chatId);
            if (userDiets.isEmpty()) {
                return BotApi
                        .createSendMessage(chatId, Response.NO_DIETS);
            } else {
                userDiets.add(NavigationMenu.GO_BACK);
                userDiets.add(NavigationMenu.GO_MAIN_MENU);
                service.getTgUser(chatId).setPrevMenu(NavigationMenu.MAIN_MENU);
                return BotApi
                        .createSendMessageWithKeyboard(chatId, Response.CHOOSE_MENU,
                                BotApi.createKeyboard(userDiets),
                                BotApi.createReplyKeyboardMarkup(true, true, false));
            }
        } else if (command.equals(Menu.MY_PARAMETERS)) {
            Map<String, String> parameters = service.getUser(chatId);
            String response = "";
            for (String parameter : parameters.keySet()) {
                response += parameter + " : " + parameters.get(parameter) + "\n";
            }
            return BotApi
                    .createSendMessageWithKeyboard(chatId, response.substring(0, response.length() - 1), BotApi.createKeyboard(Menu.MENU_COMPONENTS), BotApi.createReplyKeyboardMarkup(true, true, false));
        } else if (command.equals(Menu.REMOVE_ME)) {
            service.deleteUser(chatId);
            dietService.deleteDiets(chatId);
            return responseRemovedUser(chatId);
        } else if (command.equals(Menu.MY_DAILY_CALORIE)) {
            String dailyCalorie = dietService.getDailyCalorie(chatId);
            String response = "Ваша суточная норма калорий = " + dailyCalorie;
            return BotApi.createSendMessage(chatId, response);
        }
        return BotApi.createSendMessage(chatId, Response.COMMAND_NOT_FOUND);
    }

    private SendMessage responseRemovedUser(Long chatId) {
        if (isRegisteredUser(chatId)) {
            throw new RuntimeException("Такой пользователь уже есть");
        }
        service.registerUser(chatId);
        service.getTgUser(chatId).setPrevMessage(UserParameters.ACTIVITY);
        String text = "Параметры успешно сброшены.\n" +
                "Теперь вы можете ввести новые.\n" +
                "Оцените вашу ежедневную *активность* от 1 до 10";
        return BotApi.createMarkdownSendMessage(chatId, text);
    }

    private boolean isMenuCommand(String data, Long chatId) {
        return Menu.MENU_COMPONENTS.contains(data)
                || DietMenu.MENU_COMPONENTS.contains(data)
                || MealTime.MEALS.contains(data)
                || NavigationMenu.NAVIGATION_COMPONENTS.contains(data)
                || dietService.getUserDiets(chatId).contains(data);
    }

    private boolean isFullyRegisteredUser(Long chatId) {
        return service.getUserUnregisterParameters(chatId).isEmpty();
    }

    private boolean isUserParameterForRegistry(String key) {
        return UserParameters.ALL_USER_PARAMETERS.contains(key);
    }

    private SendMessage getCommandExecution(String command, Long chatId) {
        SendMessage result;
        switch (command) {
            case Commands.START: {
                result = BotApi.createSendMessage(chatId, Response.START);
                break;
            }
            case Commands.EXIT: {
                service.deleteUser(chatId);
                result = BotApi.createSendMessage(chatId, Response.SUCCESS_EXIT);
                break;
            }
            default:
                result = BotApi.createSendMessage(chatId, Response.COMMAND_NOT_FOUND);
        }
        return result;
    }

    private boolean isMessageCommand(Message message) {
        return message.hasEntities() && message.getEntities().stream()
                .anyMatch(entity -> entity.getType().equals(Commands.BOT_COMMAND_IDENTIFIER));
    }

    private String getCommand(Message message) throws CommandNotFoundException {
        Optional<MessageEntity> commandEntity = message.getEntities().stream()
                .filter(entity -> entity.getType().equals(Commands.BOT_COMMAND_IDENTIFIER))
                .findAny();
        if (commandEntity.isPresent()) {
            String command = message.getText()
                    .substring(commandEntity.get().getOffset(), commandEntity.get().getLength());//обрезаем команду
            return command;
        } else {
            throw new CommandNotFoundException();
        }
    }

    private SendMessage startUserMenu(Long chatId) {
        return BotApi
                .createSendMessageWithKeyboard(chatId, Response.END_OF_REGISTRATION, BotApi.createKeyboard(Menu.MENU_COMPONENTS), BotApi.createReplyKeyboardMarkup(true, true, false));
    }
}
