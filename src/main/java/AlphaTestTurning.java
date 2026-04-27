import Components.*;
import ev3dev.sensors.EV3Key;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.Color;

import java.util.concurrent.TimeUnit;

public class AlphaTestTurning {

    public static void main(final String[] args) throws InterruptedException {

        System.out.println("Creating Motor A & B");
        MotorEV3 leftMotor = new MotorEV3(MotorPort.A);
        MotorEV3 rightMotor = new MotorEV3(MotorPort.B);
        IRSensor irSensor = new IRSensor(SensorPort.S3);
        DifferentialDrive motors = new DifferentialDrive(leftMotor, rightMotor);
        EV3Key Enter = new EV3Key(28);

        System.out.println("Creating Sensors");
        ColorSensorEV3 leftColor = new ColorSensorEV3(SensorPort.S1, ColorMode.RED);
        ColorSensorEV3 rightColor = new ColorSensorEV3(SensorPort.S2, ColorMode.RED);

        //To Stop the motor in case of pkill java for example
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                System.out.println("Emergency Stop");
                motors.stop();
            }
        }));

        System.out.println("Start program");
        boolean droveOverLeftLine = false;
        boolean droveOverRightLine = false;
        boolean onLeftLine = false;
        boolean onRightLine = false;
        while(true) {

            Enter.waitForPress();
            leftColor.setLight(Color.WHITE);
            rightColor.setLight(Color.WHITE);
            motors.move(600,600);
            while (irSensor.getDistance() < 50) {
                if(leftColor.getValue1() < 300) {
                    onLeftLine = true;
                } else if (onLeftLine) {
                    onLeftLine = false;
                    droveOverLeftLine = true;
                    motors.move(600, 300);
                }
                if(rightColor.getValue1() < 300) {
                    onRightLine = true;
                } else if (onRightLine) {
                    onRightLine = false;
                    droveOverRightLine = true;
                    motors.move(300, 600);
                }
            }
        }
    }
}
