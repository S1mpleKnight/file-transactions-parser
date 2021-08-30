package by.itechart.lastcoursetask.command.impl;

import by.itechart.lastcoursetask.command.api.Command;
import by.itechart.lastcoursetask.exception.CommandNotFoundException;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Data
@Scope("singleton")
public class CommandFactory {
    private final Map<String, Command> commandMap;

    public Command getCommand(String command) {
        if (commandMap.containsKey(command)) {
            return commandMap.get(command);
        }
        throw new CommandNotFoundException(command);
    }
}
