package Controller;

import Domain.Nota;
import Domain.Participant;
import Domain.Persoana;
import Domain.RequestStatus;
import Repo.INoteRepository;
import Repo.IPersoanaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/")
public class ControllerRest {


    @Autowired
    private INoteRepository crrRepository;




    @GetMapping("/participanti/{id}")
    public Nota getAll(@PathVariable String id){
        Nota all;

        all=crrRepository.getPunctajeParticipant(id);

        return all;

    }

    @GetMapping("/juriu/{id}")
    public List<Participant> getByJuriu(@PathVariable String id){

        System.out.println("juriu = "+id);
        List<Participant> rez=crrRepository.getParticipantiDelaJuriu(id);
        return rez;
    }







}
