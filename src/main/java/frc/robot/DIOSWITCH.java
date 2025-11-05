package frc.robot;
    
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;

public class DIOSWITCH extends TimedRobot{

    private final DigitalInput switchInput = new DigitalInput(0);

    private final PWMVictorSPX motor = new PWMVictorSPX(0);
    
    @Override
    public void teleopPeriodic() {
        boolean switchPressed = switchInput.get();


        if (switchPressed){
            motor.set(0.5);
        }else{
            motor.set(0);
        }
    }
}