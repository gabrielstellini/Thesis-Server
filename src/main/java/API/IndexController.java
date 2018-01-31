package API;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class IndexController {
    @RequestMapping("/")
    String get(){
        //mapped to hostname:port/home/
        return "Hello from get";
    }
    @RequestMapping("/index")
    String index(){
        //mapped to hostname:port/home/index/
        System.out.println("HERE!");
        return "Hello from index";
    }
}