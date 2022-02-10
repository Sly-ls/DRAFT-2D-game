package fr.game.mechanics.core;

import fr.game.constants.core.FontEnum;
import fr.game.constants.core.MessageTypeEnum;
import fr.game.constants.panel.PanelStateEnum;
import fr.game.panel.game.GameController;

import java.awt.*;
import java.util.*;
import java.util.List;

public class MessageBox {
    final int max_Object = 99;
    protected HashMap<MessageTypeEnum, List<Message>> messages = new HashMap<>();

    static MessageBox instance;

    private MessageBox(){
    }
    public static MessageBox getInstance() {
        if(instance==null){
            instance = new MessageBox();
        }
        return instance;
    }

    public List<Message> getMessageToDraw() {
        List<Message> messagesToReturn = new ArrayList<>();
        for (Map.Entry<MessageTypeEnum, List<Message>> entryMessages : this.messages.entrySet()){
            messagesToReturn.addAll(entryMessages.getValue());
        }
        return messagesToReturn;
    }

    public void addOrRemove(Message newObject, boolean add){
        List toUpdate = messages.getOrDefault(newObject.type, new ArrayList<>());
        if(add){
            toUpdate.add(newObject);
        }else{
            toUpdate.remove(newObject);
        }
        messages.put(newObject.type, toUpdate);
    }


    public void sendMessage(MessageTypeEnum messageType,
                            String text,
                            FontEnum font, Color fontColor, int fontSize,
                            boolean selected, int padding,
                            int messageX, int messageY){

        Message message = new Message(UUID.randomUUID().toString(), messageType,
                text,
                font, fontSize, fontColor,
                selected, padding,
                messageX, messageY);
        MessageBox.getInstance().addOrRemove(message, true);
        if(message.type == MessageTypeEnum.DIALOG){
            GameController.getInstance().getPanel().setPanelState(PanelStateEnum.DIALOG);
        }
    }


    public void sendMessage(MessageTypeEnum messageType,
                            String text, int screenX, int screenY, boolean selected){
        Message message = new Message(UUID.randomUUID().toString(), messageType,
                text, screenX, screenY,selected);
        MessageBox.getInstance().addOrRemove(message, true);
        if(message.type == MessageTypeEnum.DIALOG){
            GameController.getInstance().getPanel().setPanelState(PanelStateEnum.DIALOG);
        }
    }

    public List<Message> getMessages(MessageTypeEnum valueType) {
        return messages.getOrDefault(valueType,new ArrayList<>());
    }
    public Set<Map.Entry<MessageTypeEnum, List<Message>>> getMessages() {
        return messages.entrySet();
    }

}
