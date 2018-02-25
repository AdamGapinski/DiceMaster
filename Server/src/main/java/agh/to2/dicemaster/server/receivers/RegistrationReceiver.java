package agh.to2.dicemaster.server.receivers;

import agh.to2.dicemaster.common.DTO.RegistrationConfirmationDTO;
import agh.to2.dicemaster.common.DTO.RegistrationRequestDTO;
import agh.to2.dicemaster.server.Exceptions.UsernameTakenException;
import agh.to2.dicemaster.server.User;
import agh.to2.dicemaster.server.managers.UsersManager;
import agh.to2.dicemaster.server.services.QueueService;
import agh.to2.dicemaster.server.services.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegistrationReceiver {
    private final UsersManager usersManager;
    private final SenderService senderService;

    private final QueueService queueService;

    @Autowired
    public RegistrationReceiver(UsersManager usersManager, QueueService queueService, SenderService senderService) {
        this.usersManager = usersManager;
        this.queueService = queueService;
        this.senderService = senderService;
    }


    public void onRegistrationRequest(RegistrationRequestDTO requestDTO, String replyToQueueName) {
        if(requestDTO.getUsername().startsWith("bot#")){
            senderService.sendRequestErrorResponse("Username cannot start with \"bot#\"", replyToQueueName);
        } else {
            try {
                User createdUser = usersManager.createUser(requestDTO.getUsername(), requestDTO.getClientQueueName());
                senderService.sendRegistrationConfirmation(new RegistrationConfirmationDTO(createdUser.getServerQueueName()), replyToQueueName);
                queueService.addRegisteredClientQueue(createdUser.getServerQueueName());
            } catch (UsernameTakenException e) {
                senderService.sendRequestErrorResponse(String.format("Username \"%s\" is taken", requestDTO.getUsername()), replyToQueueName);
            }

        }
    }
}
