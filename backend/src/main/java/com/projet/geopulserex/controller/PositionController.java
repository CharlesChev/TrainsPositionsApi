package com.projet.geopulserex.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.text.ParseException;
import org.apache.http.client.ClientProtocolException;
import javax.validation.constraints.Pattern;
import java.util.List;

import com.projet.geopulserex.customclass.positioncontollerresponseobjects.PositionControllerCourseResponse;
import com.projet.geopulserex.customclass.positioncontollerresponseobjects.PositionControllerEnginResponse;
import com.projet.geopulserex.entity.Position;
import com.projet.geopulserex.exception.GeomobileException;
import com.projet.geopulserex.repository.ParentRepository;
import com.projet.geopulserex.service.SurrondingPositionsSizeCaseService;
import com.projet.geopulserex.service.RepositoryService;

@CrossOrigin("*")
@Controller
@Validated
public class PositionController {

    @Autowired
    private RepositoryService getRepository;

    @Autowired
    private SurrondingPositionsSizeCaseService posEncadrantesSizeCase;

    @Value("${maxExceeding}")
    public int maxExceeding;

    /**
     * 
     * @param env
     * @param enginNumb
     * @param timeStamp
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws org.apache.http.auth.AuthenticationException
     * @throws ParseException
     */
    // Prend la position gps prise juste avant l'heure demandée et celle juste après (posEncadrantes) puis génère une polyligne avec géo2rail.
    // On extrait de cette polyligne la position la plus proche de celle de ou était l'engin à l'heure donnée.
    @ResponseBody
    @GetMapping("/position/engin/{env}/{enginNumb}/{timeStamp}")
    public PositionControllerEnginResponse getPositionByNumEnginAndUtc(@PathVariable String env,
            @PathVariable @Pattern(regexp = "[A-Za-z0-9]+", message = "Le numero d'engin n'est pas au bon format") String enginNumb,
            @PathVariable @Pattern(regexp = "[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9]", message = "l'horraire doit être au format 'yyyy-MM-dd HH:mm:ss'") String timeStamp)
            throws ClientProtocolException, IOException, org.apache.http.auth.AuthenticationException, ParseException {

        // recupération des positions précédante et suivante de l'heure donnée
        ParentRepository repository = getRepository.getRepo(env);
        
        List<Position> surrondingPos;

        surrondingPos = repository.getPositionsByNumEnginAndUtc(enginNumb, timeStamp);
       
        // Vérification du nombre de positions encadrante reçue (idéalement 2)
        if (surrondingPos.isEmpty()) {
            throw new GeomobileException(
                    "Géomobile n'a pas trouvé de données correspondantes.");
        } else if (surrondingPos.size() == 1) {
            return posEncadrantesSizeCase.enginSurrondingPosSizeEqualOne(timeStamp, enginNumb, surrondingPos, maxExceeding);
        } else {
            return posEncadrantesSizeCase.enginSurrondingPosSizeEqualTwo(timeStamp, enginNumb, surrondingPos);
        }

    }

    
    /**
     * 
     * @param env
     * @param courseNumb
     * @param timeStamp
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws org.apache.http.auth.AuthenticationException
     * @throws ParseException
     */
    // Prend la position gps prise juste avant l'heure demandée et celle juste après (posEncadrantes) puis génère une polyligne avec géo2rail.
    // On extrait de cette polyligne la position la plus proche de celle de ou était la course à l'heure donnée.
    @ResponseBody
    @GetMapping("/position/course/{env}/{courseNumb}/{timeStamp}")
    public PositionControllerCourseResponse getPositionByNumCourseAndUtc(@PathVariable String env,
            @PathVariable @Pattern(regexp = "[A-Za-z0-9]+", message = "Le numero de course n'est pas au bon format") String courseNumb,
            @PathVariable @Pattern(regexp = "[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9]", message = "l'horraire doit être au format 'yyyy-MM-dd HH:mm:ss'") String timeStamp)
            throws ClientProtocolException, IOException, org.apache.http.auth.AuthenticationException, ParseException {

        // recupération des positions précédante et suivante de l'heure donnée
        ParentRepository repository = getRepository.getRepo(env);
        List<Position> surrondingPos;

       
        surrondingPos = repository.getPositionsByNumCourseAndUtc(courseNumb, timeStamp);
       
        // Vérification dunombre de positions encadrante reçue (idéalement 2)
        if (surrondingPos.isEmpty()) {
            throw new GeomobileException(
                    "Géomobile n'a pas trouvé de données correspondantes.");
        } else if (surrondingPos.size() == 1) {
            posEncadrantesSizeCase.courseSurrondingPosSizeEqualOne(timeStamp, surrondingPos);
            throw new GeomobileException(
                posEncadrantesSizeCase.courseSurrondingPosSizeEqualOne(timeStamp, surrondingPos)
            );
        } else{
            return posEncadrantesSizeCase.courseSurrondingPosSizeEqualTwo(timeStamp, surrondingPos, courseNumb);
        }
        
            
        
    }

}