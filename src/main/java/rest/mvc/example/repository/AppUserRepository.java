package rest.mvc.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rest.mvc.example.domain.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

}
