package ik.secretcloset.screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public abstract class Driverable {
    protected AppiumDriver<MobileElement> driver;

    protected Driverable(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
    }
}
