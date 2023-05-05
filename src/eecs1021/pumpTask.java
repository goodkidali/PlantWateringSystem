package eecs1021;

import org.firmata4j.Pin;
import org.firmata4j.ssd1306.MonochromeCanvas;
import org.firmata4j.ssd1306.SSD1306;

import java.util.TimerTask;

public class pumpTask extends TimerTask {
    private int duration;

    private final Pin pumpPin;
    private final SSD1306 display;
    private final Pin moistureS;

    // class constructor.
    public pumpTask(SSD1306 display, Pin pin, Pin pumpPin, int i) {
        this.moistureS = pin;
        this.display = display;
        this.pumpPin = pumpPin;
    }


    @Override
    public void run() {

        while (true) {

            var moistureSValue = moistureS.getValue();
            var moistureSString = Long.toString(moistureSValue);// note you need to convert the int to String.

            float max = 1023F;
            var a = (int) ((moistureSValue / max) * 128);

            if (moistureSValue >= 700) {

                    System.out.println("Moisture: " + moistureSString);// print to the Java console
                    System.out.println("Plant Requires Water - Pump: ON");
                    display.getCanvas().drawString(0, 0, "Moisture: " + moistureSString + "                   "); // Extra spaces to clear OLED without clearing entire thing
                    display.getCanvas().drawString(0, 10, "Pump: ON                         ");                   // This prevents the flickering of the screen while still clearing
                    display.getCanvas().setTextsize(1);

                    display.getCanvas().drawHorizontalLine(0, 30, 128, MonochromeCanvas.Color.DARK);//Next, erase the previous horizontal  line by applying "DARK"
                    if (a != 0) display.getCanvas().drawHorizontalLine(0, 30, (int) a, MonochromeCanvas.Color.BRIGHT);
                    display.display();

                try {

                    pumpPin.setValue(1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (moistureSValue <= 600) {

                    System.out.println("Moisture: " + moistureSString);// print to the Java console
                    System.out.println("Plant Has Enough Water - Pump: OFF");
                    display.getCanvas().drawString(0, 0, "Moisture: " + moistureSString + "                   ");
                    display.getCanvas().drawString(0, 10, "Pump: OFF                         ");
                    display.getCanvas().setTextsize(1);

                    display.getCanvas().drawHorizontalLine(0, 30, 128, MonochromeCanvas.Color.DARK);//Next, erase the previous horizontal  line by applying "DARK"
                    if (a != 0) display.getCanvas().drawHorizontalLine(0, 30, (int) a, MonochromeCanvas.Color.BRIGHT);
                    display.display();

                try {

                    pumpPin.setValue(0);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            else {

                    System.out.println("Moisture: " + moistureSString);// print to the Java console
                    System.out.println("Plant May Need Water Soon - Pump: OFF");
                    display.getCanvas().drawString(0, 0, "Moisture: " + moistureSString + "                   ");
                    display.getCanvas().drawString(0, 10, "Pump: OFF                         ");
                    display.getCanvas().setTextsize(1);

                    display.getCanvas().drawHorizontalLine(0, 30, 128, MonochromeCanvas.Color.DARK);//Next, erase the previous horizontal  line by applying "DARK"
                    if (a != 0) display.getCanvas().drawHorizontalLine(0, 30, (int) a, MonochromeCanvas.Color.BRIGHT);
                    display.display();

                try {

                    pumpPin.setValue(0);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }
    }
}
