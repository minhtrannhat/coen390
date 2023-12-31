# COEN390 Team 7 Project: Parking Spot Sensor

## Directories

- `coen390_android_app`: Android app for users' parking needs.
- `coen390_hardware`: Arduino project files for ESP32 hardware and CAD files.

## Notification Test

- Go the firebase realtime database console from your browser.
- Manually change the spot status A1 and A2 so that they are both `true`.
- You should get a new notification, regardless of whether or not the app is running, in the background or not running at all.

## Firebase's Firestore dev tools

- `cd utils`
- Make sure you have python3 installed
- Install the Pip dependencies with `pip install -r requirements.txt`
- Run the init data script `python3 src/init.py`

### Basic schema

```json
{
  "lots": {
    "Lb_building": {
      "name": "LB Building",
      "address": "1400 Maisonneuve Blvd W",
      "city": "Montreal",
      "postal_code": "H3G 1M8",
      "lot_owner": "Bob DeBuilder",
      "owner_tel": "5148482424",
      "floor_total": 1,
      "placed_occupancy": 2,
      "current_occupancy": 0,
      "max_occupancy": 10,
      "occupancy": {
        "firstFloor": {
          "A1": false,
          "A2": false
        }
      },
      "map_size": "small"
    }
  },
  "admins": {
    "Bob": {
      "username": "admin",
      "password": "123456",
      "lots": ["1"]
    }
  }
}
```
