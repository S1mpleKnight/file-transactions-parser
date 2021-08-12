package by.itechart.lastcoursetask.repository;

import by.itechart.lastcoursetask.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByValue(String value);
}
