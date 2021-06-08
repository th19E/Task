package com.oddschecker.task.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "odds")
@Getter
@Setter
public class OddsDao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	private String userId;

	@NotNull
	private String odds;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "bet_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private BetDao bet;

	public OddsDao() {
	}

	public OddsDao(BetDao betDao, String userId, String odds) {
		this.bet = betDao;
		this.userId = userId;
		this.odds = odds;
	}
}
