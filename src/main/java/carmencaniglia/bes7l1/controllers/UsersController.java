package carmencaniglia.bes7l1.controllers;

import carmencaniglia.bes7l1.exceptions.BadRequestException;
import carmencaniglia.bes7l1.payloads.users.UserDTO;
import carmencaniglia.bes7l1.payloads.users.UserResDTO;
import carmencaniglia.bes7l1.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import carmencaniglia.bes7l1.entities.User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;
    @GetMapping
    public Page<carmencaniglia.bes7l1.entities.User> getUsers(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size,
                                                              @RequestParam(defaultValue = "id") String orderBy){
        return usersService.getUsers(page, size, orderBy);
    }
    @GetMapping("/{userId}")
    public carmencaniglia.bes7l1.entities.User getUserById(@PathVariable long userId){
        return usersService.findById(userId);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResDTO createUser(@RequestBody @Validated UserDTO newUserPayload, BindingResult validation){
        if(validation.hasErrors()){
            throw new BadRequestException("Payload's error!");
        }else {
            User newUser = usersService.save(newUserPayload);
            return new UserResDTO(newUser.getId());
        }

    }
    @PutMapping("/{userId}")
    public User updateUser(@PathVariable long userId,@RequestBody User updateUserPayload){
        return usersService.findByIdAndUpdate(userId, updateUserPayload);
    }
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getAndDeleteUser(@PathVariable long userId){
        usersService.findByIdAndDelete(userId);
    }
}

