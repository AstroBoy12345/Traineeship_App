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
@Table(name = "companies") // Χρησιμοποιούμε πληθυντικό για συνέπεια με άλλους πίνακες
public class Company {

    @Id
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_location")
    private String companyLocation;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<TraineeshipPosition> positions;
    
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }

    public List<TraineeshipPosition> getPositions() {
        return positions;
    }

    public void setPositions(List<TraineeshipPosition> positions) {
        this.positions = positions;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
}