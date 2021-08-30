package by.itechart.lastcoursetask.command.api;

import org.springframework.stereotype.Component;

@Component
public interface Command {
    Object execute();
}
