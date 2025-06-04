package skool.saas.skool.B_COLLEGE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skool.saas.skool.B_COLLEGE.Entity.EleveCollege;
import skool.saas.skool.B_COLLEGE.enums.ClasseCOLLEGE;

import java.util.List;
import java.util.Optional;

public interface EleveCollegeRepository extends JpaRepository<EleveCollege, Long> {
    List<EleveCollege> findByClasse(ClasseCOLLEGE classe);
    Optional<EleveCollege> findByNomIgnoreCaseAndPrenomIgnoreCase(String nom, String prenom);
    long countByClasseAndSexeIgnoreCase(ClasseCOLLEGE classe, String sexe);
    long countByClasse(ClasseCOLLEGE classe);

}
