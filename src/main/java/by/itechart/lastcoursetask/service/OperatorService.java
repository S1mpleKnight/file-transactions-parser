package by.itechart.lastcoursetask.service;

import by.itechart.lastcoursetask.dto.OperatorDTO;
import by.itechart.lastcoursetask.entity.Operator;
import by.itechart.lastcoursetask.repository.OperatorRepository;
import by.itechart.lastcoursetask.util.EntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OperatorService {
    private final OperatorRepository repository;
    private final EntityMapper mapper;

    public Set<OperatorDTO> findAll() {
        Set<Operator> operators = new HashSet<>();
        repository.findAll().forEach(operators::add);
        return operators.stream().map(mapper::mapToOperatorDTO).collect(Collectors.toSet());
    }

    public OperatorDTO findById(Long id) {
        return repository.findById(id).map(mapper::mapToOperatorDTO)
                .orElseThrow(() -> new IllegalArgumentException("Operator does not exist: " + id));
    }

    public OperatorDTO findByNickName(String nickname) {
        if (repository.existsByNickname(nickname)) {
            return mapper.mapToOperatorDTO(repository.findByNickname(nickname));
        }
        throw new IllegalArgumentException("Operator does not exist: " + nickname);
    }

    public OperatorDTO findByFirstNameAndLastName(String firstName, String lastName) {
        if (isOperatorExist(firstName, lastName)) {
            return mapper.mapToOperatorDTO(repository.findByFirstNameAndLastName(firstName, lastName));
        }
        throw new IllegalArgumentException("Operator does not exist, first name: " + firstName
                + " last name: " + lastName);
    }

    @Transactional
    public void save(OperatorDTO operatorDTO) {
        Operator operator = mapper.mapToOperatorEntity(operatorDTO);
        if (!isOperatorExist(operator.getFirstName(), operator.getLastName())) {
            repository.save(operator);
        } else {
            throw new IllegalArgumentException("Operator is already exist");
        }
    }

    @Transactional
    public void delete(Long operatorId) {
        if (repository.existsById(operatorId)) {
            repository.delete(mapper.mapToOperatorEntity(findById(operatorId)));
        } else {
            throw new IllegalArgumentException("Operator is not exist");
        }
    }

    @Transactional
    public void update(Long operatorId, OperatorDTO newOperator) {
        if (repository.existsById(operatorId)) {
            Operator operator = mapper.mapToOperatorEntity(newOperator);
            operator.setId(operatorId);
            repository.save(operator);
        } else {
            throw new IllegalArgumentException("Operator is not exist");
        }
    }

    private boolean isOperatorExist(String firstName, String lastName) {
        return repository.existsByFirstNameAndLastName(firstName, lastName);
    }
}
