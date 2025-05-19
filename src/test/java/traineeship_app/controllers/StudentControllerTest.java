package traineeship_app.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.security.Principal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import traineeship_app.domainmodel.*;
import traineeship_app.services.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private WebApplicationContext context;  // Inject WebApplicationContext

    @Autowired
    private MockMvc mockMvc;  // Inject MockMvc

    @Autowired
    private StudentService studentService;  // Inject StudentService

    @Autowired
    @Qualifier("UserServicelmpl")
    private UserService userService;  // Inject UserService

    private Principal mockPrincipal;  // Mock Principal

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
          .webAppContextSetup(context)  // Set up MockMvc with WebApplicationContext
          .build();

        // Initialize Principal mock
        mockPrincipal = mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("student1");

        // Mock studentService and userService as needed
        Student student = new Student();
        student.setUsername("student1");
        when(studentService.retrieveProfile("student1")).thenReturn(student);
    }

    @Test
    void testGetStudentDashboard() throws Exception {
        mockMvc.perform(get("/student/dashboard"))
               .andExpect(status().isOk())
               .andExpect(view().name("student/dashboard"));
    }

    @Test
    void testRetrieveProfile() throws Exception {
        mockMvc.perform(get("/student/studentprofile").principal(mockPrincipal))
               .andExpect(status().isOk())
               .andExpect(model().attributeExists("student"))
               .andExpect(view().name("student/studentprofile"));
    }

    @Test
    void testFillLogbook() throws Exception {
        Student student = new Student();
        TraineeshipPosition position = new TraineeshipPosition();
        student.setAssignedTraineeship(position);

        when(studentService.retrieveProfile("student1")).thenReturn(student);

        mockMvc.perform(get("/student/logbook").principal(mockPrincipal))
               .andExpect(status().isOk())
               .andExpect(model().attributeExists("position"))
               .andExpect(view().name("student/logbook"));
    }

    @Test
    void testStudentDetailsForm() throws Exception {
        mockMvc.perform(get("/student/studentdetails").principal(mockPrincipal))
               .andExpect(status().isOk())
               .andExpect(model().attributeExists("student"))
               .andExpect(view().name("student/studentdetails"));
    }

    @Test
    void testSaveStudentDetails() throws Exception {
        // Prepare student details form
        Student student = new Student();
        student.setUsername("student1");

        MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
        formParams.add("username", student.getUsername());
        formParams.add("firstName", "Student");
        formParams.add("lastName", "One");
        formParams.add("email", "student1@mail.com");

        mockMvc.perform(post("/student/details/save")
            .params(formParams).principal(mockPrincipal))
            .andExpect(status().is3xxRedirection()) // Expect redirect after save
            .andExpect(view().name("redirect:/student/dashboard"));
       
    }
}



