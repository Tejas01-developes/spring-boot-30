package project1.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project1.project.repos.usersrepo;
import project1.project.schemas.userschema;

import java.util.Optional;

@Service
public class usersservice {

    @Autowired
    private usersrepo usersrepo;



public userschema inseruser(userschema data) throws Exception {
    try{
       userschema insertt=usersrepo.save(data);
       return  insertt;
    } catch (RuntimeException e) {
        throw new Exception("insertion failed");
    }
}

public Optional<userschema> getdetail(userschema data){
    try{
       return usersrepo.findById(String.valueOf(data));
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}


}
