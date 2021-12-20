package mitin.frontend.telegram.exception;

public class CommandNotFoundException extends RuntimeException{
    private final static String REASON_FOR_EXCEPTION = "У бота нет такой команды";
    public CommandNotFoundException() {
        super(REASON_FOR_EXCEPTION);
    }
}
