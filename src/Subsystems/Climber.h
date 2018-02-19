#ifndef FRC2018_CLIMB_H
#define FRC2018_CLIMB_H

#include "WPILib.h"
#include "ctre/Phoenix.h"

class Climber {
public:
	Climber();
	WPI_TalonSRX * climbMotor;
	void Climb(double speed);
};


#endif //FRC2018_CLIMB_H
