import firebase_admin
from firebase_admin import credentials
from firebase_admin import db
import json

# Fetch the service account key JSON file contents
cred = credentials.Certificate(
    "test-hw-project-86ca6-firebase-adminsdk-ni11w-2bf3dcf38e.json"
)

# Initialize the app with a service account, granting admin privileges
firebase_admin.initialize_app(
    cred, {"databaseURL": "https://test-hw-project-86ca6-default-rtdb.firebaseio.com/"}
)

ref = db.reference("Test")

print(f"Current database values: {ref.get()}\n\n")

with open("init_data.json", "r") as init_data_file:
    init_data_content = json.load(init_data_file)

print(f"Json file content: {json.dumps(init_data_content, indent=2)}\n\n")

# set the database to what we have inside the json file
ref.set(init_data_content)

print(f"Updated database values: {ref.get()}\n\n")

# # comment out everything below here to not reset the database
# dummy_data_content = {"Distance": 18, "spotStatus": True}

# ref.set(dummy_data_content)

# print(f"Updated database values: {ref.get()}\n\n")
