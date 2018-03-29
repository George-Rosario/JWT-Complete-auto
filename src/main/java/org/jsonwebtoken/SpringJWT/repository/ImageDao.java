package org.jsonwebtoken.SpringJWT.repository;

import org.jsonwebtoken.SpringJWT.model.Images;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ImageDao extends JpaRepository<Images, Integer>{

	

}
