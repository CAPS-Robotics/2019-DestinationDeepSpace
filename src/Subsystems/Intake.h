#ifndef FRC2018_INTAKE_H
#define FRC2018_INTAKE_H

#include "wpilib.h"
#include "ctre/Phoenix.h"
#include "RobotMap.h"

class Intake {
public:
	Intake();
    DoubleSolenoid * piston;
    bool SetState(bool closed);
    WPI_TalonSRX * intakeMotor;
    bool TurnTo(double degrees);
    double GetAngle();
	AnalogInput * intakeEncoder;
};


#endif //FRC2018_INTAKE_H
