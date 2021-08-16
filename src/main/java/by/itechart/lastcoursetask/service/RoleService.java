package by.itechart.lastcoursetask.service;

import by.itechart.lastcoursetask.entity.Role;
import by.itechart.lastcoursetask.exception.RoleNotFoundException;
import by.itechart.lastcoursetask.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository repository;

    public Role findByName(String value) {
        if (repository.existsByName(value)) {
            return repository.findByName(value);
        }
        throw new RoleNotFoundException(value);
    }

    public Role findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RoleNotFoundException(id.toString()));
    }
}
