package skool.saas.skool.B_COLLEGE.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import skool.saas.skool.A_PRIMAIRE.Entity.Scolarite;
import skool.saas.skool.B_COLLEGE.Dto.CollegeStatistiqueDto;
import skool.saas.skool.B_COLLEGE.Dto.EleveCollegeDto;
import skool.saas.skool.B_COLLEGE.Dto.TuteurCollegeSimpleDto;
import skool.saas.skool.B_COLLEGE.Entity.EleveCollege;
import skool.saas.skool.B_COLLEGE.Entity.ScolariteCollege;
import skool.saas.skool.B_COLLEGE.Entity.TuteurCollege;
import skool.saas.skool.B_COLLEGE.enums.ClasseCOLLEGE;
import skool.saas.skool.B_COLLEGE.service.EleveCollegeService;
import skool.saas.skool.B_COLLEGE.service.ScolariteCollegeService;
import skool.saas.skool.B_COLLEGE.service.TuteurCollegeService;


import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/College")

@Tag(name = "College", description = "Gestion du system COLLEGE")

public class CollegeController {

    @Autowired
    private EleveCollegeService eleveCollegeService;

    @Autowired
    private TuteurCollegeService tuteurCollegeService;

    @Autowired
    private ScolariteCollegeService scolariteCollegeService;

    @Operation(summary = "Ajout d'un eleve ")
    @PostMapping("/eleve")
    public ResponseEntity<EleveCollege> ajouterEleve(@RequestBody EleveCollegeDto dto) {
       EleveCollege eleveCree = eleveCollegeService.ajouterEleveEtTuteur(dto);
        return ResponseEntity.ok(eleveCree);
    }

    @Operation(summary = "modifier d'un eleve ")
    @PutMapping("/eleve/{id}")
    public ResponseEntity<EleveCollege> modifierEleveParId(
            @PathVariable("id") Long id,
            @RequestBody EleveCollegeDto dto) {

        EleveCollege eleveMisAJour = eleveCollegeService.modifierEleveEtTuteur(id, dto);
        return ResponseEntity.ok(eleveMisAJour);
    }

    @Operation(summary = "delete un eleve ")
    @DeleteMapping("/eleve/{id}")
    public ResponseEntity<Void> supprimerEleveParId(@PathVariable("id") Long id) {
        eleveCollegeService.supprimerEleve(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Récupérer tous les tuteurs ")
    @GetMapping("/tuteur")
    public List<TuteurCollegeSimpleDto> getAllTuteurs() {
        return tuteurCollegeService.getAllTuteurs();
    }


    @Operation(summary = "get by nom et prenom ")
    @GetMapping("/getby")
    public ResponseEntity<?> getEleveByNomAndPrenom(
            @RequestParam String nom,
            @RequestParam String prenom) {

        return eleveCollegeService.getEleveByNomAndPrenom(nom, prenom)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "get les stats ")
    @GetMapping("/stats")
    public List<CollegeStatistiqueDto> getStatistiquesCollege() {
        return eleveCollegeService.getStatistiquesParClasseCollege();
    }

    @Operation(summary = "get les eleves par classe  ")
    @GetMapping("/{classe}")
    public List<EleveCollege> getElevesByClasseCollege(@PathVariable("classe") String classeStr) {
        ClasseCOLLEGE classe;

        try {
            classe = ClasseCOLLEGE.valueOf(classeStr.toUpperCase()); // Ex: "cp1" => CP1
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Classe invalide : " + classeStr);
        }

        return eleveCollegeService.getElevesByClasseCollege(classe);
    }


    @Operation(summary = "get un eleve par son id")
    @GetMapping("/eleve/{id}")
    public ResponseEntity<EleveCollegeDto> getEleveParId(@PathVariable Long id) {
        EleveCollegeDto dto = eleveCollegeService.getEleveDtoById(id);
        return ResponseEntity.ok(dto);
    }


    @Operation(summary = "Récupérer tous les classes")
    @GetMapping("/classes")
    public List<ClasseCOLLEGE> getAllClassesCollege() {
        return Arrays.asList(ClasseCOLLEGE.values());
    }


    // // // // // // // // // // // // // // // // // // // // // // //
    // // // // //// // //  Scolarité
    @Operation(summary = "ajout de scolarite")
    @PostMapping("/scolarite")
    public ResponseEntity<ScolariteCollege> createScolarite(@RequestBody ScolariteCollege scolarite) {
        ScolariteCollege saved = scolariteCollegeService.saveScolarite(scolarite);
        return ResponseEntity.ok(saved);
    }

    @Operation(summary = "put scolarite par son id")
    @PutMapping("/scolarite/{id}")
    public ResponseEntity<ScolariteCollege> updateScolarite(@PathVariable Long id, @RequestBody Long montant) {
        ScolariteCollege updated = scolariteCollegeService.updateScolarite(id, montant);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "get les scolarites")
    @GetMapping("/scolarite")
    public ResponseEntity<List<ScolariteCollege>> getAllScolarites() {
        return ResponseEntity.ok(scolariteCollegeService.getAll());
    }

    @Operation(summary = "get scolarite by classe")
    @GetMapping("/scolarite/{classe}")
    public ResponseEntity<ScolariteCollege> getByClasse(@PathVariable ClasseCOLLEGE classe) {
        return ResponseEntity.ok(scolariteCollegeService.getByClasse(classe));
    }

    
}
