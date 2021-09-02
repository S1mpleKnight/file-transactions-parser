package by.itechart.lastcoursetask.command;

import by.itechart.lastcoursetask.dto.TransactionDto;
import by.itechart.lastcoursetask.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("aboveTransactions")
@Slf4j
@RequiredArgsConstructor
public class AboveAmountTransactionCommand extends Command{
    private static final String AMOUNT_REGEX = "[0-9]+";
    private final TransactionService transactionService;

    @Override
    public List<TransactionDto> execute() {
        log.info("Above transaction command");
        return transactionService.findAboveTransactions(getAmount(this.getArgument()));
    }

    private Long getAmount(String argument) {
        if (checkAmount(argument)) {
            return Long.parseLong(argument);
        }
        throw new IllegalArgumentException("Invalid amount: " + argument);
    }

    private boolean checkAmount(String amountValue) {
        return amountValue.matches(AMOUNT_REGEX);
    }
}
