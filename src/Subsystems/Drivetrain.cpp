#include "Drivetrain.h"
#include "../Robot.h"
#include "../RobotMap.h"
#include "SwerveModule.h"

Drivetrain::Drivetrain() : Subsystem("Drivetrain") {
    Robot::gyro.get();
    this->fl = new SwerveModule(FL_TALON_SRX, FL_DRIVE_TALON, FL_STEER_ENCODER, FL_OFFSET, true);
    this->fr = new SwerveModule(FR_TALON_SRX, FR_DRIVE_TALON, FR_STEER_ENCODER, FR_OFFSET, false);
    this->bl = new SwerveModule(BL_TALON_SRX, BL_DRIVE_TALON, BL_STEER_ENCODER, BL_OFFSET, true);
    this->br = new SwerveModule(BR_TALON_SRX, BR_DRIVE_TALON, BR_STEER_ENCODER, BR_OFFSET, false);
    this->rangeFinder = new AnalogInput(RANGE_FINDER);
    this->desiredHeading = 0;
}

void Drivetrain::JoystickDrive() {
    double speedMultiplier = (1 - Robot::oi->GetSlider()) / 2;
    if (Robot::oi->joy1->GetRawButton(1)) {
        Robot::drivetrain->CrabDrive(Robot::oi->GetX(), Robot::oi->GetY(), Robot::oi->GetTwist(), speedMultiplier, false);
    } else if (Robot::oi->joy1->GetPOV() == 0) {
        Robot::drivetrain->Drive(0, Robot::oi->GetY(), speedMultiplier);
    } else if (Robot::oi->joy1->GetPOV() == 90) {
        Robot::drivetrain->Drive(90, -Robot::oi->GetX(), speedMultiplier);
    } else if (Robot::oi->joy1->GetPOV() == 180) {
        Robot::drivetrain->Drive(180, -Robot::oi->GetY(), speedMultiplier);
    } else if (Robot::oi->joy1->GetPOV() == 270) {
        Robot::drivetrain->Drive(270, Robot::oi->GetX(), speedMultiplier);
    } else {
        Robot::drivetrain->CrabDrive(Robot::oi->GetX(), Robot::oi->GetY(), Robot::oi->GetTwist(), speedMultiplier, true);
    }
}

double Drivetrain::GetDistanceAway() {
    return this->rangeFinder->GetVoltage() / 0.012446;
}

void Drivetrain::ReturnWheelsToZero() {
    this->fl->ReturnToZero();
    this->fr->ReturnToZero();
    this->bl->ReturnToZero();
    this->br->ReturnToZero();
}

void Drivetrain::Brake() {
    this->fl->Drive(0, 0);
    this->fr->Drive(0, 0);
    this->bl->Drive(0, 0);
    this->br->Drive(0, 0);
}

void Drivetrain::Drive(double angle, double speed, double speedMultiplier) {
    this->fl->Drive(speed * speedMultiplier, angle);
    this->fr->Drive(speed * speedMultiplier, angle);
    this->bl->Drive(speed * speedMultiplier, angle);
    this->br->Drive(speed * speedMultiplier, angle);
}

void Drivetrain::RotateRobot(double speed) {
    SmartDashboard::PutNumber("Speed", speed);
    this->fl->Drive( speed, -45);
    this->fr->Drive(-speed,  45);
    this->bl->Drive( speed,  45);
    this->br->Drive(-speed, -45);
    desiredHeading = Robot::gyro->GetHeading();
}

void Drivetrain::ArcadeDrive(double forward, double rotation, double speedMultiplier) {
    double correction = 0.025 * (Robot::gyro->GetHeading() - desiredHeading);
    SmartDashboard::PutNumber("Difference", correction);
    this->fl->Drive((forward + rotation * 1 / sqrt(2) + correction) * speedMultiplier, 0);
    this->fr->Drive((forward - rotation * 1 / sqrt(2) - correction) * speedMultiplier, 0);
    this->bl->Drive((forward + rotation * 1 / sqrt(2) + correction) * speedMultiplier, 0);
    this->br->Drive((forward - rotation * 1 / sqrt(2) - correction) * speedMultiplier, 0);
}

void Drivetrain::CrabDrive(double x, double y, double rotation, double speedMultiplier, bool useGyro) {
    double forward, strafe;
    SmartDashboard::PutNumber("FL Angle", fl->GetAngle());
    SmartDashboard::PutNumber("FR Angle", fr->GetAngle());
    SmartDashboard::PutNumber("BL Angle", bl->GetAngle());
    SmartDashboard::PutNumber("BR Angle", br->GetAngle());
    if (useGyro) {
        double heading = Robot::gyro->GetHeading();
        forward = -x * sin(heading * PI / 180) + y * cos(heading * PI / 180);
        strafe  =  x * cos(heading * PI / 180) + y * sin(heading * PI / 180);
    } else {
        forward = y;
        strafe  = x;
    }
    if (x != 0 || y != 0 || rotation != 0) {
        double back, front, right, left;

        if (rotation != 0) {
            back  = strafe  - rotation * 1.0 / sqrt(2);
            front = strafe  + rotation * 1.0 / sqrt(2);
            right = forward - rotation * 1.0 / sqrt(2);
            left  = forward + rotation * 1.0 / sqrt(2);
        } else {
            back  = strafe;
            front = strafe;
            right = forward;
            left  = forward;
        }

        double flds = sqrt(front * front + left  * left);
        double frds = sqrt(front * front + right * right);
        double blds = sqrt(back  * back  + left  * left);
        double brds = sqrt(back  * back  + right * right);

        double maxSpeed = std::max(std::max(std::max(flds, frds), blds), brds);
        if (maxSpeed > 1) {
            flds /= maxSpeed;
            frds /= maxSpeed;
            blds /= maxSpeed;
            brds /= maxSpeed;
        }

        double fla = 0, fra = 0, bla = 0, bra = 0;

        if (rotation != 0) {
            desiredHeading = Robot::gyro->GetHeading();
        }

        if (front != 0 || left != 0) {
            fla = fmod(360 + (180 / PI) * -atan2(front, left),  360);
        } else {
            fla = 0;
        }
        if (front != 0 || right != 0) {
            fra = fmod(360 + (180 / PI) * -atan2(front, right), 360);
        } else {
            fra = 0;
        }
        if (back != 0 || left != 0) {
            bla = fmod(360 + (180 / PI) * -atan2(back,  left),  360);
        } else {
            bla = 0;
        }
        if (back != 0 || right != 0) {
            bra = fmod(360 + (180 / PI) * -atan2(back,  right), 360);
        } else {
            bra = 0;
        }

        double correction = 0; //-0.0025 * (Robot::gyro->GetHeading() - desiredHeading);
        this->fl->Drive((flds + correction) * speedMultiplier, fla);
        this->fr->Drive((frds - correction) * speedMultiplier, fra);
        this->bl->Drive((blds + correction) * speedMultiplier, bla);
        this->br->Drive((brds - correction) * speedMultiplier, bra);
    } else {
        this->fl->Drive(0, this->fl->GetAngle());
        this->fr->Drive(0, this->fr->GetAngle());
        this->bl->Drive(0, this->bl->GetAngle());
        this->br->Drive(0, this->br->GetAngle());
    }
}
