package mitin.frontend.telegram.constant.user;

import java.util.List;

public abstract class UserParameters {

    public static final String ACTIVITY = "Активность";
    public static final String WEIGHT = "Вес";
    public static final String HEIGHT = "Рост";
    public static final String AGE = "Возраст";
    public static final String GENDER = "Пол";

    public static final List<String> ALL_USER_PARAMETERS = List.of(ACTIVITY, WEIGHT, HEIGHT, AGE, GENDER);
}
