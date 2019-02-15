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
			lowestOffset = Math.abs(ROCKET_RIGHT_RIGHT-angle);
			target = ROCKET_RIGHT_RIGHT;
		}
		if(Math.abs(INTAKE - angle) < lowestOffset) {
			target = INTAKE;
		}
		
		double angleDiff = target - Robot.gyro.getHeading();
		Robot.drivetrain.crabDrive(0, 0, 0, 1, true);
		if(Math.abs(angleDiff) < 10) placeState++;
		Robot.drivetrain.desiredHeading = target;
	}
	
	/*private void driveToLine() {
		Robot.drivetrain.crabDrive(0, 1, 0, 0.3, false);
		if(Robot.vision.getCentralValue() != 0) {
			placeState++;
			t.reset();
			t.start();
		}
	}*/
	
	private void alignLine() {
		double[] centerValues = Robot.vision.getCentralValue();
		double distanceToCenter = CAMERA_WIDTH / 2 - centerValues[0];
		double distanceBack = CAMERA_HEIGHT / 6 - centerValues[1];
		
		if(Math.abs(distanceToCenter) < 40 && Math.abs(distanceToCenter-pval) < 1 && Math.abs(distanceBack) < 20) placeState = -1;
		pval = distanceToCenter;
		
		double xspeed = 0;
		if(Math.abs(distanceToCenter) > 40) {
			xspeed = -distanceToCenter/(CAMERA_WIDTH*1.6);
			if(xspeed > -0.12 && xspeed < 0) xspeed = -0.12;
			if(xspeed < 0.12 && xspeed > 0) xspeed = 0.12;
		}
		
		double yspeed = 0;
		if(Math.abs(distanceBack) > 20) {
			yspeed = distanceBack/(CAMERA_HEIGHT * 1.5);
			if(yspeed > -0.12 && yspeed < 0) yspeed = -0.12;
			if(yspeed < 0.12 && yspeed > 0) yspeed = 0.12;
		}
		Robot.drivetrain.crabDrive(xspeed, yspeed, 0, 1, false);
	}
	
	private void deliver(boolean hatch) {
		if(hatch) {
			Robot.elevator.setHatch(true);
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
				alignLine();
				break;
			case 2:
				if(elevatorSetpoint(hatch ? HATCH_WRIST_ANGLE : CARGO_WRIST_ANGLE, PLACE_HEIGHT[level-1])) placeState++;
				Robot.drivetrain.startTravel();
				break;
			case 3:
				if(driveToDistance((hatch ? HATCH_DISTANCE : CARGO_DISTANCE) - Robot.drivetrain.getTravel(), false)) placeState++;
				t.reset();
				t.start();
				break;
			case 4:
				deliver(hatch);
				Robot.drivetrain.startTravel();
				break;
			case 5:
				if(driveToDistance(Robot.drivetrain.getTravel() - (hatch ? HATCH_DISTANCE : CARGO_DISTANCE), false)) placeState++;
				break;
			default:
				Robot.drivetrain.brake();
		}
	}
	
	private void lift() {
		elevatorSetpoint(CLIMB_WRIST_ANGLE[1], 0);
		Robot.climb.set(true);
		
		if(Robot.elevator.getPosition() < 2) climbState++;
	}
	
	public void climb() {
		switch(climbState){
			case 0:
				if(elevatorSetpoint(CLIMB_WRIST_ANGLE[0], 0)) climbState++;
				break;
			case 1:
				Robot.drivetrain.desiredHeading = 180;
				climbState++;
				break;
			case 2:
				Robot.elevator.setIntake(false);
				Robot.drivetrain.crabDrive(0, 1, 0, 0.20, false);
				lift();
				break;
		}
	}
	
	public boolean elevatorSetpoint(double wristAngle, double elevatorHeight) {
		boolean elevatorAt = Math.abs(Robot.elevator.getPosition() - elevatorHeight) < 3;
		boolean wristAt = Math.abs(Robot.elevator.getWristAngle() - wristAngle) < 5;
		if(elevatorAt) Robot.elevator.moveWristTo(wristAngle);
		Robot.elevator.moveTo(elevatorHeight);
		return elevatorAt && wristAt;
	}
	
	private boolean driveToDistance(double distance, boolean useGyro){
		if(Math.abs(distance) < 1) return true;
		
		double speed = distance/10;
		/*if(speed < -1) speed = -1;
		if(speed > 1) speed = 1;*/
		speed = Math.abs(speed)/speed;
		Robot.drivetrain.crabDrive(0, speed, 0, 0.3, useGyro);
		
		return false;
	}
	
	
}
