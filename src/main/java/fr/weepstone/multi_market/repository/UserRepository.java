package fr.weepstone.multi_market.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.weepstone.multi_market.model.UserMarket;

@Repository
public interface UserRepository extends JpaRepository<UserMarket, Integer> {

	List<UserMarket> findAll();

	UserMarket findByUsername(String username);

}
