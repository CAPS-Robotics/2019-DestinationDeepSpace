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
	//Climb
	if(buttonPad->GetRawButton(1)) {
		Robot::arm->SetSequence(new bool[3] {true, false, true}, new int[3] {135, 75, 60}, 3);
	}
	if(buttonPad->GetRawButton(5)) {
		Robot::climber->Climb(1);
	} else if(buttonPad->GetRawButton(6)) {
		Robot::climber->Climb(-1);
	} else {
		Robot::climber->Climb(0);
	}
	//Scale
	if(buttonPad->GetRawButton(4)) {
		Robot::arm->SetSequence(new bool[3] {true, false, true}, new int[3] {135, 75, 45}, 3);
	}
	if(buttonPad->GetRawButton(3)) {
		Robot::arm->SetSequence(new bool[3] {true, false, true}, new int[3] {135, 75, 75}, 3);
	}
	if(buttonPad->GetRawButton(2)) {
		Robot::arm->SetSequence(new bool[3] {true, false, true}, new int[3] {135, 75, 105}, 3);
	}
	//Switch
	if(buttonPad->GetPOV(0) == 90) {
		Robot::arm->SetSequence(new bool[2] {true, false}, new int[2] {135, -30}, 2);
	}
	//Intake
	if(buttonPad->GetPOV(0) == 180) {
		Robot::arm->SetSequence(new bool[3] {true, false, true}, new int[3] {135, -60, 60}, 3);
	}
	//Driving
	if(buttonPad->GetPOV(0) == 270) {
		Robot::arm->SetSequence(new bool[2] {true, false}, new int[2] {150, -60}, 2);
	}
	//Manual arm
	if(this->GetAnalogY(0) != 0 || this->GetAnalogY(1) != 0) {
		Robot::arm->SetSequence(new bool[0], new int[0], 0);
		Robot::arm->armPos += this->GetAnalogY(1);
		Robot::arm->intakePos += this->GetAnalogY(0);
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

double OI::GetAnalogY(int stickNum) {
	return this->applyDeadzone(buttonPad->GetRawAxis((stickNum*2)+1), 0.50, 1);
}

double OI::applyDeadzone(double val, double deadzone, double maxval) {
	if (fabs(val) <= deadzone) {
		return 0;
	}
	double sign = val / fabs(val);
	val = sign * maxval * (fabs(val) - deadzone) / (maxval - deadzone);
	return val;
}
