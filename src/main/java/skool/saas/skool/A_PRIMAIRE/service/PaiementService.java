package skool.saas.skool.A_PRIMAIRE.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skool.saas.skool.A_PRIMAIRE.Dto.PaiementDto;
import skool.saas.skool.A_PRIMAIRE.Entity.Eleve;
import skool.saas.skool.A_PRIMAIRE.Entity.Paiement;
import skool.saas.skool.A_PRIMAIRE.Entity.Scolarite;
import skool.saas.skool.A_PRIMAIRE.enums.ClassePRIMAIRE;
import skool.saas.skool.A_PRIMAIRE.enums.StatutScolarite;
import skool.saas.skool.A_PRIMAIRE.repository.EleveRepository;
import skool.saas.skool.A_PRIMAIRE.repository.PaiementRepository;
import skool.saas.skool.A_PRIMAIRE.repository.ScolariteRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaiementService {

    @Autowired
    private PaiementRepository paiementRepository;

    @Autowired
    private ScolariteRepository scolariteRepository;

    @Autowired
    private EleveRepository eleveRepository;

    public List<PaiementDto> getPaiementsParClasse(ClassePRIMAIRE classe) {
        List<Eleve> eleves = eleveRepository.findByClasse(classe);
        Optional<Scolarite> scolariteOpt = scolariteRepository.findByClasse(classe);

        return eleves.stream().map(eleve -> {
            PaiementDto dto = new PaiementDto();
            dto.setEleveId(eleve.getId());
            dto.setEleveNom(eleve.getNom());
            dto.setElevePrenom(eleve.getPrenom());
            dto.setClasse(classe);

            Paiement dernierPaiement = paiementRepository
                    .findTopByEleveOrderByDatePaiementDesc(eleve)
                    .orElse(null);

            double montantScolarite = scolariteOpt.map(s -> s.getMontant().doubleValue()).orElse(0.0);
            dto.setMontantScolarite(montantScolarite);

            if (dernierPaiement != null) {
                dto.setDatePaiement(dernierPaiement.getDatePaiement());
                dto.setMontantActuel(dernierPaiement.getMontantActuel().longValue());
                dto.setMontantDejaPaye(dernierPaiement.getMontantDejaPaye().longValue());
                dto.setResteEcolage(dernierPaiement.getResteEcolage().longValue());

                // Déterminer le statut
                if (dernierPaiement.getResteEcolage() != null && dernierPaiement.getResteEcolage() == 0.0) {
                    dto.setStatut(StatutScolarite.SOLDÉ);
                } else {
                    dto.setStatut(StatutScolarite.EN_COURS);
                }
            } else {
                dto.setMontantActuel(0);
                dto.setMontantDejaPaye(0);
                dto.setResteEcolage((long) montantScolarite);
                dto.setStatut(StatutScolarite.EN_COURS);
            }

            return dto;
        }).collect(Collectors.toList());
    }


    public String enregistrerPaiement(PaiementDto dto) {
        Eleve eleve = eleveRepository.findById(dto.getEleveId())
                .orElseThrow(() -> new RuntimeException("Élève introuvable"));

        Scolarite scolarite = scolariteRepository.findByClasse(eleve.getClasse())
                .orElseThrow(() -> new RuntimeException("Montant scolarité introuvable pour cette classe"));

        double montantScolarite = scolarite.getMontant().doubleValue();
        Double dejaPaye = paiementRepository.sumMontantActuelByEleve(eleve);
        if (dejaPaye == null) dejaPaye = 0.0;

        double nouveauMontant = dto.getMontantActuel();
        double total = dejaPaye + nouveauMontant;

        if (total > montantScolarite) {
            throw new IllegalArgumentException("Le montant payé dépasse le reste à payer");
        }

        Paiement paiement = new Paiement();
        paiement.setEleve(eleve);
        paiement.setDatePaiement(LocalDate.now());
        paiement.setMontantActuel((double) nouveauMontant);
        paiement.setMontantDejaPaye((double) (dejaPaye + nouveauMontant));
        paiement.setResteEcolage((double) (montantScolarite - total));
        paiement.setStatut((total == montantScolarite) ? StatutScolarite.SOLDÉ : StatutScolarite.EN_COURS);

        paiement.setScolarite(scolarite);
        paiementRepository.save(paiement);

        return "Paiement enregistré avec succès";
    }


    public PaiementDto getPaiementParEleveId(Long eleveId) {
        Eleve eleve = eleveRepository.findById(eleveId)
                .orElseThrow(() -> new RuntimeException("Élève non trouvé avec l'ID : " + eleveId));

        ClassePRIMAIRE classe = eleve.getClasse();
        Optional<Scolarite> scolariteOpt = scolariteRepository.findByClasse(classe);

        PaiementDto dto = new PaiementDto();
        dto.setEleveId(eleve.getId());
        dto.setEleveNom(eleve.getNom());
        dto.setElevePrenom(eleve.getPrenom());
        dto.setClasse(classe);

        Paiement dernierPaiement = paiementRepository
                .findTopByEleveOrderByDatePaiementDesc(eleve)
                .orElse(null);

        double montantScolarite = scolariteOpt.map(s -> s.getMontant().doubleValue()).orElse(0.0);
        dto.setMontantScolarite(montantScolarite);

        if (dernierPaiement != null) {
            dto.setDatePaiement(dernierPaiement.getDatePaiement());
            dto.setMontantActuel(dernierPaiement.getMontantActuel().longValue());
            dto.setMontantDejaPaye(dernierPaiement.getMontantDejaPaye().longValue());
            dto.setResteEcolage(dernierPaiement.getResteEcolage().longValue());

            if (dernierPaiement.getResteEcolage() != null && dernierPaiement.getResteEcolage() == 0.0) {
                dto.setStatut(StatutScolarite.SOLDÉ);
            } else {
                dto.setStatut(StatutScolarite.EN_COURS);
            }
        } else {
            dto.setMontantActuel(0);
            dto.setMontantDejaPaye(0);
            dto.setResteEcolage((long) montantScolarite);
            dto.setStatut(StatutScolarite.EN_COURS);
        }

        return dto;
    }

}


