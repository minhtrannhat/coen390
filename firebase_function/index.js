const admin = require("firebase-admin");
const functions = require("firebase-functions");
const serviceAccount = require("./test-hw-project-86ca6-firebase-adminsdk-ni11w-f9f3d24f06.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://test-hw-project-86ca6-default-rtdb.firebaseio.com",
});

/**
 * Firebase Cloud Messaging service function.
 * @type {Function}
 */
exports.sendNotificationOnOccupancyChange = functions.database
  .ref("/Test/lots/Lb_building/occupancy")
  .onUpdate((change, context) => {
    const occupancySnapshot = change.after.val();

    // Check if both values in the firstFloor key are true
    if (
      occupancySnapshot.firstFloor &&
      occupancySnapshot.firstFloor.A1 &&
      occupancySnapshot.firstFloor.A2
    ) {
      // If true, send a message to the FCM topic
      const message = {
        data: {
          title: "Occupancy Alert",
          body: "Both lots on the first floor are occupied!",
        },
        topic: "occupancy_alert",
      };

      // Log the message to the Realtime Database for later viewing
      const logRef = admin.database().ref("/logs").push();
      logRef.set({
        timestamp: admin.database.ServerValue.TIMESTAMP,
        message,
        topic: "occupancy_alert",
      });

      return admin
        .messaging()
        .send(message)
        .then((response) => {
          console.log("Message sent successfully:", response);
          return null;
        })
        .catch((error) => {
          console.error("Error sending message:", error);
          return null;
        });
    }

    return null;
  });
