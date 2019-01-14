package frc.team2410.robot;

import edu.wpi.first.wpilibj.*;

public class OI {
	private boolean[][] canPress = new boolean[2][12];
	private GenericHID[] controllers = new GenericHID[2];
	private Joystick joy;
	private XboxController xbox;
	
	OI() {
		joy = new Joystick(0);
		xbox = new XboxController(1);
		controllers[0] = joy;
		controllers[1] = xbox;
	}
	
	void pollButtons() {
		if (joy.getRawButton(5)) {
			Robot.drivetrain.returnWheelsToZero();
		}
		
		Robot.fieldOriented = !joy.getRawButton(2);
		
		if(joy.getRawButton(6)) {
			Robot.gyro.resetHeading(0);
		}
	}
	
	private boolean leadingEdge(boolean joystick, int button) {
		int n = joystick?1:0;
		if(controllers[n].getRawButton(button+1)) {
			if(canPress[n][button]) {
				canPress[n][button] = false;
				return true;
			}
		} else { canPress[n][button] = true; }
		return false;
	}
	
	public double getX() {
		return this.applyDeadzone(joy.getRawAxis(0), 0.15, 1);
	}
	
	public double getY() {
		return this.applyDeadzone(-joy.getRawAxis(1), 0.15, 1);
	}
	
	public double getTwist() {
		return this.applyDeadzone(joy.getRawAxis(2), 0.70, 1)/2;
	}
	
	public double getSlider() {
		return joy.getRawAxis(3);
	}
	
	public double getAnalogStick(boolean rightStick, boolean yAxis){
		return this.applyDeadzone(xbox.getRawAxis((rightStick?1:0)*2+(yAxis?1:0)), 0.50, 1);
	}
	
	public double getJoyPOV() {
		return this.joy.getPOV(0);
	}
	
	private double applyDeadzone(double val, double deadzone, double maxval) {
		if (Math.abs(val) <= deadzone) return 0;
		double sign = val / Math.abs(val);
		val = sign * maxval * (Math.abs(val) - deadzone) / (maxval - deadzone);
		return val;
	}
}
