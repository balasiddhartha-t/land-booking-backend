package com.hackershousing.plotregistration.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hackershousing.plotregistration.model.Customer;
import com.hackershousing.plotregistration.model.Registration;
import com.hackershousing.plotregistration.repository.RegistrationRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/")
public class RegistrationController {

  private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);	

  @Autowired
  RegistrationRepository slotRepository;

  @GetMapping("/slots")
  public ResponseEntity<List<Registration>> getAllSlots(@RequestParam(required = false) Integer slotNum) {
    try {
      List<Registration> slots = new ArrayList<Registration>();

      if (slotNum == null)
        slotRepository.findAll().forEach(slots::add);
      else
        slotRepository.findBySlotNumberContaining(slotNum).forEach(slots::add);

      if (slots.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(slots, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/slot/{id}")
  public ResponseEntity<List< Optional<Registration>>> getSlotById(@PathVariable("id") Integer slotNum) {
	  try {
	      List< Optional<Registration>> slots = new ArrayList< Optional<Registration>>();
	      slots.add(slotRepository.findBySlotNumber(slotNum));
	        if (slots.isEmpty()) {
	          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        }

	        return new ResponseEntity<>(slots, HttpStatus.OK);
	        
	} catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
  }

  @PostMapping("/slots")
  public ResponseEntity<Registration> createSlot(@RequestBody Registration slot) {
    try {
       Optional<Registration> slotData = slotRepository.findBySlotNumber(slot.getSlotNumber());
       if (slotData.isPresent()) {
    	      Registration _slot = slotData.get();
    	      _slot.setFilled(slot.isFilled());
    	      _slot.setSlotNumber(slot.getSlotNumber());
    	      _slot.setCustomer( new Customer(slot.getCustomer().getName(), slot.getCustomer().getContact()));
    	      return new ResponseEntity<>(slotRepository.save(_slot), HttpStatus.OK);
       }else {
    	   Registration _slot = slotRepository.save(new Registration(slot.getSlotNumber(), slot.isFilled(), new Customer(slot.getCustomer().getName(), slot.getCustomer().getContact())));
           return new ResponseEntity<>(_slot, HttpStatus.CREATED);
       }
    	   
       
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/slots/{id}")
  public ResponseEntity<HttpStatus> deleteSlot(@PathVariable("id") String id) {
    try {
      slotRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


  @GetMapping("/slots/filled")
  public ResponseEntity<List<Registration>> findByPublished() {
    try {
      List<Registration> slots = slotRepository.findByFilled(true);

      if (slots.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(slots, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
