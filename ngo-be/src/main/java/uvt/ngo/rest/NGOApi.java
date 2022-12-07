package uvt.ngo.rest;

import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uvt.ngo.rest.controller.AuthController;
import uvt.ngo.rest.controller.NGOController;
import uvt.ngo.rest.entity.NGOResponse;
import uvt.ngo.rest.entity.UserType;

@RestController
public class NGOApi {

    @Autowired
    private NGOController ngoController;

    @Autowired
    private AuthController authController;

    final Logger logger = LoggerFactory.getLogger(NGOApi.class);

    @Operation(summary = "Fetches details about a NGO based on ID", tags = "NGO endpoints")
    @GetMapping("/ngo")
    public NGOResponse getNGOById(@RequestParam(value = "id") final String id) {
        return ngoController.getNgo(id);
    }

    @Operation(summary = "Fetches details about all NGOs", tags = "NGO endpoints")
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/ngos")
    public NGOResponse getNGOs(@RequestParam(value = "api-key") final String apiKey) {
        return ngoController.getNgos(authController.getUserType(apiKey));
    }

    /*
    This would be sent for approval if the user is not an admin
    This would be sent directly to the database if the user is an admin
     */
    @Operation(summary = "Adds or changes a NGO based on ID", tags = "NGO endpoints")
    @PutMapping("/ngo")
    public NGOResponse addNGOAndReturnId(
            @RequestParam(value = "id", defaultValue = "") final String id,
            @RequestParam(value = "name") final String name,
            @RequestParam(value = "description") final String description,
            @RequestParam(value = "address") final String address,
            @RequestParam(value = "location") final String location,
            @RequestParam(value = "founded", defaultValue = "01-01-1900") final String founded,
            @RequestParam(value = "website") final String website,
            @RequestParam(value = "imageUrl") final String imageUrl,
            @RequestParam(value = "priority", defaultValue = "0") final Integer priority,
            @RequestParam(value = "areas", defaultValue = "NGO") final String areas,
            @RequestParam(value = "api-key", defaultValue = "") final String apiKey) {

        return authController.getUserType(apiKey).equals(UserType.ADMIN) ?
                ngoController.modifyNgo(name, id, description, address, location, founded, website, imageUrl, priority, areas) :
                ngoController.addNgo(name, description, address, location, founded, website, imageUrl, priority, areas);
    }

    /*
    This would be sent for approval if the user is not an admin
    This would be sent directly to the database if the user is an admin
     */
    @Operation(summary = "Deletes a ngo based on ID", tags = "NGO endpoints")
    @DeleteMapping("/ngo")
    public NGOResponse deleteNGO(@RequestParam(value = "id") final String id,
                         @RequestParam(value = "api-key") final String apiKey) {

        return ngoController.deleteNgo(id, authController.getUserType(apiKey));
    }
}
