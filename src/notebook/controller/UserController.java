package notebook.controller;

import notebook.model.User;
import notebook.model.repository.GBRepository;
import notebook.model.repository.impl.UserRepository;
import notebook.util.mapper.UserValidation;
import notebook.view.UserView;

import java.util.List;
import java.util.Objects;

public class UserController {
    private final GBRepository repository;

    // DI
    public UserController(GBRepository repository) {
        this.repository = repository;
    }

    public void saveUser(User user) {
        repository.create(user);
    }

    public User readUser(Long userId) throws Exception {
        List<User> users = repository.findAll();
        for (User user : users) {
            if (Objects.equals(user.getId(), userId)) {
                return user;
            }
        }

        throw new RuntimeException("User not found");
    }

    public void updateUser(String userId, User update) {
        update.setId(Long.parseLong(userId));
        repository.update(Long.parseLong(userId), update);
    }

    public List<User> readAll() {
        return repository.findAll();
    }
    public static User createUser() {
        UserValidation userValidation = new UserValidation();

        String firstName = UserView.prompt("Имя: ");
        String lastName = UserView.prompt("Фамилия: ");
        String phone = UserView.prompt("Номер телефона: ");
        User user = new User(firstName, lastName, phone);
        if (userValidation.valid(user)){
            return user;
        } else {
            throw new IllegalArgumentException("Введены некорректные данные");
        }
    }
    public void deleteUser(Long id){
        repository.delete(id);
    }
}
