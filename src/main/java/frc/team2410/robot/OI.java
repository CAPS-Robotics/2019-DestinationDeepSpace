package frc.team2410.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {
	boolean[] canPress = new boolean[12];
	boolean[] canPress1 = new boolean[12];
	public Joystick joy1;
	XboxController buttonPad;
	
	public OI() {
		for (int i = 0; i < canPress.length; i++) {
			canPress[i] = true;
		}
		joy1 = new Joystick(0);
		buttonPad = new XboxController(1);
	}
	
	void pollButtons() {
		if (joy1.getRawButton(5)) {
			Robot.drivetrain.returnWheelsToZero();
		}
		
		Robot.fieldOriented = !joy1.getRawButton(2);
		
		if(joy1.getRawButton(6)) {
			Robot.gyro.resetHeading(0);
		}
	}
	
	public double getX() {
		return this.applyDeadzone(joy1.getRawAxis(0), 0.15, 1);
	}
	
	public double getY() {
		return this.applyDeadzone(-joy1.getRawAxis(1), 0.15, 1);
	}
	
	public double getTwist() {
		return this.applyDeadzone(joy1.getRawAxis(2), 0.70, 1) / 2;
	}
	
	public double getSlider() {
		return joy1.getRawAxis(3);
	}
	
	double getAnalogY(int stickNum){
		return this.applyDeadzone(buttonPad.getRawAxis(stickNum * 2 + 1), 0.50, 1);
	}
	
	double applyDeadzone(double val, double deadzone, double maxval) {
		if (Math.abs(val) <= deadzone) {
			return 0;
		}
		
		double sign = val / Math.abs(val);
		val = sign * maxval * (Math.abs(val) - deadzone) / (maxval - deadzone);
		return val;
	}
	
	public double getStick() {
		return this.applyDeadzone(buttonPad.getRawAxis(3), 0.50, 1);
	}
}
