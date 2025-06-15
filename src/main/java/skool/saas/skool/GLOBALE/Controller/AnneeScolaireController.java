package skool.saas.skool.GLOBALE.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skool.saas.skool.GLOBALE.Entity.AnneeScolaire;
import skool.saas.skool.GLOBALE.service.AnneeScolaireService;

import java.util.List;
@Tag(name = "Annee", description = "Gestion des annees")

@RestController
@RequestMapping("/annee")
public class AnneeScolaireController {

    @Autowired
    private AnneeScolaireService anneeScolaireService;


    @GetMapping("/active")
    @Operation(summary = "Récupérer l'année scolaire active")
    public ResponseEntity<AnneeScolaire> getAnneeActive() {
        AnneeScolaire active = anneeScolaireService.getAnneeActive();
        return active != null ? ResponseEntity.ok(active) : ResponseEntity.noContent().build();
    }

    @Operation(summary = "generer années scolaires")
    @GetMapping("/generer")
    public ResponseEntity<String> genererAutomatique() {
        anneeScolaireService.initialiserOuActiverAnneeScolaire();
        return ResponseEntity.ok("Année scolaire actuelle générée automatiquement.");
    }

    @GetMapping("/liste")
    @Operation(summary = "Liste des années scolaires disponibles")
    public ResponseEntity<List<AnneeScolaire>> getToutesLesAnnees() {
        List<AnneeScolaire> annees = anneeScolaireService.getAll();
        return ResponseEntity.ok(annees);
    }

}

