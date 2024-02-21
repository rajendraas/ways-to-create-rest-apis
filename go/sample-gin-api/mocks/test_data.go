package mock

const (
	WeatherResponse = string(`{
		"latitude": 18.625,
		"longitude": 74,
		"generationtime_ms": 0.03707408905029297,
		"utc_offset_seconds": 19800,
		"timezone": "Asia/Calcutta",
		"timezone_abbreviation": "IST",
		"elevation": 562,
		"current_units": {
		  "time": "iso8601",
		  "interval": "seconds",
		  "temperature_2m": "°C",
		  "wind_speed_10m": "km/h"
		},
		"current": {
		  "time": "2024-02-03T10:00",
		  "interval": 900,
		  "temperature_2m": 24.2,
		  "wind_speed_10m": 7.6
		}
	  }`)
	SunriseSunsetResponse = string(`{
		"results": {
		  "sunrise": "7:06:23 AM",
		  "sunset": "6:30:11 PM",
		  "solar_noon": "12:48:17 PM",
		  "day_length": "11:23:48",
		  "civil_twilight_begin": "6:44:51 AM",
		  "civil_twilight_end": "6:51:42 PM",
		  "nautical_twilight_begin": "6:18:42 AM",
		  "nautical_twilight_end": "7:17:52 PM",
		  "astronomical_twilight_begin": "5:52:44 AM",
		  "astronomical_twilight_end": "7:43:50 PM"
		},
		"status": "OK",
		"tzid": "Asia/Kolkata"
	  }`)
)
