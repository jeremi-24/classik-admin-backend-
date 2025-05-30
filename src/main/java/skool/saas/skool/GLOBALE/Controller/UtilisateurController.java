package skool.saas.skool.GLOBALE.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skool.saas.skool.GLOBALE.Entity.Utilisateur;
import skool.saas.skool.GLOBALE.config.JwtUtil;
import skool.saas.skool.GLOBALE.enums.Role;
import skool.saas.skool.GLOBALE.service.ConfigurationService;
import skool.saas.skool.GLOBALE.service.UtilisateurService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")

@Tag(name = "Utilisateur", description = "Gestion des utilisateurs")

public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private JwtUtil jwtUtil;

    // Obtenir tous les rôles
    @Operation(summary = "Récupérer tous les roles")
    @GetMapping("/roles")
    public ResponseEntity<Role[]> getAllRoles() {
        return ResponseEntity.ok(Role.values());
    }


    // Obtenir tous les utilisateurs
    @Operation(summary = "Récupérer tous les utilisateurs")
    @GetMapping("/utilisateur")
    public ResponseEntity<List<Utilisateur>> getAllUtilisateur() {
        List<Utilisateur> utilisateurs = utilisateurService.getAll();
        return ResponseEntity.ok(utilisateurs);
    }

    // Authentification utilisateur
    @Operation(summary = "Connexion ")
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Utilisateur utilisateur) {
        Map<String, Object> response = new HashMap<>();

        if (!configurationService.licenceEstValide()) {
            response.put("success", false);
            response.put("message", "Licence expirée. Veuillez contacter l’administration.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        boolean isAuthenticated = utilisateurService.authenticate(utilisateur.getEmail(), utilisateur.getPassword(), utilisateur.getRole());

        if (isAuthenticated) {
            String token = jwtUtil.generateToken(utilisateur.getEmail(), utilisateur.getRole().name());
            response.put("success", true);
            response.put("token", token);
            response.put("message", "Login réussi");
            response.put("role", utilisateur.getRole());
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Identifiants ou rôle incorrects");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @Operation(summary = "inscription d'un compte")
    @PostMapping("/save")
    public ResponseEntity<Utilisateur> saveUtilisateur(@RequestBody Utilisateur utilisateur) {
        Utilisateur savedUser = utilisateurService.saveUtilisateur(utilisateur);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @Operation(summary = "modifier un compte")
    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> updateUtilisateur(
            @PathVariable Long id,
            @RequestBody Utilisateur updatedData) {
        Utilisateur utilisateur = utilisateurService.updateUtilisateur(id, updatedData);
        return ResponseEntity.ok(utilisateur);
    }

    @Operation(summary = "delete un compte")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        utilisateurService.deleteUtilisateur(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Récupérer le role qui est connecté")
    @GetMapping("/role/{email}")
    public ResponseEntity<String> getRoleByEmail(@PathVariable String email) {
        String role = utilisateurService.getRoleByEmail(email);
        if (role != null) {
            return ResponseEntity.ok(role);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rôle introuvable");
        }
    }


}
