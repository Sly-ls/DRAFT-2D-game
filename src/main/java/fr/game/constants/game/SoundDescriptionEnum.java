package fr.game.constants.game;

public enum SoundDescriptionEnum {

    BLUEBOYADVENTURE(0,"/sounds/BlueBoyAdventure.wav"),
    COIN(1,"/sounds/coin.wav"),
    CURSOR(2,"/sounds/cursor.wav"),
    FANFARE(3,"/sounds/fanfare.wav"),
    HITMONSTER(4,"/sounds/hitmonster.wav"),
    LEVELUP(5,"/sounds/levelup.wav"),
    POWERUP(6,"/sounds/powerup.wav"),
    RECEIVEDAMAGE(7,"/sounds/receivedamage.wav"),
    UNLOCK(8,"/sounds/unlock.wav")
    ;

    int code;
    String path;

    SoundDescriptionEnum(int code, String path) {
        this.code = code;
        this.path = path;
    }

    public int getCode() {
        return code;
    }

    public String getPath() {
        return path;
    }
}
