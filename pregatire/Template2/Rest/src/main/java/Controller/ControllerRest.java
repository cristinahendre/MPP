package Controller;

import Domain.Persoana;
import Domain.RequestStatus;
import Repo.IPersoanaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/persoane")
public class ControllerRest {


    @Qualifier("persoanaRepo")
    @Autowired
    private IPersoanaRepository crrRepository;


    @GetMapping("/test")
    public  String test(@RequestParam(value="name", defaultValue="Hello") String name) {
        return name.toUpperCase();
    }


    @GetMapping()
    public Iterable<Persoana> getAll(@RequestParam (value="status", required=false) RequestStatus status){
        Iterable<Persoana> all;

        all=crrRepository.findAll();

        return all;

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        System.out.println("Get by id "+id);
        Persoana request=crrRepository.findOne(id);
        if (request==null)
            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Persoana>(request, HttpStatus.OK);
    }

    @GetMapping("/{n}/{p}")
    public ResponseEntity<?> getByNume(@PathVariable String n, @PathVariable String p){
        System.out.println("Get by name: "+n);
        Persoana request=crrRepository.getPersDupaDate(n,p);
        if (request==null)
            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Persoana>(request, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    Persoana update(@PathVariable int id, @RequestBody Persoana e){
        e.setId(id);
        crrRepository.update(e);
        Persoana nou =crrRepository.findOne(id);
        return nou;
    }





    @PostMapping()
    int add(@RequestBody Persoana e){
        crrRepository.save(e);
        int id =crrRepository.getPersDupaDate(e.getParola(),e.getEmail()).getId();
        System.out.println("[crest]return id : "+id);
        return id;

    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        crrRepository.delete(id);
    }




}
