package au.com.telstra.simcardactivator.component;

import au.com.telstra.simcardactivator.foundation.SimCard;
import au.com.telstra.simcardactivator.service.SIMCardService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SimCardActivationRestController {

	 private final SIMCardService simCardService;

	    @Autowired
	    public SimCardActivationRestController (SIMCardService simCardService) {
	        this.simCardService = simCardService;
	    }

	    @PostMapping("/activate")
	    public ResponseEntity<String> activateSIM(@RequestBody SimRequest  simRequest) {
	    	// Send a POST request to the SimCardActuator to activate the SIM card
	        String actuatorUrl = "http://localhost:8444/actuate"; // Replace with the correct URL
	        ActuatorRequest actuatorRequest = new ActuatorRequest(simRequest.getIccid());
	        actuatorResponse actuatorResponse = RestTemplate.postForObject(actuatorUrl, actuatorRequest, ActuatorResponse.class);

	        // Determine whether the activation was successful
	        boolean activationSuccess = actuatorResponse != null && actuatorResponse.isSuccess();

	        // Save the SIM card information and activation status to the database
	        SimCard simCard = new SimCard(simRequest.getIccid(), simRequest.getCustomerEmail(), activationSuccess);
	        SimCard savedSIMCard = simCardService.saveSIMCard(simCard);

	        if (activationSuccess) {
	            return ResponseEntity.ok("SIM card activation was successful");
	        } else {
	            return ResponseEntity.ok("SIM card activation failed");
	        }
	    }
	    }

	    @GetMapping("/query")
	    public ResponseEntity<SimCard> getSIMCardById(@RequestParam("simCardId") Long simCardId) {
	        // Query the database for SIM card information based on simCardId
	        Optional<SimCard> optionalSIMCard = simCardService.getSIMCardById(simCardId);

	        if (optionalSIMCard.isPresent()) {
	            return ResponseEntity.ok(optionalSIMCard.get());
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

}
