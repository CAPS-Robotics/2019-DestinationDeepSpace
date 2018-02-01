#ifndef Drivetrain_H
#define Drivetrain_H

#include <NumericalPIDOutput.h>
#include "ctre/Phoenix.h"
#include "SwerveModule.h"
#include "WPILib.h"
#include "Commands/Subsystem.h"

class Drivetrain : public Subsystem {
private:
    AnalogInput * rangeFinder;
public:
    double desiredHeading;
    SwerveModule * fl;
    SwerveModule * fr;
    SwerveModule * bl;
    SwerveModule * br;
    //PIDController * pid;
    //NumericalPIDOutput * pidOutput;
    Drivetrain();
    void JoystickDrive();
    //void SetPID(float p, float i, float d);
    double GetDistanceAway();
    void RotateRobot(double speed);
    void ReturnWheelsToZero();
    void Drive(double angle, double speed, double speedMultiplier);
    void CrabDrive(double x, double y, double rotation, double speedMultiplier, bool useGyro);
    void ArcadeDrive(double forward, double rotation, double speedMultiplier = 1);
    void Brake();
    static double wrap(double num, double min, double max);
};

#endif  // Drivetrain_H
