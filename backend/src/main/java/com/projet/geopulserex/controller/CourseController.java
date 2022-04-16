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
public class CourseController {

    @Autowired
    private RepositoryService getRepository;

    /**
     * 
     * @param env
     * @param courseNumb
     * @param dayHourStartingCourse
     * @return
     */
    @ResponseBody
    @GetMapping("/course/{env}/{courseNumb}/{dayHourStartingCourse}")
    public List<Position> getCourses(@PathVariable String env,
            @PathVariable @Pattern(regexp = "[A-Za-z0-9]+", message = "Le numero de course n'est pas au bon format") String courseNumb,
            @PathVariable @Pattern(regexp = "[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])", message = "l'horraire doit être au format 'yyyy-MM-dd'") String dayHourStartingCourse) {

        List<Position> course;
        ParentRepository repository = getRepository.getRepo(env);
   
        course = repository.getCourses(courseNumb, dayHourStartingCourse);
        
        if (course.isEmpty()) {
            throw new GeomobileException(
                    "Géomobile n'a pas trouvé de données correspondantes.");
        }

        return course;
    }

    /**
     * 
     * @param env
     * @param courseNumb
     * @param dayHourStartingCourse
     * @param dayHourEndCourse
     * @return
     */
    @ResponseBody
    @GetMapping("/course/{env}/{courseNumb}/{dayHourEndCourse}/{dayHourStartingCourse}")
    public List<Position> getCoursesTimelapse(@PathVariable String env,
            @PathVariable @Pattern(regexp = "[A-Za-z0-9]+", message = "Le numero de course n'est pas au bon format") String courseNumb,
            @PathVariable @Pattern(regexp = "[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])", message = "l'horraire doit être au format 'yyyy-MM-dd HH:mm:ss'") String dayHourStartingCourse,
            @PathVariable @Pattern(regexp = "[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])", message = "l'horraire doit être au format 'yyyy-MM-dd'") String dayHourEndCourse) {

        List<Position> course;
        ParentRepository repository = getRepository.getRepo(env);
    
        course = repository.getCoursesOnPeriod(courseNumb, dayHourStartingCourse, dayHourEndCourse);
        

        if (course.isEmpty()) {
            throw new GeomobileException(
                    "Géomobile n'a pas trouvé de données correspondantes.");
        }

        return course;
    }

}
