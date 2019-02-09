package frc.team2410.robot.Subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team2410.robot.Robot;
import static frc.team2410.robot.RobotMap.*;

public class SemiAuto {
	
	public int placeState = 0;
	private int climbState = 0;
	Timer t;
	public boolean engaged = false;
	public double pval = 0;
	
	public SemiAuto() {
		t = new Timer();
	}
	
	public void reset(boolean place) {
		if(place) {
			placeState = 0;
		} else {
			climbState = 0;
		}
	}
	
	private void turnToClosestStation() {
		double angle = Robot.gyro.getHeading();
		
		double lowestOffset = Math.abs(CARGO_SHIP_FRONT - angle);
		double target = CARGO_SHIP_FRONT;
		
		if(Math.abs(ROCKET_LEFT_FRONT - angle) < lowestOffset) {
			lowestOffset = Math.abs(ROCKET_LEFT_FRONT-angle);
			target = ROCKET_LEFT_FRONT;
		}
		if(Math.abs(ROCKET_RIGHT_FRONT - angle) < lowestOffset) {
			lowestOffset = Math.abs(ROCKET_RIGHT_FRONT-angle);
			target = ROCKET_RIGHT_FRONT;
		}
		if(Math.abs(ROCKET_LEFT_LEFT - angle) < lowestOffset) {
			lowestOffset = Math.abs(ROCKET_LEFT_LEFT-angle);
			target = ROCKET_LEFT_LEFT;
		}
		if(Math.abs(ROCKET_LEFT_RIGHT - angle) < lowestOffset) {
			lowestOffset = Math.abs(ROCKET_LEFT_RIGHT-angle);
			target = ROCKET_LEFT_RIGHT;
		}
		if(Math.abs(ROCKET_RIGHT_LEFT - angle) < lowestOffset) {
			lowestOffset = Math.abs(ROCKET_RIGHT_LEFT-angle);
			target = ROCKET_RIGHT_LEFT;
		}
		if(Math.abs(ROCKET_RIGHT_RIGHT - angle) < lowestOffset) {
			target = ROCKET_RIGHT_RIGHT;
		}
		
		
		double angleDiff = target - Robot.gyro.getHeading();
		Robot.drivetrain.crabDrive(0, 0, 0, 1, true);
		if(Math.abs(angleDiff) < 10) placeState++;
		Robot.drivetrain.desiredHeading = target;
	}
	
	private void driveToLine() {
		Robot.drivetrain.crabDrive(0, 1, 0, 0.3, false);
		if(Robot.vision.getCentralValue() != 0) {
			placeState++;
			t.reset();
			t.start();
		}
	}
	
	private void alignLine() {
		double distanceToCenter = CAMERA_WIDTH / 2 - Robot.vision.getCentralValue();
		
		if(Math.abs(distanceToCenter) < 10 && Math.abs(pval-distanceToCenter) < 1) placeState = -1;
		pval = distanceToCenter;
		
		double speed = distanceToCenter/(CAMERA_WIDTH/4);
		if(speed < -1) speed = -1;
		if(speed > 1) speed = 1;
		if(speed > -.4 && speed < 0) speed = -.4;
		if(speed < .4 && speed > 0) speed = .4;
		Robot.drivetrain.crabDrive(-speed, 0, 0, .4, false);
	}
	
	private void deliver(boolean hatch) {
		if(hatch) {
			Robot.elevator.toggleHatch();
		} else {
			Robot.elevator.setIntake(false);
		}
		if(t.get() > (hatch ? 0.25 : 1)) placeState++;
	}
	
	public void place(boolean hatch, int level) {
		engaged = true;
		switch(placeState) {
			case 0:
				turnToClosestStation();
				break;
			case 1:
				elevatorSetpoint(hatch ? HATCH_WRIST_ANGLE : CARGO_WRIST_ANGLE, hatch ? HATCH_HEIGHT[level-1] : CARGO_HEIGHT[level-1]); placeState++;
				break;
			case 2:
				driveToLine();
				break;
			case 3:
				Robot.drivetrain.crabDrive(0, -1, 0, 1, false);
				SmartDashboard.putNumber("semiauto frame test", SmartDashboard.getNumber("semiauto frame test", 0)+1);
				if(t.get() > .5) placeState++;
				break;
			case 4:
				alignLine();
				break;
			case 5:
				if(elevatorSetpoint(hatch ? HATCH_WRIST_ANGLE : CARGO_WRIST_ANGLE, hatch ? HATCH_HEIGHT[level-1] : CARGO_HEIGHT[level-1])) placeState++;
				Robot.drivetrain.startTravel();
				break;
			case 6:
				if(driveToDistance((hatch ? HATCH_DISTANCE : CARGO_DISTANCE) - Robot.drivetrain.getTravel(), false)) placeState++;
				t.reset();
				break;
			case 7:
				t.start();
				deliver(hatch);
				break;
			case 8:
				if(driveToDistance(Robot.drivetrain.getTravel() - (hatch ? HATCH_DISTANCE : CARGO_DISTANCE), false)) placeState++;
				break;
			default:
				Robot.drivetrain.brake();
		}
	}
	
	private void lift() {
		elevatorSetpoint(CLIMB_WRIST_ANGLE, 0);
		Robot.climb.set(true);
		
		if(Robot.elevator.getPosition() < 2) climbState++;
	}
	
	private void pullForward() {
		Robot.elevator.setIntake(false);
		if(Robot.vision.getDistanceAway() < CLIMB_WALL_DISTANCE) {
			Robot.elevator.stopIntake();
			Robot.climb.set(false);
			Robot.elevator.moveWristTo(WRIST_UP);
			climbState++;
		}
	}
	
	public void climb() {
		switch(climbState){
			case 0:
				if(elevatorSetpoint(CLIMB_WRIST_ANGLE, CLIMB_HEIGHT)) climbState++;
				break;
			case 1:
				Robot.drivetrain.desiredHeading = 180;
			case 2:
				if(driveToDistance(-(HABITAT_DISTANCE - Robot.vision.getDistanceAway()), true)) climbState++;
				break;
			case 3:
				lift();
				break;
			case 4:
				pullForward();
				break;
			case 5:
				if(driveToDistance(WALL_DISTANCE - Robot.vision.getDistanceAway(), false)) climbState++;
		}
	}
	
	public boolean elevatorSetpoint(double wristAngle, double elevatorHeight) {
		Robot.elevator.moveWristTo(wristAngle);
		Robot.elevator.moveTo(elevatorHeight);
		boolean elevatorAt = Math.abs(Robot.elevator.getPosition() - elevatorHeight) < 1;
		boolean wristAt = Math.abs(Robot.elevator.getWristAngle() - elevatorHeight) < 1;
		return elevatorAt && wristAt;
	}
	
	private boolean driveToDistance(double distance, boolean useGyro){
		if(Math.abs(distance) < 1) return true;
		
		double speed = distance/10;
		if(speed < -1) speed = -1;
		if(speed > 1) speed = 1;
		Robot.drivetrain.crabDrive(0, speed, 0, 0.3, useGyro);
		
		return false;
	}
	
	
}
