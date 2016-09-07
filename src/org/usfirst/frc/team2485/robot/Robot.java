
package org.usfirst.frc.team2485.robot;

import javax.sound.midi.Sequencer;

import org.usfirst.frc.team2485.robot.commands.DriveTo;
import org.usfirst.frc.team2485.robot.commands.SpinUpShooter;
import org.usfirst.frc.team2485.util.ConstantsIO;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Robot extends IterativeRobot {

	public void robotInit() {
		ConstantsIO.init();
		RobotMap.init();
		OI.init();
		RobotMap.updateConstants();
	}

	public void disabledInit(){
		RobotMap.driveTrain.reset();

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		ConstantsIO.init();
		RobotMap.updateConstants();


		RobotMap.gyro.reset();
		Scheduler.getInstance().add(new SpinUpShooter(85));

		//        BlockingCommandFactory.advanceTo100InchReallyBadlyPlzNeverUseThis();

		RobotMap.driveTrain.reset();
//		Scheduler.getInstance().add(new DriveTo(200, 50, 0, 0)); 
	}


	public void autonomousPeriodic() {
		Scheduler.getInstance().run();	
		System.out.println("ROBOT:SHOOTER ENC: " + RobotMap.shooterEnc.getRate());
		
	}

	public void teleopInit() {
		ConstantsIO.init();
		RobotMap.updateConstants();
	}

	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	public void testInit(){
		ConstantsIO.init();
		RobotMap.updateConstants();
	}

	public void testPeriodic() {
		
		if (RobotMap.pressureSwitch.get()) {
			RobotMap.compressorSpike.set(Relay.Value.kOff);
		} else {
			RobotMap.compressorSpike.set(Relay.Value.kForward);
		}
		
	}
}
