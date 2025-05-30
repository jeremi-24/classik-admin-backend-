package skool.saas.skool.GLOBALE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skool.saas.skool.GLOBALE.Entity.Utilisateur;
import skool.saas.skool.GLOBALE.enums.Role;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Utilisateur findByEmail(String email);
    Utilisateur findByEmailAndPasswordAndRole(String email, String password, Role role);
}

