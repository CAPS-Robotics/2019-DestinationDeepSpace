package frc.team2410.robot.Subsystems;

import frc.team2410.robot.Robot;

public class SemiAuto {
	
	public SemiAuto() {
	
	}
	
	public void alignLine() {
	
	}
	
	public void climb() {
	
	}
	
	public void placeCargo(int level) {
	
	}
	
	public void placeHatch(int level) {
	
	}
	
	public void elevatorSetpoint(double wristAngle, double elevatorHeight) {
		Robot.elevator.moveTo(elevatorHeight);
		Robot.elevator.moveWristTo(wristAngle);
	}
}
