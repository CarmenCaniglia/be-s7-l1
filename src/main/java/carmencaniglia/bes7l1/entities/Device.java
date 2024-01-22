package carmencaniglia.bes7l1.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "devices")
@Getter
@Setter
@ToString
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String category;
    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
