package skool.saas.skool.A_PRIMAIRE.Entity;

import jakarta.persistence.*;
import lombok.Data;
import skool.saas.skool.A_PRIMAIRE.enums.ClassePRIMAIRE;
import skool.saas.skool.A_PRIMAIRE.enums.StatutScolarite;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "PaiementPrimaire")

public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate datePaiement;
    private Double resteEcolage;
    private Double montantDejaPaye;
    private Double montantActuel;


    @ManyToOne
    @JoinColumn(name = "eleve_id")
    private Eleve eleve;

    @ManyToOne
    @JoinColumn(name = "scolarite_id")
    private Scolarite scolarite;


    @Enumerated(EnumType.STRING)
    private ClassePRIMAIRE classe;

    @Enumerated(EnumType.STRING)
    private StatutScolarite statut;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public Double getResteEcolage() {
        return resteEcolage;
    }

    public void setResteEcolage(Double resteEcolage) {
        this.resteEcolage = resteEcolage;
    }

    public Double getMontantDejaPaye() {
        return montantDejaPaye;
    }

    public void setMontantDejaPaye(Double montantDejaPaye) {
        this.montantDejaPaye = montantDejaPaye;
    }

    public Double getMontantActuel() {
        return montantActuel;
    }

    public void setMontantActuel(Double montantActuel) {
        this.montantActuel = montantActuel;
    }

    public Eleve getEleve() {
        return eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public Scolarite getScolarite() {
        return scolarite;
    }

    public void setScolarite(Scolarite scolarite) {
        this.scolarite = scolarite;
    }

    public ClassePRIMAIRE getClasse() {
        return classe;
    }

    public void setClasse(ClassePRIMAIRE classe) {
        this.classe = classe;
    }

    public StatutScolarite getStatut() {
        return statut;
    }

    public void setStatut(StatutScolarite statut) {
        this.statut = statut;
    }
}