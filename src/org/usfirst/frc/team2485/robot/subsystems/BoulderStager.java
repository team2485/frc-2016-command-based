package org.usfirst.frc.team2485.robot.subsystems;

import org.usfirst.frc.team2485.robot.RobotMap;
import org.usfirst.frc.team2485.robot.commands.SetBoulderStager;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
/**
 * 
 * @author Sahana Kumar
 *
 */
public class BoulderStager extends Subsystem {
	private Solenoid solenoid1, solenoid2;

	private StagerPosition position;

	public enum StagerPosition {
		INTAKE, NEUTRAL, SHOOTING;
	}

	public BoulderStager() {
		solenoid1 = RobotMap.boulderStagerSolenoid1;
		solenoid2 = RobotMap.boulderStagerSolenoid2;

		setPosition(StagerPosition.NEUTRAL);

	}

	public void setPosition(StagerPosition position) {
		
		this.position = position;

		switch (position) {

		case INTAKE:
			solenoid1.set(false);
			solenoid2.set(true);
			break;

		case SHOOTING:
			solenoid1.set(true);
			solenoid2.set(false);
			break;

		case NEUTRAL:
			solenoid1.set(false);
			solenoid2.set(false);
			break;

		}

	}

	public StagerPosition getPosition() {
		return position;
	}

	public String getPositionString() {
		if (position == StagerPosition.INTAKE) {
			return "Intake";
		} else if (position == StagerPosition.NEUTRAL) {
			return "Neutral";
		} else {
			return "Shooting";
		}
	}

	@Override
	protected void initDefaultCommand() {
	}
}
