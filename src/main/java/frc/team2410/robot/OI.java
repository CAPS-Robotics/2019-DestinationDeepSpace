package frc.team2410.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static frc.team2410.robot.RobotMap.*;

public class OI {
	private boolean[][] canPress = new boolean[2][12];
	private boolean canPressPOV = true;
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
		if(joy.getRawButton(5)) {
			Robot.drivetrain.returnWheelsToZero();
		}
		
		if(joy.getRawButton(6)) {
			Robot.drivetrain.resetHeading(0);
		}
		
		boolean resetPlace = true;
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 2; j++) {
				if(joy.getRawButton(12-(j+2*i))) {
					Robot.semiAuto.place(j != 0, i+1);
					resetPlace = false;
					
				}
			}
		}
		
		Robot.fieldOriented = !joy.getRawButton(1);
		
		if(xbox.getRawButton(7)) {
			Robot.elevator.setIntake(false);
		} else if(xbox.getRawButton(8)) {
			Robot.elevator.setIntake(true);
		} else {
			Robot.elevator.stopIntake();
		}
		
		if(joy.getPOV() != -1) {
			if(canPressPOV) {
				Robot.elevator.toggleHatch();
			}
			canPressPOV = false;
		} else { canPressPOV = true; }
		
		if(leadingEdge(false, 10)) {
			Robot.elevator.reset(0);
		}
		
		if(leadingEdge(false, 5)) {
			Robot.semiAuto.elevatorSetpoint(CARGO_WRIST_ANGLE, CARGO_INTAKE_HEIGHT);
		} else if(leadingEdge(false, 6)) {
			Robot.semiAuto.elevatorSetpoint(HATCH_WRIST_ANGLE, HATCH_INTAKE_HEIGHT);
		}
		
		if(xbox.getRawButton(1)) {
			Robot.elevator.moveTo(PLACE_HEIGHT[0]);
		} else if(xbox.getRawButton(4)) {
			Robot.elevator.moveTo(PLACE_HEIGHT[1]);
		} else if(xbox.getRawButton(3)) {
			Robot.elevator.moveTo(PLACE_HEIGHT[2]);
		}

		if(xbox.getPOV() == 0) {
			Robot.elevator.moveWristTo(CARGO_WRIST_ANGLE);
		} else if(xbox.getPOV() == 90) {
			Robot.elevator.moveWristTo(HATCH_WRIST_ANGLE);
		} else if(xbox.getPOV() == 270) {
			Robot.elevator.moveWristTo(WRIST_UP);
		}
		
		if(resetPlace) {
			Robot.semiAuto.reset(true);
			Robot.semiAuto.engaged = false;
		}
	}
	
	//Returns true for the first frame the button is pressed
	private boolean leadingEdge(boolean joystick, int button) {
		int n = joystick?0:1;
		if(controllers[n].getRawButton(button)) {
			if(canPress[n][button-1]) {
				canPress[n][button-1] = false;
				return true;
			}
		} else { canPress[n][button-1] = true; }
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
		return (1-joy.getRawAxis(3))/2;
	}
	
	public double getAnalogStick(boolean rightStick, boolean yAxis){
		return this.applyDeadzone(xbox.getRawAxis((rightStick?1:0)*2+(yAxis?1:0)), 0.25, 1);
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
