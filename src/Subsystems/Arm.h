#ifndef FRC2018_ARM_H
#define FRC2018_ARM_H

#include "wpilib.h"
#include "ctre/Phoenix.h"
#include "RobotMap.h"
#include "Intake.h"
#include <string>

class Arm {
private:
    int seqStage;
    bool * intakeSeq;
    int * seqPos;
	int seqLen;
public:
    Arm();
    WPI_TalonSRX * armMotor;
    Intake * intake;
	AnalogInput * armEncoder;
    double position;
    bool intakeClosed;
    bool TurnTo(double degrees);
    void SetSequence(bool * intakeSeq, int * seqPos, int len);
    void Loop();
    void Toggle();
    void Close();
    void Open();
    double GetAngle();
};


#endif //FRC2018_ARM_H
