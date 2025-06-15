package skool.saas.skool.GLOBALE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skool.saas.skool.GLOBALE.Entity.AnneeScolaire;
import skool.saas.skool.GLOBALE.repository.AnneeScolaireRepository;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class AnneeScolaireStateService {

    private final AtomicReference<AnneeScolaire> anneeActuelle = new AtomicReference<>();

    @Autowired
    private AnneeScolaireRepository anneeScolaireRepository;

    public void setAnneeActive(Long id) {
        AnneeScolaire annee = anneeScolaireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Année non trouvée"));
        anneeActuelle.set(annee);
    }

    public AnneeScolaire getAnneeActive() {
        return anneeActuelle.get();
    }
}
