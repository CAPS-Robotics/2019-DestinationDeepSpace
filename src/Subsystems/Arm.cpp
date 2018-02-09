#include "Arm.h"
#include "Intake.h"

Arm::Arm() {
    armMotor = new WPI_TalonSRX(ARM_CIM);
    intake = new Intake();
    intakeClosed = true;
    Toggle();
}

void Arm::TurnTo(double degrees) {
    
}

void Arm::Toggle() {
    intakeClosed = intake->SetState(!intakeClosed);
}

void Arm::Close() {
    intakeClosed = true;
    intake->SetState(true);
}

void Arm::Open() {
    intakeClosed = false;
    intake->SetState(false);
}