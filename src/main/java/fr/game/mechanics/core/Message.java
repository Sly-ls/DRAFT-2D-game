package fr.game.mechanics.core;


import fr.game.constants.AppVariables;
import fr.game.constants.core.FontEnum;
import fr.game.constants.core.MessageTypeEnum;
import fr.game.panel.AbstractUI;
import javafx.util.Pair;

import java.awt.*;

import static fr.game.panel.AbstractUI.FONT_SIZE_DEFAULT;
import static fr.game.panel.AbstractUI.PADDING_DEFAULT;

public class Message{

    public String uuid;
    public MessageTypeEnum type;
    public String text = null;
    public FontEnum font = FontEnum.ARIAL;
    public int fontSize = FONT_SIZE_DEFAULT;
    public Color fontColor = Color.RED;
    public boolean selected;
    public int padding = PADDING_DEFAULT;
    public Color subWindowColor  = new Color(46, 194, 165, 50);
    public Color subWindowFontColor  = new Color(255,255,255, 255);
    public Color subWindowBorderColor  = new Color(255,255,255, 255);
    //FIXME instance by default here to mask an NPE somewhere
    public Pair<Integer, Integer> textBound = new Pair<>(1,1);
    public Pair<Integer, Integer> screenCoordinate;
    public Integer fpsCounter = 0;
    public Integer fpsCounterMax = AppVariables.messageStayFor;

    public void refreshMessage(AbstractUI abstractUI, Graphics2D graphics2D){
        abstractUI.setUIFont(font,fontSize, fontColor);
        refreshMessage(graphics2D, screenCoordinate.getKey(), screenCoordinate.getValue());
        setDuration();
    }
    public void refreshMessage(Graphics2D graphics2D, int screenX, int screenY){
        setTextBound(graphics2D,text);
        setScreenCoordinate(screenX, screenY);
        setDuration();
    }
    public Message(String uuid, MessageTypeEnum messageType,
                   String text, int screenX, int screenY, boolean selected) {
        this.uuid = uuid;
        this.type = messageType;
        this.selected = selected;
        setText(text);
        setScreenCoordinate(screenX, screenY);
        setDuration();
    }

    private void setText(String text){
        if(text == null || text.equalsIgnoreCase("")){
            this.text = uuid;
        }else{
            this.text = text;
        }
    }
    private void setTextBound(Graphics2D graphics2D, String text){
        textBound= new Pair<> ((int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth()
        ,(int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getHeight());
    }
    private void setScreenCoordinate(int screenX, int screenY){

        switch (type){
            case MESSAGE_TO_PLAYER:
                //we get screenX_Y fixed for message type, centered on message
                if(screenY -  textBound.getValue()< 0 || screenY > AppVariables.SCREEN_HEIGHT + textBound.getValue()){
                    screenY = AppVariables.SCREEN_HEIGHT /2 - textBound.getValue()/2 - AppVariables.tileSize/2;
                }
                if(screenX -  textBound.getKey()< 0 || screenX > AppVariables.SCREEN_WIDTH + textBound.getKey()){
                    screenX = AppVariables.SCREEN_WIDTH /2 - textBound.getKey()/2 - AppVariables.tileSize/2;
                }
                break;
            case DIALOG:
                //we get screenX_Y fixed for message type
                screenX = AppVariables.tileSize;
                screenY = AppVariables.tileSize*2;
                break;
            case WORLD_EVENT:
                /*
                /relative to world coordinate, screenX_Y will be caculated during drax phase
                 */
            case MENU_TEXT:
            case NANO_TEXT:
                break;

        }

        this.screenCoordinate = new Pair<>(screenX,screenY);
    }
    private void setDuration(){

        switch (type){
            case MESSAGE_TO_PLAYER:
            case WORLD_EVENT:
                this.fpsCounterMax= AppVariables.messageStayFor*2;
                break;
            case MENU_TEXT:
                this.fpsCounterMax= AppVariables.FPS/8;
                break;
            case NANO_TEXT:
                this.fpsCounterMax=1;
                break;
            case DIALOG:
                this.fpsCounterMax= AppVariables.FPS/10;
                break;
        }
    }
    public Message(String uuid, MessageTypeEnum messageType,
                   String text,
                   FontEnum font, int fontSize, Color fontColor,
                   boolean selected, int padding,
                   int screenX, int screenY ){
        this.uuid = uuid;
        this.type = messageType;
        this.font = font;
        this.fontColor = fontColor;
        this.fontSize = fontSize;
        this.selected = selected;
        this.padding = Math.max(padding, 0);
        setText(text);
        setScreenCoordinate(screenX, screenY);
    }
}

