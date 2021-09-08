package by.itechart.lastcoursetask.command;

import by.itechart.lastcoursetask.dto.CommandDto;
import by.itechart.lastcoursetask.exception.CommandNotFoundException;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Data
@Scope("singleton")
public class CommandFactory {
    private final Map<String, Command> commandMap;

    public Command getCommand(CommandDto commandDto) {
        if (commandMap.containsKey(commandDto.getCommandName())) {
            Command command = commandMap.get(commandDto.getCommandName());
            command.setArgument(commandDto.getStringArgumentValue());
            return command;
        }
        throw new CommandNotFoundException("Command not found: " + commandDto.getCommandName());
    }

    public List<String> getAllCommands() {
        return commandMap.keySet().stream().toList();
    }
}
