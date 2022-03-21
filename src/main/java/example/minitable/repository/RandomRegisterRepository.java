package example.minitable.repository;

import example.minitable.domain.RandomRegister;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RandomRegisterRepository extends JpaRepository<RandomRegister, Long> {

    RandomRegister findByRandomNo(int tmpNo);
}
