package ru.flendger.demo.store.demo.store.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.flendger.demo.store.demo.store.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);

    @Query(value = "select case when count(u.id) > 0 then true else false end from User u where u.username = :name or u.email = :email")
    boolean checkUsernameEmail(@Param("name") String username, @Param("email") String email);
}
