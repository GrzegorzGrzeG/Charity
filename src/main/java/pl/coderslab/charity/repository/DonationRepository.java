package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.entity.User;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    @Query("SELECT SUM(d.quantity) FROM Donation d")
    Integer sum();

    @Query("SELECT COUNT(d.quantity) FROM Donation d")
    long count();

    List<Donation> getDonationsByUser(User user);
}
