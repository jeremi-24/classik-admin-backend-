package skool.saas.skool.B_COLLEGE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skool.saas.skool.B_COLLEGE.Entity.TuteurCollege;

import java.util.Optional;

public interface TuteurCollegeRepository extends JpaRepository<TuteurCollege, Long> {

    Optional<TuteurCollege> findByNomIgnoreCaseAndPrenomIgnoreCase(String nom, String prenom);


}
