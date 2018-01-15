#include "Subsystems/PigeonNav.h"
#include "WPILib.h"
#include "Subsystems/Drivetrain.h"
#include "OI.h"

class Robot: public frc::IterativeRobot {
	Command * autonomousCommand;
	SendableChooser<Command *> * autoPicker;
public:
    float p;
    float i;
    float d;
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
private:

};
