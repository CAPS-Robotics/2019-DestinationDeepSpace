package frc.team2410.robot.Subsystems;

import frc.team2410.robot.Robot;
import static frc.team2410.robot.RobotMap.*;

public class SemiAuto {
	
	public SemiAuto() {
	
	}
	
	public void alignLine() {
	
	}
	
	public double closestAngle() {
		double positiveAngle =Math.abs (Robot.gyro.getHeading());
		
		double lowestOffset;
		
		lowestOffset = Math.abs(CARGO_SHIP_FRONT) - positiveAngle;
		//lowestOffset = Math.abs(CARGO_SHIP_FRONT) - positiveAngle < lowestOffset ?
		
		
		
		if(lowestOffset < 0) {
		}
		
		return lowestOffset;
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
