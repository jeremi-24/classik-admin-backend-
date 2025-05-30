package skool.saas.skool.A_PRIMAIRE.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skool.saas.skool.A_PRIMAIRE.Entity.Scolarite;
import skool.saas.skool.A_PRIMAIRE.repository.ScolariteRepository;

@Service
public class ScolariteService {
    @Autowired
    private ScolariteRepository scolariteRepository;

    public Scolarite saveScolarite(Scolarite scolarite) {
        // Optionnel : vérifier si la classe est déjà configurée
        if (scolariteRepository.findByClasse(scolarite.getClasse()).isPresent()) {
            throw new IllegalStateException("Scolarité déjà définie pour cette classe");
        }
        return scolariteRepository.save(scolarite);
    }

    public Scolarite updateScolarite(Long id, Long nouveauMontant) {
        Scolarite scolarite = scolariteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Scolarité introuvable"));

        scolarite.setMontant(nouveauMontant);
        return scolariteRepository.save(scolarite);
    }

}
