package skool.saas.skool.B_COLLEGE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skool.saas.skool.A_PRIMAIRE.Entity.Scolarite;
import skool.saas.skool.B_COLLEGE.Entity.ScolariteCollege;
import skool.saas.skool.B_COLLEGE.enums.ClasseCOLLEGE;

import java.util.Optional;

public interface ScolariteCollegeRepository extends JpaRepository<ScolariteCollege, Long> {
    Optional<ScolariteCollege> findByClasse(ClasseCOLLEGE classeCOLLEGE);
}
