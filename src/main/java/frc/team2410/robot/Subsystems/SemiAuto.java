package frc.team2410.robot.Subsystems;

import frc.team2410.robot.Robot;
import static frc.team2410.robot.RobotMap.*;

public class SemiAuto {
	
	private int placeState = 0;
	private int climbState = 0;
	
	public SemiAuto() {
	
	}
	
	public void reset(boolean place) {
		if(place) {
			placeState = 0;
		} else {
			climbState = 0;
		}
	}
	
	private void turnToClosestStation() {
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
		
		double angleDiff = target - Robot.gyro.getHeading();
		if(Math.abs(angleDiff) < 2) placeState++;
		Robot.drivetrain.desiredHeading = target;
	}
	
	private void alignLine() {
		double distanceToCenter = CAMERA_WIDTH / 2 - Robot.vision.getCentralValue();
		
		if(Math.abs(distanceToCenter) < 10) placeState++;
		
		double speed = distanceToCenter/(CAMERA_WIDTH / 2);
		if(speed < -1) speed = -1;
		if(speed > 1) speed = 1;
		Robot.drivetrain.crabDrive(speed, 0, 0, 0.5, false);
	}
	
	private void driveToStation(boolean hatch) {
		double target = hatch ? HATCH_DISTANCE : CARGO_DISTANCE;
		
		double distanceToTarget = target - Robot.vision.getDistanceAway();
		
		if(Math.abs(distanceToTarget) < 2) placeState++;
		
		double speed = distanceToTarget/10;
		if(speed < -1) speed = -1;
		if(speed > 1) speed = 1;
		Robot.drivetrain.crabDrive(0, speed, 0, 0.5, false);
	}
	
	private void deliver(boolean hatch) {
		//TODO
	}
	
	public void place(boolean hatch, int level) {
		switch(placeState) {
			case 0:
				turnToClosestStation();
				break;
			case 1:
				alignLine();
				break;
			case 2:
				driveToStation(hatch);
				break;
			case 3:
				placeState += elevatorSetpoint(hatch ? HATCH_WRIST_ANGLE : CARGO_WRIST_ANGLE, hatch ? HATCH_HEIGHT[level-1] : CARGO_HEIGHT[level-1]) ? 1 : 0;
				break;
			case 4:
				deliver(hatch);
				break;
		}
	}
	
	public void climb() {
		//TODO
		elevatorSetpoint(CLIMB_WRIST_ANGLE, CLIMB_HEIGHT);
	}
	
	public boolean elevatorSetpoint(double wristAngle, double elevatorHeight) {
		Robot.elevator.moveWristTo(wristAngle);
		Robot.elevator.moveTo(elevatorHeight);
		boolean elevatorAt = Math.abs(Robot.elevator.getPosition() - elevatorHeight) < 1;
		boolean wristAt = Math.abs(Robot.elevator.getWristAngle() - elevatorHeight) < 1;
		return elevatorAt && wristAt;
	}
}
