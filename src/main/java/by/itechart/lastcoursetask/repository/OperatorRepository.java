package by.itechart.lastcoursetask.repository;

import by.itechart.lastcoursetask.entity.Operator;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OperatorRepository extends CrudRepository<Operator, UUID> {
    Operator findByNickname(String nickname);

    Operator findByFirstNameAndLastName(String firstName, String lastName);
}
