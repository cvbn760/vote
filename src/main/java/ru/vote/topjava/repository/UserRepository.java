package ru.vote.topjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vote.topjava.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    //Достать только администраторов ресторанов
    @Query("SELECT DISTINCT u FROM User u INNER JOIN Restaurant rest ON rest.idOwnerRest = u.id")
    List<User> findAdministrators();

    // Достать только обычных пользователей
    @Query("SELECT DISTINCT u FROM User u INNER JOIN Voter v ON v.voterIdPk = u.id")
    List<User> findOtherUsers();

//    @Query(value = "SELECT u FROM User u WHERE u.email=:email", nativeQuery = true)
//    User findUserByEmail(@Param("email") String email);
    User findUserByEmail(String email);
}
