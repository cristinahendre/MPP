package start;


import Domain.CazCaritabil;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;




public class Apel {
    private final static Test test =new Test();
    public static void main(String[] args) {
        RestTemplate restTemplate=new RestTemplate();
        CazCaritabil caz=new CazCaritabil("Donation Wild");
        try{


            System.out.println("cazuri before: ");
            show(()->{
                CazCaritabil[] res= test.getAll();
                for(CazCaritabil u:res){
                    System.out.println(u.toString());
                }
            });
            int id =test.create(caz);
            System.out.println("\n\nam creat caz cu id: "+id);

            show(()->{
                System.out.println("\n\nam adaugat:" +test.getById(id));
            });

            caz.setSuma_donata(100000);
            caz.setId(id);
            test.update(caz);
            show(()->{
                System.out.println("\n\nafter update: "+test.getById(id));
            });

            System.out.println("\n\nbefore delete.");
            show(()->{
                CazCaritabil[] res= test.getAll();
                for(CazCaritabil u:res){
                    System.out.println(u.toString());
                }
            });


            test.delete(id);

            System.out.println("\n\nafter delete.");
            show(()->{
                CazCaritabil[] res= test.getAll();
                for(CazCaritabil u:res){
                    System.out.println(u.toString());
                }
            });

        }catch(RestClientException ex){
            System.out.println("Exception ... "+ex.getMessage());
        }

    }



    private static void show(Runnable task) {
        task.run();
    }
}
