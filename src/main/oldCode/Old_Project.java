import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.sensors.Battery;
import ev3dev.sensors.ev3.EV3IRSensor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;

public class Old_Project {

    public static void main(final String[] args){

        System.out.println("Creating Motor A & B");
        final EV3LargeRegulatedMotor motorLeft = new EV3LargeRegulatedMotor(MotorPort.A);
        final EV3LargeRegulatedMotor motorRight = new EV3LargeRegulatedMotor(MotorPort.B);
        EV3IRSensor ir1 = new EV3IRSensor(SensorPort.S1);
        final int CICLE = 10;


        //To Stop the motor in case of pkill java for example
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                System.out.println("Emergency Stop");
                motorLeft.stop();
                motorRight.stop();
            }
        }));

        System.out.println("Defining the Stop mode");
        motorLeft.brake();
        motorRight.brake();

        System.out.println("Defining motor speed");
        final int motorSpeed = 200;
        motorLeft.setSpeed(motorSpeed);
        motorRight.setSpeed(motorSpeed);

        final SampleProvider sp = ir1.getDistanceMode();
        int distanceValue = 0;

        float [] sample = new float[sp.sampleSize()];
        sp.fetchSample(sample, 0);
        distanceValue = (int)sample[0];

        System.out.println("Go Forward with the motors");
        motorLeft.forward();
        motorRight.forward();

        while(distanceValue <= 50) {
            sample = new float[sp.sampleSize()];
            sp.fetchSample(sample, 0);
            distanceValue = (int)sample[0];
        }

        System.out.println("Stop motors");
        motorLeft.stop();
        motorRight.stop();

        System.out.println("Checking Battery");
        System.out.println("Votage: " + Battery.getInstance().getVoltage());

        System.exit(0);



//        System.out.println("Defining the Stop mode");
//        motorLeft.brake();
//        motorRight.brake();
//
//        System.out.println("Defining motor speed");
//        final int motorSpeed = 200;
//        motorLeft.setSpeed(motorSpeed);
//        motorRight.setSpeed(motorSpeed);
//
//        System.out.println("Go Forward with the motors");
//        motorLeft.forward();
//        motorRight.forward();
//
//        Delay.msDelay(2000);
//
//        System.out.println("Stop motors");
//        motorLeft.stop();
//        motorRight.stop();
//
//        System.out.println("Go Backward with the motors");
//        motorLeft.backward();
//        motorRight.backward();
//
//        Delay.msDelay(2000);
//
//        System.out.println("Stop motors");
//        motorLeft.stop();
//        motorRight.stop();
//
//        System.out.println("Checking Battery");
//        System.out.println("Votage: " + Battery.getInstance().getVoltage());
//
//        System.exit(0);
    }
}
