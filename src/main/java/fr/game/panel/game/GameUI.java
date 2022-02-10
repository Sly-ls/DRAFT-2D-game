package fr.game.panel.game;

import fr.game.constants.*;
import fr.game.constants.core.FontEnum;
import fr.game.constants.core.MessageTypeEnum;
import fr.game.constants.rendered.GameValueEnum;
import fr.game.constants.panel.PanelStateEnum;
import fr.game.mechanics.core.ScaledImage;
import fr.game.panel.AbstractUI;
import fr.game.mechanics.core.MessageBox;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class GameUI extends AbstractUI {

    private final HashMap<GameValueEnum, ScaledImage> bufferedIcons;
    GamePanel gamePanel;

    ScaledImage timeIcon;

    double playedTime;
    DecimalFormat decimalFormat;

    public GameUI(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;
        this.decimalFormat = new DecimalFormat("#0");
        this.timeIcon = new ScaledImage(GameValueEnum.HOURGLASS.getPath());
        this.bufferedIcons = new HashMap<GameValueEnum, ScaledImage>();
        for(GameValueEnum objectTypeEnum : GameValueEnum.values()){
            bufferedIcons.put(objectTypeEnum,new ScaledImage(objectTypeEnum.getPath(), AppVariables.tileSize/2, AppVariables.tileSize/2, AppVariables.tileSize/2, AppVariables.tileSize/2));
        }
    }

    public void draw(Graphics2D graphics2D){
        setUIFont(FontEnum.ARIAL,40,Color.WHITE);
        //PLAYER STATS
        drawPlayerUI();
        //TIMER
        graphics2D.drawImage(this.timeIcon.getBufferedImage(), AppVariables.tileSize*13, AppVariables.tileSize / 4, null);
        graphics2D.drawString(" : " + decimalFormat.format(this.playedTime), AppVariables.tileSize*14, AppVariables.tileSize);

        if (GameController.getInstance().getPanel().getPanelState() == PanelStateEnum.PAUSE) {
            MessageBox.getInstance().sendMessage(MessageTypeEnum.MENU_TEXT, "PAUSE", AppVariables.SCREEN_WIDTH/2, AppVariables.SCREEN_HEIGHT/2, true);
        }else  if (GameController.getInstance().getPanel().getPanelState() == PanelStateEnum.PLAY) {
            this.playedTime += (double) 1/ AppVariables.FPS;
        }

        drawDebug();
    }
    private void drawDebug(){
        if(AppConstants.DEBUG) {
            MessageBox.getInstance().sendMessage(MessageTypeEnum.MENU_TEXT, GameController.getInstance().getPanel().getPanelState().name(), AppVariables.SCREEN_WIDTH/2, AppVariables.tileSize, true);
        }
    }

    public void drawVictoryScreen(Graphics2D graphics2D) {

        String winningText = "Félicitations";
        setUIFont(FontEnum.ARIAL,80,Color.GREEN);
        graphics2D.setColor(Color.GREEN);
        int textLength = (int) graphics2D.getFontMetrics().getStringBounds(winningText, graphics2D).getWidth();
        int x = AppVariables.SCREEN_WIDTH /2 - textLength/2;
        int y = AppVariables.SCREEN_HEIGHT /2 - AppVariables.tileSize*3;
        graphics2D.drawString(winningText, x, y);

        String winningText3 = "Tu as trouvé le trésor en "+ decimalFormat.format(this.playedTime) +" secondes !";
        setUIFont(FontEnum.ARIAL,40,Color.GREEN);
        graphics2D.setColor(Color.YELLOW);
        int textLength3 = (int) graphics2D.getFontMetrics().getStringBounds(winningText3, graphics2D).getWidth();
        int x3 = AppVariables.SCREEN_WIDTH /2 - textLength3/2;
        int y3 = AppVariables.SCREEN_HEIGHT /2 + AppVariables.tileSize*3;
        graphics2D.drawString(winningText3, x3, y3);

    }
    private void drawPlayerUI() {
        int xStepInventory = AppVariables.tileSize/4;
        int yStepStat = AppVariables.tileSize;
        for (Map.Entry<GameValueEnum, Integer> inventoryEntry : GameController.getInstance().getPlayer().getStatus().getInventory()){

            switch (inventoryEntry.getKey()){
                case KEY:
                    graphics2D.drawImage(bufferedIcons.get(inventoryEntry.getKey()).getBufferedImage(), xStepInventory, 0, null);
                    graphics2D.drawString(String.valueOf(inventoryEntry.getValue()), xStepInventory + AppVariables.tileSize, AppVariables.tileSize);
                    xStepInventory += 2* AppVariables.tileSize;
                    break;
                case RED_KEY:
                    graphics2D.drawImage(bufferedIcons.get(inventoryEntry.getKey()).getBufferedImage(), xStepInventory, 0, null);
                    graphics2D.drawString(String.valueOf(inventoryEntry.getValue()), xStepInventory + AppVariables.tileSize, AppVariables.tileSize);
                    xStepInventory += 2* AppVariables.tileSize;
                    break;
                case HEALTH:
                    int x = AppVariables.tileSize/4;
                    for(int i = 0; i < GameController.getInstance().getPlayer().getStatus(GameValueEnum.MAX_LIFE); i+=2){
                        if(i+2 <= GameController.getInstance().getPlayer().getStatus(GameValueEnum.HEALTH)){
                            this.graphics2D.drawImage(bufferedIcons.get(GameValueEnum.HEALTH).getBufferedImage(0),x,yStepStat,null);
                        }else if(i+1 <= GameController.getInstance().getPlayer().getStatus(GameValueEnum.HEALTH)){
                            this.graphics2D.drawImage(bufferedIcons.get(GameValueEnum.HEALTH).getBufferedImage(1),x,yStepStat,null);
                        }else{
                            this.graphics2D.drawImage(bufferedIcons.get(GameValueEnum.HEALTH).getBufferedImage(2),x,yStepStat,null);
                        }
                        x += AppVariables.tileSize;
                    }
                    yStepStat += AppVariables.tileSize;
                    break;
                case MAX_LIFE:
                    if(AppConstants.DEBUG) {
                        graphics2D.drawImage(bufferedIcons.get(inventoryEntry.getKey()).getBufferedImage(), AppVariables.tileSize / 4, yStepStat, null);
                        graphics2D.drawString(GameController.getInstance().getPlayer().getStatus(GameValueEnum.HEALTH) + "/" + inventoryEntry.getValue(),
                                +AppVariables.tileSize + AppVariables.tileSize / 4, yStepStat + AppVariables.tileSize);
                        yStepStat += AppVariables.tileSize;
                    }
                    break;
                case CHEST:
                    break;
                default:
                    graphics2D.drawImage(bufferedIcons.get(inventoryEntry.getKey()).getBufferedImage(), AppVariables.tileSize/4, yStepStat, null);
                    graphics2D.drawString(String.valueOf(inventoryEntry.getValue()), + AppVariables.tileSize+ AppVariables.tileSize/4, yStepStat + AppVariables.tileSize);

                    yStepStat += AppVariables.tileSize;
                    break;
            }
        }

    }
}
