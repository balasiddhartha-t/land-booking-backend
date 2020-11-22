package com.hackershousing.plotregistration.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hackershousing.plotregistration.model.Registration;

public interface RegistrationRepository extends MongoRepository<Registration, String> {
  List<Registration> findByFilled(boolean filled);
  List<Registration> findBySlotNumberContaining(Integer slotNumber);
  Optional<Registration> findBySlotNumber(Integer slotNumber);

}
