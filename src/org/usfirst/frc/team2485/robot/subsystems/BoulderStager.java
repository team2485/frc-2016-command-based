package org.usfirst.frc.team2485.robot.subsystems;

import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team2485.robot.RobotMap;
import org.usfirst.frc.team2485.robot.commands.SetBoulderStager;
import org.usfirst.frc.team2485.robot.commands.ShakeBoulderStager;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
/**
 * 
 * @author Sahana Kumar
 *
 */
public class BoulderStager extends Subsystem implements Loggable {
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

	public Map<String, Object> getLogData() {

		Map<String, Object> logData = new HashMap<String, Object>();

		logData.put("Name", "BoulderStager");
		logData.put("Position", getPositionString());

		return logData;
	}

	@Override
	public DebugLogger getLogger() {
		return null;
	}

	@Override
	public DebugLogger initLogger(Context arg0) {
		return null;
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stu
		
		setDefaultCommand(new SetBoulderStager(StagerPosition.NEUTRAL));
	}
}
