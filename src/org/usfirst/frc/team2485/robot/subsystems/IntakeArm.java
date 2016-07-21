package org.usfirst.frc.team2485.robot.subsystems;

import org.usfirst.frc.team2485.robot.RobotMap;
import org.usfirst.frc.team2485.util.ConstantsIO;
import org.usfirst.frc.team2485.util.InvertedAbsoluteEncoder;
import org.usfirst.frc.team2485.util.SpeedControllerWrapper;
import org.usfirst.frc.team2485.util.WarlordsPIDController;

import edu.wpi.first.wpilibj.command.Subsystem;
import src.org.usfirst.frc.team2485.subsystems.PIDController;

public class IntakeArm extends Subsystem{
	
	private SpeedControllerWrapper  armSpeedControllerWrapper;
	
	private InvertedAbsoluteEncoder absEncoder;
	
	private WarlordsPIDController armPID;
	
	public static final double ABSOLUTE_TOLERANCE = 0.01;
	
	public static double FLOOR_POSITION = 0.122,

			LOW_NO_INTAKE_POSITION = (FLOOR_POSITION + 0.06) % 1,
					INTAKE_POSITION = (FLOOR_POSITION + 0.09) % 1,
					PORTCULLIS_POSITION = (FLOOR_POSITION + 0.261) % 1,
					FULL_UP_POSITION = (FLOOR_POSITION + 0.320) % 1;
	
	
	public IntakeArm() {
		armSpeedControllerWrapper = RobotMap.intakeArmSC;
		
		this.absEncoder = RobotMap.intakeAbsEncoder;
		
		armPID = new WarlordsPIDController(ConstantsIO.kP_IntakeArm,
				ConstantsIO.kI_IntakeArm, ConstantsIO.kD_IntakeArm, absEncoder,
				armSpeedControllerWrapper, 00.0100);
		armPID.setAbsoluteTolerance(ABSOLUTE_TOLERANCE);

		armPID.setInputRange(0.0, 1.0);
		armPID.setContinuous();

		armPID.setOutputRange(-0.22, 0.55);
		
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
