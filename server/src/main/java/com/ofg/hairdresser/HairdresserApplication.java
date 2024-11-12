package com.ofg.hairdresser;

import com.ofg.hairdresser.model.entity.Hairdresser;
import com.ofg.hairdresser.model.entity.Role;
import com.ofg.hairdresser.model.entity.Treatment;
import com.ofg.hairdresser.model.entity.User;
import com.ofg.hairdresser.model.request.AppointmentCreateRequest;
import com.ofg.hairdresser.repository.*;
import com.ofg.hairdresser.service.abstact.AppointmentService;
import com.ofg.hairdresser.service.abstact.ReviewService;
import com.ofg.hairdresser.service.abstact.TreatmentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@SpringBootApplication
@EnableScheduling
public class HairdresserApplication {
    public static void main(String[] args) {
        SpringApplication.run(HairdresserApplication.class, args);
    }

    @Bean
    public CommandLineRunner createUsers(UserRepository userRepository,
                                         PasswordEncoder passwordEncoder,
                                         RoleRepository roleRepository,
                                         HairdresserRepository hairdresserRepository,
                                         TreatmentRepository treatmentRepository,
                                         TreatmentService treatmentService,
                                         ReviewService reviewService,
                                         AppointmentService appointmentService) {
        return args -> {
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setName("ROLE_USER");
                        return roleRepository.save(role);
                    });

            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setName("ROLE_ADMIN");
                        return roleRepository.save(role);
                    });

            roleRepository.findByName("ROLE_HAIRDRESSER")
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setName("ROLE_HAIRDRESSER");
                        return roleRepository.save(role);
                    });

            if (userRepository.findByEmail("omer@gmail.com").isEmpty()) {
                Set<Role> roles = new HashSet<>();
                roles.add(adminRole);
                roles.add(userRole);

                User user = new User();
                user.setEmail("omer@gmail.com");
                user.setPassword(passwordEncoder.encode("P4ssword"));
                user.setFirstName("omer");
                user.setLastName("gulhan");
                user.setProfileImage("default.png");
                user.setActive(true);
                user.setRoles(roles);
                userRepository.save(user);
            }

            if (userRepository.findByEmail("faruk@gmail.com").isEmpty()) {
                Set<Role> roles = new HashSet<>();
                roles.add(userRole);

                User user = new User();
                user.setEmail("faruk@gmail.com");
                user.setPassword(passwordEncoder.encode("P4ssword"));
                user.setFirstName("faruk");
                user.setLastName("gulhan");
                user.setProfileImage("default.png");
                user.setActive(true);
                user.setRoles(roles);
                userRepository.save(user);
            }
//
//            String[][] users = new String[20][3];
//            users[0] = new String[]{"john.doe@example.com", "John", "Doe"};
//            users[1] = new String[]{"jane.smith@example.com", "Jane", "Smith"};
//            users[2] = new String[]{"michael.jones@example.com", "Michael", "Jones"};
//            users[3] = new String[]{"lisa.white@example.com", "Lisa", "White"};
//            users[4] = new String[]{"david.brown@example.com", "David", "Brown"};
//            users[5] = new String[]{"emily.green@example.com", "Emily", "Green"};
//            users[6] = new String[]{"steve.black@example.com", "Steve", "Black"};
//            users[7] = new String[]{"mary.miller@example.com", "Mary", "Miller"};
//            users[8] = new String[]{"robert.taylor@example.com", "Robert", "Taylor"};
//            users[9] = new String[]{"susan.wilson@example.com", "Susan", "Wilson"};
//            users[10] = new String[]{"charles.harris@example.com", "Charles", "Harris"};
//            users[11] = new String[]{"olivia.davis@example.com", "Olivia", "Davis"};
//            users[12] = new String[]{"benjamin.martin@example.com", "Benjamin", "Martin"};
//            users[13] = new String[]{"isabella.clark@example.com", "Isabella", "Clark"};
//            users[14] = new String[]{"william.lewis@example.com", "William", "Lewis"};
//            users[15] = new String[]{"sofia.walker@example.com", "Sofia", "Walker"};
//            users[16] = new String[]{"jacob.moore@example.com", "Jacob", "Moore"};
//            users[17] = new String[]{"amelia.jackson@example.com", "Amelia", "Jackson"};
//            users[18] = new String[]{"ethan.thomas@example.com", "Ethan", "Thomas"};
//            users[19] = new String[]{"mia.white@example.com", "Mia", "White"};
//
//            for (int i = 0; i < 20; i++) {
//                Set<Role> roles = new HashSet<>();
//                roles.add(userRole);
//                if (i < 5) {
//                    roles.add(hairdresserRole);
//                }
//                User user = new User();
//                user.setEmail(users[i][0]);
//                user.setPassword(passwordEncoder.encode("P4ssword"));
//                user.setFirstName(users[i][1]);
//                user.setLastName(users[i][2]);
//                user.setProfileImage("default.png");
//                user.setActive(true);
//                user.setRoles(roles);
//                userRepository.save(user);
//            }
//
//            //hairdressers
//            String[] bios = new String[5];
//            int[] yearsOfExperiences = new int[5];
//            String[][] specialties = new String[5][3];
//
//            bios[0] = "Experienced hairdresser with a focus on modern cuts and color.";
//            bios[1] = "Passionate about styling and providing clients with personalized looks.";
//            bios[2] = "Skilled in both men's and women's haircuts, specializing in curly hair.";
//            bios[3] = "Creative stylist with a love for balayage and custom color techniques.";
//            bios[4] = "Focused on long-lasting styles with attention to hair health and texture.";
//
//            yearsOfExperiences[0] = 8;
//            yearsOfExperiences[1] = 5;
//            yearsOfExperiences[2] = 6;
//            yearsOfExperiences[3] = 4;
//            yearsOfExperiences[4] = 7;
//
//            specialties[0] = new String[]{"Modern Cuts", "Coloring", "Hair Extensions"};
//            specialties[1] = new String[]{"Styling", "Blowouts", "Updos"};
//            specialties[2] = new String[]{"Curly Hair", "Men's Haircuts", "Texturizing"};
//            specialties[3] = new String[]{"Balayage", "Hair Coloring", "Highlights"};
//            specialties[4] = new String[]{"Long-lasting Styles", "Hair Health", "Textured Hair"};
//
//            for (int i = 0; i < 5; i++) {
//                Hairdresser hairdresser = new Hairdresser();
//                hairdresser.setUser(userRepository.findById((long) i + 2).orElse(null));
//                User user = userRepository.findById((long) i + 2).orElse(null);
//                System.out.println(user.getEmail());
//                hairdresser.setBio(bios[i]);
//                hairdresser.setYearsOfExperience(yearsOfExperiences[i]);
//                hairdresser.setActive(true);
//                hairdresser.setSpecialties(Arrays.stream(specialties[i]).toList());
//                hairdresserRepository.save(hairdresser);
//            }
//
//            //treatments
//            String[] names = new String[5];
//            String[] description = new String[5];
//            double[] prices = new double[5];
//            int[] durations = new int[5];
//
//            names[0] = "Haircut & Style";
//            names[1] = "Balayage Highlights";
//            names[2] = "Curly Hair Treatment";
//            names[3] = "Full Hair Coloring";
//            names[4] = "Hair Extensions";
//
//            description[0] = "A fresh haircut and styling to suit your look.";
//            description[1] = "Custom balayage highlights to add depth and dimension.";
//            description[2] = "Specialized treatment for curly hair to reduce frizz and enhance curls.";
//            description[3] = "Full coverage coloring to change or refresh your hair color.";
//            description[4] = "Long-lasting hair extensions for added volume and length.";
//
//            prices[0] = 45.99;
//            prices[1] = 120.50;
//            prices[2] = 85.00;
//            prices[3] = 100.00;
//            prices[4] = 250.00;
//
//            durations[0] = 60;
//            durations[1] = 150;
//            durations[2] = 90;
//            durations[3] = 120;
//            durations[4] = 180;
//
//            Random random = new Random();
//
//            for (int i = 0; i < 5; i++) {
//                Treatment treatment = new Treatment();
//                treatment.setName(names[0]);
//                treatment.setDescription(description[0]);
//                treatment.setPrice(prices[0]);
//                treatment.setDuration(durations[0]);
//                treatment.setHairdresser(hairdresserRepository.findById((long) (i + 1)).orElse(null));
//                treatmentRepository.save(treatment);
//            }
//
//            for (int i = 0; i < 5; i++) {
//                int count = random.nextInt(3) + 2;
//                List<Integer> availableIndices = new ArrayList<>();
//                for (int j = 1; j < names.length; j++) {
//                    availableIndices.add(j);
//                }
//                for (int j = 0; j < count; j++) {
//                    int randomIndex = random.nextInt(availableIndices.size());
//                    int index = availableIndices.remove(randomIndex);
//                    Treatment treatment = new Treatment();
//                    treatment.setName(names[index]);
//                    treatment.setDescription(description[index]);
//                    treatment.setPrice(prices[index]);
//                    treatment.setDuration(durations[index]);
//                    treatment.setHairdresser(hairdresserRepository.findById((long) (i + 1)).orElse(null));
//                    treatmentRepository.save(treatment);
//                }
//            }
        };
    }
}