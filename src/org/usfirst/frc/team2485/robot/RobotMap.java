package org.usfirst.frc.team2485.robot;

import org.usfirst.frc.team2485.util.InvertedAbsoluteEncoder;
import org.usfirst.frc.team2485.util.SpeedControllerWrapper;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.VictorSP;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	public static VictorSP intakeArmVictorSP = new VictorSP(8);
    public static SpeedControllerWrapper intakeArmSC= new SpeedControllerWrapper(intakeArmVictorSP);
    public static InvertedAbsoluteEncoder intakeAbsEncoder = new InvertedAbsoluteEncoder(
			new AnalogPotentiometer(0));
    
	
}
