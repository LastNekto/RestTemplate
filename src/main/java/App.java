import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class App {

    public static String URL = "http://94.198.50.185:7081/api/users";

    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();

        //все пользователи и sessionId
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, null, String.class);
        String sessionId = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);


        //заголовок
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Cookie", sessionId);

        //Пользователь с Id = 3 сохранен
        User newUser = new User(3L, "James", "Brown", (byte)25);
        HttpEntity<User> postRequest = new HttpEntity<>(newUser, headers);
        ResponseEntity<String> postResponse = restTemplate.exchange(URL,  HttpMethod.POST, postRequest, String.class );
        String codePart1 = postResponse.getBody();

        //обновить
        User userUpdate = new User(3L, "Thomas", "Shelby", (byte)25);
        HttpEntity<User> putRequest = new HttpEntity<>(userUpdate, headers);
        ResponseEntity<String> putResponse = restTemplate.exchange(URL + "/3", HttpMethod.PUT, putRequest, String.class );
        String codePart2 = putResponse.getBody();

        //удалить
        HttpEntity<String> deleteRequest = new HttpEntity<>(headers);
        ResponseEntity<String> deleteResponse = restTemplate.exchange(URL + "/3", HttpMethod.DELETE, deleteRequest, String.class );
        String codePart3 = deleteResponse.getBody();

        //показать
        String finalCode = codePart1 + codePart2 + codePart3;
        System.out.println(finalCode);
    }
}
