package io.github.rajendrasatpute.samplespringbootapi.exception;

public class CityNotFoundException extends Exception {
    private static final String ERROR_MESSAGE = "City %s is not found";

    public CityNotFoundException(String cityName) {
        super(String.format(ERROR_MESSAGE, cityName));
    }
}
