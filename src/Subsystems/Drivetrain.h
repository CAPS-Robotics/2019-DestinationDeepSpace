#ifndef Drivetrain_H
#define Drivetrain_H

#include "ctre/Phoenix.h"
#include "SwerveModule.h"
#include "WPILib.h"
#include "Commands/Subsystem.h"

class Drivetrain : public Subsystem {
private:
    AnalogInput * rangeFinder;
    double desiredHeading;
public:
    SwerveModule * fl;
    SwerveModule * fr;
    SwerveModule * bl;
    SwerveModule * br;
    Drivetrain();
    void JoystickDrive();
    double GetDistanceAway();
    void RotateRobot(double speed);
    void ReturnWheelsToZero();
    void Drive(double angle, double speed, double speedMultiplier);
    void CrabDrive(double x, double y, double rotation, double speedMultiplier, bool useGyro);
    void ArcadeDrive(double forward, double rotation, double speedMultiplier = 1);
    void Brake();
};

#endif  // Drivetrain_H
