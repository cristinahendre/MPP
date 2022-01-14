package Controller;

import Domain.Joc;
import Domain.Persoana;
import Domain.RequestStatus;
import Repo.IJocRepository;
import Repo.IPersoanaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;

@CrossOrigin
@RestController
@RequestMapping("api/persoane")
public class ControllerRest {


    @Autowired
    private IJocRepository crrRepository;



    @GetMapping("/{nume}")
    public Iterable<Joc> getAll(@PathVariable Integer nume){
        Iterable<Joc> all;

        all=crrRepository.getJocuriPersoana(nume);

        return all;

    }




}
