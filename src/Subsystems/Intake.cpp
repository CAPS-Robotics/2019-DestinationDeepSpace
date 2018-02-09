#include "Intake.h"

Intake::Intake() {
    piston = new DoubleSolenoid(PCM, INTAKE_FORWARD, INTAKE_BACKWARD);
    intakeMotor = new WPI_TalonSRX(INTAKE_SRX);
    this->encoder = new AnalogInput(INTAKE_ENCODER);
}

bool Intake::SetState(bool closed) {
    if(closed) {
        piston->Set(DoubleSolenoid::kReverse);
    } else {
        piston->Set(DoubleSolenoid::kForward);
    }
    return closed;
}

bool Intake::TurnTo(double degrees) {
    if (fmod(fabs(this->GetAngle() - degrees), 360) < 8) {
        intakeMotor->Set(0);
        return true;
    } else if ((this->GetAngle() - degrees) > 0) {
        intakeMotor->Set(-.28);
        return false;
    } else {
        intakeMotor->Set(.28);
        return false;
    }
}

double Intake::GetAngle() {
    return fmod(this->encoder->GetVoltage() - INTAKE_OFFSET + 5, 5) * 72.f;
}
