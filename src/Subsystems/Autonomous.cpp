#include <Robot.h>
#include "Autonomous.h"

Autonomous::Autonomous() = default;

void Autonomous::Init(int station, std::string data) {
	state = 0;
    if(station == 1) {
        this->autoNum = 1;
        this->left = data[0] == 'L';
    } else if((station == 0 && data[0] == 'L') || (station == 2 && data[0] == 'R')) {
        this->autoNum = 0;
        this->left = data[0] == 'L';
    } else {
        this->autoNum = 2;
        this->left = data[0] == 'L';
    }
	timer = new Timer();
}

void Autonomous::Loop() {
    switch(this->autoNum) {
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
			Robot::arm->SetSequence(new bool[4] {true, false, true, false}, new int[4] {225, 150, 45, 45}, 4);
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
		default:
			Robot::drivetrain->Brake();
			break;
	}
}

void Autonomous::StraightAhead(bool left) {
	switch(state) {
		case 0:
			timer->Reset();
			timer->Start();
			Robot::arm->SetSequence(new bool[4] {true, false, true, false}, new int[4] {225, 150, 45, 45}, 4);
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
		default:
			Robot::drivetrain->Brake();
			break;
	}
}

void Autonomous::HalfWay(bool left) {
	switch(state) {
		case 0:
			timer->Reset();
			timer->Start();
			Robot::arm->SetSequence(new bool[4] {true, false, true, false}, new int[4] {225, 150, 45, 45}, 4);
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
		default:
			Robot::drivetrain->Brake();
			break;
	}
}

void Autonomous::GoAround(bool left) {

}
