package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.charity.entity.Institution;

import java.util.Optional;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {


}
