package by.itechart.lastcoursetask.command;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public abstract class Command {
    private String argument;

    public abstract Object execute();
}
