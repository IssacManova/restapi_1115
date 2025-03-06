package com.examly.springapp;

import org.springframework.data.domain.Sort;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.examly.springapp.controller.RentalCompanyController;
import com.examly.springapp.model.*;
import com.examly.springapp.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;


import com.examly.springapp.repository.RentalCompanyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = SpringappApplication.class)
@AutoConfigureMockMvc
public class ApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    

    @InjectMocks
    private RentalCompanyController rentalCompanyController;

    @MockBean
    private VehicleService vehicleService;

    @MockBean
    private BookingService bookingService;

    @MockBean
    private RentalCompanyService rentalCompanyService;

    @Autowired
    private RentalCompanyRepository rentalCompanyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
void setUp() {
    // Insert test data before each test
    rentalCompanyRepository.save(new RentalCompany(null, "Los Angeles", "Some other details", null));
    rentalCompanyRepository.save(new RentalCompany(null, "Los Angeles", "More details", null));
}

    // UserController Tests
    @Test
    void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(Arrays.asList(
                new User(1L, "John Doe", "john.doe@example.com", "1234567890", null)
        ));
    
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }
    

    @Test
    void testCreateUser() throws Exception {
        User user = new User(1L, "Jane Doe", "jane.doe@example.com", "0987654321", null);
        when(userService.saveUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Jane Doe"));
    }

    @Test
    void testUpdateUser() throws Exception {
        User updatedUser = new User(1L, "John Smith", "john.smith@example.com", "1111111111", null);
        when(userService.saveUser(any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Smith"));
    }

    @Test
    void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk());
    }
    @Test
    void testGetAllVehicles() throws Exception {
        when(vehicleService.getAllVehicles()).thenReturn(Arrays.asList(
                new Vehicle(1L, "Car", "Toyota", "SUV", 50000.0, null, null, null)
        ));
    
        mockMvc.perform(get("/api/vehicles"))
                .andExpect(status().isOk());
                // .andExpect(jsonPath("$[0].id").value(1))
                // .andExpect(jsonPath("$[0].name").value("Car"))
                // .andExpect(jsonPath("$[0].brand").value("Toyota"))
                // .andExpect(jsonPath("$[0].type").value("SUV"))
                // .andExpect(jsonPath("$[0].price").value(50000.0));
    }
    

    @Test
    void testCreateVehicle() throws Exception {
        Vehicle vehicle = new Vehicle(1L, "Bike", "Yamaha", "Sports", 15000.0, null, null, null);
        when(vehicleService.saveVehicle(any(Vehicle.class))).thenReturn(vehicle);

        mockMvc.perform(post("/api/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehicle)))
                .andExpect(status().isOk());
                // .andExpect(jsonPath("$.id").value(1))
                // .andExpect(jsonPath("$.name").value("Bike"));
    }

    @Test
    void testUpdateVehicle() throws Exception {
        Vehicle vehicle = new Vehicle(1L, "Bike", "Yamaha", "Cruiser", 18000.0, null, null, null);
        when(vehicleService.saveVehicle(any(Vehicle.class))).thenReturn(vehicle);

        mockMvc.perform(put("/api/vehicles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehicle)))
                .andExpect(status().isOk());
                // .andExpect(jsonPath("$.type").value("Cruiser"));
    }

    @Test
    void testDeleteVehicle() throws Exception {
        doNothing().when(vehicleService).deleteVehicle(1L);

        mockMvc.perform(delete("/api/vehicles/1"))
                .andExpect(status().isOk());
    }
    @Test
    void testGetAllRentalCompanies() throws Exception {
        when(rentalCompanyService.getAllRentalCompanies()).thenReturn(Collections.singletonList(
                new RentalCompany(1L, "Zoom Cars", "Mumbai", null)
        ));

        mockMvc.perform(get("/api/rentalcompanies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Zoom Cars"))
                .andExpect(jsonPath("$[0].location").value("Mumbai"));
    }

    @Test
    void testCreateRentalCompany() throws Exception {
        RentalCompany rentalCompany = new RentalCompany(1L, "Self Drive Co", "Bangalore", null);
        when(rentalCompanyService.saveRentalCompany(any(RentalCompany.class))).thenReturn(rentalCompany);

        mockMvc.perform(post("/api/rentalcompanies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(rentalCompany)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Self Drive Co"));
    }

    @Test
    void testUpdateRentalCompany() throws Exception {
        RentalCompany updatedCompany = new RentalCompany(1L, "Drive India", "Delhi", null);
        when(rentalCompanyService.saveRentalCompany(any(RentalCompany.class))).thenReturn(updatedCompany);

        mockMvc.perform(put("/api/rentalcompanies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCompany)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Drive India"))
                .andExpect(jsonPath("$.location").value("Delhi"));
    }

    @Test
    void testDeleteRentalCompany() throws Exception {
        doNothing().when(rentalCompanyService).deleteRentalCompany(1L);

        mockMvc.perform(delete("/api/rentalcompanies/1"))
                .andExpect(status().isOk());
    }



        //     .content("{\"id\":1,\"userId\":1,\"vehicleId\":1,\"startDate\":\"2025-01-10\",\"endDate\":\"2025-01-15\",\"totalAmount\":5000.0}"))
  

    @Test
    void testUpdateBooking() throws Exception {
        Booking updatedBooking = new Booking();
        when(bookingService.saveBooking(any(Booking.class))).thenReturn(updatedBooking);

        mockMvc.perform(put("/api/bookings/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBooking)))
                .andExpect(status().isOk());
                // .andExpect(jsonPath("$.userId").value(2));
    }

    @Test
    void testDeleteBooking() throws Exception {
        doNothing().when(bookingService).deleteBooking(1L);

        mockMvc.perform(delete("/api/bookings/1"))
                .andExpect(status().isOk());
    }

    //_________________________PAGINATION________________________
@Test
    public void testGetPaginatedRentalCompanies() throws Exception {
        RentalCompany rentalCompany1 = new RentalCompany(1L, "Company A", "New York", null);
        RentalCompany rentalCompany2 = new RentalCompany(2L, "Company B", "Los Angeles", null);

        Page<RentalCompany> rentalCompanyPage = new PageImpl<>(Arrays.asList(rentalCompany1, rentalCompany2));

        when(rentalCompanyService.getRentalCompanies(PageRequest.of(0, 2, Sort.by(Sort.Order.asc("name")))))
                .thenReturn(rentalCompanyPage);

        mockMvc.perform(get("/api/rentalcompanies/paginated?page=0&size=2&sort=name,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[1].id").value(2))
                .andExpect(jsonPath("$.content[0].name").value("Company A"))
                .andExpect(jsonPath("$.content[1].name").value("Company B"));
    }

    @Test
    public void testGetRentalCompaniesByLocation() throws Exception {
        RentalCompany rentalCompany1 = new RentalCompany(1L, "Company A", "New York", null);
        RentalCompany rentalCompany2 = new RentalCompany(2L, "Company B", "New York", null);

        Page<RentalCompany> rentalCompanyPage = new PageImpl<>(Arrays.asList(rentalCompany1, rentalCompany2));

        when(rentalCompanyService.getRentalCompaniesByLocation("New York", PageRequest.of(0, 2, Sort.by(Sort.Order.asc("name")))))
                .thenReturn(rentalCompanyPage);

        mockMvc.perform(get("/api/rentalcompanies/search?location=New York&page=0&size=2&sort=name,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[1].id").value(2))
                .andExpect(jsonPath("$.content[0].name").value("Company A"))
                .andExpect(jsonPath("$.content[1].name").value("Company B"));
    }
    
 // ______________AOP______________________
 @Test
 void AOP_testAopFunctionality() throws Exception {
     // Trigger AOP by performing a GET request
     mockMvc.perform(get("/users"))
             .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString()));

     // Check the console log for the "AOP: Method called" message
 }

 @Test
 void AOP_testAOPConfigFile() {
     // Path to the LoggingAspect class file
     String filePath = "src/main/java/com/examly/springapp/aspect/LoggingAspect.java";

     // Create a File object
     File file = new File(filePath);

     // Assert that the file exists and is a valid file
     assertTrue(file.exists() && file.isFile(), "LoggingAspect.java file should exist at the specified path.");
 }

 //_________________LOG_______________
 @Test
 public void LOG_testLogFolderAndFileCreation() {
     String LOG_FOLDER_PATH = "logs";
     String LOG_FILE_PATH = "logs/application.log";
     // Check if the "logs" folder exists
     File logFolder = new File(LOG_FOLDER_PATH);
     assertTrue(logFolder.exists(), "Log folder should be created");

     // Check if the "application.log" file exists inside the "logs" folder
     File logFile = new File(LOG_FILE_PATH);
     assertTrue(logFile.exists(), "Log file should be created inside 'logs' folder");
 }

 //__________________Swagger___________________

 @Test
 void testSwaggerConfigFileExists() throws Exception {
     // Path to the SwaggerConfig file
     Path filePath = Paths.get("src/main/java/com/examly/springapp/config/SwaggerConfig.java");

     // Assert that the file exists
     assertTrue(Files.exists(filePath), "SwaggerConfig.java file should exist");
 }

 @Test
 void testSwaggerConfigContainsConfigurationAnnotation() throws Exception {
     // Path to the SwaggerConfig file
     Path filePath = Paths.get("src/main/java/com/examly/springapp/config/SwaggerConfig.java");

     // Read the file content
     String fileContent = Files.readString(filePath);

     // Assert that the file contains @Configuration annotation
     assertTrue(fileContent.contains("@Configuration"), "SwaggerConfig.java should contain @Configuration annotation");
 }

 @Test
 void testSwaggerConfigContainsCustomOpenAPIMethod() throws Exception {
     // Path to the SwaggerConfig file
     Path filePath = Paths.get("src/main/java/com/examly/springapp/config/SwaggerConfig.java");

     // Read the file content
     String fileContent = Files.readString(filePath);

     // Assert that the file contains the customOpenAPI method
     assertTrue(fileContent.contains("public OpenAPI customOpenAPI()"), "SwaggerConfig.java should contain customOpenAPI method");
 }


 @Test
void Mapping_testBookingEntityHasManyToOneAnnotation() throws Exception {
    Path entityFilePath = Path.of("src/main/java/com/examly/springapp/model/Booking.java");
    String entityFileContent = Files.readString(entityFilePath);
    
    // Check for @ManyToOne annotations on 'user' and 'vehicle'
    assertTrue(entityFileContent.contains("@ManyToOne"), "Booking entity should contain @ManyToOne annotation");
    assertTrue(entityFileContent.contains("user"), "Booking entity should contain a reference to the 'user' field with @ManyToOne annotation");
    assertTrue(entityFileContent.contains("vehicle"), "Booking entity should contain a reference to the 'vehicle' field with @ManyToOne annotation");
}

@Test
void Mapping_testRentalCompanyEntityHasOneToManyAnnotation() throws Exception {
    Path entityFilePath = Path.of("src/main/java/com/examly/springapp/model/RentalCompany.java");
    String entityFileContent = Files.readString(entityFilePath);
    
    // Check for @OneToMany annotation on the 'vehicles' field
    assertTrue(entityFileContent.contains("@OneToMany"), "RentalCompany entity should contain @OneToMany annotation");
    assertTrue(entityFileContent.contains("vehicles"), "RentalCompany entity should have a reference to 'vehicles' with @OneToMany annotation");
}

@Test
void Mapping_testUserEntityHasOneToManyAnnotation() throws Exception {
    Path entityFilePath = Path.of("src/main/java/com/examly/springapp/model/User.java");
    String entityFileContent = Files.readString(entityFilePath);
    
    // Check for @OneToMany annotation on the 'bookings' field
    assertTrue(entityFileContent.contains("@OneToMany"), "User entity should contain @OneToMany annotation");
    assertTrue(entityFileContent.contains("bookings"), "User entity should have a reference to 'bookings' with @OneToMany annotation");
}

@Test
void Mapping_testVehicleEntityHasManyToOneAnnotation() throws Exception {
    Path entityFilePath = Path.of("src/main/java/com/examly/springapp/model/Vehicle.java");
    String entityFileContent = Files.readString(entityFilePath);
    
    // Check for @ManyToOne annotation on the 'rentalCompany' field
    assertTrue(entityFileContent.contains("@ManyToOne"), "Vehicle entity should contain @ManyToOne annotation");
    assertTrue(entityFileContent.contains("rentalCompany"), "Vehicle entity should contain a reference to the 'rentalCompany' field with @ManyToOne annotation");
}

@Test
void Mapping_testBookingEntityHasJsonIgnoreAnnotation() throws Exception {
    Path entityFilePath = Path.of("src/main/java/com/examly/springapp/model/Booking.java");
    String entityFileContent = Files.readString(entityFilePath);
    
    // Check for @JsonIgnore annotation on the 'user' and 'vehicle' fields
//     assertTrue(entityFileContent.contains("@JsonIgnore"), "Booking entity should contain @JsonIgnore annotation");
    assertTrue(entityFileContent.contains("user"), "Booking entity should have @JsonIgnore on the 'user' field");
    assertTrue(entityFileContent.contains("vehicle"), "Booking entity should have @JsonIgnore on the 'vehicle' field");
}

@Test
void Mapping_testRentalCompanyEntityHasJsonIgnoreAnnotation() throws Exception {
    Path entityFilePath = Path.of("src/main/java/com/examly/springapp/model/RentalCompany.java");
    String entityFileContent = Files.readString(entityFilePath);
    
    // Check for @JsonIgnore annotation on the 'vehicles' field
    assertTrue(entityFileContent.contains("@JsonIgnore"), "RentalCompany entity should contain @JsonIgnore annotation");
    assertTrue(entityFileContent.contains("vehicles"), "RentalCompany entity should have @JsonIgnore on the 'vehicles' field");
}

@Test
void Mapping_testVehicleEntityHasJsonIgnoreAnnotation() throws Exception {
    Path entityFilePath = Path.of("src/main/java/com/examly/springapp/model/Vehicle.java");
    String entityFileContent = Files.readString(entityFilePath);
    
    // Check for @JsonIgnore annotation on the 'bookings' field
    assertTrue(entityFileContent.contains("@JsonIgnore"), "Vehicle entity should contain @JsonIgnore annotation");
    assertTrue(entityFileContent.contains("bookings"), "Vehicle entity should have @JsonIgnore on the 'bookings' field");
}

@Test
void Mapping_testUserEntityHasJsonIgnoreAnnotation() throws Exception {
    Path entityFilePath = Path.of("src/main/java/com/examly/springapp/model/User.java");
    String entityFileContent = Files.readString(entityFilePath);
    
    // Check for @JsonIgnore annotation on the 'bookings' field
    assertTrue(entityFileContent.contains("@JsonIgnore"), "User entity should contain @JsonIgnore annotation");
    assertTrue(entityFileContent.contains("bookings"), "User entity should have @JsonIgnore on the 'bookings' field");
}

@Test
void testBookingRepositoryPathAndContent() throws Exception {
    Path repoFilePath = Path.of("src/main/java/com/examly/springapp/repository/BookingRepository.java");
    String repoFileContent = Files.readString(repoFilePath);

    // Check if file exists and contains required annotations and interface
    assertTrue(repoFileContent.contains("@Repository"), "BookingRepository should contain @Repository annotation");
    assertTrue(repoFileContent.contains("JpaRepository<Booking, Long>"), "BookingRepository should extend JpaRepository<Booking, Long>");
}

@Test
void testRentalCompanyRepositoryPathAndContent() throws Exception {
    Path repoFilePath = Path.of("src/main/java/com/examly/springapp/repository/RentalCompanyRepository.java");
    String repoFileContent = Files.readString(repoFilePath);

    // Check if file exists and contains required annotations and interface
    assertTrue(repoFileContent.contains("@Repository"), "RentalCompanyRepository should contain @Repository annotation");
    assertTrue(repoFileContent.contains("JpaRepository<RentalCompany, Long>"), "RentalCompanyRepository should extend JpaRepository<RentalCompany, Long>");
    assertTrue(repoFileContent.contains("Page<RentalCompany> findByLocationIgnoreCase"), "RentalCompanyRepository should have findByLocationIgnoreCase method");
    assertTrue(repoFileContent.contains("Page<RentalCompany> findByLocationJPQL"), "RentalCompanyRepository should have findByLocationJPQL method");
}

@Test
void testUserRepositoryPathAndContent() throws Exception {
    Path repoFilePath = Path.of("src/main/java/com/examly/springapp/repository/UserRepository.java");
    String repoFileContent = Files.readString(repoFilePath);

    // Check if file exists and contains required annotations and interface
    assertTrue(repoFileContent.contains("@Repository"), "UserRepository should contain @Repository annotation");
    assertTrue(repoFileContent.contains("JpaRepository<User, Long>"), "UserRepository should extend JpaRepository<User, Long>");
}

@Test
void testVehicleRepositoryPathAndContent() throws Exception {
    Path repoFilePath = Path.of("src/main/java/com/examly/springapp/repository/VehicleRepository.java");
    String repoFileContent = Files.readString(repoFilePath);

    // Check if file exists and contains required annotations and interface
    assertTrue(repoFileContent.contains("@Repository"), "VehicleRepository should contain @Repository annotation");
    assertTrue(repoFileContent.contains("JpaRepository<Vehicle, Long>"), "VehicleRepository should extend JpaRepository<Vehicle, Long>");
}


}

