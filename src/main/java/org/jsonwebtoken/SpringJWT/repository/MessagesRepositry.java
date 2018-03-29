package org.jsonwebtoken.SpringJWT.repository;

import java.util.List;

import org.jsonwebtoken.SpringJWT.model.Messages;
import org.jsonwebtoken.SpringJWT.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesRepositry extends JpaRepository<Messages, Integer>{

	List<Messages> findByToUser(User userId);
	List<Messages> findByFromUser(User userId);
}
