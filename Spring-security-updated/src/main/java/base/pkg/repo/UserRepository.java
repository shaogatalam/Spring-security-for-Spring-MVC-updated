package base.pkg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import base.pkg.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //UserDetails findByEmail(String username);
    Optional<User> findByEmail(String email);

}
