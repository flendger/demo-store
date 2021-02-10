package ru.flendger.demo.store.demo.store.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.flendger.demo.store.demo.store.model.Role;
import ru.flendger.demo.store.demo.store.repositories.RoleRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Optional<Role> findRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }
}
