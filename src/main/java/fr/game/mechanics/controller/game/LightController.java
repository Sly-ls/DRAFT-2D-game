package fr.game.mechanics.controller.game;

import fr.game.constants.AppConstants;
import fr.game.constants.AppVariables;
import fr.game.constants.game.DayDescriptorEnum;
import fr.game.main.utils.NumberUtils;

import java.awt.*;

public class LightController {
    //SINGLETON
    static LightController instance;
    private LightController() {
        this.dayDescriptorEnum = DayDescriptorEnum.DAY_1;
        this.nbOfCounterInADay = AppVariables.FPS*60*dayDescriptorEnum.getDaylLengthInMinute();
        this.timeOfDusk = (dayDescriptorEnum.getDuskHour() * nbOfCounterInADay)/24;
        this.timeOfDawn = (dayDescriptorEnum.getDawnHour() * nbOfCounterInADay)/24;
        int timeToDawn = (nbOfCounterInADay/24) * dayDescriptorEnum.getDawnSpeed();
        int timeToDusk = (nbOfCounterInADay/24) * dayDescriptorEnum.getDuskSpeed();
        this.tickForDawn = timeToDawn/dayTransparencyMax;
        this.tickForDusk = timeToDusk/dayTransparencyMax;
        if(AppConstants.DEBUG && AppConstants.DEBUG_WITH_DAYLIGHT) {
            timeCounter = tickForDusk;
        }else{
            timeCounter = nbOfCounterInADay/2;
        }
    }

    public static LightController getInstance() {
        if(instance == null){
            instance = new LightController();
        }
        return instance;
    }
    DayDescriptorEnum dayDescriptorEnum;
    int sunset = 0;
    int timeCounter = 0;
    int dayCounter = 0;
    int dayTransparency = 0;
    int dayTransparencyMax = 245;
    int dayTransparencyMin = 0;
    int timeOfDusk = 0;
    int timeOfDawn = 0;
    int timeToDawn = 0;
    int timeToDusk = 0;
    int tickForDusk = 0;
    int tickForDawn = 0;
    int nbOfCounterInADay;

    public void update(){

            timeCounter++;
            sunset++;
            StringBuffer sbTest = new StringBuffer();
            if(timeCounter > timeOfDawn && timeCounter < timeOfDusk && dayTransparency > dayTransparencyMin){
                if(sunset >= tickForDawn){
                    sunset=0;
                    dayTransparency = NumberUtils.incrementCommand(-1,dayTransparency,dayTransparencyMax,false);
                    if(AppConstants.DEBUG) {
                        System.out.println(sbTest.append(this.getClass()).append(" time is DAWN ").append(dayTransparency));
                    }
                }else{
                    if(AppConstants.DEBUG) {
                        System.out.println(sbTest.append(this.getClass()).append(" TIC ").append(dayTransparency));
                    }
                    sunset++;
                }
            }else if(((timeCounter < timeOfDawn && timeCounter < timeOfDusk)
                    || (timeCounter > timeOfDawn && timeCounter > timeOfDusk))
                    && dayTransparency < dayTransparencyMax){
                if(sunset >= tickForDusk){
                    sunset=0;
                    dayTransparency = NumberUtils.incrementCommand(1,dayTransparency,dayTransparencyMax,false);
                    if(AppConstants.DEBUG) {
                        System.out.println(sbTest.append(this.getClass()).append(" time is DUSK ").append(dayTransparency));
                    }
                }else{
                    if(AppConstants.DEBUG) {
                        System.out.println(this.getClass() + " TAC " + dayTransparency);
                    }
                    sunset++;
                }
            }
        if(timeCounter >= nbOfCounterInADay){
            timeCounter =0;
            dayCounter++;
        }

    }

    private void showDebug(){

    }
    public void drawNaturalLight(Graphics2D graphics2D) {

        if (!AppConstants.DEBUG ||(AppConstants.DEBUG &&AppConstants.DEBUG_WITH_DAYLIGHT)) {
            graphics2D.setColor(new Color(0, 0, 0, dayTransparency));
            graphics2D.fillRect(0,
                    0,
                    AppVariables.SCREEN_WIDTH,
                    AppVariables.SCREEN_HEIGHT);
        }
    }

}
