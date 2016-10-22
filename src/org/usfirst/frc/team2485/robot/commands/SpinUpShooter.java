package org.usfirst.frc.team2485.robot.commands;

import org.usfirst.frc.team2485.robot.RobotMap;
import org.usfirst.frc.team2485.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class SpinUpShooter extends Command {
	private double rpm;
	
	public SpinUpShooter(double rpm) {
		this.rpm = rpm;
	}


	
	@Override
	protected void end() { }

	@Override
	protected void execute() { }

	@Override
	protected void initialize() {
		
		if (rpm == Shooter.RPS_BATTER_SHOT) {
			Shooter.RPS_BATTER_SHOT = SmartDashboard.getNumber("Batter Shot RPS");
			rpm = Shooter.RPS_BATTER_SHOT;
		}
		
		if (rpm == Shooter.RPS_LONG_SHOT) {
			Shooter.RPS_LONG_SHOT = SmartDashboard.getNumber("Long Shot RPS");
			rpm = Shooter.RPS_LONG_SHOT;
		}
		
		RobotMap.shooter.setTargetSpeed(rpm);
	}

	@Override
	protected void interrupted() { }

	@Override
	protected boolean isFinished() {
		return true;
	}
}
