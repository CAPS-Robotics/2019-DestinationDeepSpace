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
	public Autonomous() {
		super();
	}
	public void init(int station, String data) {
		//set which auto to run based on field data
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
				//Same side switch
				straightAhead(this.left);
				break;
			case 1:
				//In the middle
				halfWay(this.left);
				break;
			case 2:
				//Opposite side switch (probably don't do this one usually unless you want to crash into someone and break everything)
				crossField(this.left);
				break;
		}
	}

	void crossField(boolean left) {
		switch(state) {
			case 0:
				//lift arm to switch height
				timer.reset();
				timer.start();
				Robot.arm.moveTo(22);
				state++;
				break;
			case 1:
				//Move across the field to the opposite side switch vision target
				Robot.drivetrain.crabDrive(left ? -1 : 1, .2, 0, 1, false);
				SmartDashboard.putNumber("time", timer.get());
				if(Robot.vision.getCentralValue() > 120 + (left ? -80 : 80) && Robot.vision.getCentralValue() < 200 + (left ? -80 : 80)) { state++; }
				//We've gone too far.  Abort.
				if(timer.get() > 1.5) state = -1;
				break;
			case 2:
				//Go fast straight until it's close enough
				Robot.drivetrain.crabDrive(0, 1, 0, .75, false);
				if(Robot.drivetrain.getDistanceAway() < 42) state++;
				break;
			case 3:
				//Go slow straight until it's even closer
				Robot.drivetrain.crabDrive(0, 1, 0, .25, false);
				if(Robot.drivetrain.getDistanceAway() < 12) state++;
				break;
			default:
				//Go around the switch
				this.goAround(left);
				break;
			case -1:
				Robot.drivetrain.brake();
				break;
		}
	}

	void straightAhead(boolean left) {
		switch(state) {
			case 0:
				//Move arm to switch height
				timer.reset();
				timer.start();
				Robot.arm.moveTo(22);
				state++;
				break;
			case 1:
				//Move forwardish to same side switch vision target
				Robot.drivetrain.crabDrive(left ? .2 : -.2, 1, 0, 1, false);
				SmartDashboard.putNumber("time", timer.get());
				if(Robot.vision.getCentralValue() > 120 + (left ? -80 : 80) && Robot.vision.getCentralValue() < 200 + (left ? -80 : 80)) { state++; }
				//Too far
				if(timer.get() > 1.5) { state = -1; }
				break;
			case 2:
				//Go fast straight until it's close enough
				Robot.drivetrain.crabDrive(0, 1, 0, .75, false);
				if(Robot.drivetrain.getDistanceAway() < 42) state++;
				break;
			case 3:
				//Go slower straight until it's closer
				Robot.drivetrain.crabDrive(0, 1, 0, .25, false);
				if(Robot.drivetrain.getDistanceAway() < 12) state++;
				break;
			default:
				//Go around switch
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
				//arm to switch
				timer.reset();
				timer.start();
				Robot.arm.moveTo(22);
				state++;
				break;
			case 1:
				//diagonal to target
				Robot.drivetrain.crabDrive(left ? -1 : 1, 1, 0, 1, false);
				SmartDashboard.putNumber("time", timer.get());
				if(Robot.vision.getCentralValue() > 120 + (left ? -80 : 80) && Robot.vision.getCentralValue() < 200 + (left ? -80 : 80)) { state++; }
				//ahh
				if(timer.get() > 1.5) { state = -1; }
				break;
			case 2:
				//fast until distance
				Robot.drivetrain.crabDrive(0, 1, 0, .75, false);
				if(Robot.drivetrain.getDistanceAway() < 42) state++;
				break;
			case 3:
				//slow until distance
				Robot.drivetrain.crabDrive(0, 1, 0, .25, false);
				if(Robot.drivetrain.getDistanceAway() < 12) state++;
				break;
			default:
				//go around
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
				//Go sideways until you've passed the switch
				Robot.drivetrain.crabDrive(left ? -1 : 1, 0 , 0, .5, false);
				if(Robot.drivetrain.getDistanceAway() > 50) {
					Robot.drivetrain.startTravel();
					state++;
				}
				break;
			case 5:
				//Go sideways another 24 inches
				Robot.drivetrain.crabDrive(left ? -1 : 1, 0 , 0, .5, false);
				if(Math.abs(Robot.drivetrain.getTravel()) >= 24 + (left ? RobotMap.SONAR_CENTER : -RobotMap.SONAR_CENTER)) {
					Robot.drivetrain.startTravel();
					state++;
				}
				break;
			case 6:
				//Go forwards 100 inches
				Robot.drivetrain.crabDrive(0, 1, 0, .5, false);
				if(Math.abs(Robot.drivetrain.getTravel()) >= 100) {
					Robot.drivetrain.startTravel();
					state++;
				}
				break;
			case 7:
				//Sideways back to switch
				Robot.drivetrain.crabDrive(left ? -1 : 1, 0, 0, .5, false);
				if(Math.abs(Robot.drivetrain.getTravel()) >= 30.5) state++;
			case 8:
				//Turn 180
				Robot.drivetrain.crabDrive(0, 0, Robot.gyro.getHeading() > 0 ? 1 : -1, 0.5, false);
				if(Math.abs(Robot.gyro.getHeading() - 180) < 5) {
					Robot.arm.open();
					Robot.arm.kickDown();
					state++;
				}
				break;
			case 9:
				//Grab a new cube
				Robot.arm.open();
				Robot.drivetrain.crabDrive(0, -1, 0, 0.25, false);
				if(Robot.drivetrain.getDistanceAway() < 2) {
					Robot.arm.close();
					state++;
				}
				break;
			default:
				//Scale auto *magic*
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
				//lift arm to talll
				Robot.arm.moveTo(72);
				state++;
				break;
			case 11:
				//turn 180
				Robot.drivetrain.crabDrive(0, 0, Robot.gyro.getHeading() < 0 ? 1 : -1, 0.5, false);
				if(Math.abs(Robot.gyro.getHeading()) < 5) {
					Robot.drivetrain.startTravel();
					state++;
				}
				break;
			case 12:
				//go sideways to the scale
				Robot.drivetrain.crabDrive(scLeft ? -1 : 1, 0, 0, .5, false);
				if(Math.abs(Robot.drivetrain.getTravel()) >= 18) {
					Robot.drivetrain.startTravel();
					Robot.arm.kickUp();
					state++;
				}
				break;
			case 13:
				//go forward to the scale and drop
				Robot.drivetrain.crabDrive(0, 1, 0, .75, false);
				if(Math.abs(Robot.drivetrain.getTravel()) >= 65) {
					Robot.arm.open();
					Robot.arm.kickDown();
					Robot.drivetrain.startTravel();
					state++;
				}
				break;
			case 14:
				//back off scale and lower arm
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
				//arm up
				Robot.arm.moveTo(72);
				state++;
				break;
			case 11:
				//turn around
				Robot.drivetrain.crabDrive(0, 0, Robot.gyro.getHeading() < 0 ? 1 : -1, 0.5, false);
				if(Math.abs(Robot.gyro.getHeading()) < 5) {
					Robot.drivetrain.startTravel();
					state++;
				}
				break;
			case 12:
				//go to the other scale
				Robot.drivetrain.crabDrive(scLeft ? 1 : -1, 0, 0, .5, false);
				if(Math.abs(Robot.drivetrain.getTravel()) >= 159) {
					Robot.drivetrain.startTravel();
					Robot.arm.kickUp();
					state++;
				}
				break;
			case 13:
				//go forward and drop
				Robot.drivetrain.crabDrive(0, 1, 0, .75, false);
				if(Math.abs(Robot.drivetrain.getTravel()) >= 65) {
					Robot.arm.open();
					Robot.arm.kickDown();
					Robot.drivetrain.startTravel();
					state++;
				}
				break;
			case 14:
				//back up and lower
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