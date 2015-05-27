package fr.weepstone.multi_market;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;


@Configuration
public class MultiMarketConfig {

	@Bean
	public HibernateJpaSessionFactoryBean sessionFactory(EntityManagerFactory emf) {
		HibernateJpaSessionFactoryBean factory = new HibernateJpaSessionFactoryBean();
		factory.setEntityManagerFactory(emf);
		return factory;
	}

}
