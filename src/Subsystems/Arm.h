#ifndef FRC2018_ARM_H
#define FRC2018_ARM_H

#include "wpilib.h"
#include "ctre/Phoenix.h"
#include "RobotMap.h"
#include "Intake.h"

class Arm {
public:
    Arm();
    WPI_TalonSRX * armMotor;
    Intake * intake;
    //def encoder
    double position;
    bool intakeClosed;
    void Turn(double speed);
    void Toggle();
    void Close();
    void Open();
};


#endif //FRC2018_ARM_H
