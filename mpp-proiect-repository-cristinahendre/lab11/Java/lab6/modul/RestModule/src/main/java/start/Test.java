package start;


import Domain.CazCaritabil;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;



public class Test {
    public static final String URL = "http://localhost:8080/api/cazuri";

    private RestTemplate restTemplate = new RestTemplate();

    private <T> T execute(Callable<T> callable) throws ServiceException {
        try {
            return callable.call();
        } catch (ResourceAccessException | HttpClientErrorException e) { // server down, resource exception
            throw new ServiceException(e);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public CazCaritabil[] getAll() {
        try {
            return execute(() -> restTemplate.getForObject(URL, CazCaritabil[].class));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return null;
    }

    public CazCaritabil getById(int id) {
        try {
            return execute(() -> restTemplate.getForObject(String.format("%s/%s", URL, id), CazCaritabil.class));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int create(CazCaritabil user) {
        try {
          return execute(() ->
                    restTemplate.postForObject(URL, user, int.class));

        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return -1;

    }

    public CazCaritabil update(CazCaritabil user) {
        try {
            String newUrl= URL+"/"+user.getId();
            System.out.println("new url is: "+newUrl);
            return execute(() -> {
               restTemplate.put(newUrl,user,CazCaritabil.class);
              return user;
            });
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(int id) {
        try {
            execute(() -> {
                restTemplate.delete(String.format("%s/%s", URL, id));
                return null;
            });
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

}
