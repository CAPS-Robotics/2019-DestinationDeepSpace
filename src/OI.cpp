#include "OI.h"
#include "Robot.h"
#include "WPILib.h"


OI::OI() {
    for(bool &canPressi : canPress) {
        canPressi = true;
    }
    joy1 = new Joystick(0);
}

void OI::pollButtons() {
    if(joy1->GetRawButton(2)) {
        if(canPress[1]) {
            Robot::drivetrain->ReturnWheelsToZero();
        }
        canPress[1] = false;
    } else { canPress[1] = true; }
    if(joy1->GetRawButton(6)) {
        if(canPress[5]) {
            Robot::gyro->ResetHeading();
        }
        canPress[5] = false;
    } else { canPress[5] = true; }
}

double OI::GetX() {
    return this->applyDeadzone(joy1->GetRawAxis(0), 0.15);
}

double OI::GetY() {
    return this->applyDeadzone(-joy1->GetRawAxis(1), 0.15);
}

double OI::GetTwist() {
    return this->applyDeadzone(joy1->GetRawAxis(2), 0.70) / 2;
}

double OI::GetSlider() {
    return joy1->GetRawAxis(3);
}

double OI::applyDeadzone(double val, double deadzone) {
    if (fabs(val) <= deadzone) {
        return 0;
    }
    double sign = val / fabs(val);
    val = sign * (fabs(val) - deadzone) / (1 - deadzone);
    return val;
}
