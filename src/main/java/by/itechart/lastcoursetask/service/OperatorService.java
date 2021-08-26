package by.itechart.lastcoursetask.service;

import by.itechart.lastcoursetask.dto.OperatorDto;
import by.itechart.lastcoursetask.dto.TransactionDto;
import by.itechart.lastcoursetask.entity.Operator;
import by.itechart.lastcoursetask.exception.OperatorExistException;
import by.itechart.lastcoursetask.exception.OperatorNotFoundException;
import by.itechart.lastcoursetask.repository.OperatorRepository;
import by.itechart.lastcoursetask.util.EntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OperatorService implements UserDetailsService {
    private static final Long ADMIN_ID = 1L;
    private final TransactionService transactionService;
    private final OperatorRepository repository;
    private final EntityMapper mapper;

    public List<OperatorDto> findAll() {
        List<Operator> operators = new ArrayList<>(repository.findAll());
        return operators.stream().map(mapper::mapToOperatorDTO).collect(Collectors.toList());
    }

    public OperatorDto findById(Long id) {
        return repository.findById(id).map(mapper::mapToOperatorDTO)
                .orElseThrow(() -> new OperatorNotFoundException(id.toString()));
    }

    public OperatorDto findByFirstNameAndLastName(String firstName, String lastName) {
        if (isOperatorExist(firstName, lastName)) {
            return mapper.mapToOperatorDTO(repository.findByFirstNameAndLastName(firstName, lastName));
        }
        throw new OperatorNotFoundException(firstName + " " + lastName);
    }

    @Transactional
    public void save(OperatorDto operatorDTO) {
        Operator operator = mapper.mapToOperatorEntity(operatorDTO);
        if (!isOperatorExist(operator.getFirstName(), operator.getLastName())) {
            repository.save(operator);
        } else {
            throw new OperatorExistException(operator.getId().toString());
        }
    }

    @Transactional
    public void delete(Long operatorId) {
        if (repository.existsById(operatorId)) {
            List<TransactionDto> transactionDtos = transactionService.findByOperatorId(operatorId);
            repository.deleteById(operatorId);
            for (TransactionDto transaction : transactionDtos) {
                transactionService.updateOperator(UUID.fromString(transaction.getTransactionId()), ADMIN_ID);
            }
        } else {
            throw new OperatorNotFoundException(operatorId.toString());
        }
    }

    @Transactional
    public void update(Long operatorId, OperatorDto newOperator) {
        if (repository.existsById(operatorId)) {
            Operator operator = mapper.mapToOperatorEntity(newOperator);
            operator.setId(operatorId);
            repository.save(operator);
        } else {
            throw new OperatorNotFoundException(operatorId.toString());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String nickname) {
        OperatorDto operatorDto = findByNickName(nickname);
        return castFromOperatorDto(operatorDto);
    }

    private UserDetails castFromOperatorDto(OperatorDto operatorDto) {
        return new User(operatorDto.getNickname(), operatorDto.getPassword(),
                true, true, true, true, Collections.emptyList());
    }

    private boolean isOperatorExist(String firstName, String lastName) {
        return repository.existsByFirstNameAndLastName(firstName, lastName);
    }

    public OperatorDto findByNickName(String nickname) {
        if (repository.existsByNickname(nickname)) {
            return mapper.mapToOperatorDTO(repository.findByNickname(nickname));
        }
        throw new OperatorNotFoundException(nickname);
    }

}
