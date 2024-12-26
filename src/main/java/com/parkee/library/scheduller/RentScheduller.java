package com.parkee.library.scheduller;


import com.parkee.library.constants.ReturnStatus;
import com.parkee.library.repositories.RentHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Slf4j
public class RentScheduller {
    private final RentHistoryRepository rentHistRepository;

    public RentScheduller(RentHistoryRepository rentHistRepository) {
        this.rentHistRepository = rentHistRepository;
    }

    @Transactional
    @Scheduled(cron = "#{'${rent.update.status.cron}'}", zone = "Asia/Jakarta")
    public void updateRentStatusOverdue() {
        log.info("Starting update RentStatus {}", LocalDateTime.now());
        var rentHistories = rentHistRepository.findAllNotReturnedBook(ReturnStatus.BORROWED, LocalDate.now());
        rentHistories.forEach(rentHistory -> rentHistory.setStatus(ReturnStatus.OVERDUE));
        rentHistRepository.saveAll(rentHistories);
        log.info("Finish update RentStatus {}", LocalDateTime.now());
    }
}
