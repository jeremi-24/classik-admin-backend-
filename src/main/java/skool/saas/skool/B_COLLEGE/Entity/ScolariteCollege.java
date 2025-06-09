package skool.saas.skool.B_COLLEGE.Entity;

import jakarta.persistence.*;
import skool.saas.skool.B_COLLEGE.enums.ClasseCOLLEGE;

@Entity
@Table(name = "scolarite_college") // nom de table plus clair
public class ScolariteCollege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long montant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private ClasseCOLLEGE classe;

    // --- Getters & Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMontant() {
        return montant;
    }

    public void setMontant(Long montant) {
        this.montant = montant;
    }

    public ClasseCOLLEGE getClasse() {
        return classe;
    }

    public void setClasse(ClasseCOLLEGE classe) {
        this.classe = classe;
    }
}
