package org.usfirst.frc.team2485.robot;

import org.usfirst.frc.team2485.robot.commandGroups.ShakeBoulderStager;
import org.usfirst.frc.team2485.robot.commandGroups.ShootHighGoal;
import org.usfirst.frc.team2485.robot.commandGroups.ShootLowGoal;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

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
    	
    	
    	Button BtnA = new JoystickButton(xBox, XBOX_BTN_A);
        
        Button BtnB = new JoystickButton(xBox, XBOX_BTN_B);
        
        Button BtnX = new JoystickButton(xBox, XBOX_BTN_X);
        
        Button BtnY = new JoystickButton(xBox, XBOX_BTN_Y);
        
        Button Btn1 = new JoystickButton(joystick, 1);
        
        Button Btn2 = new JoystickButton(joystick, 2);
        
        Button Btn3 = new JoystickButton(joystick, 3);
        
        Button Btn4 = new JoystickButton(joystick, 4);
        
        Button Btn5 = new JoystickButton(joystick, 5);
        
        Button Btn6 = new JoystickButton(joystick, 6);
        
        
        Btn1.whenPressed(new ShootHighGoal());
        
        Btn2.whenPressed(new ShootLowGoal());
        
        Btn3.whenPressed(new ShakeBoulderStager());
        
       // Btn4.whenPressed(new );
        
       // Btn5.whenPressed(new );
        
       // Btn6.whenPressed(new );
        
       // BtnA.whenPressed(new );
        
       // BtnB.whenPressed(new );
        
       // BtnX.whenPressed(new );
        
       // BtnY.whenPressed(new );
    }
    // Button button = new JoystickButton(stick, buttonNumber);
    public static final int XBOX_BTN_A = 1;
  
    public static final int XBOX_BTN_B = 2;
  
    public static final int XBOX_BTN_X = 3;
  
    public static final int XBOX_BTN_Y = 4;
    
   
    
    
    
 
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

