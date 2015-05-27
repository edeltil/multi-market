package fr.weepstone.multi_market.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Entity
@Data
@Getter
@ToString(exclude = "user")
public final class Provider {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Enumerated(EnumType.ORDINAL)
	private NameProvider name;
	@OneToOne
	private ParamProvider userName;
	@OneToOne
	private ParamProvider password;
	@OneToMany(targetEntity = ParamProvider.class, mappedBy = "provider", fetch = FetchType.EAGER)
	private List<ParamProvider> params;
	private Boolean isFB;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private UserMarket user;
}
