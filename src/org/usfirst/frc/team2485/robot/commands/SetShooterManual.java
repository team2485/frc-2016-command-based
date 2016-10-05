package org.usfirst.frc.team2485.robot.commands;

import org.usfirst.frc.team2485.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
/**
 * Never Finishes, should always be wrapped in CommandTimeout
 */
public class SetShooterManual extends Command {
	
	private double pwm;
	
	public SetShooterManual(double pwm) {
		this.pwm = pwm;
	}
	
	@Override
	protected void initialize() { }

	@Override
	protected void execute() {
		RobotMap.shooter.setManual(pwm);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		RobotMap.shooter.disableShooter();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
