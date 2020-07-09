package rest.mvc.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import rest.mvc.example.model.AppUserDTO;
import rest.mvc.example.model.AppUserListDTO;
import rest.mvc.example.service.AppUserService;

import java.net.URI;

@RestController
@RequestMapping(AppUserController.BASE_URL)
public class AppUserController {
    public static final String BASE_URL = "/app-users";

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AppUserListDTO> getAllUsers() {
        return ResponseEntity.ok(new AppUserListDTO(appUserService.getAllAppUsers()));
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AppUserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(appUserService.getAppUserById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AppUserDTO> createNewAppUser(@RequestBody AppUserDTO appUserDTO) {
        AppUserDTO savedAppUser = appUserService.createNewAppUser(appUserDTO);
        URI location =
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedAppUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AppUserDTO> updateAppUser(@PathVariable Long id, @RequestBody AppUserDTO appUserDTO) {
        return ResponseEntity.ok(appUserService.saveAppUserByDTO(id, appUserDTO));
    }

    @PatchMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AppUserDTO> patchAppUser(@PathVariable Long id, @RequestBody AppUserDTO appUserDTO) {
        return ResponseEntity.ok(appUserService.patchAppUser(id, appUserDTO));
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteAppUser(@PathVariable Long id) {
        appUserService.deleteAppUserById(id);
        return ResponseEntity.ok("Deleted");
    }
}
