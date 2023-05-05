package eecs1021;

import org.firmata4j.I2CDevice;
import org.firmata4j.firmata.*; // Maven import Firmata4j & SLF4j on macOS & Windows
import org.firmata4j.IODevice; // You also need to import JSSC in using Windows.
import org.firmata4j.ssd1306.SSD1306;

public class BoardConnection {
    public static void main(String[] args) {
        String myPort = "/dev/cu.usbserial-0001"; // The USB port name varies.
        IODevice myGroveBoard = new FirmataDevice(myPort); // Board object, using the name of a port
        try {
            myGroveBoard.start(); // start comms with board;
            System.out.println("Board started.");
            myGroveBoard.ensureInitializationIsDone(); // make sure connection is good to board.

            I2CDevice i2cObject = myGroveBoard.getI2CDevice((byte) 0x3C); // Use 0x3C for the Grove OLED
            SSD1306 theOledObject = new SSD1306(i2cObject, SSD1306.Size.SSD1306_128_64); // 128x64 OLED SSD1515// Set up the OLED  display (type, size ...)
            theOledObject.init();
            theOledObject.clear();

            myGroveBoard.stop(); // finish with the board. Shut down the connection.
            System.out.println("Board stopped.");

        }
        catch (Exception ex){
            System.out.println("couldn't connect to board."); // message if the connection didn’t happen.
        }
    }
}
