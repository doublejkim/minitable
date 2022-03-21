package example.minitable.service;

import example.minitable.domain.RandomRegister;
import example.minitable.repository.RandomRegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RandomRegisterService {

    private final RandomRegisterRepository randomRegisterRepository;

    @Transactional
    public void initRandomRegister(int count) {

        for (int i = 1; i <= count; i++) {

            RandomRegister randomRegister = new RandomRegister(i);
            randomRegisterRepository.save(randomRegister);

        }

    }


}
