package com.d4.codefellowship.repos;

import com.d4.codefellowship.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {
    Role findByName(String role);
}
