package com.projet.geopulserex.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import java.util.List;

import javax.validation.constraints.Pattern;

import com.projet.geopulserex.entity.Position;
import com.projet.geopulserex.exception.GeomobileException;
import com.projet.geopulserex.repository.ParentRepository;
import com.projet.geopulserex.service.RepositoryService;

@CrossOrigin("*")
@Controller
@Validated
public class ParcoursEnginController {

    @Autowired
    private RepositoryService repositoryService;

    /**
     * 
     * @param env
     * @param enginNumb
     * @param startDate
     * @return
     */
    @ResponseBody
    @GetMapping("/parcoursEngin/{env}/{enginNumb}/{startDate}")
    public List<Position> getEnginPositionsByNumAndDateDepart(@PathVariable String env, @PathVariable @Pattern(regexp = "[A-Za-z0-9]+", message = "Le numero d'engin n'est pas au bon format") String enginNumb, @PathVariable @Pattern(regexp = "[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])", message = "l'horraire doit être au format 'yyyy-MM-dd'") String startDate) {
        ParentRepository repository = repositoryService.getRepo(env);

        List<Position> parcoursEngin;

        parcoursEngin = repository.getEnginPositions(enginNumb, startDate);
        
        if (parcoursEngin.isEmpty()) {
            throw new GeomobileException(
                "Géomobile n'a pas trouvé de données correspondantes.");
        }
        return parcoursEngin;
    }

    /**
     * 
     * @param env
     * @param enginNumb
     * @param dayHourStart
     * @param dayHourEnd
     * @return
     */
    @ResponseBody
    @GetMapping("/parcoursEngin/{env}/{enginNumb}/{dayHourStart}/{dayHourEnd}")
    public List<Position> getEnginPositionsTimelapse(@PathVariable String env, @PathVariable @Pattern(regexp = "[A-Za-z0-9]+", message = "Le numero d'engin n'est pas au bon format") String enginNumb, @PathVariable @Pattern(regexp = "[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])", message = "l'horraire doit être au format 'yyyy-MM-dd HH:mm:ss'") String dayHourStart, @PathVariable @Pattern(regexp = "[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])", message = "l'horraire doit être au format 'yyyy-MM-dd'") String dayHourEnd) {
        List<Position> parcoursEngin;
        ParentRepository repository = repositoryService.getRepo(env);


    parcoursEngin = repository.getEnginsOnPeriod(enginNumb, dayHourStart, dayHourEnd);
   

    if (parcoursEngin.isEmpty()) {
        throw new GeomobileException(
            "Géomobile n'a pas trouvé de données correspondantes.");
        }
    return parcoursEngin;
    }

}
