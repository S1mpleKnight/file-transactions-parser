package by.itechart.lastcoursetask.service;

import by.itechart.lastcoursetask.dto.OperatorDto;
import by.itechart.lastcoursetask.dto.TransactionDto;
import by.itechart.lastcoursetask.entity.Operator;
import by.itechart.lastcoursetask.exception.OperatorExistException;
import by.itechart.lastcoursetask.exception.OperatorNotFoundException;
import by.itechart.lastcoursetask.exception.RejectAccessException;
import by.itechart.lastcoursetask.repository.OperatorRepository;
import by.itechart.lastcoursetask.util.EntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OperatorService implements UserDetailsService {
    private static final Long ADMIN_ID = 1L;
    private static final String ADMIN_ROLE_NAME = "ADMIN";
    private static final String ROOT_ADMIN_NICKNAME = "adminchik";
    private final TransactionService transactionService;
    private final OperatorRepository repository;
    private final EntityMapper mapper;

    public Page<OperatorDto> findAll(Pageable pageable) {
        return new PageImpl<>(getOperatorsDto(repository.findAll(pageable)));
    }

    public OperatorDto findById(Long id) {
        return repository.findById(id).map(mapper::mapToOperatorDTO)
                .orElseThrow(() -> new OperatorNotFoundException(id.toString()));
    }

    public OperatorDto findByFirstNameAndLastName(String firstName, String lastName) {
        if (isPersonExist(firstName, lastName)) {
            return mapper.mapToOperatorDTO(repository.findByFirstNameAndLastName(firstName, lastName));
        }
        throw new OperatorNotFoundException(firstName + " " + lastName);
    }

    @Transactional
    public void save(OperatorDto operatorDTO) {
        Operator operator = mapper.mapToOperatorEntity(operatorDTO);
        if (isOperatorExist(operator)) {
            repository.save(operator);
        } else {
            throw new OperatorExistException(operator.getId().toString());
        }
    }

    @Transactional
    public void delete(Long operatorId, Principal principal) {
        if (operatorId != 1 && repository.existsById(operatorId)) {
            deleteUser(operatorId, principal);
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
                true, true, true, true,
                Collections.singleton(new SimpleGrantedAuthority(operatorDto.getRole())));
    }

    private boolean isPersonExist(String firstName, String lastName) {
        return repository.existsByFirstNameAndLastName(firstName, lastName);
    }

    private boolean isNicknameExist(String nickname) {
        return repository.existsByNickname(nickname);
    }

    public OperatorDto findByNickName(String nickname) {
        if (repository.existsByNickname(nickname)) {
            return mapper.mapToOperatorDTO(repository.findByNickname(nickname));
        }
        throw new OperatorNotFoundException(nickname);
    }

    private void deleteUser(Long operatorId, Principal principal) {
        if (isAdminRole(operatorId)) {
            deleteAdmin(operatorId, principal);
        } else {
            deleteOperator(operatorId);
        }
    }

    private boolean isAdminRole(Long operatorId) {
        return repository.getById(operatorId).getRole().getName().equals(ADMIN_ROLE_NAME);
    }

    private void deleteAdmin(Long operatorId, Principal principal) {
        if (principal.getName().equals(ROOT_ADMIN_NICKNAME)) {
            deleteOperator(operatorId);
        } else {
            throw new RejectAccessException("Can not delete admin");
        }
    }

    private void deleteOperator(Long operatorId) {
        List<TransactionDto> transactionDtos = transactionService.findByOperatorId(operatorId);
        repository.deleteById(operatorId);
        for (TransactionDto transaction : transactionDtos) {
            transactionService.updateOperator(UUID.fromString(transaction.getTransactionId()), ADMIN_ID);
        }
    }

    public List<OperatorDto> getOperatorsDto(Page<Operator> operators) {
        return operators
                .stream()
                .map(mapper::mapToOperatorDTO)
                .collect(Collectors.toList());
    }

    private boolean isOperatorExist(Operator operator) {
        return !isPersonExist(operator.getFirstName(), operator.getLastName()) || !isNicknameExist(operator.getNickname());
    }
}
