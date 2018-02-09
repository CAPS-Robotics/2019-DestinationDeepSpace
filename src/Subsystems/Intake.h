#ifndef FRC2018_INTAKE_H
#define FRC2018_INTAKE_H

#include "wpilib.h"
#include "ctre/Phoenix.h"
#include "RobotMap.h"

class Intake {
public:
    DoubleSolenoid * piston;
    Intake();
    bool SetState(bool closed);

    WPI_TalonSRX * intakeMotor;
    AnalogInput * encoder;
    bool TurnTo(double degrees);
    double GetAngle();
};


#endif //FRC2018_INTAKE_H
