package agh.to2.dicemaster.common.DTO;

public class RegistrationConfirmationDTO {
    private String serverQueueName;

    public RegistrationConfirmationDTO() { // Converter default constructor
    }

    public RegistrationConfirmationDTO(String serverQueueName) {
        this.serverQueueName = serverQueueName;
    }

    public String getServerQueueName() {
        return serverQueueName;
    }

    public void setServerQueueName(String serverQueueName) {
        this.serverQueueName = serverQueueName;
    }
}
