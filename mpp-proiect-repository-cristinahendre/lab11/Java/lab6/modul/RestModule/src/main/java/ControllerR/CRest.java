package ControllerR;

import Domain.CazCaritabil;
import Domain.RequestStatus;
import Repository.CazRepo;
import Repository.CazRepoI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/cazuri")
public class CRest {
    @Autowired
    private CazRepoI crrRepository;


    @GetMapping("/test")
    public  String test(@RequestParam(value="name", defaultValue="Hello") String name) {
        return name.toUpperCase();
    }



    /*@RequestMapping(method = RequestMethod.GET)
    public Collection<ComputerRepairRequest> getAll(){
        System.out.println("Getting computerRepairRequests");
        Collection<ComputerRepairRequest> all=crrRepository.getAll();
        return all;

    }*/

    @GetMapping()
    public Iterable<CazCaritabil> getAll(@RequestParam (value="status", required=false) RequestStatus status){
        Iterable<CazCaritabil> all;

        all=crrRepository.findAll();

        return all;

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        System.out.println("Get by id "+id);
        CazCaritabil request=crrRepository.findOne(id);
        if (request==null)
            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<CazCaritabil>(request, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    CazCaritabil update(@PathVariable int id, @RequestBody CazCaritabil e){
        e.setId(id);
        crrRepository.update(e);
        CazCaritabil nou =crrRepository.findOne(id);
        return nou;
    }





    @PostMapping()
    int add(@RequestBody CazCaritabil e){
        crrRepository.save(e);
        int id =crrRepository.getIdCaz(e);
        System.out.println("[crest]return id : "+id);
        return id;

    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        crrRepository.delete(id);
    }


    @GetMapping("/testare")
    void testare(){

        Iterable<CazCaritabil> all= getAll(RequestStatus.New);
        for(CazCaritabil c: all){
            System.out.println(c);
        }

        System.out.println(getById(2));
        CazCaritabil c= new CazCaritabil("nouCaz");
        c.setSuma_donata(12);

        add(c);
        System.out.println("after add: ");
        all= getAll(RequestStatus.New);
        for(CazCaritabil ca: all){
            System.out.println(ca);
        }
        int id =crrRepository.getIdCaz(c);
        System.out.println("am gasit id: "+id);
        if(id!=-1){
            c.setId(id);
            c.setNume("nume nou");
            update(id, c);
            System.out.println("after update: ");
            all= getAll(RequestStatus.New);
            for(CazCaritabil ca: all){
                System.out.println(ca);
            }


            delete(id);
            System.out.println("after delete: ");
            all= getAll(RequestStatus.New);
            for(CazCaritabil ca: all){
                System.out.println(ca);
            }
        }

    }

}
