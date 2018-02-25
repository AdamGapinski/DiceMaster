package agh.to2.dicemaster.server.listeners;

import agh.to2.dicemaster.server.User;
import agh.to2.dicemaster.server.managers.UsersManager;
import agh.to2.dicemaster.server.services.QueueService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UnregistrationListener implements MessageListener {

    private final UsersManager usersManager;
    private final QueueService queueService;

    @Autowired
    public UnregistrationListener(UsersManager usersManager, QueueService queueService) {
        this.usersManager = usersManager;
        this.queueService = queueService;
    }

    @Override
    public void onMessage(Message message) {
        String clientQueueName = message.getMessageProperties().
                getHeaders().get("name").toString();

        Optional<User> userOptional = usersManager.getUserByQueueName(clientQueueName);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            user.leaveGame();
            usersManager.removeUser(user.getId());
            queueService.removeRegisteredClientQueue(user.getServerQueueName());
        }
    }
}
