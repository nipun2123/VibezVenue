package com.VibezVenue;

import com.VibezVenue.model.User;
import com.VibezVenue.repository.UserRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    private final Faker faker;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.faker = new Faker();
    }

    @Override
    public void run(String... args) throws Exception {

        List<User> orgies = IntStream.range(1,5)
                .mapToObj(i -> User.builder().userCode(faker.random().nextInt(1111,9999999).toString()).fName(faker.name()
                        .firstName()).lName(faker.name().lastName()).email(faker.internet()
                        .emailAddress()).number(faker.phoneNumber().phoneNumber()).build()).toList();

        userRepository.saveAll(orgies);
    }
}
