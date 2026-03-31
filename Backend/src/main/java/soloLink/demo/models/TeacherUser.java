package soloLink.demo.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teacher_users") // Buena práctica: nombrar la tabla en plural
public class TeacherUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String publicId; // para el link (ej: /profe/juan-perez)

    private Double pricePerHour;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Un profesor tiene muchas disponibilidades
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Availability> availabilities = new ArrayList<>();

    // Un profesor tiene muchas reservas
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();

    public TeacherUser() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public Double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(Double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<Availability> availabilities) {
        this.availabilities = availabilities;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}