package pl.coderslab.charity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.repository.DonationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DonationService {
    @Autowired
    private DonationRepository donationRepository;

    public DonationService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public Integer sum() {
        return donationRepository.sum();
    }

    public long count() {
        return donationRepository.count();
    }

    public void save(Donation donation){
        donationRepository.save(donation);
    }

    public List<Donation> findAllByUser(User user) {
        return donationRepository.getDonationsByUser(user);
    }

    public List<Donation> findAll() {
        return donationRepository.findAll();
    }

    public Optional<Donation> findById(Long id) {
        return donationRepository.findById(id);
    }


}
