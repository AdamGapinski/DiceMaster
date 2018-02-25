package agh.to2.dicemaster.server.api;

import agh.to2.dicemaster.common.api.GameDTO;

public class GameParticipant {
    private String id;

    public GameParticipant() {}

    public GameParticipant(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public  void notifyGameStateChange(GameDTO gameDTO){}

    public  void registerPlayerEventHandler(PlayerEventHandler playerEventHandler){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameParticipant that = (GameParticipant) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
