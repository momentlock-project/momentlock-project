package momentlockdemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import momentlockdemo.entity.Afile;
import momentlockdemo.entity.Capsule;

public interface AfileRepository extends JpaRepository<Afile, Long> {

	List<Afile> findByCapsule(Capsule capsule);

	List<Afile> findByCapsuleOrderByAfregdateDesc(Capsule capsule);

	List<Afile> findByAfdelyn(String afdelyn);

	List<Afile> findByCapsuleAndAfdelyn(Capsule capsule, String afdelyn);

	long countByCapsule(Capsule capsule);

}
