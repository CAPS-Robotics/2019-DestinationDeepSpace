package frc.team2410.robot.Subsystems;

import frc.team2410.robot.Robot;
import static frc.team2410.robot.RobotMap.*;

public class SemiAuto {
	
	public SemiAuto() {
	
	}
	
	public void alignLine() {
	
	}
	
	public double closestAngle() {
		double angle = Math.abs(Robot.gyro.getHeading());
		
		double lowestOffset = Math.abs(CARGO_SHIP_FRONT) - angle;
		double target = CARGO_SHIP_FRONT;
		
		if(Math.abs(ROCKET_LEFT_FRONT) - angle < lowestOffset) {
			lowestOffset = Math.abs(ROCKET_LEFT_FRONT)-angle;
			target = ROCKET_LEFT_FRONT;
		}
		if(Math.abs(ROCKET_RIGHT_FRONT) - angle < lowestOffset) {
			lowestOffset = Math.abs(ROCKET_RIGHT_FRONT)-angle;
			target = ROCKET_RIGHT_FRONT;
		}
		if(Math.abs(ROCKET_LEFT_LEFT) - angle < lowestOffset) {
			lowestOffset = Math.abs(ROCKET_LEFT_LEFT)-angle;
			target = ROCKET_LEFT_LEFT;
		}
		if(Math.abs(ROCKET_LEFT_RIGHT) - angle < lowestOffset) {
			lowestOffset = Math.abs(ROCKET_LEFT_RIGHT)-angle;
			target = ROCKET_LEFT_RIGHT;
		}
		if(Math.abs(ROCKET_RIGHT_LEFT) - angle < lowestOffset) {
			lowestOffset = Math.abs(ROCKET_RIGHT_LEFT)-angle;
			target = ROCKET_RIGHT_LEFT;
		}
		if(Math.abs(ROCKET_RIGHT_RIGHT) - angle < lowestOffset) {
			target = ROCKET_RIGHT_RIGHT;
		}
		
		return target;
	}
	
	public void climb() {
		elevatorSetpoint(CLIMB_WRIST_ANGLE, CLIMB_HEIGHT);
	}
	
	public void placeCargo(int level) {
		elevatorSetpoint(CARGO_WRIST_ANGLE, CARGO_HEIGHT[level-1]);
	}
	
	public void placeHatch(int level) {
		elevatorSetpoint(HATCH_WRIST_ANGLE, HATCH_HEIGHT[level-1]);
	}
	
	public void elevatorSetpoint(double wristAngle, double elevatorHeight) {
		Robot.elevator.moveTo(elevatorHeight);
		Robot.elevator.moveWristTo(wristAngle);
	}
}
