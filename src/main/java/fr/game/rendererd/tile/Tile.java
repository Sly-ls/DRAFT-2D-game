package fr.game.rendererd.tile;

import fr.game.constants.world.tiles.TilesDescriptionEnum_ISLAND;
import fr.game.constants.world.tiles.TilesDescriptionEnum_TEST_MAP;
import fr.game.mechanics.core.ScaledImage;

public class Tile {

    public ScaledImage image;
    public int code = -1;
    public boolean collision = false;


    public Tile(TilesDescriptionEnum_TEST_MAP tilesDescription) {
        this.image = new ScaledImage(tilesDescription.getPath());
        this.collision = tilesDescription.isCollision();
        this.code = tilesDescription.getCode();
    }
    public Tile(TilesDescriptionEnum_ISLAND tilesDescription) {
        this.image = new ScaledImage(tilesDescription.getPath());
        this.collision = tilesDescription.isCollision();
        this.code = tilesDescription.getCode();
    }

    public Tile( int code, ScaledImage image) {
        this.image = image;
        this.code = code;
    }

    public ScaledImage getImage() {
        return image;
    }

    public void setImage(ScaledImage image) {
        this.image = image;
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
