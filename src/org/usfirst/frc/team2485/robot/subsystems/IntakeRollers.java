package org.usfirst.frc.team2485.robot.subsystems;

import org.usfirst.frc.team2485.robot.commands.RollersOn;

import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakeRollers extends Subsystem{

	@Override
	protected void initDefaultCommand() {
		
		Scheduler.getInstance().add(new RollersOn(0.0, 0.0));
		
	}
	
	

}
