package mitin.frontend.telegram.constant.diet;

import java.util.List;

public abstract class DietTypes {
    public static final String WEIGHT_LOSS = "Похудение";
    public static final String WEIGHT_GAIN = "Набор массы";
    public static final String NORMAL = "Поддержание формы";

    public static final List<String> DIET_TYPES = List.of(WEIGHT_GAIN, WEIGHT_LOSS, NORMAL);
}
