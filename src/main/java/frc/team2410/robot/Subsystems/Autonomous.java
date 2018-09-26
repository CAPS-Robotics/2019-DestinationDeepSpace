package frc.team2410.robot.Subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team2410.robot.Robot;
import frc.team2410.robot.RobotMap;

public class Autonomous
{
	private int autoNum;
	private boolean left;
	private boolean scLeft;
	private boolean autoSc;
	private int state;
	private Timer timer;
	Autonomous() {
		super();
	}
	public void init(int station, String data)
	{
		state = 0;
		this.left = data.charAt(0) == 'L';
		this.scLeft = data.charAt(1) == 'L';
		this.autoSc = left == scLeft;
		if (station == 1) {
			this.autoNum = 1;
		} else if ((station == 0 && left) || (station == 2 && !left)) {
			this.autoNum = 0;
		} else {
			this.autoNum = 2;
		}
		timer = new Timer();
	}

	public void loop() {
		switch(autoNum) {
			case 0:
				straightAhead(this.left);
				break;
			case 1:
				halfWay(this.left);
				break;
			case 2:
				crossField(this.left);
				break;
		}
	}

	public void crossField(boolean left) {
		switch(state) {
			case 0:
				timer.reset();
				timer.start();
				Robot.arm.moveTo(22);
				state++;
				break;
			case 1:
				Robot.drivetrain.crabDrive(left ? -1 : 1, .2, 0, 1, false);
				SmartDashboard.putNumber("time", timer.get());
				if(Robot.vision.getCentralValue() > 120 + (left ? -80 : 80) && Robot.vision.getCentralValue() < 200 + (left ? -80 : 80)) { state++; }
				if(timer.get() > 1.5) { state = -1; }
				break;
			case 2:
				Robot.drivetrain.crabDrive(0, 1, 0, .75, false);
				if(Robot.drivetrain.getDistanceAway() < 42) state++;
				break;
			case 3:
				Robot.drivetrain.crabDrive(0, 1, 0, .25, false);
				if(Robot.drivetrain.getDistanceAway() < 12) state++;
				break;
			default:
				this.goAround(left);
				break;
			case -1:
				Robot.drivetrain.brake();
				break;
		}
	}

	public void straightAhead(boolean left) {
		switch(state) {
			case 0:
				timer.reset();
				timer.start();
				Robot.arm.moveTo(22);
				state++;
				break;
			case 1:
				Robot.drivetrain.crabDrive(left ? .2 : -.2, 1, 0, 1, false);
				SmartDashboard.putNumber("time", timer.get());
				if(Robot.vision.getCentralValue() > 120 + (left ? -80 : 80) && Robot.vision.getCentralValue() < 200 + (left ? -80 : 80)) { state++; }
				if(timer.get() > 1.5) { state = -1; }
				break;
			case 2:
				Robot.drivetrain.crabDrive(0, 1, 0, .75, false);
				if(Robot.drivetrain.getDistanceAway() < 42) state++;
				break;
			case 3:
				Robot.drivetrain.crabDrive(0, 1, 0, .25, false);
				if(Robot.drivetrain.getDistanceAway() < 12) state++;
				break;
			default:
				this.goAround(left);
				break;
			case -1:
				Robot.drivetrain.brake();
				break;
		}
	}

	void halfWay(boolean left) {
		switch(state) {
			case 0:
				timer.reset();
				timer.start();
				Robot.arm.moveTo(22);
				state++;
				break;
			case 1:
				Robot.drivetrain.crabDrive(left ? -1 : 1, 1, 0, 1, false);
				SmartDashboard.putNumber("time", timer.get());
				if(Robot.vision.getCentralValue() > 120 + (left ? -80 : 80) && Robot.vision.getCentralValue() < 200 + (left ? -80 : 80)) { state++; }
				if(timer.get() > 1.5) { state = -1; }
				break;
			case 2:
				Robot.drivetrain.crabDrive(0, 1, 0, .75, false);
				if(Robot.drivetrain.getDistanceAway() < 42) state++;
				break;
			case 3:
				Robot.drivetrain.crabDrive(0, 1, 0, .25, false);
				if(Robot.drivetrain.getDistanceAway() < 12) state++;
				break;
			default:
				this.goAround(left);
				break;
			case -1:
				Robot.drivetrain.brake();
				break;
		}
	}

	void goAround(boolean left) {
		switch(state) {
			case 4:
				Robot.drivetrain.crabDrive(left ? -1 : 1, 0 , 0, .5, false);
				if(Robot.drivetrain.getDistanceAway() > 50) {
					Robot.drivetrain.startTravel();
					state++;
				}
				break;
			case 5:
				Robot.drivetrain.crabDrive(left ? -1 : 1, 0 , 0, .5, false);
				if(Math.abs(Robot.drivetrain.getTravel()) >= 24 + (left ? RobotMap.SONAR_CENTER : -RobotMap.SONAR_CENTER)) {
					Robot.drivetrain.startTravel();
					state++;
				}
				break;
			case 6:
				Robot.drivetrain.crabDrive(0, 1, 0, .5, false);
				if(Math.abs(Robot.drivetrain.getTravel()) >= 100) {
					Robot.drivetrain.startTravel();
					state++;
				}
				break;
			case 7:
				Robot.drivetrain.crabDrive(left ? -1 : 1, 0, 0, .5, false);
				if(Math.abs(Robot.drivetrain.getTravel()) >= 30.5) state++;
			case 8:
				Robot.drivetrain.crabDrive(0, 0, Robot.gyro.getHeading() > 0 ? 1 : -1, 0.5, false);
				if(Math.abs(Robot.gyro.getHeading() - 180) < 5) {
					Robot.arm.open();
					Robot.arm.kickDown();
					state++;
				}
				break;
			case 9:
				Robot.arm.open();
				Robot.drivetrain.crabDrive(0, -1, 0, 0.25, false);
				if(Robot.drivetrain.getDistanceAway() < 2) {
					Robot.arm.close();
					state++;
				}
				break;
			default:
				if(autoSc)
					scaleAhead(scLeft);
				else
					scaleAcross(scLeft);
				break;
			case -1:
				Robot.drivetrain.brake();
				break;
		}
	}

	void scaleAhead(boolean left) {
		switch(state) {
			case 10:
				Robot.arm.moveTo(72);
				state++;
				break;
			case 11:
				Robot.drivetrain.crabDrive(0, 0, Robot.gyro.getHeading() < 0 ? 1 : -1, 0.5, false);
				if(Math.abs(Robot.gyro.getHeading()) < 5) {
					Robot.drivetrain.startTravel();
					state++;
				}
				break;
			case 12:
				Robot.drivetrain.crabDrive(scLeft ? -1 : 1, 0, 0, .5, false);
				if(Math.abs(Robot.drivetrain.getTravel()) >= 18) {
					Robot.drivetrain.startTravel();
					Robot.arm.kickUp();
					state++;
				}
				break;
			case 13:
				Robot.drivetrain.crabDrive(0, 1, 0, .75, false);
				if(Math.abs(Robot.drivetrain.getTravel()) >= 65) {
					Robot.arm.open();
					Robot.arm.kickDown();
					Robot.drivetrain.startTravel();
					state++;
				}
				break;
			case 14:
				Robot.drivetrain.crabDrive(0, -1, 0, .5, false);
				if(Math.abs(Robot.drivetrain.getTravel()) >= 21) {
					Robot.arm.moveTo(0);
					state++;
				}
			default:
				Robot.drivetrain.brake();
				break;
		}
	}

	void scaleAcross(boolean left) {
		switch(state) {
			case 10:
				Robot.arm.moveTo(72);
				state++;
				break;
			case 11:
				Robot.drivetrain.crabDrive(0, 0, Robot.gyro.getHeading() < 0 ? 1 : -1, 0.5, false);
				if(Math.abs(Robot.gyro.getHeading()) < 5) {
					Robot.drivetrain.startTravel();
					state++;
				}
				break;
			case 12:
				Robot.drivetrain.crabDrive(scLeft ? 1 : -1, 0, 0, .5, false);
				if(Math.abs(Robot.drivetrain.getTravel()) >= 159) {
					Robot.drivetrain.startTravel();
					Robot.arm.kickUp();
					state++;
				}
				break;
			case 13:
				Robot.drivetrain.crabDrive(0, 1, 0, .75, false);
				if(Math.abs(Robot.drivetrain.getTravel()) >= 65) {
					Robot.arm.open();
					Robot.arm.kickDown();
					Robot.drivetrain.startTravel();
					state++;
				}
				break;
			case 14:
				Robot.drivetrain.crabDrive(0, -1, 0, .5, false);
				if(Math.abs(Robot.drivetrain.getTravel()) >= 21) {
					Robot.arm.moveTo(0);
					state++;
				}
			default:
				Robot.drivetrain.brake();
				break;
		}
	}
}