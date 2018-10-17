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
				//Middle god auto
				halfWay(this.swLeft);
				break;
			case 1:
				//switch or scale on same side
				sideAuto(this.roLeft);
				break;
			case 2:
				//does the scale in auto
				scaleAuto(this.roLeft);
				break;
		}
	}
	
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