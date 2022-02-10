package fr.game.constants.world.tiles;

public enum TilesDescriptionEnum_TEST_MAP implements TileSet {
    GRASS(0,"/tiles/grass.png",false),
    WALL(1,"/tiles/wall.png",true),
    WATER(2,"/tiles/water.png",true),
    SAND(3,"/tiles/sand.png",false),
    TREE(4,"/tiles/tree.png",true),
    EARTH(5,"/tiles/earth.png",false),
    TREASURE(6,"/objects/treasure.png",true)
    ;

    int code;
    String path;
    boolean collision;

    TilesDescriptionEnum_TEST_MAP(int code, String path, boolean collision) {
        this.code = code;
        this.path = path;
        this.collision = collision;
    }

    public int getCode() {
        return code;
    }

    public String getPath() {
        return path;
    }

    public boolean isCollision() {
        return collision;
    }
}
