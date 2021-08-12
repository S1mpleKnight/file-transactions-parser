package by.itechart.lastcoursetask.repository;

import by.itechart.lastcoursetask.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByValue(String value);
}
