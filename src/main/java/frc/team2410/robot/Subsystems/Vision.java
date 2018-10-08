package frc.team2410.robot.Subsystems;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.CameraServer;
import frc.team2410.robot.RobotMap;

//TODO: fix it
public class Vision
{
	public UsbCamera cam0;
	public UsbCamera cam1;
	public int camera;
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
		cam0.setBrightness(RobotMap.CAMERA_BRIGHTNESS);
		cam0.setFPS(10);
		cam0.setExposureManual(/*CAMERA_EXPOSURE*/50);
		cam1 = new UsbCamera("cam1", 1);
		cam1.setResolution(320, 240);
		cam1.setBrightness(RobotMap.CAMERA_BRIGHTNESS);
		cam1.setFPS(10);
		cam1.setExposureManual(/*CAMERA_EXPOSURE*/50);
		CameraServer.getInstance().putVideo("GRIPCam", 320, 240);
	/*sdb0 = CameraServer::GetInstance()->PutVideo("cam0", 320, 240);
	sdb1 = CameraServer::GetInstance()->PutVideo("cam1", 320, 240);*/
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
		//NetworkTableInstance.getDefault().getEntry("/CameraPublisher/GRIPCam/streams").setStringArray(string[2]{"mjpeg:http://roborio-2410-frc.local:1181/?action=stream, mjpeg:http://10.24.10.2:1181/?action=stream"});
		table = NetworkTableInstance.getDefault().getTable("GRIP/AllDemContours");
		this.update();
		
		/*void Vision::VisionThread() {
			cv::Mat img0;
			cv::Mat img1;
			cvsink0->GrabFrame(img0);
			cvsink1->GrabFrame(img1);
			sdb0.PutFrame(img0);
			sdb1.PutFrame(img1);
		}*/
	}
	
	void update() {
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
		
		public void setCamera(int camera) {
		this.camera = camera;
		if (camera == 0) {
			server.setSource(cam0);
		} else {
			server.setSource(cam1);
		}
	}
}
