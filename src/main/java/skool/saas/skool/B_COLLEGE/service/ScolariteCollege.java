package skool.saas.skool.B_COLLEGE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skool.saas.skool.A_PRIMAIRE.Entity.Scolarite;
import skool.saas.skool.A_PRIMAIRE.repository.ScolariteRepository;
import skool.saas.skool.B_COLLEGE.repository.ScolariteCollegeRepository;

@Service
public class ScolariteCollege {
    @Autowired
    private ScolariteCollegeRepository scolariteCollegeRepository;

//    // ✅ Save
//    public ScolariteCollege save(ScolariteCollege scolariteCollege) {
//        return scolariteCollegeRepository.save(scolariteCollege);
//    }
//
//    // ✅ Update
//    public ScolariteCollege update(Long id, ScolariteCollege updatedScolarite) {
//        Optional<ScolariteCollege> optional = scolariteCollegeRepository.findById(id);
//        if (optional.isPresent()) {
//            ScolariteCollege existing = optional.get();
//            existing.setMontant(updatedScolarite.getMontant());
//            existing.setClasse(updatedScolarite.getClasse());
//            return scolariteCollegeRepository.save(existing);
//        } else {
//            throw new RuntimeException("Scolarité non trouvée avec l'id : " + id);
//        }
//    }
}
