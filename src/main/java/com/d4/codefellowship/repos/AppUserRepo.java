package com.d4.codefellowship.repos;

import com.d4.codefellowship.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepo extends JpaRepository<ApplicationUser,Long> {

    Optional<ApplicationUser> findApplicationUserByUsername(String username);

}
