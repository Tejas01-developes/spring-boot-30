package project1.project.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.project.schemas.userschema;

import java.util.Optional;

public interface usersrepo extends JpaRepository<userschema,String> {

}
