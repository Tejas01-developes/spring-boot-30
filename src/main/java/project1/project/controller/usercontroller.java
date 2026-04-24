package project1.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import project1.project.schemas.userschema;
import project1.project.service.usersservice;

import java.util.Map;
import java.util.Optional;
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
    @GetMapping("/get")
    public ResponseEntity<?> getdetails(@RequestBody userschema data){
    try{
       Optional<userschema> res= usersservice.getdetail(data);
       if(res.isEmpty()){
           return ResponseEntity.badRequest().body(Map.of("message","result is empty"));
       }
    boolean compare=bcrypt.matches(data.getPassword(), res.get().getPassword());
       if(!compare){
           return ResponseEntity.badRequest().body(Map.of("message","password is incorrect"));
       }
       return  ResponseEntity.ok().body(Map.of("message","login success"));

    } catch (Exception e) {
        throw new RuntimeException(e);
    }

    }



}
