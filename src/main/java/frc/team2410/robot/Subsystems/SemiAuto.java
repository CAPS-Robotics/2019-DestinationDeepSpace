package frc.team2410.robot.Subsystems;

import frc.team2410.robot.Robot;
import static frc.team2410.robot.RobotMap.*;

public class SemiAuto {
	
	public SemiAuto() {
	
	}
	
	public void alignLine() {
	
	}
	
	public void climb() {
	
	}
	
	public void placeCargo(int level) {
		switch(level){
			case 1:
				elevatorSetpoint(CARGO_WRIST_ANGLE, CARGOSHIP_BALL_HEIGHT);
				break;
			case 2:
				elevatorSetpoint(CARGO_WRIST_ANGLE, ROCKET_BALL_LEVEL_TWO_HEIGHT);
				break;
			case 3:
				elevatorSetpoint(CARGO_WRIST_ANGLE, ROCKET_BALL_LEVEL_THRREE_HEIGHT);
				break;
		}
	}
	
	public void placeHatch(int level) {
		switch(level){
			case 1:
				elevatorSetpoint(HATCH_WRIST_ANGLE, CARGOSHIP_HATCH_HEIGHT);
				break;
			case 2:
				elevatorSetpoint(HATCH_WRIST_ANGLE, ROCKET_HATCH_LEVEL_TWO_HEIGHT);
				break;
			case 3:
				elevatorSetpoint(HATCH_WRIST_ANGLE, ROCKET_HATCH_LEVEL_THREE_HEIGHT);
				break;
		}
	}
	
	public void elevatorSetpoint(double wristAngle, double elevatorHeight) {
		Robot.elevator.moveTo(elevatorHeight);
		Robot.elevator.moveWristTo(wristAngle);
	}
	
	
}
