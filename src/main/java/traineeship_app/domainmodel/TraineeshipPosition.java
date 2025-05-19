package traineeship_app.domainmodel;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "traineeship_positions")
public class TraineeshipPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    @Column(name = "topics")
    private String topics;

    @Column(name = "skills")
    private String skills;

    @Column(name = "is_assigned")
    private boolean isAssigned;

    @Column(name = "student_logbook")
    private String studentLogbook;

    @Column(name = "pass_fail_grade")
    private Boolean passFailGrade = null;

    @OneToOne
    @JoinColumn(name = "student_username", referencedColumnName = "username")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "professor_username", referencedColumnName = "username")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "company_username", referencedColumnName = "username")
    private Company company;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "traineeship_position_id") // η ξένη κλείδα θα μπει στον πίνακα evaluations
    private List<Evaluation> evaluations = new ArrayList<>();


    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public boolean isAssigned() {
        return isAssigned;
    }

    public void setAssigned(boolean assigned) {
        isAssigned = assigned;
    }

    public String getStudentLogbook() {
        return studentLogbook;
    }

    public void setStudentLogbook(String studentLogbook) {
        this.studentLogbook = studentLogbook;
    }

    public Boolean getPassFailGrade() {
        return passFailGrade;
    }

    public void setPassFailGrade(boolean passFailGrade) {
        this.passFailGrade = passFailGrade;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    
    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

}