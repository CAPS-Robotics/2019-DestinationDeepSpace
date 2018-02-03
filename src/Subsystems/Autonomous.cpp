#include <Robot.h>
#include "Autonomous.h"

Autonomous::Autonomous() = default;

void Autonomous::Init(int station, std::string data) {
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

}

void Autonomous::StraightAhead(bool left) {

}

void Autonomous::HalfWay(bool left) {
	if(Robot::vision->GetCentralValue() < 120 || Robot::vision->GetCentralValue() > 200) {
		Robot::drivetrain->CrabDrive(left ? -.5 : .5, .5, 0, .5, false);
	} else if(Robot::drivetrain->GetDistanceAway() < 12) {
		Robot::drivetrain->CrabDrive(0, .5, 0, .5, false);
	}
}

void Autonomous::GoAround(bool left) {

}
