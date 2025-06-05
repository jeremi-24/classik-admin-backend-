package skool.saas.skool.A_PRIMAIRE.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import skool.saas.skool.A_PRIMAIRE.Dto.ClasseStatistiqueDto;
import skool.saas.skool.A_PRIMAIRE.Dto.EleveDto;
import skool.saas.skool.A_PRIMAIRE.Dto.PaiementDto;
import skool.saas.skool.A_PRIMAIRE.Dto.TuteurSimpleDto;
import skool.saas.skool.A_PRIMAIRE.Entity.Eleve;
import skool.saas.skool.A_PRIMAIRE.Entity.Professeur;
import skool.saas.skool.A_PRIMAIRE.Entity.Scolarite;
import skool.saas.skool.A_PRIMAIRE.enums.ClassePRIMAIRE;
import skool.saas.skool.A_PRIMAIRE.service.*;
import skool.saas.skool.GLOBALE.enums.Role;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Primaire")
@CrossOrigin(origins = "http://localhost:3000")

@Tag(name = "Primaire", description = "Gestion du system PRIMAIRE")

public class PrimaireController {

    @Autowired
    private EleveService eleveService;

    @Autowired
    private TuteurService tuteurService;

    @Autowired
    private ScolariteService scolariteService;

    @Autowired
    private ProfesseurService professeurService;

    @Autowired
    private PaiementService paiementService;

    @Operation(summary = "Ajout d'un eleve ")
    @PostMapping("/eleve")
    public ResponseEntity<Eleve> ajouterEleve(@RequestBody EleveDto dto) {
        Eleve eleveCree = eleveService.ajouterEleveEtTuteur(dto);
        return ResponseEntity.ok(eleveCree);
    }
    @Operation(summary = "modifier d'un eleve ")
    @PutMapping("/eleve/{id}")
    public ResponseEntity<Eleve> modifierEleve(
            @PathVariable Long id,
            @RequestBody EleveDto dto) {
        Eleve updated = eleveService.modifierEleve(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "delete un eleve ")
    @DeleteMapping("/eleve/{id}")
    public ResponseEntity<Void> supprimerEleve(@PathVariable Long id) {
        eleveService.supprimerEleve(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Récupérer tous les tuteurs ")
    @GetMapping("/tuteur")
    public List<TuteurSimpleDto> getAllTuteurs() {
        return tuteurService.getAllTuteurs();
    }


    @Operation(summary = "get by nom et prenom ")
    @GetMapping("/getby")
    public ResponseEntity<?> getEleveByNomAndPrenom(
            @RequestParam String nom,
            @RequestParam String prenom) {

        return eleveService.getEleveByNomAndPrenom(nom, prenom)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "get les stats ")
    @GetMapping("/stats")
    public List<ClasseStatistiqueDto> getStatistiquesPrimaire() {
        return eleveService.getStatistiquesParClassePrimaire();
    }

    @Operation(summary = "get les eleves par classe  ")
    @GetMapping("/{classe}")
    public List<Eleve> getElevesByClasse(@PathVariable("classe") String classeStr) {
        ClassePRIMAIRE classe;

        try {
            classe = ClassePRIMAIRE.valueOf(classeStr.toUpperCase()); // Ex: "cp1" => CP1
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Classe invalide : " + classeStr);
        }

        return eleveService.getElevesByClasse(classe);
    }


    @Operation(summary = "Récupérer tous les classes")
    @GetMapping("/classes")
    public List<String> getAllClassesPrimaire() {
        return Arrays.stream(ClassePRIMAIRE.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Operation(summary = "get un eleve par son id")
    @GetMapping("/eleve/{id}")
    public EleveDto getEleveById(@PathVariable Long id) {
        return eleveService.getEleveById(id);
    }


    // // // // // // // // // // // // // // // // // // // // // // //
    // // // // //// // //  Scolarité
    @Operation(summary = "ajout de scolarite")
    @PostMapping("/scolarite")
    public ResponseEntity<Scolarite> createScolarite(@RequestBody Scolarite scolarite) {
        Scolarite saved = scolariteService.saveScolarite(scolarite);
        return ResponseEntity.ok(saved);
    }

    @Operation(summary = "put scolarite par son id")
    @PutMapping("/scolarite/{id}")
    public ResponseEntity<Scolarite> updateScolarite(
            @PathVariable Long id,
            @RequestParam Long montant) {
        Scolarite updated = scolariteService.updateScolarite(id, montant);
        return ResponseEntity.ok(updated);
    }

    // // // // // // // // // // // // // // // // // // // // // // //
    // // // // //// // //  Professeur
    @Operation(summary = "ajout de prof")
    @PostMapping("/prof")
    public ResponseEntity<Professeur> createProf(@RequestBody Professeur prof) {
        Professeur saved = professeurService.saveProfesseur(prof);
        return ResponseEntity.ok(saved);
    }

    @Operation(summary = "put prof par son id")
    @PutMapping("/prof/{id}")
    public ResponseEntity<Professeur> updateProf(@PathVariable Long id, @RequestBody Professeur prof) {
        Professeur updated = professeurService.updateProfesseur(id, prof);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "delete prof par son id")
    @DeleteMapping("/prof/{id}")
    public ResponseEntity<Void> deleteProf(@PathVariable Long id) {
        professeurService.deleteProfesseur(id);
        return ResponseEntity.noContent().build();
    }


    // // // // // // // // // // // // // // // // // // // // // // //
    // // // // //// // //  Paiement
    @Operation(summary = "get les paiement")
    @GetMapping("/paiement/{classe}")
    public List<PaiementDto> getPaiementsParClasse(@PathVariable ClassePRIMAIRE classe) {
        return paiementService.getPaiementsParClasse(classe);
    }

    @Operation(summary = "post un paiement")
    @PostMapping("/paiement")
    public ResponseEntity<?> enregistrerPaiement(@RequestBody PaiementDto dto) {
        try {
            String message = paiementService.enregistrerPaiement(dto);
            return ResponseEntity.ok(Collections.singletonMap("message", message));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Erreur serveur"));
        }
    }

    @Operation(summary = "get les paiement by id")
    @GetMapping("/paiement/eleve/{eleveId}")
    public ResponseEntity<PaiementDto> getPaiementParEleve(@PathVariable Long eleveId) {
        PaiementDto dto = paiementService.getPaiementParEleveId(eleveId);
        return ResponseEntity.ok(dto);
    }

}
