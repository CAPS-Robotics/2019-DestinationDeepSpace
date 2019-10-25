package frc.team2410.robot.Subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.Timer;
import frc.team2410.robot.Robot;

public class Autonomous {
	public Timer timer;
	private int autoNumber;
	private int state = 0;
	public boolean autoDone = false;
	
	public Autonomous() {
	
	}
	
	public void init(int autoNumber) {
		this.autoNumber = autoNumber;
		timer = new Timer();
	}
	
	public void loop() {
		SmartDashboard.putNumber("Auto State", state);
		SmartDashboard.putNumber("Auto Number", autoNumber);
		switch(autoNumber) {
			case 0:
				autoDone = true;
				break;
			case 1:
				leftCargoship();
				break;
			case 2:
				break;
			case 3:
				break;
		}
	}
	public void leftCargoship() {
		switch(state) {
			case 0:
				timer.reset();
				state++;
				break;
			case 1:
				Robot.drivetrain.crabDrive(0, 1, 0, 1, true);
				if(timer.get() > 0.5) {
					Robot.drivetrain.brake();
					autoDone = true;
					state++;
				}
				break;
		}
	}
}
