package uvt.ngo.rest.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import uvt.ngo.repositories.NgoRepository;
import uvt.ngo.rest.entity.IssueArea;
import uvt.ngo.rest.entity.NGO;
import uvt.ngo.rest.entity.NGOResponse;
import uvt.ngo.rest.entity.UserType;
import uvt.ngo.rest.util.BinomialHeap;

import java.util.*;
import java.util.stream.Collectors;

import static uvt.ngo.rest.util.PatternAutomata.search;

@Controller
public class NGOController {

    final Logger logger = LoggerFactory.getLogger(NGOController.class);
    final ArrayList<String> forbiddenWords = new ArrayList<>(List.of("poop", "zdoop"));

    @Autowired
    private NgoRepository ngoRepository;

    public NGOResponse getNgo(final String id) {
        final NGO ngo = ngoRepository.findById(id).orElse(null);
        return !Objects.isNull(ngo) ?
                NGOResponse.builder()
                        .ngos(Collections.singletonList(ngo))
                        .message("Successfully deleted ngo with id:" + id)
                        .build()
                :
                NGOResponse.builder()
                        .message("NGO with id:" + id + " was not found")
                        .build();
    }

    public NGOResponse getNgos(UserType userType) {
        final List<NGO> listOfNgos = ngoRepository.findAll();
        return userType.equals(UserType.ADMIN) ?
                NGOResponse.builder()
                        .message("All present NGOs, including the unapproved ones")
                        .ngos(listOfNgos).build()
                :
                NGOResponse.builder()
                        .message("All present NGOs, except the unapproved ones")
                        .ngos(listOfNgos.stream().filter(NGO::getApproved).collect(Collectors.toList()))
                        .build();
    }

    public NGOResponse getPrioNgos(UserType userType) {
        final List<NGO> listOfNgos = ngoRepository.findAll();

        return userType.equals(UserType.ADMIN) ?
                NGOResponse.builder()
                        .message("All present NGOs, including the unapproved ones")
                        .ngos(getPrioNgosBasedOnHeap(listOfNgos)).build()
                :
                NGOResponse.builder()
                        .message("All present NGOs, except the unapproved ones")
                        .ngos(getPrioNgosBasedOnHeap(listOfNgos).stream().filter(NGO::getApproved).collect(Collectors.toList()))
                        .build();
    }

    /**
     * This method is quite useless in the scope of the project
     * But was introduced in order to prove a concept for SDA
     * @param listOfNgos
     * @return
     */
    private List<NGO> getPrioNgosBasedOnHeap(List<NGO> listOfNgos) {
        final BinomialHeap binomialHeap = new BinomialHeap();
        listOfNgos.stream().forEach(binomialHeap::insert);
        int min = binomialHeap.extractMin();

        return listOfNgos.stream().filter(e -> e.getPriority() == min).collect(Collectors.toList());

    }

    public NGOResponse modifyNgo(final String name, final String id, final String description, final String address,
                                 final String location, final String founded,final String website, final String imageUrl,
                                 final Integer priority, final String areas) {
        final String dbId = StringUtils.isEmpty(id) ? RandomStringUtils.randomAlphabetic(20) : id;
        final NGO ngo = ngoRepository.findById(dbId)
                .orElse(new NGO());

        getNgo(name, description, address, location, founded, website, imageUrl, priority, areas, dbId, ngo, true);
        return NGOResponse.builder()
                .message("Added/Modified NGO with id:" + dbId)
                .ngos(Collections.singletonList(ngo))
                .build();
    }

    public NGOResponse addNgo(final String name, final String description, final String address, final String location,
                              final String founded, final String website, final String imageUrl, final Integer priority,
                              final String areas) {
        final String dbId = RandomStringUtils.randomAlphabetic(20);
        final NGO ngo = new NGO();

        boolean hasForbidden = forbiddenWords.stream().anyMatch(e -> search(e.toCharArray(), description.toCharArray()));
        if (hasForbidden) {
            return NGOResponse.builder()
                    .message("The description contains forbidden words. Cannot be send for approval!")
                    .build();
        }

        getNgo(name, description, address, location, founded, website, imageUrl, priority, areas, dbId, ngo, false);
        return NGOResponse.builder()
                .message("Added/Modified NGO with id:" + dbId)
                .ngos(Collections.singletonList(ngo))
                .build();
    }

    private void getNgo(String name, String description, String address, String location, String founded, String website, String imageUrl,
                        Integer priority, String areas, String dbId, NGO ngo, Boolean approved) {
        ngo.setId(dbId);
        ngo.setName(name);
        ngo.setDescription(description);
        ngo.setAddress(address);
        ngo.setLocation(location);
        ngo.setFounded(founded);
        ngo.setWebsite(website);
        ngo.setImageUrl(imageUrl);
        ngo.setPriority(priority);
        ngo.setApproved(approved);

        final List<String> areasAsList = List.of(areas.split(","));
        ngo.setIssueAreaList(areasAsList.stream().filter(StringUtils::isNotEmpty).map(IssueArea::valueOf).collect(Collectors.toList()));

        ngoRepository.save(ngo);
    }

    public NGOResponse deleteNgo(final String id, final UserType userType) {
        if (userType.equals(UserType.ADMIN)) {
            final Optional<NGO> ngo = ngoRepository.findById(id);
            ngoRepository.deleteById(id);
            return ngo.isPresent() ? NGOResponse.builder()
                    .message("Successfully deleted the NGO with id:" + id)
                    .ngos(Collections.singletonList(ngo.get()))
                    .build()
                    :
                    NGOResponse.builder().message("Ngo with id:" + id + " not found").build();
        }
        return NGOResponse.builder()
                .message("Unauthorized for the deletion of NGO with id:" + id)
                .build();
    }
}
