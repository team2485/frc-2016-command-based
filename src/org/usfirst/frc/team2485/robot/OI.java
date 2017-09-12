package org.usfirst.frc.team2485.robot;

import org.usfirst.frc.team2485.robot.commandGroups.AutoAimAndPrep;
import org.usfirst.frc.team2485.robot.commandGroups.Hat;
import org.usfirst.frc.team2485.robot.commandGroups.IntakeBall;
import org.usfirst.frc.team2485.robot.commandGroups.PrepForBatterShot;
import org.usfirst.frc.team2485.robot.commandGroups.PrepForLongShot;
import org.usfirst.frc.team2485.robot.commandGroups.PrepForLowBar;
import org.usfirst.frc.team2485.robot.commandGroups.ShakeBoulderStager;
import org.usfirst.frc.team2485.robot.commandGroups.ShootHighGoal;
import org.usfirst.frc.team2485.robot.commandGroups.ShootLowGoal;
import org.usfirst.frc.team2485.robot.commands.IntakeArmSetSetpoint;
import org.usfirst.frc.team2485.robot.commands.RollersOn;
import org.usfirst.frc.team2485.robot.commands.SetBoulderStager;
import org.usfirst.frc.team2485.robot.commands.SetShooterManual;
import org.usfirst.frc.team2485.robot.commands.SpinUpShooter;
import org.usfirst.frc.team2485.robot.subsystems.BoulderStager.StagerPosition;
import org.usfirst.frc.team2485.robot.subsystems.IntakeArm;
import org.usfirst.frc.team2485.util.CommandTimeout;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    public static Joystick xBox;
    public static Joystick joystick;
    
    public static void init(){
    	xBox = new Joystick(0);
    	joystick = new Joystick(1);
    	
    	Button BtnHat = new Button() {
			
			@Override
			public boolean get() {
				return joystick.getPOV(0) != -1;
			}
		};
		
		BtnHat.whileHeld(new Hat());
		BtnHat.whenReleased(new SetBoulderStager(StagerPosition.NEUTRAL));
    	
    	Button BtnA = new JoystickButton(xBox, XBOX_BTN_A);
        
        Button BtnB = new JoystickButton(xBox, XBOX_BTN_B);
        
        Button BtnX = new JoystickButton(xBox, XBOX_BTN_X);
        
        Button BtnY = new JoystickButton(xBox, XBOX_BTN_Y);
        
        Button BtnLBumper = new JoystickButton(xBox, XBOX_LBUMPER);
        
        Command autoAim = new AutoAimAndPrep();
        
        BtnA.whenPressed(autoAim);
        
        BtnB.whenPressed(new PrepForLowBar());
        
        BtnX.whenPressed(new IntakeBall());
        
        BtnY.whenPressed(new CommandTimeout(new SetShooterManual(-1), 0.5));
        
        BtnLBumper.cancelWhenPressed(autoAim);
        
        
        Button Btn1 = new JoystickButton(joystick, 1);
        
        Button Btn2 = new JoystickButton(joystick, 2);
        
        Button Btn3 = new JoystickButton(joystick, 3);
        
        Button Btn4 = new JoystickButton(joystick, 4);
        
        Button Btn5 = new JoystickButton(joystick, 5);
        
        Button Btn6 = new JoystickButton(joystick, 6);
        
        Button Btn7 = new JoystickButton(joystick, 7);
        
        Button Btn8 = new JoystickButton(joystick, 8);
        
        Button Btn9 = new JoystickButton(joystick, 9);
        
        Button Btn10 = new JoystickButton(joystick, 10);
        
        Button Btn11 = new JoystickButton(joystick, 11);
        
        Button Btn12 = new JoystickButton(joystick, 12);
        
        Btn1.whenPressed(new ShootHighGoal());
        
        Btn2.whenPressed(new ShootLowGoal());
        
        Btn3.whenPressed(new ShakeBoulderStager());
        
        Btn4.whenPressed(new PrepForBatterShot());
        
        Btn5.whenPressed(new SpinUpShooter(0));
        
        Btn6.whenPressed(new PrepForLongShot());
        
        Btn7.whenPressed(new RollersOn(0, 0));
        
        Btn8.whenPressed(new IntakeArmSetSetpoint(IntakeArm.FLOOR_POSITION));
        
        Btn9.whenPressed(new IntakeArmSetSetpoint(IntakeArm.LOW_NO_INTAKE_POSITION));
        
        Btn10.whenPressed(new IntakeArmSetSetpoint(IntakeArm.INTAKE_POSITION));
        
        Btn11.whenPressed(new IntakeArmSetSetpoint(IntakeArm.PORTCULLIS_POSITION));
        
        Btn12.whenPressed(new IntakeArmSetSetpoint(IntakeArm.FULL_UP_POSITION));
        
       
    }
    // Button button = new JoystickButton(stick, buttonNumber);
    public static final int XBOX_BTN_A = 1;
  
    public static final int XBOX_BTN_B = 2;
  
    public static final int XBOX_BTN_X = 3;
  
    public static final int XBOX_BTN_Y = 4;
    
    public static final int XBOX_LBUMPER = 5;
    
    public static final int XBOX_RBUMPER = 6;
    
   
    
    public static final int XBOX_LTRIGGER = 2;
    
    public static final int XBOX_RTRIGGER = 3;
    
 
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
   
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
}

