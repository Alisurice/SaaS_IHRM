import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.client.RestTemplate;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@SpringBootTest(classes = TestUserController.class)
//@RunWith(SpringRunner.class)
public class TestUserController {

    private int port = 9002;
    RestTemplate restTemplate = new RestTemplate();
    @Test
    public void testLogin(){


        Map<String, String> form = new HashMap<>();
        form.put("mobile", "234");
        form.put("password", "123456");

        String result = restTemplate.postForObject("http://localhost:"+port+"/sys/login", form, String.class);
        System.out.println(result);

        form.put("password", "x3456");
        result = restTemplate.postForObject("http://localhost:"+port+"/sys/login", form, String.class);
        System.out.println(result);


    }
    @Test
    public void testProfile(){
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("Authorization", "Bearer eyJhbGciOiJIUdzI1NiJ9.eyJqdGkiOiIxNDczODg3Nzg2MDE2NDY4OTkyIiwic3ViIjoiMjM0IiwiaWF0IjoxNjQwMzQ5NzA2LCJjb21wYW55SWQiOiIxIiwiZXhwIjoxNjQwMzUzMzA2fQ.3dzzoM11hw8FxZalwoXZ2_Om7Q0s6YD_qbB8ZlUQCUE");
        HttpEntity<String> formEntity = new HttpEntity<String>("", headers);


        String result = restTemplate.postForObject("http://localhost:"+port+"/sys/profile", formEntity, String.class);
        System.out.println(result);
    }
}
