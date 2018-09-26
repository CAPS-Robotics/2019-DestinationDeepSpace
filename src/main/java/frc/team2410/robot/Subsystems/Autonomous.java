package frc.team2410.robot;

import frc.team2410.robot.Robot;

//Autonomous::Autonomous() = default;
public class Autonomous
{
	private int autoNum;
	private bool left;
	private bool scLeft;
	private bool autoSc;
	private int state;
	private Timer timer;
	
	public void Init(int station, String data)
	{
		state = 0;
		if (station == 1) {
			this.autoNum = 1;
		} else if ((station == 0 && data[0] == 'L') || (station == 2 && data[0] == 'R')) {
			this.autoNum = 0;
		} else {
			this.autoNum = 2;
		}
		this.left = data[0] == 'L';
		this.scLeft = data[1] == 'L';
		this.autoSc = left == scLeft;
		timer = new Timer();
	}

	public void Loop() {
		switch(autoNum) {
			case 0:
				StraightAhead(this.left);
				break;
			case 1:
				HalfWay(this.left);
				break;
			case 2:
				CrossField(this.left);
				break;
		}
	}

	public void CrossField(bool left) {
		switch(state) {
			case 0:
				timer.Reset();
				timer.Start();
				Robot.arm.MoveTo(22);
				state++;
				break;
			case 1:
				Robot.drivetrain.CrabDrive(left ? -1 : 1, .2, 0, 1, false);
				SmartDashboard.PutNumber("time", timer.Get());
				if(Robot.vision.GetCentralValue() > 120 + (left ? -80 : 80) && Robot.vision.GetCentralValue() < 200 + (left ? -80 : 80)) { state++; }
				if(timer.Get() > 1.5) { state = -1; }
				break;
			case 2:
				Robot.drivetrain.CrabDrive(0, 1, 0, .75, false);
				if(Robot.drivetrain.GetDistanceAway() < 42) state++;
				break;
			case 3:
				Robot.drivetrain.CrabDrive(0, 1, 0, .25, false);
				if(Robot.drivetrain.GetDistanceAway() < 12) state++;
				break;
			default:
				this.GoAround(left);
				break;
			case -1:
				Robot.drivetrain.Brake();
				break;
		}
	}

	public void StraightAhead(bool left) {
		switch(state) {
			case 0:
				timer.Reset();
				timer.Start();
				Robot.arm.MoveTo(22);
				state++;
				break;
			case 1:
				Robot.drivetrain.CrabDrive(left ? .2 : -.2, 1, 0, 1, false);
				SmartDashboard.PutNumber("time", timer.Get());
				if(Robot.vision.GetCentralValue() > 120 + (left ? -80 : 80) && Robot.vision.GetCentralValue() < 200 + (left ? -80 : 80)) { state++; }
				if(timer.Get() > 1.5) { state = -1; }
				break;
			case 2:
				Robot.drivetrain.CrabDrive(0, 1, 0, .75, false);
				if(Robot.drivetrain.GetDistanceAway() < 42) state++;
				break;
			case 3:
				Robot.drivetrain.CrabDrive(0, 1, 0, .25, false);
				if(Robot.drivetrain.GetDistanceAway() < 12) state++;
				break;
			default:
				this.GoAround(left);
				break;
			case -1:
				Robot.drivetrain.Brake();
				break;
		}
	}

	void Autonomous.HalfWay(bool left) {
		switch(state) {
			case 0:
				timer.Reset();
				timer.Start();
				Robot.arm.MoveTo(22);
				state++;
				break;
			case 1:
				Robot.drivetrain.CrabDrive(left ? -1 : 1, 1, 0, 1, false);
				SmartDashboard.PutNumber("time", timer.Get());
				if(Robot.vision.GetCentralValue() > 120 + (left ? -80 : 80) && Robot.vision.GetCentralValue() < 200 + (left ? -80 : 80)) { state++; }
				if(timer.Get() > 1.5) { state = -1; }
				break;
			case 2:
				Robot.drivetrain.CrabDrive(0, 1, 0, .75, false);
				if(Robot.drivetrain.GetDistanceAway() < 42) state++;
				break;
			case 3:
				Robot.drivetrain.CrabDrive(0, 1, 0, .25, false);
				if(Robot.drivetrain.GetDistanceAway() < 12) state++;
				break;
			default:
				this.GoAround(left);
				break;
			case -1:
				Robot.drivetrain.Brake();
				break;
		}
	}

	void Autonomous.GoAround(bool left) {
		switch(state) {
			case 4:
				Robot.drivetrain.CrabDrive(left ? -1 : 1, 0 , 0, .5, false);
				if(Robot.drivetrain.GetDistanceAway() > 50) {
					Robot.drivetrain.StartTravel();
					state++;
				}
				break;
			case 5:
				Robot.drivetrain.CrabDrive(left ? -1 : 1, 0 , 0, .5, false);
				if(fabs(Robot.drivetrain.GetTravel()) >= 24 + left ? SONAR_CENTER : -SONAR_CENTER) {
					Robot.drivetrain.StartTravel();
					state++;
				}
				break;
			case 6:
				Robot.drivetrain.CrabDrive(0, 1, 0, .5, false);
				if(fabs(Robot.drivetrain.GetTravel()) >= 100) {
					Robot.drivetrain.StartTravel();
					state++;
				}
				break;
			case 7:
				Robot.drivetrain.CrabDrive(left ? -1 : 1, 0, 0, .5, false);
				if(fabs(Robot.drivetrain.GetTravel()) >= 30.5) state++;
			case 8:
				Robot.drivetrain.CrabDrive(0, 0, Robot.gyro.GetHeading() > 0 ? 1 : -1, 0.5, false);
				if(fabs(Robot.gyro.GetHeading() - 180) < 5) {
					Robot.arm.Open();
					Robot.arm.KickDown();
					state++;
				}
				break;
			case 9:
				Robot.arm.Open();
				Robot.drivetrain.CrabDrive(0, -1, 0, 0.25, false);
				if(Robot.drivetrain.GetDistanceAway() < 2) {
					Robot.arm.Close();
					state++;
				}
				break;
			default:
				if(autoSc)
					ScaleAhead(scLeft);
				else
					ScaleAcross(scLeft);
				break;
			case -1:
				Robot.drivetrain.Brake();
				break;
		}
	}

	void Autonomous.ScaleAhead(bool left) {
		switch(state) {
			case 10:
				Robot.arm.MoveTo(72);
				state++;
				break;
			case 11:
				Robot.drivetrain.CrabDrive(0, 0, Robot.gyro.GetHeading() < 0 ? 1 : -1, 0.5, false);
				if(fabs(Robot.gyro.GetHeading()) < 5) {
					Robot.drivetrain.StartTravel();
					state++;
				}
				break;
			case 12:
				Robot.drivetrain.CrabDrive(scLeft ? -1 : 1, 0, 0, .5, false);
				if(fabs(Robot.drivetrain.GetTravel()) >= 18) {
					Robot.drivetrain.StartTravel();
					Robot.arm.KickUp();
					state++;
				}
				break;
			case 13:
				Robot.drivetrain.CrabDrive(0, 1, 0, .75, false);
				if(fabs(Robot.drivetrain.GetTravel()) >= 65) {
					Robot.arm.Open();
					Robot.arm.KickDown();
					Robot.drivetrain.StartTravel();
					state++;
				}
				break;
			case 14:
				Robot.drivetrain.CrabDrive(0, -1, 0, .5, false);
				if(fabs(Robot.drivetrain.GetTravel()) >= 21) {
					Robot.arm.MoveTo(0);
					state++;
				}
			default:
				Robot.drivetrain.Brake();
				break;
		}
	}

	void Autonomous.ScaleAcross(bool left) {
		switch(state) {
			case 10:
				Robot.arm.MoveTo(72);
				state++;
				break;
			case 11:
				Robot.drivetrain.CrabDrive(0, 0, Robot.gyro.GetHeading() < 0 ? 1 : -1, 0.5, false);
				if(fabs(Robot.gyro.GetHeading()) < 5) {
					Robot.drivetrain.StartTravel();
					state++;
				}
				break;
			case 12:
				Robot.drivetrain.CrabDrive(scLeft ? 1 : -1, 0, 0, .5, false);
				if(fabs(Robot.drivetrain.GetTravel()) >= 159) {
					Robot.drivetrain.StartTravel();
					Robot.arm.KickUp();
					state++;
				}
				break;
			case 13:
				Robot.drivetrain.CrabDrive(0, 1, 0, .75, false);
				if(fabs(Robot.drivetrain.GetTravel()) >= 65) {
					Robot.arm.Open();
					Robot.arm.KickDown();
					Robot.drivetrain.StartTravel();
					state++;
				}
				break;
			case 14:
				Robot.drivetrain.CrabDrive(0, -1, 0, .5, false);
				if(fabs(Robot.drivetrain.GetTravel()) >= 21) {
					Robot.arm.MoveTo(0);
					state++;
				}
			default:
				Robot.drivetrain.Brake();
				break;
		}
	}*/
}