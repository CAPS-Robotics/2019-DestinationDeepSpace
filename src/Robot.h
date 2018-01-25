#include <Subsystems/Arm.h>
#include <Subsystems/Autonomous.h>
#include "Subsystems/PigeonNav.h"
#include "WPILib.h"
#include "Subsystems/Drivetrain.h"
#include "OI.h"

class Robot: public frc::IterativeRobot {
	SendableChooser<int> * autoPicker;
public:
    float smp;
    float smi;
    float smd;
    float gp;
    float gi;
    float gd;
	Robot();
	void RobotInit() override;
	void DisabledInit() override;
	void DisabledPeriodic() override;
	void AutonomousInit() override;
	void AutonomousPeriodic() override;
	void TeleopInit() override;
	void TeleopPeriodic() override;
	void TestPeriodic() override;
	static std::shared_ptr<Drivetrain> drivetrain;
	static std::shared_ptr<PigeonNav> gyro;
	static std::shared_ptr<OI> oi;
	static std::shared_ptr<Arm> arm;
	static std::shared_ptr<Autonomous> autonomous;
private:

};
