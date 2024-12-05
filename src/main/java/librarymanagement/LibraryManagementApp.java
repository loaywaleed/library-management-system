package librarymanagement;

import java.util.ArrayList;
import java.util.Arrays;

import lombok.RequiredArgsConstructor;
import librarymanagement.model.AppUser;
import librarymanagement.model.AppUserRole;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import librarymanagement.service.UserService;

@SpringBootApplication
@RequiredArgsConstructor
public class LibraryManagementApp implements CommandLineRunner {

  final UserService userService;

  public static void main(String[] args) {
    SpringApplication.run(LibraryManagementApp.class, args);
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Override
  public void run(String... params) throws Exception {
    AppUser admin = new AppUser();
    admin.setUsername("admin");
    admin.setPassword("admin");
    admin.setEmail("admin@email.com");
    admin.setAppUserRoles(new ArrayList<AppUserRole>(Arrays.asList(AppUserRole.ROLE_ADMIN)));

    userService.signup(admin);

    AppUser client = new AppUser();
    client.setUsername("client");
    client.setPassword("client");
    client.setEmail("client@email.com");
    client.setAppUserRoles(new ArrayList<AppUserRole>(Arrays.asList(AppUserRole.ROLE_CLIENT)));

    userService.signup(client);
  }

}
