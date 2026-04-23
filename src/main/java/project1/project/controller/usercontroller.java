package project1.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project1.project.schemas.userschema;
import project1.project.service.usersservice;

import java.util.Map;
import java.util.UUID;

@RequestMapping("/apis")
@RestController
public class usercontroller {

    @Autowired
    private usersservice usersservice;
    @Autowired
    private BCryptPasswordEncoder bcrypt=new BCryptPasswordEncoder();
@PostMapping("/")
    public ResponseEntity<?>inseruser(@RequestBody userschema data) {
        String hash = bcrypt.encode(data.getPassword());
        data.setPassword(hash);
        String userid= UUID.randomUUID().toString().substring(0,8);
        data.setUserid(userid);
        try {
            usersservice.inseruser(data);
            return ResponseEntity.ok(Map.of("message", "user inserted"));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(Map.of("message","insertion failed"));
        }
    }
    public ResponseEntity<?> getdetails(@RequestBody userschema data){
    try{
        usersservice.getdetail(data);

        return  ResponseEntity.ok().body(Map.of("message","details fetch succesfully done"));
    } catch (Exception e) {
        throw new RuntimeException(e);
    }

    }



}
