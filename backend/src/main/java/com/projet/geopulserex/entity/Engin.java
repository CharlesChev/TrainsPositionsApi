package com.projet.geopulserex.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "engins")
public class Engin {


	@Id
	@GeneratedValue
	private int id;
	@Column(name="id_positions")
	private int idposition;
	@Column(name="numero_engin")
	private String numeroengin;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idposition",  referencedColumnName="id")
	private Position position;
	
	
}
