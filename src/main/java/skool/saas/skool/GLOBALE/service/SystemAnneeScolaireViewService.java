package skool.saas.skool.GLOBALE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skool.saas.skool.GLOBALE.Entity.AnneeScolaire;
import skool.saas.skool.GLOBALE.Dto.SystemAnneeResponse;

@Service
public class SystemAnneeScolaireViewService {

    @Autowired
    private AnneeScolaireStateService anneeScolaireStateService;

    @Autowired
    private SystemStateService systemStateService;

    public SystemAnneeResponse getSystemeEtAnnee() {
        AnneeScolaire annee = anneeScolaireStateService.getAnneeActive();
        System systeme = systemStateService.getSystem();

        String anneeLibelle = (annee != null) ? annee.getLibelle() : null;

        return new SystemAnneeResponse(systeme, anneeLibelle);
    }
}
