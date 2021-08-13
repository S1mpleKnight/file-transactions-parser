package by.itechart.lastcoursetask.repository;

import by.itechart.lastcoursetask.entity.Operator;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorRepository extends CrudRepository<Operator, Long> {
    Operator findByNickname(String nickname);

    boolean existsByNickname(String nickname);

    Operator findByFirstNameAndLastName(String firstName, String lastName);

    boolean existsByFirstNameAndLastName(String firstName, String lastName);
}
