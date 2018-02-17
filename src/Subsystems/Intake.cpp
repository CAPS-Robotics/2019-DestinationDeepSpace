#include <Robot.h>
#include "Intake.h"

Intake::Intake() {
    piston = new DoubleSolenoid(PCM, INTAKE_FORWARD, INTAKE_BACKWARD);
    intakeMotor = new WPI_TalonSRX(INTAKE_SRX);
    intakeEncoder = new AnalogInput(INTAKE_ENCODER);
}

bool Intake::SetState(bool closed) {
    if(closed) {
        piston->Set(DoubleSolenoid::kReverse);
    } else {
        piston->Set(DoubleSolenoid::kForward);
    }
    return closed;
}

bool Intake::TurnTo(double degrees, bool compensate) {
	if (fmod(fabs(this->GetAngle() - degrees), 360) < 8 || compensate) {
		intakeMotor->Set((Robot::arm->GetAngle()+this->GetAngle() < 90 && Robot::arm->GetAngle()+this->GetAngle() > -90) ? (((this->GetAngle() - degrees) > 0) ? -.1*fabs(cos((Robot::arm->GetAngle()+this->GetAngle())*PI/180)) : -.3*fabs(cos((Robot::arm->GetAngle()+this->GetAngle())*PI/180))) : (((this->GetAngle() - degrees) < 0) ? .1*fabs(cos((Robot::arm->GetAngle()+this->GetAngle())*PI/180)) : .3*fabs(cos((Robot::arm->GetAngle()+this->GetAngle())*PI/180))));
		return true;
	} else {
		intakeMotor->Set((Robot::arm->GetAngle()+this->GetAngle() < 90 && Robot::arm->GetAngle()+this->GetAngle() > -90) ? (((this->GetAngle() - degrees) > 0) ? 0*fabs(cos((Robot::arm->GetAngle()+this->GetAngle())*PI/180))+.2 : -.4*fabs(cos((Robot::arm->GetAngle()+this->GetAngle())*PI/180))-.2) : (((this->GetAngle() - degrees) < 0) ? -0*fabs(cos((Robot::arm->GetAngle()+this->GetAngle())*PI/180))-.2 : .4*fabs(cos((Robot::arm->GetAngle()+this->GetAngle())*PI/180))+.2));
		return false;
	}
}

double Intake::GetAngle() {
	return (this->intakeMotor->GetSensorCollection().GetAnalogIn() - INTAKE_OFFSET) * (360.0/(1024.0*GR));
}
