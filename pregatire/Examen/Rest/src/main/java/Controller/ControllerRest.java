package Controller;

import Domain.Jucator;
import Domain.RequestStatus;
import Repo.IJocRepository;
import Repo.IJucatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api")
public class ControllerRest {


    @Autowired
    private IJocRepository crrRepository;




    @GetMapping("/{id}")
    public String getAll( @PathVariable Integer id){
        String all;

        all=crrRepository.getCuvinteRunde(id);

        return all;

    }


    @GetMapping("/{id}/{user}")
    public String getAll2( @PathVariable Integer id,  @PathVariable Integer user){
        String all;

        all=crrRepository.getCaracteristiciPeJoc(id,user);

        return all;

    }



}
