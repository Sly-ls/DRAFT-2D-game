package fr.game.main.utils;

public class NumberUtils {

    public static int incrementCommand( int newValue, int originalValue, int maxValue, boolean loop){
        int lastValue = originalValue;
        newValue+=originalValue;
        if (newValue > maxValue){
            if(loop) {
                newValue = 0;
            }else{
                newValue = maxValue;
            }
        }else if(newValue < 0){
            if(loop) {
                newValue = maxValue;
            }else{
                newValue = 0;
            }
        }
        return newValue;
    }


}
