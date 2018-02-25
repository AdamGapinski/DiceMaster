package agh.to2.dicemaster.server.managers;

import agh.to2.dicemaster.server.Exceptions.UsernameTakenException;
import agh.to2.dicemaster.server.User;
import agh.to2.dicemaster.server.services.SenderService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UsersManagerTest {

    private List<User> users = new ArrayList<>();
    @Mock
    private SenderService senderService;

    private UsersManager usersManager = new UsersManager(senderService);

    @Before
    public void setUp() throws Exception {
        users.add(usersManager.createUser("user1", "clientQueue1"));
        users.add(usersManager.createUser("user2", "clientQueue2"));
        assert usersManager.getAll().size() == 2;
    }

    @After
    public void tearDown() throws Exception {
        new ArrayList<>(usersManager.getAll()).forEach(user -> usersManager.removeUser(user.getId()));

        assert usersManager.getAll().isEmpty();
    }

    @Test(expected = UsernameTakenException.class)
    public void createUserWithUsernameTaken() throws Exception {
//        given
//        when
        usersManager.createUser("user1", "otherClientQueue");
    }

    @Test
    public void createUser() throws Exception {
        User newUser = usersManager.createUser("Newuser", "newUserQueueName");
//        then
        assert usersManager.getUserById(newUser.getId()).isPresent();
        assert usersManager.getUserByQueueName(newUser.getClientQueueName()).isPresent();
    }

    @Test
    public void getUserById() throws Exception {
        assert usersManager.getUserById(users.get(0).getId()).isPresent();
    }

    @Test
    public void getUserByQueueName() throws Exception {
        assert usersManager.getUserByQueueName(users.get(0).getClientQueueName()).isPresent();
    }

    @Test
    public void getAll() throws Exception {
//        given
//        when
        Collection<User> usersFromManager = usersManager.getAll();
//        then
        assert usersFromManager.containsAll(users);
        assert users.containsAll(usersFromManager);
    }

    @Test
    public void removeUser() throws Exception {
//        given
//        when
        usersManager.removeUser(users.get(0).getId());
//        then
        assert !usersManager.getUserById(users.get(0).getId()).isPresent();
        assert !usersManager.getUserByQueueName(users.get(0).getClientQueueName()).isPresent();
    }

}