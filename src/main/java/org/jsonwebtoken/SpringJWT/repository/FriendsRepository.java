package org.jsonwebtoken.SpringJWT.repository;

import java.util.List;

import org.jsonwebtoken.SpringJWT.model.Friends;
import org.jsonwebtoken.SpringJWT.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendsRepository extends JpaRepository<Friends, Long>{

	public Friends findByFriendRequesterAndFriendAccepter(User friendRequester, User friendAccepter);
	
	public List<Friends> findAllByFriendRequesterOrFriendAccepterAndStatusCode(
										User friendRequester, User friendAccepter, Integer statusCode);

	@Query("SELECT f FROM Friends f WHERE (f.friendRequester=:requester OR f.friendAccepter=:requester) "
			+ "AND f.statusCode=:statusCode")
	public List<Friends> findAllByFriendRequesterOrFriendAccepterAndStatusCode(
							@Param("requester") User requester, @Param("statusCode") Integer statusCode);
	
	
	
	@Query("SELECT f FROM Friends f WHERE (f.friendRequester=:requester AND f.friendAccepter=:accepter) "
					+ "OR (f.friendRequester=:accepter AND f.friendAccepter=:requester)")
	public Friends findStatusCodeByFriendRequesterAndFriendAccepter(
						@Param("requester") User requester, @Param("accepter") User accepter);
	
	@Query("SELECT f FROM Friends f WHERE (f.friendRequester=:requester OR f.friendAccepter=:requester) "
					+ "AND f.statusCode=0 AND f.lastActedFriend !=:requester ")
	public List<Friends> getAllRecievedPendingFriendRequests(@Param("requester") User requester);
	
	
	@Query("SELECT f FROM Friends f WHERE (f.friendRequester=:requester OR f.friendAccepter=:requester) "
			+ "AND f.statusCode=0 AND f.lastActedFriend =:requester ")
	public List<Friends> getAllSentPendingFriendRequests(@Param("requester") User requester);
	
	
}