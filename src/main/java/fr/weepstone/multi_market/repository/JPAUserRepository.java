package fr.weepstone.multi_market.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import fr.weepstone.multi_market.model.UserMarket;

@Repository
public class JPAUserRepository /* implements UserRepository */{

	@PersistenceContext
	private EntityManager entityManager;

	public List<UserMarket> findAll() {
		return entityManager.createQuery("SELECT * FROM user_market ", UserMarket.class)
				.getResultList();
	}

	public UserMarket findByUsername(String username) {
		return entityManager.createQuery("SELECT * FROM user_market where username ='" + username + "'", UserMarket.class).getSingleResult();
	}
}
