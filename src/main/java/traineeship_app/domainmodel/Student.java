package traineeship_app.domainmodel;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "AM", unique = true)
    private String AM;

    @Column(name = "avg_grade")
    private double avgGrade;

    @Column(name = "preferred_location")
    private String preferredLocation;

    @Column(name = "interests")
    private String interests;

    @Column(name = "skills")
    private String skills;

    @Column(name = "looking_for_traineeship")
    private boolean lookingForTraineeship;

    @OneToOne(mappedBy = "student",cascade = CascadeType.ALL)
    private TraineeshipPosition assignedTraineeship;
    
    @OneToOne
    @MapsId
    @JoinColumn(name = "username")
    private User user;
    
    // === Getters and Setters ===

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getAM() {
        return AM;
    }

    public void setAM(String AM) {
        this.AM = AM;
    }

    public double getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(double avgGrade) {
        this.avgGrade = avgGrade;
    }

    public String getPreferredLocation() {
        return preferredLocation;
    }

    public void setPreferredLocation(String preferredLocation) {
        this.preferredLocation = preferredLocation;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public boolean getLookingForTraineeship() {
        return lookingForTraineeship;
    }

    public void setLookingForTraineeship(boolean lookingForTraineeship) {
        this.lookingForTraineeship = lookingForTraineeship;
    }

    public TraineeshipPosition getAssignedTraineeship() {
        return assignedTraineeship;
    }

    public void setAssignedTraineeship(TraineeshipPosition assignedTraineeship) {
        this.assignedTraineeship = assignedTraineeship;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
}