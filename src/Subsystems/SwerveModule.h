#ifndef SwerveModule_H
#define SwerveModule_H

#include "ctre/Phoenix.h"
#include "WPILib.h"
#include "Commands/Subsystem.h"

class SwerveModule : public Subsystem {
private:
    Talon * drive;
    float offset;
    float currentSpeed;
public:
    WPI_TalonSRX * steer;
    PIDController * pid;
    AnalogInput * positionEncoder;
    void Drive(double speed, double angle);
    SwerveModule(int steerMotor, int driveMotor, int encoder, float offset, bool isInverted);
    void InitDefaultCommand();
    void ReturnToZero();
    void ResetEncoder();
    double GetAngle();
    void setPID(float p, float i, float d);
};

#endif  // SwerveModule_H
