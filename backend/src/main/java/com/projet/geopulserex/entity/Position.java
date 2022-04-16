package com.projet.geopulserex.entity;

import javax.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.*;

@Entity
@Table(name = "positions")
public class Position {
	@Id
	private int id;
	private double x;
	private double y;
	@DateTimeFormat(pattern = "yyyy-MM-dd hh.mm.ss")
	private Date date_heure_depart_sillon;
	private Date utc_observation;
	private Integer ecart_horaire_sillon ;
	private Integer cap_circulation;
	private String source;
	private String technique_detection;
	private Double  vitesse_instantanee;
	 
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "id")
    private List<Engin> engin;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "id_positions")
    private List<Course> course;
	
	@Override
	public String toString() {
	    return "Position [id=" + id + ", x=" + x   +"y" +y+ "ecart_horaire_sillon" +ecart_horaire_sillon+
	    		"utc observation" + utc_observation + "date" + date_heure_depart_sillon +
	    		"cap_circluation"+ cap_circulation+ "source" +source+ "technique_detection" +technique_detection+ "vitesse_instantanee" +vitesse_instantanee+course+"///]";
	}
	 
	 

		@Column(name = "id", nullable = false)
		public int getId() {
			return id;
		}



		public void setId(int id) {
			this.id = id;
		}


	    @Column(name = "x", nullable = false)
		public double getX() {
			return x;
		}



		public void setX(double x) {
			this.x = x;
		}


	    @Column(name = "y", nullable = false)
		public double getY() {
			return y;
		}

		public void setY(double y) {
			this.y = y;
		}

	    @Column(name = "date_heure_depart_sillon", nullable = false)
		public Date getDate_heure_depart_sillon() {
			return date_heure_depart_sillon;
		}


		public void setDate_heure_depart_sillon(Date date_heure_depart_sillon) {
			this.date_heure_depart_sillon = date_heure_depart_sillon;
		}

	    @Column(name = "ecart_horaire_sillon", nullable = true)
		public Integer getEcart_horaire_sillon() {
			return ecart_horaire_sillon;
		}


		public void setEcart_horaire_sillon(Integer ecart_horaire_sillon) {
			this.ecart_horaire_sillon = ecart_horaire_sillon;
		}

	    @Column(name = "utc_observation", nullable = false)
	    public Date getUtc_observation() {
			return utc_observation;
		}


		public void setUtc_observation(Date utc_observation) {
			this.utc_observation = utc_observation;
		}

	    @Column(name = "cap_circulation")
	    public Integer getCap_circulation() {
		return cap_circulation;
	    }


	    public void setCap_circulation(Integer cap_circulation) {
		this.cap_circulation = cap_circulation;
		}

	    @Column(name = "source")
		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

	    @Column(name = "technique_detection")
		public String getTechnique_detection() {
			return technique_detection;
		}


		public void setTechnique_detection(String technique_detection) {
			this.technique_detection = technique_detection;
		}

	    @Column(name = "vitesse_instantanee",nullable = true)
		public Double  getVitesse_instantanee() {
			return vitesse_instantanee;
		}

		public void setVitesse_instantanee(Double  vitesse_instantanee) {
			this.vitesse_instantanee = vitesse_instantanee;
		}

		public List<Course> getCourse(){
			return course;
		}

		public void course(List<Course> course) {
			this.course = course;
		}
	
}