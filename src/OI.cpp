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
    //Block button
    //REMOVE BEFORE COMP
    //trolololol @Lucas
    /*if(joy1->GetRawButton(1)) {
        while(true) {}
    }*/
    if(joy1->GetRawButton(2)) {
        Robot::drivetrain->ReturnWheelsToZero();
    }
    if(joy1->GetRawButton(4)) {
        if(canPress[2]) {
            Robot::arm->Toggle();
        }
        canPress[2] = false;
    } else { canPress[2] = true; }
    if(joy1->GetRawButton(6)) {
        if(canPress[5]) {
            Robot::gyro->ResetHeading();
            Robot::drivetrain->desiredHeading = 180.0;
        }
        canPress[5] = false;
    } else { canPress[5] = true; }
	if(joy1->GetRawButton(3)) {
		Robot::arm->SetSetpoint();
		Robot::arm->armMotor->Set(0);
		Robot::arm->intake->intakeMotor->Set(0);
	}
    // ARM Turning
    /*if(joy1->GetRawButton(7)) {
        Robot::arm->SetSequence({true,}, {}, );
    }
    if(joy1->GetRawButton(8)) {
        Robot::arm->SetSequence();
    }*/
    if(joy1->GetRawButton(9)) {
		if(canPress[8]) {
			Robot::arm->SetSequence(new bool[2] {true, false}, new int[2] {135, -30}, 2);
		}
		canPress[8] = false;
	} else { canPress[8] = true; }
    /*if(joy1->GetRawButton(10)) {
        Robot::arm->SetSequence();
    }*/
    if(joy1->GetRawButton(11)) {
		if(canPress[10]) {
			Robot::arm->SetSequence(new bool[3] {true, false, true}, new int[3] {135, -60, 60}, 3);
		}
		canPress[10] = false;
	} else { canPress[10] = true; }
    /*if(joy1->GetRawButton(12)) {
        Robot::arm->SetSequence();
    }*/
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
