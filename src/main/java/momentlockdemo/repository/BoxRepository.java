package momentlockdemo.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import momentlockdemo.entity.Box;

public interface BoxRepository extends JpaRepository<Box, Long> {

	List<Box> findByBoxnameContaining(String boxname);

	Optional<Box> findByBoxinvitecode(String boxinvitecode);

	List<Box> findByBoxlocationContaining(String boxlocation);

	List<Box> findByBoxopendateBefore(LocalDateTime date);

	List<Box> findByBoxopendateAfter(LocalDateTime date);

	Optional<Box> findByBoxburycode(String boxburycode);

}
