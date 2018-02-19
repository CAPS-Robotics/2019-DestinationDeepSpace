//
// Created by robotics on 2/17/2018.
//

#include <RobotMap.h>
#include "Climber.h"

Climber::Climber() {
	climbMotor = new WPI_TalonSRX(CLIMB_CIM);
}

void Climber::Climb(double speed) {
	this->climbMotor->Set(speed);
}
