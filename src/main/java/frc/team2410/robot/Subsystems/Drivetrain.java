package frc.team2410.robot.Subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team2410.robot.NumericalPIDOutput;
import frc.team2410.robot.Robot;
import static frc.team2410.robot.RobotMap.*;

public class Drivetrain {
	private AnalogInput rangeFinder;
	public double desiredHeading;
	public SwerveModule fl;
	public SwerveModule fr;
	public SwerveModule bl;
	public SwerveModule br;
	private PIDController gyroPID;
	private Encoder driveEnc;
	private double prot = 0; // Previous rotation
	
	public Drivetrain() {
		this.fl = new SwerveModule(FRONT_LEFT_STEER, FRONT_LEFT_DRIVE, FL_STEER_ENCODER, FL_OFFSET, true);
		this.fr = new SwerveModule(FRONT_RIGHT_STEER, FRONT_RIGHT_DRIVE, FR_STEER_ENCODER, FR_OFFSET, false);
		this.bl = new SwerveModule(BACK_LEFT_STEER, BACK_LEFT_DRIVE, BL_STEER_ENCODER, BL_OFFSET, true);
		this.br = new SwerveModule(BACK_RIGHT_STEER, BACK_RIGHT_DRIVE, BR_STEER_ENCODER, BR_OFFSET, false);
		this.rangeFinder = new AnalogInput(RANGE_FINDER);
		this.desiredHeading = Robot.gyro.getHeading();
		this.driveEnc = new Encoder(DRIVE_CIMCODER_A, DRIVE_CIMCODER_B);
		this.driveEnc.setDistancePerPulse(DRIVE_DIST_PER_PULSE);
		this.gyroPID = new PIDController(GYRO_P, GYRO_I, GYRO_D, Robot.gyro, new NumericalPIDOutput(), 0.002);
		gyroPID.setInputRange(-180, 180);
		gyroPID.setOutputRange(-.5, .5);
		gyroPID.setContinuous(true);
		gyroPID.enable();
	}
	
	public void resetHeading(int head) {
		Robot.gyro.resetHeading(head);
		desiredHeading = head;
	}
	
	public void joystickDrive(boolean fieldOriented) {
		double speedMultiplier = (1-Robot.oi.getSlider())/2;
		Robot.drivetrain.crabDrive(Robot.oi.getX(), Robot.oi.getY(), Robot.oi.getTwist(), speedMultiplier, fieldOriented);
	}
	
	public double getDistanceAway() {
		return (this.rangeFinder.getVoltage()/0.012446);
	}
	
	public void returnWheelsToZero() {
		this.fl.returnToZero();
		this.fr.returnToZero();
		this.bl.returnToZero();
		this.br.returnToZero();
	}
	
	void brake() {
		this.fl.drive(0, 0);
		this.fr.drive(0, 0);
		this.bl.drive(0, 0);
		this.br.drive(0, 0);
	}
	
	void drive(double angle, double speed, double speedMultiplier) {
		this.fl.drive(speed*speedMultiplier, angle);
		this.fr.drive(speed*speedMultiplier, angle);
		this.bl.drive(speed*speedMultiplier, angle);
		this.br.drive(speed*speedMultiplier, angle);
	}
	
	void arcadeDrive(double forward, double rotation, double speedMultiplier) {
		double correction = 0.025*(Robot.gyro.getHeading()-desiredHeading);
		SmartDashboard.putNumber("Difference", correction);
		this.fl.drive((forward+rotation*1/Math.sqrt(2)+correction)*speedMultiplier, 0);
		this.fr.drive((forward-rotation*1/Math.sqrt(2)-correction)*speedMultiplier, 0);
		this.bl.drive((forward+rotation*1/Math.sqrt(2)+correction)*speedMultiplier, 0);
		this.br.drive((forward-rotation*1/Math.sqrt(2)-correction)*speedMultiplier, 0);
	}
	
	public void crabDrive(double x, double y, double rotation, double speedMultiplier, boolean useGyro) {
		double forward, strafe;
		SmartDashboard.putNumber("FL Angle", fl.getAngle());
		SmartDashboard.putNumber("x", x);
		SmartDashboard.putNumber("y", y);
		SmartDashboard.putNumber("speed", speedMultiplier);
		SmartDashboard.putNumber("FR Angle", fr.getAngle());
		SmartDashboard.putNumber("BL Angle", bl.getAngle());
		SmartDashboard.putNumber("BR Angle", br.getAngle());
		double heading = Robot.gyro.getHeading()*Math.PI/180; //Degrees -> Radians
		if (useGyro) {
			forward = -x*Math.sin(heading)+y*Math.cos(heading);
			strafe = x*Math.cos(heading)+y*Math.sin(heading);
		} else {
			forward = y;
			strafe = x;
		}

		// Sets desired heading dependant if previous rotation was zero or not
		if(rotation == 0 && prot == 0) {
			prot = 0;
			gyroPID.setSetpoint(desiredHeading);
			rotation = -gyroPID.get();
		} else {
			prot = rotation;
			desiredHeading = Robot.gyro.getHeading();
			desiredHeading = wrap(desiredHeading, 180, -180);
		}


		if (x != 0 || y != 0 || rotation != 0) {
			double back, front, right, left;
			if (rotation != 0) {
				back = strafe-rotation/Math.sqrt(2); //strafe-rotation*1.0/Math.sqrt(2);
				front = strafe+rotation/Math.sqrt(2);
				right = forward-rotation/Math.sqrt(2);
				left = forward+rotation/Math.sqrt(2);
			} else {
				back = strafe;
				front = strafe;
				right = forward;
				left = forward;
			}
			double flds = Math.sqrt(front*front+left*left);
			double frds = Math.sqrt(front*front+right*right);
			double blds = Math.sqrt(back*back+left*left);
			double brds = Math.sqrt(back*back+right*right);
			double maxSpeed = Math.max(Math.max(Math.max(flds, frds), blds), brds);
			if(maxSpeed > 1) {
				flds /= maxSpeed;
				frds /= maxSpeed;
				blds /= maxSpeed;
				brds /= maxSpeed;
			}
			double fla = 0, fra = 0, bla = 0, bra = 0;
			if(front != 0 || left != 0) {
				fla = (180/Math.PI)*-Math.atan2(front, left);
				fla = wrap(fla, 360, 0);
			}
			if(front != 0 || right != 0) {
				fra = (180/Math.PI)*-Math.atan2(front, right);
				fra = wrap(fra, 360, 0);
			}
			if(back != 0 || left != 0) {
				bla = (180/Math.PI)*-Math.atan2(back, left);
				bla = wrap(bla, 360, 0);
			}
			if(back != 0 || right != 0) {
				bra = (180/Math.PI)*-Math.atan2(back, right);
				bra = wrap(bra, 360, 0);
			}
			this.fl.drive(flds*speedMultiplier, fla);
			this.fr.drive(frds*speedMultiplier, fra);
			this.bl.drive(blds*speedMultiplier, bla);
			this.br.drive(brds*speedMultiplier, bra);
		} else {
			this.fl.drive(0, this.fl.getAngle());
			this.fr.drive(0, this.fr.getAngle());
			this.bl.drive(0, this.bl.getAngle());
			this.br.drive(0, this.br.getAngle());
		}
	}
	
	public double wrap(double num, double max, double min) {
		return (num-min)-(max-min)*Math.floor((num-min)/(max-min))+min;
	}
	
	public void startTravel() {
		driveEnc.reset();
	}
	
	public double getTravel() {
		return driveEnc.getDistance();
	}
	public void setGyroPID(double p, double i, double d) {
		gyroPID.setPID(p, i, d);
	}
	public void setPID(float p, float i, float d) {
		fl.setPID(p, i, d);
		fr.setPID(p, i, d);
		bl.setPID(p, i, d);
		br.setPID(p, i, d);
	}
}