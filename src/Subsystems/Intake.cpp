#include <Robot.h>
#include "Intake.h"

Intake::Intake() {
    grip = new DoubleSolenoid(PCM, INTAKE_FORWARD, INTAKE_BACKWARD);
    kick = new DoubleSolenoid(PCM, INTAKE_KICK_FORWARD, INTAKE_KICK_BACKWARD);
}

bool Intake::SetState(bool closed) {
    if(closed) {
        grip->Set(DoubleSolenoid::kReverse);
    } else {
        grip->Set(DoubleSolenoid::kForward);
    }
    return closed;
}

bool Intake::SetKicked(bool forward) {
    if(forward) {
        kick->Set(DoubleSolenoid::kForward);
    } else {
        kick->Set(DoubleSolenoid::kReverse);
    }
    return forward;
}
