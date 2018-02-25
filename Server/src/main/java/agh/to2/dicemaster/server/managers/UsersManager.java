package agh.to2.dicemaster.server.managers;

import agh.to2.dicemaster.server.Exceptions.UsernameTakenException;
import agh.to2.dicemaster.server.User;
import agh.to2.dicemaster.server.services.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

@Service
public class UsersManager {

    private final SenderService senderService;

//    FixMe: Maybe storing this in HashMaps isn't the best idea?
    private HashMap<String, String> userIdByQueueName = new HashMap<>();
    private HashMap<String, User> users = new HashMap<>();

    @Autowired
    public UsersManager(SenderService senderService) {
        this.senderService = senderService;
    }

    public User createUser(String username, String clientQueueName) throws UsernameTakenException {
        if(users.get(username) != null || userIdByQueueName.get(clientQueueName) != null){
            throw new UsernameTakenException();
        }
        User user = new User(username, clientQueueName, senderService);
        userIdByQueueName.put(user.getServerQueueName(), user.getId());
        userIdByQueueName.put(user.getClientQueueName(), user.getId());
        users.put(user.getId(), user);
        return user;
    }

    public Optional<User> getUserById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public Optional<User> getUserByQueueName(String queueName){
        return Optional.ofNullable(users.get(userIdByQueueName.get(queueName)));
    }

    public Collection<User> getAll() {
        return users.values();
    }

    public void removeUser(String userId) {
        User removedUser = users.remove(userId);
        userIdByQueueName.remove(removedUser.getServerQueueName());
        userIdByQueueName.remove(removedUser.getClientQueueName());
    }
}
