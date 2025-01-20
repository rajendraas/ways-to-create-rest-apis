package io.github.rajendrasatpute.samplespringmvcapi.exception;

public class CityAlreadyExistsException extends Exception {
    private static final String ERROR_MESSAGE = "City %s already exists";

    public CityAlreadyExistsException(String cityName) {
        super(String.format(ERROR_MESSAGE, cityName));
    }
}
