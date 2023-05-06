package eecs1021;

import org.firmata4j.I2CDevice;
import org.firmata4j.Pin;
import org.firmata4j.firmata.FirmataDevice;
import org.firmata4j.ssd1306.SSD1306;

import java.io.IOException;
import java.util.Timer;

public class MinorProject {

    // Pin definitions (assuming Nano or UNO)
    static final int A1 = 15; // Moisture Sensor
    static final int D2 =  2; // Pump Pin
    static final int D6 =  6; // Button
    static final int D4 =  4; // LED
    static final byte I2C0 = 0x3C; // OLED Display



    public static void main(String[] args)
            throws InterruptedException, IOException
    {
        var device = new FirmataDevice("/dev/cu.usbserial-0001"); // Change to your serial port

        device.start();
        device.ensureInitializationIsDone();

        var moistureSensor = device.getPin(A1);
        moistureSensor.setMode(Pin.Mode.ANALOG);
        var pumpPin = device.getPin(D2);
        pumpPin.setMode(Pin.Mode.OUTPUT);

        I2CDevice i2cObject = device.getI2CDevice((byte) 0x3C); // Use 0x3C for the Grove OLED
        SSD1306 theOledObject = new SSD1306(i2cObject, SSD1306.Size.SSD1306_128_64); // 128x64 OLED SSD1515// Set up the OLED  display (type, size ...)
        theOledObject.init();


        var task = new pumpTask(theOledObject, moistureSensor, pumpPin, 0);
        new Timer().schedule(task, 0, 1000);

    }
}
