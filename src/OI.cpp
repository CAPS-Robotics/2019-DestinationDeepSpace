#include "OI.h"
#include "Robot.h"
#include "WPILib.h"


OI::OI() {
    for(bool &canPressi : canPress) {
        canPressi = true;
    }
    joy1 = new Joystick(0);
    buttonPad = new XboxController(1);
}

void OI::pollButtons() {
    //Block button
    //REMOVE BEFORE COMP
    //trolololol @Lucas
    /*if(joy1->GetRawButton(1)) {
        while(true) {}
    }*/
    if (joy1->GetRawButton(2)) {
        Robot::drivetrain->ReturnWheelsToZero();
    }
    if (joy1->GetRawButton(4)) {
        if (canPress[3]) {
            Robot::arm->ToggleIntake();
        }
        canPress[3] = false;
    } else { canPress[3] = true; }
    if(joy1->GetRawButton(3)) {
        if (canPress[2]) {
            Robot::arm->ToggleKick();
        }
        canPress[2] = false;
    } else { canPress[2] = true; }
    if (joy1->GetRawButton(6)) {
        Robot::gyro->ResetHeading();
    }
    if (fabs(Robot::arm->GetCurrent()) < 30) {
        if (this->GetStick() == 0) {
            if (fabs(Robot::arm->cimcoder->GetDistance() - Robot::arm->targetPos) < .5) Robot::arm->armMotor->Set(0);
        } else {
            Robot::arm->armMotor->Set(this->GetStick());
            Robot::arm->targetPos = Robot::arm->cimcoder->GetDistance();
        }
    }
    //Scale
    if (buttonPad->GetRawButton(4)) {
        Robot::arm->MoveTo(72);
    }
    if (buttonPad->GetRawButton(3)) {
        Robot::arm->MoveTo(60);
    }
    if (buttonPad->GetRawButton(2)) {
        Robot::arm->MoveTo(48);
    }
    //Intake
    if (buttonPad->GetPOV(0) == 180) {
        Robot::arm->MoveTo(0);
    }
    //Switch
    if (buttonPad->GetPOV(0) == 90) {
        Robot::arm->MoveTo(20);
    }
    //Reset
    if (buttonPad->GetPOV(0) == 270) {
        Robot::arm->cimcoder->Reset();
        Robot::arm->targetPos = 0;
    }

}

double OI::GetX() {
    return this->applyDeadzone(joy1->GetRawAxis(0), 0.15, 1);
}

double OI::GetY() {
    return this->applyDeadzone(-joy1->GetRawAxis(1), 0.15, 1);
}

double OI::GetTwist() {
    return this->applyDeadzone(joy1->GetRawAxis(2), 0.70, 1) / 2;
}

double OI::GetSlider() {
    return joy1->GetRawAxis(3);
}

double OI::applyDeadzone(double val, double deadzone, double maxval) {
    if (fabs(val) <= deadzone) {
        return 0;
    }
    double sign = val / fabs(val);
    val = sign * maxval * (fabs(val) - deadzone) / (maxval - deadzone);
    return val;
}

double OI::GetStick() {
    return this->applyDeadzone(buttonPad->GetRawAxis(3), 0.50, 1);
}
