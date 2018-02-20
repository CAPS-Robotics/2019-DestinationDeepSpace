#include <Robot.h>
#include "Autonomous.h"

Autonomous::Autonomous() = default;

void Autonomous::Init(int station, std::string data) {
	state = 0;
    if(station == 1) {
        this->autoNum = 1;
    } else if((station == 0 && data[0] == 'L') || (station == 2 && data[0] == 'R')) {
        this->autoNum = 0;
    } else {
        this->autoNum = 2;
    }
	this->left = data[0] == 'L';
	this->scLeft = data[1] == 'L';
	this->autoSc = left == scLeft;
	timer = new Timer();
}

void Autonomous::Loop() {
	switch(autoNum) {
		case 0:
			StraightAhead(this->left);
			break;
		case 1:
			HalfWay(this->left);
			break;
		case 2:
			CrossField(this->left);
			break;
	}
}

void Autonomous::CrossField(bool left) {
	switch(state) {
		case 0:
			timer->Reset();
			timer->Start();
			Robot::arm->MoveTo(22);
			state++;
			break;
		case 1:
			Robot::drivetrain->CrabDrive(left ? -1 : 1, .2, 0, 1, false);
			SmartDashboard::PutNumber("time", timer->Get());
			if(Robot::vision->GetCentralValue() > 120 + (left ? -80 : 80) && Robot::vision->GetCentralValue() < 200 + (left ? -80 : 80)) { state++; }
			if(timer->Get() > 1.5) { state = -1; }
			break;
		case 2:
			Robot::drivetrain->CrabDrive(0, 1, 0, .75, false);
			if(Robot::drivetrain->GetDistanceAway() < 42) state++;
			break;
		case 3:
			Robot::drivetrain->CrabDrive(0, 1, 0, .25, false);
			if(Robot::drivetrain->GetDistanceAway() < 12) state++;
			break;
		default:
			this->GoAround(left);
			break;
		case -1:
			Robot::drivetrain->Brake();
			break;
	}
}

void Autonomous::StraightAhead(bool left) {
	switch(state) {
		case 0:
			timer->Reset();
			timer->Start();
			Robot::arm->MoveTo(22);
			state++;
			break;
		case 1:
			Robot::drivetrain->CrabDrive(left ? .2 : -.2, 1, 0, 1, false);
			SmartDashboard::PutNumber("time", timer->Get());
			if(Robot::vision->GetCentralValue() > 120 + (left ? -80 : 80) && Robot::vision->GetCentralValue() < 200 + (left ? -80 : 80)) { state++; }
			if(timer->Get() > 1.5) { state = -1; }
			break;
		case 2:
			Robot::drivetrain->CrabDrive(0, 1, 0, .75, false);
			if(Robot::drivetrain->GetDistanceAway() < 42) state++;
			break;
		case 3:
			Robot::drivetrain->CrabDrive(0, 1, 0, .25, false);
			if(Robot::drivetrain->GetDistanceAway() < 12) state++;
			break;
		default:
			this->GoAround(left);
			break;
		case -1:
			Robot::drivetrain->Brake();
			break;
	}
}

void Autonomous::HalfWay(bool left) {
	switch(state) {
		case 0:
			timer->Reset();
			timer->Start();
			Robot::arm->MoveTo(22);
			state++;
			break;
		case 1:
			Robot::drivetrain->CrabDrive(left ? -1 : 1, 1, 0, 1, false);
			SmartDashboard::PutNumber("time", timer->Get());
			if(Robot::vision->GetCentralValue() > 120 + (left ? -80 : 80) && Robot::vision->GetCentralValue() < 200 + (left ? -80 : 80)) { state++; }
			if(timer->Get() > 1.5) { state = -1; }
			break;
		case 2:
			Robot::drivetrain->CrabDrive(0, 1, 0, .75, false);
			if(Robot::drivetrain->GetDistanceAway() < 42) state++;
			break;
		case 3:
			Robot::drivetrain->CrabDrive(0, 1, 0, .25, false);
			if(Robot::drivetrain->GetDistanceAway() < 12) state++;
			break;
		default:
			this->GoAround(left);
			break;
		case -1:
			Robot::drivetrain->Brake();
			break;
	}
}

void Autonomous::GoAround(bool left) {
	switch(state) {
		case 4:
			Robot::drivetrain->CrabDrive(left ? -1 : 1, 0 , 0, .5, false);
			if(Robot::drivetrain->GetDistanceAway() > 50) {
				Robot::drivetrain->StartTravel();
				state++;
			}
			break;
		case 5:
			Robot::drivetrain->CrabDrive(left ? -1 : 1, 0 , 0, .5, false);
			if(fabs(Robot::drivetrain->GetTravel()) >= 24 + left ? SONAR_CENTER : -SONAR_CENTER) {
				Robot::drivetrain->StartTravel();
				state++;
			}
			break;
		case 6:
			Robot::drivetrain->CrabDrive(1, 0, 0, .5, false);
			if(Robot::drivetrain->GetTravel() >= 100) state++;
			break;
		case 7:
			Robot::drivetrain->CrabDrive(0, 0, Robot::gyro->GetHeading() < 180 ? 1 : -1, 0.5, false);
			if(fabs(Robot::gyro->GetHeading() - 180) < 5) state++;
			break;
		case 8:
			Robot::arm->Open();
			Robot::drivetrain->CrabDrive(0, -1, 0, 0.25, false);
			if(Robot::drivetrain->GetDistanceAway() < 2) {
				Robot::arm->Close();
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
			Robot::drivetrain->Brake();
			break;
	}
}

void Autonomous::ScaleAhead(bool left) {

}

void Autonomous::ScaleAcross(bool left) {

}