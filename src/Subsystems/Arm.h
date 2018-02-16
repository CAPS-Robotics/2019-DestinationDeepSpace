#ifndef FRC2018_ARM_H
#define FRC2018_ARM_H

#include "wpilib.h"
#include "ctre/Phoenix.h"
#include "RobotMap.h"
#include "Intake.h"
#include <string>

class Arm {
private:

public:
	int seqStage;
	bool * intakeSeq;
	int * seqPos;
	int seqLen;
	double armPos;
	double intakePos;
    Arm();
    WPI_TalonSRX * armMotor;
    Intake * intake;
	AnalogInput * armEncoder;
    double position;
    bool intakeClosed;
	void SetSetpoint();
    bool TurnTo(double degrees, bool compensate);
    void SetSequence(bool * intakeSeq, int * seqPos, int len);
    void Loop();
    void Toggle();
    void Close();
    void Open();
    double GetAngle();
};


#endif //FRC2018_ARM_H
