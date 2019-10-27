package frc.team2410.robot.Subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.Timer;
import frc.team2410.robot.Robot;

import static frc.team2410.robot.RobotMap.*;

public class Autonomous {
	public Timer timer;
	private int autoNumber;
	private int state = 0;
	private boolean autoDone = false;
	
	public Autonomous() {
	
	}
	
	public void init(int autoNumber) {
		this.autoNumber = autoNumber;
		timer = new Timer();
	}

	public boolean getAutoDone() { return autoDone; }
	public void setAutoDone(boolean autoDone) { this.autoDone = autoDone; }
	
	public void loop() {
		SmartDashboard.putNumber("Auto State", state);
		SmartDashboard.putNumber("Auto Number", autoNumber);
		switch(autoNumber) {
			case 0:
				autoDone = true;
				break;
			case 1:
				cargoship(true);
				break;
			case 2:
				cargoship(false);
				break;
			case 3:
				rocketFront(true);
				break;
			case 4:
				rocketFront(false);
			default:
				autoDone = true;
				break;
		}
	}

	private void cargoship(boolean left) {
		final double STRAFE_SPEED = 0.1;

		switch(state) {
			case 0:
				timer.reset();
				timer.start();
				state++;
				break;
			case 1:
				// Drive to cargoship
				Robot.drivetrain.crabDrive(left ? -STRAFE_SPEED : STRAFE_SPEED, 0.5, 0, 1, true);
				if(timer.get() > 1) {
					Robot.drivetrain.brake();
					timer.reset();
					timer.start();
					state++;
				}
				break;
			case 2:
				// Raise elevator and extend wrist
				Robot.semiAuto.elevatorSetpoint(HATCH_WRIST_ANGLE, PLACE_HEIGHT[0], true);
				if(timer.get() > 0.25) {
					timer.reset();
					autoDone = true;
				}
				break;
		}
	}

	private void rocketFront(boolean left) {
		final double X_SPEED = 0.4;

		switch(state) {
			case 0:
				timer.reset();
				timer.start();
				state++;
				break;
			case 1:
				// Drive off hab
				Robot.drivetrain.crabDrive(0, 0.5, 0, 1, true);
				if(timer.get() > 0.5) {
					Robot.drivetrain.brake();
					Robot.drivetrain.desiredHeading = left ? ROCKET_LEFT_LEFT : ROCKET_RIGHT_RIGHT;
					timer.reset();
					timer.start();
					state++;
				}
				break;
			case 2:
				// Drive to rocket
				Robot.drivetrain.crabDrive(left ? -X_SPEED : X_SPEED, 0.4, 0, 1, true);
				if(timer.get() > 2) {
					Robot.drivetrain.brake();
					timer.reset();
					timer.start();
					state++;
				}
				break;
			case 3:
				// Raise elevator and extend wrist
				Robot.semiAuto.elevatorSetpoint(HATCH_WRIST_ANGLE, PLACE_HEIGHT[0], true);
				if(timer.get() > 0.25) {
					timer.reset();
					autoDone = true;
				}
				break;
		}
	}
}
