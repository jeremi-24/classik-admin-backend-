package skool.saas.skool.A_PRIMAIRE.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import skool.saas.skool.A_PRIMAIRE.Dto.ClasseStatistiqueDto;
import skool.saas.skool.A_PRIMAIRE.Dto.EleveDto;
import skool.saas.skool.A_PRIMAIRE.Entity.Eleve;
import skool.saas.skool.A_PRIMAIRE.Entity.Tuteur;
import skool.saas.skool.A_PRIMAIRE.enums.ClassePRIMAIRE;
import skool.saas.skool.A_PRIMAIRE.repository.EleveRepository;
import skool.saas.skool.A_PRIMAIRE.repository.TuteurRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EleveService {


    @Autowired
    private TuteurRepository tuteurRepository;

    @Autowired
    private EleveRepository eleveRepository;


    @Transactional
    public Eleve ajouterEleveEtTuteur(EleveDto dto) {

        // 1. Vérifier si un tuteur avec ce nom et prénom existe déjà
        Optional<Tuteur> optionalTuteur = tuteurRepository
                .findByNomIgnoreCaseAndPrenomIgnoreCase(dto.getTuteurNom(), dto.getTuteurPrenom());

        Tuteur tuteur;

        if (optionalTuteur.isPresent()) {
            // Tuteur déjà existant
            tuteur = optionalTuteur.get();
        } else {
            // Nouveau tuteur
            tuteur = new Tuteur();
            tuteur.setNom(dto.getTuteurNom());
            tuteur.setPrenom(dto.getTuteurPrenom());
            tuteur.setTelephone(dto.getTuteurTelephone());
            tuteur.setProfession(dto.getTuteurProfession());
            tuteur = tuteurRepository.save(tuteur);
        }

        // Création de l’élève
        Eleve eleve = new Eleve();
        eleve.setNom(dto.getNom());
        eleve.setPrenom(dto.getPrenom());
        eleve.setAdresse(dto.getAdresse());
        eleve.setClasse(dto.getClasse());
        eleve.setSexe(dto.getSexe());
        eleve.setLieuNais(dto.getLieuNais());
        eleve.setEtblProv(dto.getEtblProv());
        eleve.setNationnalite(dto.getNationnalite());
        eleve.setDateNaiss(dto.getDateNaiss());
        eleve.setPhoto(dto.getPhoto());

        eleve.setTuteur(tuteur);

        return eleveRepository.save(eleve);
    }

    @Transactional
    public Eleve modifierEleve(Long id, EleveDto dto) {
        Eleve eleve = eleveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Élève non trouvé avec l’ID: " + id));

        // Mise à jour des informations de l'élève
        eleve.setNom(dto.getNom());
        eleve.setPrenom(dto.getPrenom());
        eleve.setAdresse(dto.getAdresse());
        eleve.setClasse(dto.getClasse());
        eleve.setSexe(dto.getSexe());
        eleve.setLieuNais(dto.getLieuNais());
        eleve.setEtblProv(dto.getEtblProv());
        eleve.setNationnalite(dto.getNationnalite());
        eleve.setDateNaiss(dto.getDateNaiss());
        eleve.setPhoto(dto.getPhoto());

        // Tuteur
        Optional<Tuteur> optionalTuteur = tuteurRepository
                .findByNomIgnoreCaseAndPrenomIgnoreCase(dto.getTuteurNom(), dto.getTuteurPrenom());

        Tuteur tuteur;
        if (optionalTuteur.isPresent()) {
            tuteur = optionalTuteur.get();
        } else {
            tuteur = new Tuteur();
            tuteur.setNom(dto.getTuteurNom());
            tuteur.setPrenom(dto.getTuteurPrenom());
            tuteur.setTelephone(dto.getTuteurTelephone());
            tuteur.setProfession(dto.getTuteurProfession());
            tuteur = tuteurRepository.save(tuteur);
        }

        eleve.setTuteur(tuteur);

        return eleveRepository.save(eleve);
    }

    public void supprimerEleve(Long id) {
        if (!eleveRepository.existsById(id)) {
            throw new EntityNotFoundException("Élève non trouvé avec l’ID: " + id);
        }
        eleveRepository.deleteById(id);
    }

    public List<Eleve> getElevesByClasse(ClassePRIMAIRE classe) {
        return eleveRepository.findByClasse(classe);
    }


    public Optional<Eleve> getEleveByNomAndPrenom(String nom, String prenom) {
        return eleveRepository.findByNomIgnoreCaseAndPrenomIgnoreCase(nom, prenom);
    }


    public List<ClasseStatistiqueDto> getStatistiquesParClassePrimaire() {
        List<ClasseStatistiqueDto> stats = new ArrayList<>();

        for (ClassePRIMAIRE classeEnum : ClassePRIMAIRE.values()) {
            String classe = classeEnum.name();
            long total = eleveRepository.countByClasse(ClassePRIMAIRE.valueOf(classe));
            long garcons = eleveRepository.countByClasseAndSexeIgnoreCase(ClassePRIMAIRE.valueOf(classe), "Masculin");
            long filles = eleveRepository.countByClasseAndSexeIgnoreCase(ClassePRIMAIRE.valueOf(classe), "Feminin");

            stats.add(new ClasseStatistiqueDto(classe, total, garcons, filles));
        }

        return stats;
    }



    public EleveDto getEleveById(Long id) {
        Eleve eleve = eleveRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Élève non trouvé avec l'id : " + id));

        // Mapper Eleve vers EleveDto
        EleveDto dto = new EleveDto();
        dto.setId(eleve.getId());
        dto.setNom(eleve.getNom());
        dto.setPrenom(eleve.getPrenom());
        dto.setAdresse(eleve.getAdresse());
        dto.setClasse(ClassePRIMAIRE.valueOf(String.valueOf(eleve.getClasse()))); // convertir String en enum
        dto.setSexe(eleve.getSexe());
        dto.setLieuNais(eleve.getLieuNais());
        dto.setEtblProv(eleve.getEtblProv());
        dto.setNationnalite(eleve.getNationnalite());
        dto.setDateNaiss(eleve.getDateNaiss());
        dto.setPhoto(eleve.getPhoto());

        if (eleve.getTuteur() != null) {
            dto.setTuteurNom(eleve.getTuteur().getNom());
            dto.setTuteurPrenom(eleve.getTuteur().getPrenom());
            dto.setTuteurTelephone(eleve.getTuteur().getTelephone());
            dto.setTuteurProfession(eleve.getTuteur().getProfession());
        }

        return dto;
    }


}
