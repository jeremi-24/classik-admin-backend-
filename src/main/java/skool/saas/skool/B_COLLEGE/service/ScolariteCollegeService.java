package skool.saas.skool.B_COLLEGE.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skool.saas.skool.B_COLLEGE.Entity.ScolariteCollege;
import skool.saas.skool.B_COLLEGE.enums.ClasseCOLLEGE;
import skool.saas.skool.B_COLLEGE.repository.ScolariteCollegeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ScolariteCollegeService {
    @Autowired
    private ScolariteCollegeRepository scolariteCollegeRepository;

    public ScolariteCollege saveScolarite(ScolariteCollege scolarite) {
        Optional<ScolariteCollege> existing = scolariteCollegeRepository.findByClasse(scolarite.getClasse());
        if (existing.isPresent()) {
            throw new IllegalStateException("Une scolarité existe déjà pour la classe : " + scolarite.getClasse());
        }
        return scolariteCollegeRepository.save(scolarite);
    }

    public ScolariteCollege updateScolarite(Long id, Long nouveauMontant) {
        ScolariteCollege scolarite = scolariteCollegeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Scolarité introuvable avec l'ID : " + id));
        scolarite.setMontant(nouveauMontant);
        return scolariteCollegeRepository.save(scolarite);
    }

    public List<ScolariteCollege> getAll() {
        return scolariteCollegeRepository.findAll();
    }

    public ScolariteCollege getByClasse(ClasseCOLLEGE classe) {
        return scolariteCollegeRepository.findByClasse(classe)
                .orElseThrow(() -> new EntityNotFoundException("Aucune scolarité trouvée pour la classe : " + classe));
    }
}
