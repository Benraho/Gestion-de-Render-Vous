import os
import pickle
import numpy as np
import cv2
import face_recognition
import firebase_admin
from firebase_admin import credentials
from firebase_admin import db
from firebase_admin import storage
import numpy as np
from datetime import datetime

# Import Firebase credentials from the service account key JSON file
cred = credentials.Certificate("serviceAccountKey.json")
if not firebase_admin._apps:
    firebase_admin.initialize_app(cred, {
        'databaseURL': "https://menara-79435-default-rtdb.firebaseio.com/",
    })

cap = cv2.VideoCapture(0)
cap.set(3, 640)
cap.set(4, 480)

imgBackground = cv2.imread('static/Files/Resources/background.png')

# Importing the mode images into a list
folderModePath = 'static/Files/Resources/Modes'
modePathList = os.listdir(folderModePath)
imgModeList = []
for path in modePathList:
    imgModeList.append(cv2.imread(os.path.join(folderModePath, path)))

modeType = 0
counter = 0
id = -1
imgemploye = []

# Load encoding file
print("Loading Encode File ...")
file = open('EncodeFile.p', 'rb')
encodeListKnownWithIds = pickle.load(file)
file.close()
encodeListKnown, employeIds = encodeListKnownWithIds
print("Encode File Loaded")

while True:
    success, img = cap.read()

    imgS = cv2.resize(img, (0, 0), None, 0.25, 0.25)
    imgS = cv2.cvtColor(imgS, cv2.COLOR_BGR2RGB)

    faceCurFrame = face_recognition.face_locations(imgS)
    encodeCurFrame = face_recognition.face_encodings(imgS, faceCurFrame)

    imgBackground[162:162 + 480, 55:55 + 640] = img
    imgBackground[44:44 + 633, 808:808 + 414] = imgModeList[modeType]

    if faceCurFrame:
        for encodeFace, faceLoc in zip(encodeCurFrame, faceCurFrame):
            matches = face_recognition.compare_faces(encodeListKnown, encodeFace)
            faceDis = face_recognition.face_distance(encodeListKnown, encodeFace)

            matchIndex = np.argmin(faceDis)

            if matches[matchIndex]:
                y1, x2, y2, x1 = faceLoc
                y1_new, x2_new, y2_new, x1_new = y1 * 4, x2 * 4, y2 * 4, x1 * 4

                x = 55 + x1_new
                y = 162 + y1_new
                width = x2_new - x1_new
                height = y2_new - y1_new
                bbox = (x, y, width, height)
                color = (0, 255, 0)  # Color in BGR format (green in this case)
                thickness = 2      # Line thickness
                imgBackground = cv2.rectangle(imgBackground, (int(x), int(y)), (int(x + width), int(y + height)), color, thickness)
                id = employeIds[matchIndex]
                print(id)
                if counter == 0:
                    cv2.imshow("TEST", imgBackground)
                    cv2.waitKey(1)
                    counter = 1
                    modeType = 1

        if counter != 0:
            if counter == 1:
                employeInfo = db.reference(f'Employees/{id}').get()
                image_path = f'static/Files/Images/{id}.png'
                imgemploye = cv2.imread(image_path)
                imgemploye = cv2.resize(imgemploye, (216, 216))
                imgBackground[175:175 + 216, 909:909 + 216] = imgemploye
                datetimeObject = datetime.strptime(employeInfo['last_attendance_time'], "%Y-%m-%d %H:%M:%S")
                secondsElapsed = (datetime.now() - datetimeObject).total_seconds()
                if secondsElapsed > 22:
                    ref = db.reference(f'Employees/{id}')
                    employeInfo['total_attendance'] += 1
                    ref.child('total_attendance').set(employeInfo['total_attendance'])
                    ref.child('last_attendance_time').set(datetime.now().strftime("%Y-%m-%d %H:%M:%S"))
                else:
                    modeType = 3
                    counter = 0
                    imgBackground[44:44 + 633, 808:808 + 414] = imgModeList[modeType]

            if modeType != 3:
                if 10 < counter < 20:
                    modeType = 2

                imgBackground[44:44 + 633, 808:808 + 414] = imgModeList[modeType]

                if counter <= 10:
                    # Display attendance and employee information
                    cv2.putText(imgBackground, str(employeInfo['total_attendance']), (861, 125), cv2.FONT_HERSHEY_COMPLEX, 1, (255, 255, 255), 1)
                    cv2.putText(imgBackground, str(employeInfo['major']), (1006, 550), cv2.FONT_HERSHEY_COMPLEX, 0.5, (255, 255, 255), 1)
                    cv2.putText(imgBackground, str(id), (1006, 493), cv2.FONT_HERSHEY_COMPLEX, 0.5, (255, 255, 255), 1)

                    (w, h), _ = cv2.getTextSize(employeInfo['name'], cv2.FONT_HERSHEY_COMPLEX, 1, 1)
                    offset = (414 - w) // 2
                    cv2.putText(imgBackground, str(employeInfo['name']), (808 + offset, 445), cv2.FONT_HERSHEY_COMPLEX, 1, (50, 50, 50), 1)

                    imgBackground[175:175 + 216, 909:909 + 216] = imgemploye

                counter += 1

                if counter >= 20:
                    counter = 0
                    modeType = 0
                    employeInfo = {}
                    imgemploye = []
                    imgBackground[44:44 + 633, 808:808 + 414] = imgModeList[modeType]
    else:
        modeType = 0
        counter = 0

    cv2.imshow("TEST", imgBackground)
    cv2.waitKey(1)
    