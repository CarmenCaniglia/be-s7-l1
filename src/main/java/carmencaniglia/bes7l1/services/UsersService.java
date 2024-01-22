package carmencaniglia.bes7l1.services;

import carmencaniglia.bes7l1.entities.User;
import carmencaniglia.bes7l1.exceptions.BadRequestException;
import carmencaniglia.bes7l1.exceptions.NotFoundException;
import carmencaniglia.bes7l1.payloads.users.UserDTO;
import carmencaniglia.bes7l1.repositories.UsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UsersService {
    @Autowired
    private UsersDAO usersDAO;
    public Page<User> getUsers(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return usersDAO.findAll(pageable);
    }

    public User save(UserDTO body){
        usersDAO.findByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestException("The email "+ user.getEmail() + " is already in use!");
        });
        usersDAO.findByUsername(body.username()).ifPresent(user -> {
            throw new BadRequestException("The username "+ user.getUsername() + " is already in use!");
        });
        User newUser = new User();
        newUser.setAvatar("https://ui-avatars.com/api/?name="+body.name()+"+"+body.surname());
        newUser.setUsername(body.username());
        newUser.setName(body.name());
        newUser.setSurname(body.surname());
        newUser.setEmail(body.email());
        return usersDAO.save(newUser);
    }

    public User findById(long id){
        return usersDAO.findById(id).orElseThrow(()->new NotFoundException(id));
    }

    public void findByIdAndDelete (long id){
        User found = this.findById(id);
        usersDAO.delete(found);
    }

    public User findByIdAndUpdate(long id, User body){
        User found = this.findById(id);
        found.setUsername(body.getUsername());
        found.setName(body.getName());
        found.setSurname(body.getSurname());
        found.setEmail(body.getEmail());
        return usersDAO.save(found);
    }
}

