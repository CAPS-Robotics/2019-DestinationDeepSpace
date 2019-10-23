package frc.team2410.robot;

import edu.wpi.first.wpilibj.*;

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
		
		if(joy.getRawButton(11)){
			Robot.semiAuto.turnToNearestAngle(180);
			resetPlace = false;
		} else if(joy.getRawButton(9)) {
			Robot.semiAuto.turnToNearestAngle(0);
			resetPlace = false;
		} else if(joy.getRawButton(10)){
			//Robot.semiAuto.turnToNearestAngle(new double);
			resetPlace = false;
		} else if(joy.getRawButton(12)) {
			//Robot.semiAuto.turnToNearestAngle();
			resetPlace = false;
		} else {
			Robot.semiAuto.reng = false;
		}
		
		Robot.fieldOriented = !joy.getRawButton(2);
		
		if(xbox.getRawButton(7)) {
			Robot.intake.setIntake(false);
		} else if(xbox.getRawButton(8)) {
			Robot.intake.setIntake(true);
		} else {
			Robot.intake.stopIntake();
		}
		
		if(leadingEdge(true, 1)) {
			Robot.intake.toggleHatch();
		}
		
		if(leadingEdge(false, 9)) {
			Robot.climb.reset(0);
		}
		
		if(leadingEdge(false, 10)) {
			//Robot.elevator.reset(0);
		}
		
		if(joy.getRawButton(3)) {
			Robot.semiAuto.climb(0);
		} else if(joy.getRawButton(4)) {
			Robot.semiAuto.climb(1);
		} else {
			Robot.semiAuto.reset(false);
			if(Robot.semiAuto.lift) {
				Robot.elevator.moveTo(Robot.semiAuto.pFrontPos);
				Robot.climb.moveTo(Robot.semiAuto.pBackPos);
				Robot.semiAuto.lift = false;
			}
		}
		
		if(leadingEdge(false, 5)) {
			Robot.semiAuto.elevatorSetpoint(TRAVEL_ANGLE, TRAVEL_HEIGHT, true);
			Robot.intake.setHatch(false);
		} else if(leadingEdge(false, 6)) {
			Robot.semiAuto.elevatorSetpoint(HATCH_WRIST_ANGLE, INTAKE_HEIGHT, true);
			Robot.intake.setHatch(true);
		}
		
		
		
		if(xbox.getRawButton(1)) {
			Robot.elevator.moveTo(PLACE_HEIGHT[0]);
		} else if(xbox.getRawButton(4)) {
			Robot.elevator.moveTo(PLACE_HEIGHT[1]);
		} else if(xbox.getRawButton(3)) {
			Robot.elevator.moveTo(PLACE_HEIGHT[2]);
			Robot.intake.moveWristTo(HATCH_LEVEL_THREE_WRIST);
		} else if(xbox.getRawButton(2)) {
			Robot.elevator.moveTo(CARGO_LOADING_STATION_HEIGHT);
			Robot.intake.moveWristTo(CARGO_LOADING_STATION_ANGLE);
		}

		if(xbox.getPOV() == 0) {
			Robot.intake.moveWristTo(CARGO_WRIST_ANGLE);
		} else if(xbox.getPOV() == 90) {
			Robot.intake.moveWristTo(HATCH_WRIST_ANGLE);
		} else if(xbox.getPOV() == 270) {
			Robot.intake.moveWristTo(WRIST_UP);
		} else if(xbox.getPOV() == 180) {
			Robot.intake.moveWristTo(CARGO_WRIST_DOWN_ANGLE);
		}
		
		if(resetPlace) {
			Robot.semiAuto.reset(true);
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
		return this.applyDeadzone(joy.getRawAxis(0), 0.05, 1);
	}
	
	public double getY() {
		return this.applyDeadzone(-joy.getRawAxis(1), 0.05, 1);
	}
	
	public double getTwist() {
		return this.applyDeadzone(joy.getRawAxis(2), 0.01, 1)/2;
	}
	
	public double getSlider() {
		return (1-joy.getRawAxis(3))/2;
	}
	
	public double getAnalogStick(boolean rightStick, boolean yAxis){
		return this.applyDeadzone(xbox.getRawAxis((rightStick?1:0)*2+(yAxis?1:0)), 0.25, 1);
	}
	
	public boolean startPressed() { return xbox.getRawButton(10); }
	
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
