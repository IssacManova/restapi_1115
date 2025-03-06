package com.examly.springapp.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RentalCompany {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    private String details;

    @OneToMany(mappedBy = "rentalCompany")
    @JsonIgnore
    private List<Vehicle> vehicles;

    public RentalCompany(Long id, String name, String location, String details) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.details = details;
    }
}
