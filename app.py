from flask import Flask, render_template, Response, redirect, url_for
import cv2
import os
import pickle
import face_recognition
import numpy as np
import mysql.connector
from datetime import datetime

app = Flask(__name__)

# Connexion à la base de données MySQL
connection = mysql.connector.connect(
    host="localhost",
    user="root",
    password="",  # Entrez votre mot de passe MySQL
    database="gestion"
)

def increment_attendance(employee_id):
    cursor = connection.cursor()
    query = "UPDATE employee SET nmbre_de_jour = nmbre_de_jour + 1 WHERE id = %s"
    cursor.execute(query, (employee_id,))
    connection.commit()
    cursor.close()

def get_employee_info(employee_id):
    cursor = connection.cursor(dictionary=True)
    cursor.execute("SELECT * FROM employee WHERE id = %s", (employee_id,))
    employee = cursor.fetchone()
    cursor.close()
    return employee

def generate_frame():
    capture = cv2.VideoCapture(0)
    capture.set(cv2.CAP_PROP_FRAME_WIDTH, 640)
    capture.set(cv2.CAP_PROP_FRAME_HEIGHT, 480)

    imgBackground = cv2.imread("static/Files/Resources/background.png")
    folderModePath = "static/Files/Resources/Modes/"
    modePathList = os.listdir(folderModePath)
    imgModeList = [cv2.imread(os.path.join(folderModePath, path)) for path in modePathList]

    modeType = 0
    counter = 0
    employee_id = -1
    imgEmployee = []
    last_detection_time = datetime.min

    # Charger le fichier d'encodage
    print("Chargement du fichier d'encodage ...")
    with open("EncodeFile.p", "rb") as file:
        encodeListKnownWithIds = pickle.load(file)
    encodeListKnown, employeeIds = encodeListKnownWithIds
    print("Fichier d'encodage chargé")

    while True:
        success, img = capture.read()

        imgSmall = cv2.resize(img, (0, 0), None, 0.25, 0.25)
        imgSmall = cv2.cvtColor(imgSmall, cv2.COLOR_BGR2RGB)

        faceCurrentFrame = face_recognition.face_locations(imgSmall)
        encodeCurrentFrame = face_recognition.face_encodings(imgSmall, faceCurrentFrame)

        imgBackground[162:162 + 480, 55:55 + 640] = img
        imgBackground[44:44 + 633, 808:808 + 414] = imgModeList[modeType]

        if faceCurrentFrame:
            for encodeFace, faceLocation in zip(encodeCurrentFrame, faceCurrentFrame):
                matches = face_recognition.compare_faces(encodeListKnown, encodeFace)
                faceDistance = face_recognition.face_distance(encodeListKnown, encodeFace)
                matchIndex = np.argmin(faceDistance)

                if matches[matchIndex]:
                    y1, x2, y2, x1 = faceLocation
                    y1_new, x2_new, y2_new, x1_new = y1 * 4, x2 * 4, y2 * 4, x1 * 4

                    x = 55 + x1_new
                    y = 162 + y1_new
                    width = x2_new - x1_new
                    height = y2_new - y1_new
                    bbox = (x, y, width, height)
                    color = (0, 255, 0)  # Couleur en format BGR (vert dans ce cas)
                    thickness = 2         # Épaisseur de la ligne
                    imgBackground = cv2.rectangle(imgBackground, (int(x), int(y)), (int(x + width), int(y + height)), color, thickness)

                    employee_id = employeeIds[matchIndex]
                    if counter == 0:
                        cv2.putText(imgBackground, "Visage détecté", (65, 200), cv2.FONT_HERSHEY_COMPLEX, 1, (255, 255, 255), 2)
                        counter = 1
                        modeType = 1

            if counter != 0:
                if counter == 1:
                    employeeInfo = get_employee_info(employee_id)
                    if employeeInfo is not None:
                        imgEmployee = cv2.imdecode(np.frombuffer(employeeInfo["image"], np.uint8), cv2.IMREAD_COLOR)
                        imgEmployee = cv2.resize(imgEmployee, (216, 216))
                        imgBackground[175:175 + 216, 909:909 + 216] = imgEmployee
                        
                        if (datetime.now() - last_detection_time).total_seconds() > 22:
                            increment_attendance(employee_id)
                            last_detection_time = datetime.now()
                        else:
                            modeType = 3
                            counter = 0
                            imgBackground[44:44 + 633, 808:808 + 414] = imgModeList[modeType]

            if modeType != 3:
                if 10 < counter <= 20:
                    modeType = 2

                imgBackground[44:44 + 633, 808:808 + 414] = imgModeList[modeType]

                if counter <= 10:
                    cv2.putText(imgBackground, str(employeeInfo['nom']), (1008, 530), cv2.FONT_HERSHEY_COMPLEX, 1, (255, 255, 255), 1)
                    cv2.putText(imgBackground, str(employee_id), (1006, 493), cv2.FONT_HERSHEY_COMPLEX, 1, (255, 255, 255), 1)
                    cv2.putText(imgBackground, str(employeeInfo['nmbre_de_jour']), (861, 125), cv2.FONT_HERSHEY_COMPLEX, 1, (255, 255, 255), 1)

                    imgEmployeeResize = cv2.resize(imgEmployee, (216, 216))
                    imgBackground[175:175 + 216, 909:909 + 216] = imgEmployeeResize

                counter += 1

                if counter >= 20:
                    counter = 0
                    modeType = 0
                    employeeInfo = None
                    imgEmployee = []
                    imgBackground[44:44 + 633, 808:808 + 414] = imgModeList[modeType]

        else:
            modeType = 0
            counter = 0

        # Encodage de l'image en JPEG
        ret, buffer = cv2.imencode('.jpg', imgBackground)
        frame = buffer.tobytes()

        yield (b"--frame\r\n" b"Content-Type: image/jpeg\r\n\r\n" + frame + b"\r\n")

@app.route("/")
def index():
    return render_template("index.html")

@app.route("/video")
def video():
    return Response(generate_frame(), mimetype="multipart/x-mixed-replace; boundary=frame")

@app.route("/agent")
def agent():
    return render_template("agent.html")

@app.route("/admin")
def admin():
    return redirect("http://localhost:4200/")

if __name__ == "__main__":
    app.run(debug=True)
