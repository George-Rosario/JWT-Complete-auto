package org.jsonwebtoken.SpringJWT.repository;

import java.util.List;

import org.jsonwebtoken.SpringJWT.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    public List<User> findAllByFirstnameOrLastname(String firstname, String lastname);
}
