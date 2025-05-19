package traineeship_app.domainmodel;

import jakarta.persistence.*;

@Entity
@Table(name = "evaluations")
public class Evaluation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Enumerated(EnumType.STRING)
    private EvaluationType evaluationType;  
    
    @Column(name = "motivation")
    private int motivation;
    
    @Column(name = "efficiency")
    private int efficiency;
    
    @Column(name = "effectiveness")
    private int effectiveness;
    
    @Column(name = "facilitiesAndGuidance") //FOR PROFESSOR ONLY
    private int facilitiesAndGuidance;
   
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EvaluationType getEvaluationType() {
        return evaluationType;
    }

    public void setEvaluationType(EvaluationType evaluationType) {
        this.evaluationType = evaluationType;
    }

    public int getMotivation() {
        return motivation;
    }

    public void setfacilitiesAndGuidance(int facilitiesAndGuidance) {
        this.facilitiesAndGuidance = facilitiesAndGuidance;
    }
    
    public int getFacilitiesAndGuidance() {
        return facilitiesAndGuidance;
    }

    public void setMotivation(int motivation) {
        this.motivation = motivation;
    }

    public int getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(int efficiency) {
        this.efficiency = efficiency;
    }
    
    public int setEffectiveness(int effectiveness) {
        return this.effectiveness = effectiveness;
    }

    public int getEffectiveness() {
        return effectiveness;
    }
}