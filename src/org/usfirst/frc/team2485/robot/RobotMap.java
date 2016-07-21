package org.usfirst.frc.team2485.robot;

import org.usfirst.frc.team2485.robot.subsystems.IntakeArm;
import org.usfirst.frc.team2485.robot.subsystems.IntakeRollers;
import org.usfirst.frc.team2485.util.InvertedAbsoluteEncoder;
import org.usfirst.frc.team2485.util.SpeedControllerWrapper;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Victor;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
	
	public static int scIntakeRollerPort1 = 1;
	public static int scIntakeRollerPort2 = 2;
	public static int scIntakeArmPort1 = 3;
	
	public static IntakeRollers intakeRollers = new IntakeRollers();
	public static IntakeArm intakeArm = new IntakeArm();
	
	public static Victor intakeRollerLateralsc = new Victor(scIntakeRollerPort1);
	public static Victor intakeRollerIntakesc = new Victor(scIntakeRollerPort2);
	public static Victor intakeArmVictor = new Victor(scIntakeArmPort1);
	
	public static SpeedControllerWrapper intakeArmSC = new SpeedControllerWrapper(intakeArmVictor);
	
	public static InvertedAbsoluteEncoder intakeAbsEncoder = new InvertedAbsoluteEncoder(new AnalogPotentiometer(0));
}
