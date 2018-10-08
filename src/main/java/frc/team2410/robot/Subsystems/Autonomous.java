package frc.team2410.robot.Subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team2410.robot.Robot;
import frc.team2410.robot.RobotMap;

public class Autonomous
{
	private int autoNum;
	private boolean swLeft;
	private boolean scLeft;
	private boolean roLeft;
	private boolean autoSc;
	private int state;
	private Timer timer;
	public Autonomous() {
		super();
	}
	public void init(int station, String data) {
		//set which auto to run based on field data
		SmartDashboard.putNumber("Auto Picked", station);
		state = 0;
		this.swLeft = data.charAt(0) == 'L';
		this.scLeft = data.charAt(1) == 'L';
		this.autoSc = swLeft == scLeft;
		Robot.vision.setCamera(swLeft ? 1 : 0);
		if (station == 1) {
			this.autoNum = 0;
		} else if (station == 0 || station == 2) {
			this.autoNum = 1;
			this.roLeft = station == 0;
		} else {
			this.autoNum = 2;
			this.roLeft = station == 3;
		}
		timer = new Timer();
		Robot.arm.setPosition(27);
	}

	public void loop() {
		SmartDashboard.putNumber("Auto State", state);
		switch(autoNum) {
			case 0:
				//Same side switch
				halfWay(this.swLeft);
				break;
			case 1:
				//In the middle
				sideAuto(this.roLeft);
				break;
			case 2:
				//Opposite side switch (probably don't do this one usually unless you want to crash into someone and break everything)
				scaleAuto(this.roLeft);
				break;
		}
	}

	/*void crossField(boolean left) {
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
	}*/

	/*void straightAhead(boolean left) {
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
	}*/

	void halfWay(boolean left) {
		switch(state) {
			case -1:
				Robot.drivetrain.brake();
				break;
			case 0:
				//arm to switch
				Robot.gyro.resetHeading(0);
				timer.reset();
				timer.start();
				Robot.arm.moveTo(20);
				state++;
				break;
			case 1:
				//diagonal to target
				Robot.drivetrain.crabDrive(left ? -1 : 1, 1, 0, .9, false);
				if(Robot.vision.getCentralValue() > 120 + (left ? -20 : 80) && Robot.vision.getCentralValue() < 200 + (left ? -20 : 80)) {
					state++;
					timer.reset();
					timer.start();
				} else if(timer.get() > 1.35) {
					state++;
					timer.reset();
					timer.start();
				}
				break;
			case 2:
				//fast until distance
				Robot.drivetrain.crabDrive(0, 1, 0, .5, false);
				if(Robot.drivetrain.getDistanceAway() < 36) {
					timer.stop();
					state++;
				} else if(timer.get() > 1.25)  {
					timer.stop();
					state++;
				}
				break;
			case 3:
				//slow until distance
				Robot.drivetrain.crabDrive(0, 1, 0, .25, false);
				if(Robot.drivetrain.getDistanceAway() < 14 || timer.get() > 1.25)  {
					Robot.arm.open();
					Robot.arm.kickDown();
					timer.reset();
					timer.start();
					state++;
				}
				break;
			default:
				//go around but don't actually
				state = -1;
				//this.goAround(left);
				break;
		}
	}

	/*void goAround(boolean left) {
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
	}*/

	/*void scaleAhead(boolean left) {
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
	}*/

	/*void scaleAcross(boolean left) {
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
	}*/
	void sideAuto(boolean left) {
		switch(state) {
			case 0:
				Robot.gyro.resetHeading(left ? 90 : -90);
				Robot.drivetrain.startTravel();
				state++;
				break;
			case 1:
				Robot.drivetrain.crabDrive(0, 1, 0, 0.85, false);
				if(Math.abs(Robot.drivetrain.getTravel()) >= 120) {
				state += (scLeft == roLeft ? 1 : 3);
				Robot.drivetrain.startTravel();
				if(scLeft == roLeft) { Robot.arm.moveTo (70); }
			}
			break;
			case 2:
				Robot.drivetrain.crabDrive(roLeft ? -1 : 1, 0, 0, .5, false);
				if(Math.abs(Robot.drivetrain.getTravel()) >= 12) {
				state++;
				Robot.drivetrain.startTravel();
				Robot.arm.moveTo(0);
			}
			break;
			case 3:
				Robot.drivetrain.crabDrive(0, 1, 0, 0.85, false);
				if(Math.abs(Robot.drivetrain.getTravel()) >= 186) {
				state++;
				Robot.drivetrain.startTravel();
				if(scLeft == roLeft) { Robot.arm.moveTo(70); }
			}
			break;
			case 4:
				Robot.drivetrain.crabDrive(0, 0, Robot.gyro.getHeading() > (left ? 90 : -90) ? 1 : -1, 0.4, false);
				if(Math.abs(Robot.gyro.getHeading() + (left ? 90 : -90)) < 5) {
				state++;
				Robot.drivetrain.startTravel();
			}
			break;
			case 5:
				Robot.drivetrain.brake();
				if(Robot.arm.getPosition() > 65 || scLeft != roLeft) {
				state++;
				timer.reset();
				timer.start();
			}
			break;
			case 6:
				if(scLeft != roLeft && swLeft != roLeft) {
					state = -1;
				}
				Robot.drivetrain.crabDrive(roLeft ? 1 : -1, 0, 0, .3, false);
				if(Math.abs(Robot.drivetrain.getTravel()) >= /*(scLeft == roLeft ? 6 : */18/*) || (timer.Get() > (scLeft == roLeft ? 0.5 : 2))*/) {
				state++;
				timer.stop();
				Robot.arm.open();
				Robot.arm.kickDown();
				Robot.drivetrain.startTravel();
			}
			break;
			case 7:
				Robot.drivetrain.crabDrive(roLeft ? -1 : 1, 0, 0, .5, false);
				if(Math.abs(Robot.drivetrain.getTravel()) >= 18) {
				state++;
				Robot.arm.moveTo(0);
			}
			break;
			default:
				Robot.drivetrain.brake();
		}
	}
	void scaleAuto(boolean left) {
		switch(state) {
			case 0:
				Robot.gyro.resetHeading(left ? 90 : -90);
				Robot.drivetrain.startTravel();
				state++;
				break;
			case 1:
				Robot.drivetrain.crabDrive(0, 1, 0, 0.9, false);
				if(Math.abs(Robot.drivetrain.getTravel()) >=200){
					state += ((scLeft == roLeft) ? 2 : 1);
					Robot.drivetrain.startTravel();
					if(scLeft == roLeft) Robot.arm.moveTo(70);
				} break;
			case 2:
				Robot.drivetrain.crabDrive(1, 0, 0, 0.5, false);
				if(Math.abs(Robot.drivetrain.getTravel()) >=213){
					Robot.arm.moveTo(70);
					state++;
					Robot.drivetrain.startTravel();
					//Robot.arm.moveTo(70);
				} break;
			case 3:
				Robot.drivetrain.crabDrive(0, 1, 0, 0.9, false);
				if(Math.abs(Robot.drivetrain.getTravel()) >=106){
					state++;
					Robot.drivetrain.startTravel();
				} break;
			case 4:
				Robot.drivetrain.crabDrive(0, 0, Robot.gyro.getHeading() > (scLeft ? 90 : -90) ? 1 : -1, 0.4, false);
				if(Math.abs(Robot.gyro.getHeading()+(scLeft ? 90 : -90)) < 5){
					state++;
					Robot.drivetrain.startTravel();
				} break;
			case 5:
				Robot.drivetrain.brake();
				Robot.arm.moveTo(70);
				if(Robot.arm.getPosition() > 65){
					state++;
					timer.reset();
					timer.start();
				}
				break;
			case 6:
				Robot.drivetrain.crabDrive(scLeft ? 1 : -1, 0, 0, .3, false);
				if(Math.abs(Robot.drivetrain.getTravel()) >=6 || timer.get() > 0.5){
					state++;
					timer.stop();
					Robot.arm.open();
					Robot.arm.kickDown();
					Robot.drivetrain.startTravel();
				} break;
			case 7:
				Robot.drivetrain.crabDrive(scLeft ? -1 : 1, 0, 0, .5, false);
				if(Math.abs(Robot.drivetrain.getTravel()) >= 18){
					state++;
					Robot.arm.moveTo(0);
				} break;
			default:
				Robot.drivetrain.brake();
		}
	}
}