import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


import java.util.HashMap;
import java.util.Map;


@SpringBootTest(classes = TestSSController.class)
//@RunWith(SpringRunner.class)
public class TestSSController {

    private int port = 9001;
    RestTemplate restTemplate = new RestTemplate();
    String loginResult = "";

    @Test
    public void testLogin(){
        Map<String, String> form = new HashMap<>();
        form.put("mobile", "13800000001");
        form.put("password", "123456");

        String result = restTemplate.postForObject("http://localhost:"+port+"/sys/login", form, String.class);
        System.out.println(result);



    }

    @Test
    public void testGetSettings(){
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("Authorization", "Bearer 292ea059-3465-4665-8e5d-1bc0be925324");
        HttpEntity<String> formEntity = new HttpEntity<String>("", headers);


        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:"+port+"/social_securitys/settings", HttpMethod.GET,formEntity, String.class);
        System.out.println(exchange.getBody());
    }



}
