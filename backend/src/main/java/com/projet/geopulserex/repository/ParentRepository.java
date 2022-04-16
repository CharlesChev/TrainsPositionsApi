package com.projet.geopulserex.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import com.projet.geopulserex.entity.Position;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

@Repository
public interface ParentRepository extends JpaRepository <Position,Long>{
    
//Queries appelées dans ParcoursEnginController et CourseController
    @Query(value="SELECT p.id,p.x,p.y,p.ecart_horaire_sillon,p.utc_observation,p.date_heure_depart_sillon,p.cap_circulation,p.source,p.technique_detection, p.vitesse_instantanee FROM positions p INNER JOIN courses c on p.id = c.id_positions where date_trunc('day',utc_observation) = to_timestamp(:date_heure_depart_sillon, 'YYYY-MM-DD') and c.numero_course = :courseNumb ORDER BY  p.utc_observation ASC", nativeQuery = true)
    List<Position> getCourses(@Param("courseNumb") String courseNumb, @Param("date_heure_depart_sillon")  String dayHourStartingCourse);
   
    @Query(value="SELECT p.id,p.x,p.y,p.ecart_horaire_sillon,p.utc_observation,p.date_heure_depart_sillon,p.cap_circulation,p.source,p.technique_detection, p.vitesse_instantanee FROM positions p INNER JOIN engins e on p.id = e.id_positions where date_trunc('day',utc_observation) = to_timestamp(:date_heure_depart_sillon, 'YYYY-MM-DD') and e.numero_engin = :enginNumb ORDER BY  p.utc_observation ASC", nativeQuery = true)
    List<Position> getEnginPositions(@Param("enginNumb") String enginNumb, @Param("date_heure_depart_sillon")  String startDate);
   
    @Query(value="SELECT p.id,p.x,p.y,p.ecart_horaire_sillon,p.utc_observation,p.date_heure_depart_sillon,p.cap_circulation,p.source,p.technique_detection,p.vitesse_instantanee FROM positions p INNER JOIN courses c on p.id = c.id_positions where  date_trunc('day',utc_observation) between to_timestamp(:date_depart_sillon, 'YYYY-MM-DD') and to_timestamp(:date_arrivee_sillon, 'YYYY-MM-DD') and ( c.numero_course = :courseNumb)  ORDER BY  p.utc_observation ASC ", nativeQuery = true)
    List<Position> getCoursesOnPeriod(@Param("courseNumb") String courseNumb , @Param("date_depart_sillon")  String dayHourStartingCourse, @Param("date_arrivee_sillon")  String dayHourEndCourse);
   
    @Query(value="SELECT p.id,p.x,p.y,p.ecart_horaire_sillon,p.utc_observation,p.date_heure_depart_sillon,p.cap_circulation,p.source,p.technique_detection,p.vitesse_instantanee FROM positions p INNER JOIN engins e on p.id = e.id_positions where  date_trunc('day',utc_observation) between to_timestamp(:date_depart_sillon, 'YYYY-MM-DD') and to_timestamp(:date_arrivee_sillon, 'YYYY-MM-DD') and ( e.numero_engin = :enginNumb)  ORDER BY  p.utc_observation ASC ", nativeQuery = true)
    List<Position> getEnginsOnPeriod(@Param("enginNumb") String enginNumb, @Param("date_depart_sillon")  String dayHourStart, @Param("date_arrivee_sillon")  String dayHourEnd);

//Query appelée dans PositionsByNumEnginAndUtcController
    @Query (value = "(SELECT id,x,y,ecart_horaire_sillon,utc_observation,date_heure_depart_sillon,cap_circulation,source,technique_detection,vitesse_instantanee FROM engins INNER JOIN positions ON id_positions = positions.id WHERE numero_engin = :enginNumb AND utc_observation > to_timestamp(:positionTime, 'YYYY-MM-DD HH24:MI:SS') limit 1) UNION (SELECT id,x,y,ecart_horaire_sillon,utc_observation,date_heure_depart_sillon,cap_circulation,source,technique_detection,vitesse_instantanee FROM engins INNER JOIN positions ON id_positions = positions.id WHERE utc_observation =(SELECT max(utc_observation) FROM (SELECT id,x,y,ecart_horaire_sillon,utc_observation,date_heure_depart_sillon,cap_circulation,source,technique_detection,vitesse_instantanee FROM engins INNER JOIN positions ON id_positions = positions.id WHERE numero_engin = :enginNumb AND utc_observation < to_timestamp(:positionTime, 'YYYY-MM-DD HH24:MI:SS')) as utc_inf) AND numero_engin = :enginNumb)", nativeQuery = true)
    List<Position> getPositionsByNumEnginAndUtc(@Param("enginNumb") String enginNumb, @Param("positionTime") String positionTime);

//Query appelée dans PositionByNumCourseAndUtcController
    @Query (value = "(SELECT id,x,y,ecart_horaire_sillon,utc_observation,date_heure_depart_sillon,cap_circulation,source,technique_detection,vitesse_instantanee FROM courses INNER JOIN positions ON id_positions = positions.id WHERE numero_course = :courseNumb AND utc_observation > to_timestamp(:positionTime, 'YYYY-MM-DD HH24:MI:SS') limit 1) UNION (SELECT id,x,y,ecart_horaire_sillon,utc_observation,date_heure_depart_sillon,cap_circulation,source,technique_detection,vitesse_instantanee FROM courses INNER JOIN positions ON id_positions = positions.id WHERE utc_observation =(SELECT max(utc_observation) FROM (SELECT id,x,y,ecart_horaire_sillon,utc_observation,date_heure_depart_sillon,cap_circulation,source,technique_detection,vitesse_instantanee FROM courses INNER JOIN positions ON id_positions = positions.id WHERE numero_course = :courseNumb AND utc_observation < to_timestamp(:positionTime, 'YYYY-MM-DD HH24:MI:SS')) as utc_inf) AND numero_course = :courseNumb)", nativeQuery = true)
    List<Position> getPositionsByNumCourseAndUtc(@Param("courseNumb") String courseNumb, @Param("positionTime") String positionTime);
}
