package dev.muskrat.library.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "patronymic")
    private String thirdName;

    @Column(name = "birthday")
    private Instant birthday;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="user_takenbook",
        joinColumns = @JoinColumn(name="user_id", referencedColumnName="id"),
        inverseJoinColumns = @JoinColumn(name="taken_id", referencedColumnName="id")
    )
    private List<TakenBook> books;

    public String toString() {
        return String.format("%3d %12s %12s %16s %15s",
            id,
            firstName,
            lastName,
            thirdName,
            new SimpleDateFormat ("dd MMM YYYY").format(Date.from(birthday))
        );
    }
}
