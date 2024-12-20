package org.dishantpetition;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetitionService {

    private final List<Petition> petitions = new ArrayList<>();

    public PetitionService() {
        petitions.add(new Petition("Global Warming", "A petition to reduce climate change"));
        petitions.add(new Petition("Save rhinos", "A petition to stop poaching of Rhinos"));
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

