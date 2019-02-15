package frc.team2410.robot.Subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import net.bak3dnet.robotics.led.*;
import net.bak3dnet.robotics.led.modules.*;

public class LED {
	private LightDrive12 controller;
	
	public LED() {
		controller = new LightDrive12(SerialPort.Port.kMXP);
	}
	
	private int state = 0;
	public double r;
	public double g;
	public double b;
	private long ps = -1;
	
	public void setColor(double r, double g, double b) {
		this.r = r;
		this.g = g;
		this.b = b;
		if(r > 255) r = 255;
		if(g > 255) g = 255;
		if(b > 255) b = 255;
		if(r < 0) r = 0;
		if(g < 0) g = 0;
		if(b < 0) b = 0;
		AStaticColorModule c = new AStaticColorModule(new byte[]{(byte)r, (byte)b, (byte)g});
		controller.setChannelModule(1, c);
		controller.setChannelModule(2, c);
		controller.setChannelModule(3, c);
		controller.setChannelModule(4, c);
	}
	
	public void fade(int speed) {
		switch(state) {
			case 0:
				b += speed;
				if(b >= 255) state++;
				break;
			case 1:
				r -= speed;
				if(r <= 0) state++;
				break;
			case 2:
				g += speed;
				if(g >= 255) state++;
				break;
			case 3:
				b -= speed;
				if(b <= 0) state++;
				break;
			case 4:
				r += speed;
				if(r >= 255) state++;
				break;
			case 5:
				g -= speed;
				if(g <= 0) state = 0;
				break;
		}
		setColor(r, g, b);
	}
	
	public void breathe(int r0, int g0, int b0, int r1, int g1, int b1, int speed) {
		double ri = (r1-r0)/(255.0/speed);
		double gi = (g1-g0)/(255.0/speed);
		double bi = (b1-b0)/(255.0/speed);
		if(((r >= (r0 > r1 ? r0 : r1) && ri >= 0) || (r <= (r0 < r1 ? r0 : r1) && ri <= 0)) && ((g >= (g0 > g1 ? g0 : g1) && gi >= 0) || (g <= (g0 < g1 ? g0 : g1) && gi <= 0)) && ((b >= (b0 > b1 ? b0 : b1) && bi >= 0) || (b <= (b0 < b1 ? b0 : b1) && bi <= 0))) {
			state = 0;
		}
		if(((r >= (r0 > r1 ? r0 : r1) && ri <= 0) || (r <= (r0 < r1 ? r0 : r1) && ri >= 0)) && ((g >= (g0 > g1 ? g0 : g1) && gi <= 0) || (g <= (g0 < g1 ? g0 : g1) && gi >= 0)) && ((b >= (b0 > b1 ? b0 : b1) && bi <= 0) || (b <= (b0 < b1 ? b0 : b1) && bi >= 0))) {
			state = 1;
		}
		r += ri*((state*2)-1);
		g += gi*((state*2)-1);
		b += bi*((state*2)-1);
		setColor(r, g, b);
	}
	public void blink(int r0, int g0, int b0, int r1, int g1, int b1, int speed) {
		state += speed;
		if(state > 255/2) {
			state = 0;
			if(r0 == r && g0 == g && b0 == b) {
				setColor(r1, g1, b1);
			} else {
				setColor(r0, g0, b0);
			}
		}
	}
	
	public void status(int r0, int g0, int b0, int r1, int g1, int b1, int speed, boolean status) {
		long s = r0 + 256*(g0 + 256*(b0 + 256*(status? 1 : 0)));
		if(s != ps) {
			setColor(r0, g0, b0);
		}
		ps = s;
		if(status) {
			breathe(r0, g0, b0, r1, g1, b1, speed);
		} else {
			blink(r0, g0, b0, 0, 0, 0, speed);
		}
	}
}