package librarymanagement.repository;
import librarymanagement.model.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PatronRepository extends JpaRepository<Patron, Long> {
    Optional<Patron> findByName(String name);
}