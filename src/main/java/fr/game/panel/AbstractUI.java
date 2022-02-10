package fr.game.panel;

import fr.game.constants.AppVariables;
import fr.game.constants.core.FontEnum;
import fr.game.constants.core.MessageTypeEnum;
import fr.game.constants.panel.PanelStateEnum;
import fr.game.mechanics.controller.game.Camera;
import fr.game.mechanics.core.Message;
import fr.game.mechanics.core.MessageBox;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;


public abstract class AbstractUI {

    public static final int PADDING_DEFAULT = 20;
    public static final int FONT_SIZE_DEFAULT = 30;
    protected AbstractPanel currentPanel;
    protected Graphics2D graphics2D;
    Map<FontEnum, Font> bufferedFonts = new HashMap<>();

    public AbstractUI(AbstractPanel currentPanel) {
        this.currentPanel = currentPanel;
        try {
            for (FontEnum font : FontEnum.values()) {
                InputStream is = getClass().getResourceAsStream(font.getPath());
                this.bufferedFonts.put(font, Font.createFont(Font.TRUETYPE_FONT, is));
            }
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(Graphics2D graphics2D){
        this.graphics2D = graphics2D;
    }


    public void setUIFont(FontEnum font, int size, Color color){
        setFont(font);
        setFontColor(color);
        setSize(size);
        this.graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }
    public void drawMessage(){
        List<Message> messages = MessageBox.getInstance().getMessageToDraw();
        Set<Message> messagesToRemove = new HashSet();
        for (Message message : messages){
            message.fpsCounter++;

            if ( (message.type != MessageTypeEnum.DIALOG
                    &&message.fpsCounter > message.fpsCounterMax)
                    || (message.type == MessageTypeEnum.DIALOG
                    && this.currentPanel.getPanelState() != PanelStateEnum.DIALOG)
            ) {
                messagesToRemove.add(message);
            }else if (message.type != MessageTypeEnum.DIALOG
                    || (message.type == MessageTypeEnum.DIALOG
                    && this.currentPanel.getPanelState() == PanelStateEnum.DIALOG)
            ){
                drawMessage(message);
            }
        }
        for (Message message : messagesToRemove){
            MessageBox.getInstance().addOrRemove(message,false);
        }

    }

    private void drawMessage(Message message){
        message.refreshMessage( this, this.graphics2D);
        int x = message.screenCoordinate.getKey();
        int y = message.screenCoordinate.getValue();
        switch (message.type){
            case WORLD_EVENT:
                int textLenght = (int) graphics2D.getFontMetrics().getStringBounds(message.text, graphics2D).getWidth();
                x = Camera.getInstance().getScreenX(message.screenCoordinate.getKey());
                x = x - textLenght/2;
                y = Camera.getInstance().getScreenY(message.screenCoordinate.getValue());
                y -= AppVariables.tileSize/2;
                break;
        }

        drawTextSelection(message,
                x,
                y
        );
        if(message.selected){
            this.graphics2D.setColor(message.subWindowFontColor);
        }else{
            this.graphics2D.setColor(message.fontColor);
        }
        graphics2D.drawString(message.text, x, y);
    }

    private void drawTextSelection(Message message, int x, int y) {
        //FIXME to rework, it's not doing the job properply; the perimeter to long on the rigth
        if(message.selected) {
            switch (message.type){
                case MENU_TEXT:
                case WORLD_EVENT:
                case MESSAGE_TO_PLAYER:
                case NANO_TEXT:
                    y -= message.textBound.getValue()/2 + message.padding;
                    x -= message.padding;
                    drawSubWindow(x, y, message.textBound.getKey() + message.padding*2, message.padding+message.textBound.getValue()
                            ,message.subWindowColor
                            ,message.subWindowBorderColor);
                    break;
                case DIALOG:
                    y -= message.textBound.getValue()/2 + message.padding;
                    x -= message.padding;
                    drawSubWindow(x, y, AppVariables.SCREEN_WIDTH - x*2, AppVariables.tileSize*4, new Color(0,0,0,255)
                            , new Color(255,255,255,255));
                    break;

            }
        }
    }

    protected static int getXForCenteredText(Graphics2D graphics2D, String text){
        int textLenght = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        int x = AppVariables.SCREEN_WIDTH /2 - textLenght/2;
        return  x ;
    }
    protected static int getYForCenteredText(Graphics2D graphics2D, String text){
        int textHeight = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getHeight();
        int y = AppVariables.SCREEN_HEIGHT /2 - textHeight/2;
        return  y ;
    }
    public void drawSubWindow(int x, int y, int width, int height, Color subWindowColor, Color subWindowBorderColor){
        this.graphics2D.setColor(subWindowColor);
        this.graphics2D.fillRoundRect(x,y,width,height, AppVariables.tileSize/2, AppVariables.tileSize/2);

        this.graphics2D.setColor(subWindowBorderColor);
        this.graphics2D.setStroke(new BasicStroke(5));
        this.graphics2D.drawRoundRect(x,y,width,height, AppVariables.tileSize/2, AppVariables.tileSize/2);;

    }

    private void setFont(FontEnum font){
        if(font == null) {
            this.graphics2D.setFont(this.bufferedFonts.get(FontEnum.ARIAL));
        }else{
            this.graphics2D.setFont(this.bufferedFonts.get(font));
        }
    }
    private void setFontColor(Color color){
        if(color == null) {
            this.graphics2D.setColor(Color.WHITE);
        }else{
            this.graphics2D.setColor(color);
        }
    }
    private void setSize(int size){
        if(size < 0){
            size = 100;
            setFontColor(Color.RED);
        }
        this.graphics2D.setFont(this.graphics2D.getFont().deriveFont(Font.PLAIN, size));
    }

    public Graphics2D getGraphics2D() {
        return graphics2D;
    }


}
