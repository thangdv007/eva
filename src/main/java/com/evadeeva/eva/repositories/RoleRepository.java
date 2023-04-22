package com.evadeeva.eva.repositories;

import com.evadeeva.eva.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findById(long roleId);
}
