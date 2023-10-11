package au.com.telstra.simcardactivator.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.telstra.simcardactivator.foundation.SimCard;
import au.com.telstra.simcardactivator.repository.SIMCardRepository;

@Service
public class SIMCardService {
    private final SIMCardRepository simCardRepository;

    @Autowired
    public SIMCardService(SIMCardRepository simCardRepository) {
        this.simCardRepository = simCardRepository;
    }

    public SimCard saveSIMCard(SimCard simCard) {
        return simCardRepository.save(simCard);
    }

    public Optional<SimCard> getSIMCardById(Long simCardId) {
        return simCardRepository.findById(simCardId);
    }
}