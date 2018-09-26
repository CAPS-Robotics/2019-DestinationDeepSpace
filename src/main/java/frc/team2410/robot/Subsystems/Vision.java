package frc.team2410.robot.Subsystems;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.CameraServer;

//TODO: fix it
public class Vision
{
	UsbCamera cam0;
	UsbCamera cam1;
	CvSink cvsink0;
	CvSink cvsink1;
	VideoSink server;
	NetworkTable table;
	Number[] centerX;
	Number[] centerY;
	Number[] height;
	Number[] width;
	public Vision() {
		cam0 = new UsbCamera("cam0", 0);
		cam0.setResolution(320, 240);
		cam0.setBrightness(10);
		cam1 = new UsbCamera("cam1", 1);
		cam1.setResolution(320, 240);
		cam1.setBrightness(10);
		cvsink0 = new CvSink("cam0cv");
		cvsink0.setSource(cam0);
		cvsink0.setEnabled(true);
		cvsink1 = new CvSink("cam1cv");
		cvsink1.setSource(cam1);
		cvsink1.setEnabled(true);
		CameraServer.getInstance().startAutomaticCapture(cam0);
		CameraServer.getInstance().startAutomaticCapture(cam1);
		server = CameraServer.getInstance().getServer();
		setCamera(0);
		table = NetworkTableInstance.getDefault().getTable("GRIP/AllDemContours");
		this.update();
	}
	
	void update() {
		centerX = table.getEntry("centerX").getNumberArray(new Number[0]);
		centerY = table.getEntry("centerY").getNumberArray(new Number[0]);
		height = table.getEntry("height").getNumberArray(new Number[0]);
		width = table.getEntry("width").getNumberArray(new Number[0]);
	}
	
	double getCentralValue() {
		this.update();
		double theCenterX = 0;
		for (Number aCenterX : centerX) {
			theCenterX += (double)aCenterX;
		}
		if (centerX.length != 0) { theCenterX /= centerX.length; }
		return theCenterX;
	}
		
		void setCamera(int camera) {
		if (camera == 0) {
			server.setSource(cam0);
		} else {
			server.setSource(cam1);
		}
	}
}
