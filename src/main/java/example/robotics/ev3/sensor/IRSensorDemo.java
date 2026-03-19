package example.robotics.ev3.sensor;

import ev3dev.sensors.Battery;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import ev3dev.sensors.ev3.EV3IRSensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IRSensorDemo {

    private static EV3IRSensor ir1 = new EV3IRSensor(SensorPort.S1);

    public static Logger LOGGER = LoggerFactory.getLogger(USSensorExample.class);

    private static int HALF_SECOND = 1000;

    public static void main(String[] args) {

        System.out.println(Battery.getInstance().getVoltage());

        final SampleProvider sp = ir1.getDistanceMode();
        int distanceValue = 0;

        //Control loop
        final int iteration_threshold = 100;
        for(int i = 0; i <= iteration_threshold; i++) {

            float [] sample = new float[sp.sampleSize()];
            sp.fetchSample(sample, 0);
            distanceValue = (int)sample[0];

            System.out.println("Iteration: {}" + i);
            System.out.println("Distance: {}" + distanceValue);
            LOGGER.info("Distance:  {}", distanceValue);

            Delay.msDelay(HALF_SECOND);
        }

        System.out.println(Battery.getInstance().getVoltage());


    }

}
