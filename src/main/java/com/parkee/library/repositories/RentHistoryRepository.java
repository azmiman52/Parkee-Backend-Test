package com.parkee.library.repositories;

import com.parkee.library.constants.ReturnStatus;
import com.parkee.library.domains.entities.RentHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RentHistoryRepository extends JpaRepository<RentHistory, Integer> {
    @Query(value = "SELECT rh FROM RentHistory rh WHERE rh.user.id = :userId AND rh.book.isbn = :isbn AND rh.returnDate IS NULL")
    @EntityGraph(attributePaths = {"user", "book"})
    Optional<RentHistory> findOneRentHistory(Integer userId, String isbn);

    Page<RentHistory> findAllByUserId(Integer userId, Pageable pageable);

    @Query(value = "SELECT rh FROM RentHistory rh WHERE rh.rentDue = :dueDate AND rh.status = :status AND rh.returnDate IS NULL")
    List<RentHistory> findAllNotReturnedBook(ReturnStatus status, LocalDate dueDate);
}
