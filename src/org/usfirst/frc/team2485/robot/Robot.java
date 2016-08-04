
package org.usfirst.frc.team2485.robot;

import org.usfirst.frc.team2485.robot.commands.DriveTo;
import org.usfirst.frc.team2485.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team2485.util.ConstantsIO;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Robot extends IterativeRobot {

    public void robotInit() {
		OI.init();
		ConstantsIO.init();
		RobotMap.init();
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
    	if(Robot.isSimulation()){
    		 RobotMap.simulationGyro.resetAccumulator();
    	} else {
    		RobotMap.ahrs.reset();
    	}
//        BlockingCommandFactory.advanceTo100InchReallyBadlyPlzNeverUseThis();
    	RobotMap.driveTrain.reset();
    	Scheduler.getInstance().add(new DriveTo(200, 50, 0, 0)); 
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();	
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
        LiveWindow.run();
//        RobotMap.driveTrain.setLeftRight(0.3, 0.3);
        RobotMap.driveTrain.setLeftRightVelocity(10, 10);
        System.out.println("Left"+RobotMap.leftDriveEnc.getRate());
        System.out.println("Right"+RobotMap.rightDriveEnc.getRate());
    }
}
