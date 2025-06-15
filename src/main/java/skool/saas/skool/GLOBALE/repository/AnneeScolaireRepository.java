package skool.saas.skool.GLOBALE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skool.saas.skool.GLOBALE.Entity.AnneeScolaire;

import java.util.Optional;

public interface AnneeScolaireRepository extends JpaRepository<AnneeScolaire, Long> {
    Optional<AnneeScolaire> findByLibelle(String libelle);
    Optional<AnneeScolaire> findByActiveTrue();
}
