package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.coderslab.charity.entity.Donation;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    @Query("SELECT SUM(d.quantity) FROM Donation d")
    Integer sum();

    @Query("SELECT COUNT(d.quantity) FROM Donation d")
    long count();

    @Query(value = "SELECT d FROM Donation d WHERE d.user = :id")
    List<Donation> getDonationsByUserId(@Param("id") Long id);
}
