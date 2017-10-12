package pl.michal.olszewski.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.michal.olszewski.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findById(Long userId);

    @Transactional
    @Modifying
    @Query("update User u set u.username = ?1, u.firstName = ?2, u.lastName = ?3, u.email = ?4 where u.id = ?5")
    int updateUser(String username, String firstName, String lastName, String email, Long id);
}
