package com.VibezVenue;

import com.VibezVenue.model.Org;
import com.VibezVenue.repository.OrgRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class DataLoader implements CommandLineRunner {

    private final OrgRepository orgRepository;
    private final Faker faker;

    public DataLoader(OrgRepository orgRepository) {
        this.orgRepository = orgRepository;
        this.faker = new Faker();
    }

    @Override
    public void run(String... args) throws Exception {

        List<Org> orgies = IntStream.range(1,5)
                .mapToObj(i -> new Org(
                        faker.company().name(),
                        faker.lorem().paragraph()
                )).toList();

        orgRepository.saveAll(orgies);
    }
}
