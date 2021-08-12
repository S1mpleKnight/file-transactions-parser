package by.itechart.lastcoursetask.repository;

import by.itechart.lastcoursetask.entity.Operator;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OperatorRepository extends CrudRepository<Operator, UUID> {
    Operator findByNickname(String nickname);

    Operator findByFirstNameAndLastName(String firstName, String lastName);
}
