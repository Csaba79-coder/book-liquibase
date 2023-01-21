package com.csaba79coder.bookliquibase.domain.book.entity;

import com.csaba79coder.bookliquibase.domain.book.value.Availability;
import com.csaba79coder.bookliquibase.domain.book.value.Genre;
import com.csaba79coder.bookliquibase.domain.book.value.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "book")
@NoArgsConstructor
@Where(clause = "availability != 'DELETED'")
public class Book {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id = UUID.randomUUID();

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "genre", nullable = false)
    @Enumerated(EnumType.STRING)
    private Genre genre = Genre.OTHER;

    @Column(name = "isbn", nullable = false, unique = true)
    private Long isbn;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.AVAILABLE;

    @Column(name = "availability", nullable = false)
    @Enumerated(EnumType.STRING)
    private Availability availability = Availability.AVAILABLE;

    public void delete(Book book) {
        book.setAvailability(Availability.DELETED);
    }
}
