package skool.saas.skool.A_PRIMAIRE.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import skool.saas.skool.A_PRIMAIRE.Entity.Eleve;
import skool.saas.skool.A_PRIMAIRE.Entity.Paiement;
import skool.saas.skool.A_PRIMAIRE.enums.ClassePRIMAIRE;

import java.util.List;
import java.util.Optional;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    Optional<Paiement> findTopByEleveOrderByDatePaiementDesc(Eleve eleve);
    @Query("SELECT SUM(p.montantActuel) FROM Paiement p WHERE p.eleve = :eleve")
    Double sumMontantActuelByEleve(@Param("eleve") Eleve eleve);

}
