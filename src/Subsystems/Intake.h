#ifndef FRC2018_INTAKE_H
#define FRC2018_INTAKE_H

#include "wpilib.h"
#include "ctre/Phoenix.h"
#include "RobotMap.h"

class Intake {
public:
    DoubleSolenoid * grip;
    DoubleSolenoid * kick;
    Intake();
    bool SetState(bool closed);
    bool SetKicked(bool kick);
};


#endif //FRC2018_INTAKE_H
