package skool.saas.skool.B_COLLEGE.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skool.saas.skool.B_COLLEGE.Dto.CollegeStatistiqueDto;
import skool.saas.skool.B_COLLEGE.Dto.EleveCollegeDto;
import skool.saas.skool.B_COLLEGE.Entity.EleveCollege;
import skool.saas.skool.B_COLLEGE.Entity.TuteurCollege;
import skool.saas.skool.B_COLLEGE.enums.ClasseCOLLEGE;
import skool.saas.skool.B_COLLEGE.repository.EleveCollegeRepository;
import skool.saas.skool.B_COLLEGE.repository.TuteurCollegeRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EleveCollegeService {


    @Autowired
    private TuteurCollegeRepository tuteurCollegeRepository;

    @Autowired
    private EleveCollegeRepository eleveCollegeRepository;


    @Transactional
    public EleveCollege ajouterEleveEtTuteur(EleveCollegeDto dto) {

        // 1. Vérifier si un tuteur avec ce nom et prénom existe déjà
        Optional<TuteurCollege> optionalTuteur = tuteurCollegeRepository
                .findByNomIgnoreCaseAndPrenomIgnoreCase(dto.getTuteurNom(), dto.getTuteurPrenom());

        TuteurCollege tuteur;

        if (optionalTuteur.isPresent()) {
            // Tuteur déjà existant
            tuteur = optionalTuteur.get();
        } else {
            // Nouveau tuteur
            tuteur = new TuteurCollege();
            tuteur.setNom(dto.getTuteurNom());
            tuteur.setPrenom(dto.getTuteurPrenom());
            tuteur.setTelephone(dto.getTuteurTelephone());
            tuteur.setProfession(dto.getTuteurProfession());
            tuteur = tuteurCollegeRepository.save(tuteur);
        }

        // Création de l’élève
        EleveCollege eleve = new EleveCollege();
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

        eleve.setTuteurCollege(tuteur);

        return eleveCollegeRepository.save(eleve);
    }

    public List<EleveCollege> getElevesByClasse(ClasseCOLLEGE classe) {
        return eleveCollegeRepository.findByClasse(classe);
    }


    public Optional<EleveCollege> getEleveByNomAndPrenom(String nom, String prenom) {
        return eleveCollegeRepository.findByNomIgnoreCaseAndPrenomIgnoreCase(nom, prenom);
    }


    public List<CollegeStatistiqueDto> getStatistiquesParClasseCollege() {
        List<CollegeStatistiqueDto> stats = new ArrayList<>();

        for (ClasseCOLLEGE classeEnum : ClasseCOLLEGE.values()) {
            String classe = classeEnum.name();
            long total = eleveCollegeRepository.countByClasse(ClasseCOLLEGE.valueOf(classe));
            long garcons = eleveCollegeRepository.countByClasseAndSexeIgnoreCase(ClasseCOLLEGE.valueOf(classe), "Masculin");
            long filles = eleveCollegeRepository.countByClasseAndSexeIgnoreCase(ClasseCOLLEGE.valueOf(classe), "Feminin");

            stats.add(new CollegeStatistiqueDto(classe, total, garcons, filles));
        }

        return stats;
    }

    public List<EleveCollege> getElevesByClasseCollege(ClasseCOLLEGE classe) {
        return eleveCollegeRepository.findByClasse(ClasseCOLLEGE.valueOf(classe.name()));
    }


}
