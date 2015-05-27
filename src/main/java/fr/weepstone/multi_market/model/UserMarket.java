package fr.weepstone.multi_market.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.Getter;

@Entity
@Data
@Getter
public class UserMarket {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String username;
	private String password;
	@OneToMany(targetEntity = Provider.class, mappedBy = "user", fetch = FetchType.EAGER)
	private List<Provider> providers;

}
