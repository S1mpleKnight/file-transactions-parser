package by.itechart.lastcoursetask.service;

import by.itechart.lastcoursetask.entity.Role;
import by.itechart.lastcoursetask.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository repository;

    public Role findByValue(String value) throws IllegalAccessException {
        if (repository.existsByValue(value)) {
            return repository.findByValue(value);
        }
        throw new IllegalAccessException("Role does not exist: " + value);
    }

    public Role findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Role does not exist, id: " + id));
    }
}
