#include "Intake.h"

Intake::Intake() {
    piston = new DoubleSolenoid(PCM, INTAKE_FORWARD, INTAKE_BACKWARD);
}

bool Intake::SetState(bool closed) {
    if(closed) {
        piston->Set(DoubleSolenoid::kReverse);
    } else {
        piston->Set(DoubleSolenoid::kForward);
    }
    return closed;
}
