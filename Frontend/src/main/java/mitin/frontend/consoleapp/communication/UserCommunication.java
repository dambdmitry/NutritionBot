package mitin.frontend.consoleapp.communication;

import java.util.Set;

public interface UserCommunication {
    /**
     * @return all available commands
     */
    Set<String> getCommands();

    /**
     * @param command command to be executed
     * @return result of command execution
     */
    void executeCommand(String command);

    /**
     * @return requested command
     */
    String requestCommand();
}
