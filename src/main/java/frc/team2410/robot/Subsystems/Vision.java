package frc.team2410.robot.Subsystems;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.CameraServer;
import frc.team2410.robot.RobotMap;

public class Vision
{
	private NetworkTable table;
	private Number[] centerX;
	private Number[] centerY;
	private Number[] height;
	private Number[] width;
	
	public Vision() {
		table = NetworkTableInstance.getDefault().getTable("GRIP/AllDemContours");
		this.update();
	}
	
	private void update() {
		centerX = table.getEntry("centerX").getNumberArray(new Number[0]);
		centerY = table.getEntry("centerY").getNumberArray(new Number[0]);
		height = table.getEntry("height").getNumberArray(new Number[0]);
		width = table.getEntry("width").getNumberArray(new Number[0]);
	}
	
	public double getCentralValue() {
		this.update();
		double theCenterX = 0;
		for (Number aCenterX : centerX) {
			theCenterX += (double)aCenterX;
		}
		if (centerX.length != 0) { theCenterX /= centerX.length; }
		return theCenterX;
	}
}
