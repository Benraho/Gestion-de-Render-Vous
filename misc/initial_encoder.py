import cv2
import pickle
import face_recognition
import mysql.connector
import numpy as np

# Connexion à la base de données MySQL
connection = mysql.connector.connect(
    host="localhost",
    user="root",
    password="",  # Entrez votre mot de passe MySQL
    database="gestion"
)

def get_employee_images():
    cursor = connection.cursor(dictionary=True)
    cursor.execute("SELECT id, image FROM employee WHERE image IS NOT NULL")
    employees = cursor.fetchall()
    cursor.close()
    return employees

def find_encodings(images):
    encode_list = []
    for img in images:
        img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
        encodings = face_recognition.face_encodings(img)
        if encodings:
            encode_list.append(encodings[0])
        else:
            print("Aucun visage détecté dans l'image.")
    return encode_list

print("Loading employee images from database...")

employees = get_employee_images()
img_list = [cv2.imdecode(np.frombuffer(employee['image'], np.uint8), cv2.IMREAD_COLOR) for employee in employees]
employee_ids = [str(employee['id']) for employee in employees]

print("Encoding Started")

encode_list_known = find_encodings(img_list)

encode_list_known_with_ids = [encode_list_known, employee_ids]

with open("EncodeFile.p", "wb") as file:
    pickle.dump(encode_list_known_with_ids, file)

print("Encoding Ended")
