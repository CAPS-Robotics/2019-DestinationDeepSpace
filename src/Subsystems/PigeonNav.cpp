#include "../Robot.h"
#include "../RobotMap.h"

PigeonNav::PigeonNav() : Subsystem("PigeonNav"), PIDSource() {
    this->gyro = new PigeonIMU(new TalonSRX(PIGEON_IMU_SRX));
    this->ypr = new double[3];
    this->ResetHeading();
}

double PigeonNav::PIDGet() {
    return this->GetHeading();
}

void PigeonNav::SetPIDSourceType(PIDSourceType pidSource) {
    if (wpi_assert(pidSource == PIDSourceType::kDisplacement)) {
        m_pidSource = pidSource;
    }
}

double PigeonNav::GetHeading() {
    double angle = fmod(fmod(this->gyro->GetFusedHeading(), 360) + 360, 360);
    return angle <= 180 ? angle : -360 + angle;
}

double PigeonNav::GetAngularRate() {
    this->gyro->GetRawGyro(this->ypr);
    return ypr[0];
}

void PigeonNav::ResetHeading() {
    this->gyro->SetFusedHeading(0, 20);
}

PIDSourceType PigeonNav::GetPIDSourceType() const {
    return PIDSourceType::kDisplacement;
}

