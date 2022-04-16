package com.projet.geopulserex.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Table;



@Entity
@Table(name = "courses")
public class Course {
		
	
		@Id
		@Column(name="id_courses")
		private String id_courses;
		@Column(name="id_course")
		//!! s et sans s
		private String id_course;
		@Column(name="id_positions")
		private int id_positions;
		@Column(name="utc_depart")
		private Date utc_depart;
		@Column(name="numero_course")
		private String numerocourse;
		
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "id_positions",  referencedColumnName="id", insertable = false, updatable = false)
		private Position position;
		
		@Column(name = "id_course", nullable = false)
		public Date getUtc_depart() {
			return utc_depart;
		}			
		
}
