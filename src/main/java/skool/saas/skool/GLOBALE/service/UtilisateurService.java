package skool.saas.skool.GLOBALE.service;



import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import skool.saas.skool.GLOBALE.Entity.Utilisateur;
import skool.saas.skool.GLOBALE.enums.Role;
import skool.saas.skool.GLOBALE.repository.UtilisateurRepository;

import java.util.List;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private ConfigurationService configurationService;

    public String getRoleByEmail(String email) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email);
        if (utilisateur != null && utilisateur.getRole() != null) {
            return utilisateur.getRole().name();
        }
        return null;
    }


//    public boolean authenticate(String email, String password, Role role) {
//        // Vérifier si la licence est encore valide
//        if (!configurationService.licenceEstValide()) {
//            return false; // On bloque la connexion
//        }
//
//        // Si la licence est valide, on continue normalement
//        Utilisateur utilisateur = utilisateurRepository.findByEmailAndPasswordAndRole(email, password, role);
//        return utilisateur != null;
//    }
//
//
//    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
//        return utilisateurRepository.save(utilisateur);
//    }


    public Utilisateur findByEmailAndPassword(String email, String password) {
        return utilisateurRepository.findByEmailAndPassword(email, password);
    }

    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }


    @Transactional
    public Utilisateur updateUtilisateur(Long id, Utilisateur updatedData) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l’ID: " + id));

        utilisateur.setEmail(updatedData.getEmail());
        utilisateur.setPassword(updatedData.getPassword());
        utilisateur.setRole(updatedData.getRole());

        return utilisateurRepository.save(utilisateur);
    }

    public void deleteUtilisateur(Long id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new EntityNotFoundException("Utilisateur non trouvé avec l’ID: " + id);
        }
        utilisateurRepository.deleteById(id);
    }


    public List<Utilisateur> getAll() {
        return utilisateurRepository.findAll();
    }


    public Utilisateur getUtilisateurConnecte() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return null;
        }

        String email = authentication.getName(); 

        return utilisateurRepository.findByEmail(email);
    }
}
