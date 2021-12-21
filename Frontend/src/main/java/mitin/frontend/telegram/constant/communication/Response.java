package mitin.frontend.telegram.constant.communication;

public abstract class Response {
    public static final String START = "Приветствую!" +
            "\nЯ тот бот, который поможет вам разобраться с питанием\uD83E\uDD51" +
            "\nЯ могу собрать рацион именно под вас, ваши особенности тела, возраст, вес и так далее\uD83D\uDCAA\uD83C\uDFFB" +
            "\nВы можете выбрать, к какой цели мы будем с вами идти: набрать/сбросить вес или просто поддерживать ваше тело в тонусе\uD83E\uDDD8\uD83C\uDFFC\u200D♀" +
            "\nЯ буду сопровождать вас на протяжении всего пути!";

    public static final String UNREGISTERED_USER_RESPONSE = "Мне нужно узнать ваши параметры, чтобы знать с чем работать" +
            "\nРегистрируйтесь скорее⬇️";

    public static final String SUCCESS_EXIT = "Выход совершен успешно";
    public static final String COMMAND_NOT_FOUND = "Такой команды нет";
    public static final String INCORRECT_COMMAND = "Нет команды для исполнения";

    public static final String END_OF_REGISTRATION = "Отлично!" +
            "\nТеперь мы можем приступить к работе \uD83C\uDFC5" +
            "\nДальнейшая навигация будет идти через клавиатуру⬇️⬇️⬇️";

    public static final String NO_DIETS = "У вас нет рационов";
    public static final String CHOOSE_MENU = "Вебирете меню";
    public static final String CHOOSE_DIET_TYPE = "Для какой цели создать рацион?";
    public static final String CHOOSE_MEAL_TIME = "Выбирите прием пищи";
    public static final String CHOOSE_DAY = "Выбирете день";
    public static final String MENU = "Главное меню";
    public static final String WEIGHT_LOSS_DIET_EXISTS = "У вас уже составлено меню на похудение";
    public static final String WEIGHT_GAIN_DIET_EXISTS = "У вас уже составлено меню на набор массы";
    public static final String NORMAL_DIET_EXISTS = "У вас уже составлено меню на поддержание формы";
    public static final String DIET_CREATED_SUCCESSFULLY = "Меню создано, можете выбрать день на клавиатуре";
    public static final String UNKNOWN_ERROR = "Что-то пошло не так";
}
