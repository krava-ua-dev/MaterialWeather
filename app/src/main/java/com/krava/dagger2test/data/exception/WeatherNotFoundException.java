package com.krava.dagger2test.data.exception;

/**
 * Exception throw by the application when a Weather search can't return a valid result.
 */
public class WeatherNotFoundException extends Exception {

  public WeatherNotFoundException() {
    super();
  }

  public WeatherNotFoundException(final String message) {
    super(message);
  }

  public WeatherNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public WeatherNotFoundException(final Throwable cause) {
    super(cause);
  }
}
