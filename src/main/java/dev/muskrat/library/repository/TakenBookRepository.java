package dev.muskrat.library.repository;

import dev.muskrat.library.dao.TakenBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TakenBookRepository extends JpaRepository<TakenBook, Long> {
}
