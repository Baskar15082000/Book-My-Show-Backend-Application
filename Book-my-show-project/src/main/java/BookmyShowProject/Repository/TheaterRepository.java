package BookmyShowProject.Repository;

import BookmyShowProject.Models.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TheaterRepository extends JpaRepository<Theater,Integer> {
    @Query(value ="Select name from theater where city =:city" ,nativeQuery = true)
    public List<String> findtheaterbylocation(String city);

}
