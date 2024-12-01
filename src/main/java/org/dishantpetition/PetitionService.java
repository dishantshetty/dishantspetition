package org.dishantpetition;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetitionService {

    private final List<Petition> petitions = new ArrayList<>();

    public PetitionService() {
        petitions.add(new Petition("Save the Forest", "A petition to save the Amazon rainforest"));
        petitions.add(new Petition("Save the Oceans", "A petition to reduce ocean pollution"));
    }

    public List<Petition> getAllPetitions() {
        return petitions;
    }

    public Petition getPetitionByTitle(String title) {
        return petitions.stream()
                .filter(petition -> petition.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    public void addPetition(Petition petition) {
        petitions.add(petition);
    }

    public void addSignature(String title, Signature signature) {
        Petition petition = getPetitionByTitle(title);
        if (petition != null) {
            petition.addSignature(signature);
        }
    }
}

