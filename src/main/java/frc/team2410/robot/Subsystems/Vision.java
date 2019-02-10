package frc.team2410.robot.Subsystems;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team2410.robot.RobotMap;

import static frc.team2410.robot.RobotMap.*;

public class Vision
{
	private NetworkTable table;
	private Number[] centerX;
	private Number[] centerY;
	private Number[] area;
	//private DigitalOutput light;
	private AnalogInput rangeFinder;
	
	public Vision() {
		table = NetworkTableInstance.getDefault().getTable("GRIP/AllDemContours");
		this.update();
		//light = new DigitalOutput(CAMERA_LIGHT);
		rangeFinder = new AnalogInput(RANGE_FINDER);
	}
	
	public double getDistanceAway() {
		return this.rangeFinder.getVoltage()/SONAR_VOLTS_PER_INCH;
	}
	
	private void update() {
		centerX = table.getEntry("centerX").getNumberArray(new Number[0]);
		centerY = table.getEntry("centerY").getNumberArray(new Number[0]);
		area = table.getEntry("area").getNumberArray(new Number[0]);
	}
	
	public double[] getCentralValue() {
		this.update();
		double x = 0;
		double y = 0;
		double greatestArea = 0;
		for(int i = 0; (i < area.length) && (i < centerX.length) && (i < centerY.length); i++) {
			if((double)area[i] > greatestArea) {
				x = (double)centerX[i];
				y = (double)centerY[i];
				greatestArea = (double)area[i];
			}
		}
		return new double[] {x, y};
	}
	/*public void setLight(boolean on) {
		light.set(on);
	}*/
}
