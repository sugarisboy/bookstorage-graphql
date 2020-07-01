package dev.muskrat.library.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;

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

    @Column(name = "created")
    private Instant created;

    public String toString() {
        return String.format("%3d %3d %30s %15s %15s",
            user.getId(),
            book.getId(),
            book.getTitle(),
            new SimpleDateFormat("dd MMM YYYY").format(Date.from(created)),
            new SimpleDateFormat("dd MMM YYYY").format(Date.from(expired))
        );
    }
}
