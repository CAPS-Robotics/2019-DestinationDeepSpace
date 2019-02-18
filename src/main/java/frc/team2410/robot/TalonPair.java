package frc.team2410.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class TalonPair {
	private WPI_TalonSRX a;
	private WPI_TalonSRX b;
	
	public TalonPair(int aAddr, int bAddr, boolean inv, boolean sameDir) {
		a = new WPI_TalonSRX(aAddr);
		b = new WPI_TalonSRX(bAddr);
		a.setInverted(inv);
		b.setInverted(sameDir ? inv : !inv);
	}
	
	public void set(double speed) {
		a.set(speed);
		b.set(speed);
	}
	public boolean badCurrent() {
		return a.getOutputCurrent() > 30 || b.getOutputCurrent() > 30;
	}
}
