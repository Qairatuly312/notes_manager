package spring.notes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.notes.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);
    boolean existsByName(String name);

}
