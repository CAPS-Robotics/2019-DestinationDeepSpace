package frc.team2410.robot.Subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team2410.robot.Robot;
import static frc.team2410.robot.RobotMap.*;

public class SemiAuto {
	
	public int placeState = 0;
	private int climbState = 0;
	public Timer t;
	public boolean engaged = false;
	public boolean ceng = false;
	public boolean peng = false;
	public boolean lift = false;
	public double pval = 0;
	private double pspeed = 0;
	private double frontPower = 0.20;
	private double backPower = -0.5;
	public double pFrontPos;
	public double pBackPos;
	
	
	public SemiAuto() {
		t = new Timer();
	}
	
	public void reset(boolean place) {
		if(place) {
			placeState = 0;
			peng = false;
		} else {
			climbState = 0;
			ceng = false;
		}
		engaged = ceng || peng;
	}
	
	public boolean startMatch() {
		Robot.intake.moveWristTo(TRAVEL_ANGLE + ((WRIST_UP - TRAVEL_ANGLE) * (1-(t.get() / 2))));
		if(Math.abs(Robot.intake.getAngle() - TRAVEL_ANGLE) < 5) {
			Robot.intake.toggleHatch();
			t.stop();
			return true;
		}
		return false;
	}
	
	public void turnToNearestAngle(double angle) {
		Robot.drivetrain.crabDrive(0, 0, 0, 1, true);
		Robot.drivetrain.desiredHeading = angle;
	}
	
	public void turnToNearestAngle(double [] angles) {
	
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
			lowestOffset = Math.abs(INTAKE-angle);
			target = INTAKE;
		}
		if(Math.abs(INTAKE2 - angle) < lowestOffset) {
			target = INTAKE2;
		}
		
		double angleDiff = target - Robot.gyro.getHeading();
		Robot.drivetrain.crabDrive(0, 0, 0, 1, true);
		if(Math.abs(angleDiff) < 10) placeState++;
		Robot.drivetrain.desiredHeading = target;
	}
	
	public void turnToClosestRocket() {
		double angle = Robot.gyro.getHeading();
		
		double lowestOffset = Math.abs(ROCKET_RIGHT_LEFT - angle);
		double target = ROCKET_RIGHT_LEFT;
		
		if(Math.abs(ROCKET_RIGHT_LEFT - angle) < lowestOffset) {
			lowestOffset = Math.abs(ROCKET_RIGHT_LEFT);
		}
		if(Math.abs(ROCKET_RIGHT_RIGHT - angle) < lowestOffset) {
			lowestOffset = Math.abs(ROCKET_RIGHT_RIGHT-angle);
			target = ROCKET_RIGHT_RIGHT;
		}
		if(Math.abs(ROCKET_LEFT_LEFT - angle) < lowestOffset) {
			lowestOffset = Math.abs(ROCKET_LEFT_LEFT-angle);
			target = ROCKET_LEFT_LEFT;
		}
		if(Math.abs(ROCKET_LEFT_RIGHT - angle) < lowestOffset) {
			target = ROCKET_LEFT_RIGHT;
		}
		
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
		
		boolean xDone = Math.abs(distanceToCenter) < (MULTIPLIER_WIDTH * CAMERA_WIDTH);
		boolean yDone = Math.abs(distanceBack) < (CAMERA_HEIGHT * MULTIPLIER_HEIGHT);
		
		if(xDone && yDone && Math.abs(pval - distanceToCenter) < 1 && centerValues[0] != 0) placeState = -1;
		pval = distanceToCenter;
		
		double xspeed = 0;
		if(!xDone) {
			xspeed = -distanceToCenter/(CAMERA_WIDTH*2.4);
			if(xspeed > -MIN_XSPEED && xspeed < 0) xspeed = -MIN_XSPEED;
			if(xspeed < MIN_XSPEED && xspeed > 0) xspeed = MIN_XSPEED;
			//Makes robot drive even if y is done
			if(yDone && Math.abs(xspeed) <= MIN_YSPEED + EXTRA_XSPEED) xspeed += xspeed < 0 ? -EXTRA_XSPEED : EXTRA_XSPEED;
		}
		/*if(centerValues[0] == 0) xspeed = pspeed;
		pspeed = xspeed;*/
		
		double yspeed = 0;
		if(!yDone) {
			yspeed = distanceBack/(CAMERA_HEIGHT * 1.80);
			if(yspeed > -MIN_YSPEED && yspeed < 0) yspeed = -MIN_YSPEED;
			if(yspeed < MIN_YSPEED && yspeed > 0) yspeed = MIN_YSPEED;
			//Makes robot drive even if x is done
			if(xDone && Math.abs(yspeed) <= MIN_YSPEED + EXTRA_XSPEED) yspeed += yspeed < 0 ? -EXTRA_YSPEED : EXTRA_YSPEED;
		}
		SmartDashboard.putNumber("Distance to Center", distanceToCenter);
		SmartDashboard.putNumber("XSpeed", xspeed);
		SmartDashboard.putNumber("YSpeed", yspeed);
		Robot.drivetrain.crabDrive(xspeed, yspeed, 0, 1, false);
	}
	
	private void deliver(boolean hatch) {
		if(hatch) {
			Robot.intake.setHatch(true);
		} else {
			Robot.intake.setIntake(false);
		}
		if(t.get() > (hatch ? 0.25 : 1)) placeState++;
	}
	
	public void place(boolean hatch, int level) {
		peng = true;
		engaged = true;
		switch(placeState) {
			case 0:
				turnToClosestStation();
				break;
			case 1:
				alignLine();
				break;
			/*case 2:
				if(elevatorSetpoint(hatch ? HATCH_WRIST_ANGLE : CARGO_WRIST_ANGLE, PLACE_HEIGHT[level-1], false)) placeState++;
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
				break;*/
			default:
				Robot.drivetrain.brake();
		}
	}
	
	private void lift(int level) {
		Robot.climb.moveTo(CLIMB_HEIGHT[level] + CLIMB_ELEVATOR_MAX_OFFSET);
		
		if(elevatorSetpoint(CLIMB_WRIST_ANGLE[1], CLIMB_HEIGHT[level] - Robot.climb.getPosition(), true)) {
			//Robot.elevator.setIntake(false);
		}
		/*double frontSpeed = Math.abs(pFrontPos - Robot.elevator.getPosition()) / t.get();
		double backSpeed = Math.abs(pBackPos - Robot.climb.getPosition()) / t.get();
		
		pFrontPos = Robot.elevator.getPosition();
		pBackPos = Robot.climb.getPosition();
		
		if(frontSpeed < backSpeed) {
			if(backPower == -1) backPower += 0.03;
			else frontPower += 0.03;
		} else if(backSpeed < frontSpeed){
			if(frontSpeed == 1) frontPower -= 0.03;
			else backPower -= 0.03;
		}
		
		if(Math.abs(Robot.elevator.getPosition() - 1) < 1) frontPower = 0;
		if(Math.abs(Robot.climb.getPosition() - CLIMB_HEIGHT[level] + CLIMB_OFFSET) < 1) backPower = 0;
		
		if(frontPower == 0 && backPower == 0) {
			climbState++;
			lift = false;
		}
		
		if(frontPower > 1) frontPower = 1;
		if(backPower < -1) backPower = -1;
		
		Robot.elevator.setSpeed(frontPower);
		Robot.climb.setSpeed(backPower);
		
		t.reset();
		t.start();*/
	}
	
	public void climb(int level) {
		ceng = true;
		engaged = true;
		switch(climbState) {
			case 0:
				//Robot.climb.moveTo(CLIMB_OFFSET * 2);
				if(elevatorSetpoint(CLIMB_WRIST_ANGLE[0], CLIMB_HEIGHT[level], true)) {
					climbState++;
					t.reset();
					t.start();
				}
				break;
			case 1:
				//Robot.drivetrain.desiredHeading = 180;
				if(t.get() > 1) {
					/*Robot.elevator.setSpeed(frontPower);
					Robot.climb.setSpeed(backPower);
					pFrontPos = Robot.elevator.getPosition();
					pBackPos = Robot.climb.getPosition();
					t.reset();
					t.start();
					lift = true;*/
					climbState++;
				}
				break;
			case 2:
				//Robot.drivetrain.crabDrive(0, 1, 0, 0.20, false);
				lift(level);
				break;
		}
	}
	
	public boolean elevatorSetpoint(double wristAngle, double elevatorHeight, boolean sameTime) {
		boolean elevatorAt = Math.abs(Robot.elevator.getPosition() - elevatorHeight) < 3;
		boolean wristAt = Math.abs(Robot.intake.getAngle() - wristAngle) < 5;
		if(elevatorAt || sameTime) Robot.intake.moveWristTo(wristAngle);
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
