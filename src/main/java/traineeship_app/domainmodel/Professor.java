package traineeship_app.domainmodel;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "professors")
public class Professor {

	@Id
	@Column(name = "username", unique = true, nullable = false)
    private String username;
    
	@Column(name = "professor_name")
    private String professorName;
    
	@Column(name = "interests")
    private String interests;
	
	@OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private List<TraineeshipPosition> supervisedPositions;
	
    @OneToOne
    @MapsId
    @JoinColumn(name = "username")
    private User user;
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public List<TraineeshipPosition> getSupervisedPositions() {
        return supervisedPositions;
    }

    public void setSupervisedPositions(List<TraineeshipPosition> supervisedPositions) {
        this.supervisedPositions = supervisedPositions;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
}