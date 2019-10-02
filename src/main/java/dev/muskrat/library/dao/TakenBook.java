package dev.muskrat.library.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@Table(name = "taken_books")
@AllArgsConstructor
@NoArgsConstructor
public class TakenBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinTable(name="book_takenbook",
        joinColumns = @JoinColumn(name="taken_id", referencedColumnName="id"),
        inverseJoinColumns = @JoinColumn(name="book_id", referencedColumnName="id")
    )
    private Book book;

    @ManyToOne
    @JoinTable(name="user_takenbook",
        joinColumns = @JoinColumn(name="taken_id", referencedColumnName="id"),
        inverseJoinColumns = @JoinColumn(name="user_id", referencedColumnName="id")
    )
    private User user;

    @Column(name = "expired")
    private Instant expired;

    @CreatedDate
    @Column(name = "created")
    private Integer created;
}
