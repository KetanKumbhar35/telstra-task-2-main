package au.com.telstra.simcardactivator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import au.com.telstra.simcardactivator.foundation.SimCard;

public interface SIMCardRepository extends JpaRepository<SimCard, Long> {
}
