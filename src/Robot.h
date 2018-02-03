#include <Subsystems/Arm.h>
#include <Subsystems/Autonomous.h>
#include <Subsystems/Vision.h>
#include <Subsystems/Drivetrain.h>
#include <Subsystems/PigeonNav.h>
#include <OI.h>
#include <string>

class Robot: public frc::IterativeRobot {
	enum AutoStations {
		LEFT,
		CENTER,
		RIGHT
	};
	SendableChooser<AutoStations> * autoPicker;
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
	static std::shared_ptr<Vision> vision;
private:

};
