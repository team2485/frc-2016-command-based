package org.usfirst.frc.team2485.robot.commands;

import org.usfirst.frc.team2485.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class SetQuickTurn extends Command{
	
	private boolean quickTurn;
	
	public SetQuickTurn(boolean quickTurn){
		requires(RobotMap.driveTrain);
		this.quickTurn = quickTurn;
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		RobotMap.driveTrain.setQuickTurn(quickTurn);
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
