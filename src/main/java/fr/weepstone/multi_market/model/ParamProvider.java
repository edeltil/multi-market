package fr.weepstone.multi_market.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Entity
@Data
@Getter
@ToString(exclude = "provider")
public class ParamProvider {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String key;
	private String value;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = true)
	private Provider provider;
}
