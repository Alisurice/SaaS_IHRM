import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.client.RestTemplate;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@SpringBootTest(classes = TestUserController.class)
//@RunWith(SpringRunner.class)
public class TestUserController {

    private int port = 9002;
    RestTemplate restTemplate = new RestTemplate();
    String loginResult = "";

    @Test
    public void testLogin(){
        Map<String, String> form = new HashMap<>();
        form.put("mobile", "13800000001");
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
        headers.add("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxNDczODg3Nzg2MDE2NDY4OTkyIiwic3ViIjoiMjM0IiwiaWF0IjoxNjQwNjA0OTU2LCJjb21wYW55SWQiOiIxIiwiZXhwIjoxNjQwNjA4NTU2fQ.PRrqzJtqPv4DEE_VTlKVdcSQxQVqKyAxtMbe0_4b3QU");
        HttpEntity<String> formEntity = new HttpEntity<String>("", headers);


        String result = restTemplate.postForObject("http://localhost:"+port+"/sys/profile", formEntity, String.class);
        System.out.println(result);
    }

    @Test
    public void testShiroLogin(){
        Map<String, String> form = new HashMap<>();
        form.put("mobile", "2341");
        form.put("password", "123456");

        String result = restTemplate.postForObject("http://localhost:"+port+"/sys/login", form, String.class);
        System.out.println(result);
        loginResult =  result;
    }

    @Test
    public void testShiroProfile() throws IOException {
        //得先登录，完成安全数据的构造
        ObjectMapper mapper = new ObjectMapper();
        TestUserController controller = new TestUserController();
        controller.testShiroLogin();
        JsonNode jsonNode = mapper.readTree(controller.loginResult);
        String json = "Bearer "+jsonNode.get("data").asText();
        System.out.println(json);

        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("Authorization", json);
        HttpEntity<String> formEntity = new HttpEntity<String>("", headers);


        String result = restTemplate.postForObject("http://localhost:"+port+"/sys/profile", formEntity, String.class);
        System.out.println(result);
    }
}
