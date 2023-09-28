package pl.coderslab.charity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.repository.InstitutionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InstitutionService {
    @Autowired
    private InstitutionRepository institutionRepository;

    public InstitutionService(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }
    public void save(Institution institution){
        institutionRepository.save(institution);
    }

    public List<Institution> listAll() {
        return institutionRepository.findAll();
    }

    public Optional<Institution> findById(Long id) {
        return institutionRepository.findById(id);
    }

    public void deleteById(Long id) {
        institutionRepository.deleteById(id);
    }
}
