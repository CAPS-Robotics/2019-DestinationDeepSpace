package frc.team2410.robot.Subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team2410.robot.Robot;
import frc.team2410.robot.RobotMap;

public class Drivetrain {
	AnalogInput rangeFinder;
	DoubleSolenoid shift;
	public boolean speedShift;
	double desiredHeading;
	public SwerveModule fl;
	public SwerveModule fr;
	public SwerveModule bl;
	public SwerveModule br;
	Encoder driveEnc;
	//public PIDController pid;
	//public NumericalPIDOutput pidOutput;
	
	public Drivetrain() {
		//pidOutput = new NumericalPIDOutput();
		//Robot.gyro.get(); wtf was this
		this.fl = new SwerveModule(RobotMap.FRONT_LEFT_STEER, RobotMap.FRONT_LEFT_DRIVE, RobotMap.FL_STEER_ENCODER, RobotMap.FL_OFFSET, true);
		this.fr = new SwerveModule(RobotMap.FRONT_RIGHT_STEER, RobotMap.FRONT_RIGHT_DRIVE, RobotMap.FR_STEER_ENCODER, RobotMap.FR_OFFSET, false);
		this.bl = new SwerveModule(RobotMap.BACK_LEFT_STEER, RobotMap.BACK_LEFT_DRIVE, RobotMap.BL_STEER_ENCODER, RobotMap.BL_OFFSET, true);
		this.br = new SwerveModule(RobotMap.BACK_RIGHT_STEER, RobotMap.BACK_RIGHT_DRIVE, RobotMap.BR_STEER_ENCODER, RobotMap.BR_OFFSET, false);
		this.rangeFinder = new AnalogInput(RobotMap.RANGE_FINDER);
		this.desiredHeading = 0;
		this.driveEnc = new Encoder(RobotMap.DRIVE_CIMCODER_A, RobotMap.DRIVE_CIMCODER_B);
		this.driveEnc.setDistancePerPulse(RobotMap.DRIVE_DIST_PER_PULSE);
		shift = new DoubleSolenoid(RobotMap.PCM, RobotMap.SHIFT_FORWARD, RobotMap.SHIFT_BACKWARD);
		speedShift = true;
		this.setShift(true);
		/*this.pid = new PIDController(GYRO_P, GYRO_I, GYRO_D, Robot.gyro.get(), pidOutput, 0.002);
		this.pid.SetContinuous(true);
		this.pid.SetPercentTolerance(1);
		this.pid.SetInputRange(-180, 180.0);
		this.pid.SetOutputRange(-0.5, 0.5);
		this.pid.SetEnabled(true);*/
	}
	
	public void shift() {
		speedShift = setShift(!speedShift);
	}

	boolean setShift(boolean shifted) {
		if(shifted) {
			shift.set(DoubleSolenoid.Value.kReverse);
		} else {
			shift.set(DoubleSolenoid.Value.kForward);
		}
		return shifted;
	}
	
	public void joystickDrive() {
		double speedMultiplier = (1-Robot.oi.getSlider())/2;
		/*if (Robot::oi->joy1->GetPOV(0) != -1) {
		Robot::drivetrain->Drive(Robot::oi->joy1->GetPOV(0), 1, speedMultiplier);
		} else {*/
			Robot.drivetrain.crabDrive(Robot.oi.getX(), Robot.oi.getY(), Robot.oi.getTwist(), speedMultiplier, false);
		//}
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
		double heading = Robot.gyro.getHeading();
		forward = -x*Math.sin(heading*Math.PI/180)+y*Math.cos(heading*Math.PI/180);
		strafe = x*Math.cos(heading*Math.PI/180)+y*Math.sin(heading*Math.PI/180);
		if(x != 0 || y != 0 || rotation != 0) {
			SmartDashboard.putNumber("initrot", rotation);
			desiredHeading -= 8.0*rotation;
			desiredHeading = wrap(desiredHeading, -180.0, 180.0);
			double diff = desiredHeading-heading;
			if(diff > 180.0) { diff -= 360.0; }
			if(diff < -180.0) { diff += 360.0; }
			if(Math.abs(diff) < 7) { diff = 0; }
			diff /= 360;
			//SmartDashboard.PutNumber("ActP", this.pid.GetP());
			//SmartDashboard.PutNumber("ActI", this.pid.GetI());
			//SmartDashboard.PutNumber("ActD", this.pid.GetD());
			//this.pid.SetSetpoint(desiredHeading);
			if(useGyro) {
				rotation = diff;
			}
			SmartDashboard.putNumber("turnv", rotation);
			//SmartDashboard.PutNumber("TechnicalP", rotation/this.pid.GetError());
			double back, front, right, left;
			if(rotation != 0) {
				back = strafe-rotation*1.0/Math.sqrt(2);
				front = strafe+rotation*1.0/Math.sqrt(2);
				right = forward-rotation*1.0/Math.sqrt(2);
				left = forward+rotation*1.0/Math.sqrt(2);
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
				fla = (360+(180/Math.PI)*-Math.atan2(front, left))%360;
			}
			if(front != 0 || right != 0) {
				fra = (360+(180/Math.PI)*-Math.atan2(front, right))%360;
			}
			if(back != 0 || left != 0) {
				bla = (360+(180/Math.PI)*-Math.atan2(back, left))%360;
			}
			if(back != 0 || right != 0) {
				bra = (360+(180/Math.PI)*-Math.atan2(back, right))%360;
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
	
	private double wrap(double num, double max, double min) {
		return (num-min)-(max-min)*Math.floor((num-min)/(max-min))+min;
	}
	
	public void startTravel() {
		driveEnc.reset();
	}
	
	public double getTravel() {
		return driveEnc.getDistance();
	}
	/*void Drivetrain.SetPID(float p, float i, float d) {
	    this.pid.SetPID(p, i, d);
	}*/
}