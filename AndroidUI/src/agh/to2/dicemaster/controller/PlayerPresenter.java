package agh.to2.dicemaster.controller;

import agh.to2.dicemaster.common.api.*;
import agh.to2.dicemaster.model.gui.GameEventHandler;
import agh.to2.dicemaster.model.server.*;
import agh.to2.dicemaster.view.*;

public class PlayerPresenter implements GameEventHandler{
    private LoginView loginView = new LoginView();
    private LobbyView lobbyView = new LobbyView();
    private TableCreatorView tableCreatorView = new TableCreatorView();
    private TableView tableView = new TableView();

    private Server server;
    private ServerGame serverGame;
    private UserInGame userInGame;
    private String userName;
    private UserType userType;

    public PlayerPresenter() {
        loginView.switchTo();
        //will be changed to be the default view after launching the app
    }

    public void onLoginButtonPress(String userName) {
        if (!server.registerClient(userName));      //TODO: komunikat o błędzie
        else {
            this.userName = userName;
            lobbyView.refresh(userName, server.getAvailableGames());
            lobbyView.switchTo();
        }
    }

    public void onTableListClick() {
        //TODO
    }

    public void onJoinTable(GameDTO gameDTO, UserType userType) {
        if (server.requestJoinGame(gameDTO, null, userType) == null);    //TODO: GameEventHandler, komunikat o błędzie
        else {
            tableView.refresh(gameDTO, userType);
            tableView.switchTo();
        }

    }

    public void onLobbyCreateTableButtonClick() {
        tableCreatorView.switchTo();
    }

    public void onCreateTableButtonClick(GameConfigDTO gameConfigDTO, UserType userType) {
        serverGame = server.createGame(gameConfigDTO, null, userType);

        if (serverGame == null);      //TODO: GameEventHandler, komunikat o błędzie
        else {
            tableView.refresh(serverGame.getGameDTO(), userType);
            tableView.switchTo();
            this.userType = userType;
        }
    }

    public void onDiceClick(MoveDTO moveDTO) {

    }

    public void onRollButtonClick(MoveDTO moveDTO) {
        serverGame.makeMove(moveDTO);
    }

    public void onLeaveTableButtonClick() {
        serverGame.leaveGame();
        lobbyView.switchTo();
    }

    public void onGameStatusRefresh(GameDTO gameDTO) {
        tableView.refresh(gameDTO, null);   //TODO: userType
    }

    @Override
    public void onGameChange(GameDTO gameDTO) {
        tableView.refresh(gameDTO,this.userType);
    }
}
