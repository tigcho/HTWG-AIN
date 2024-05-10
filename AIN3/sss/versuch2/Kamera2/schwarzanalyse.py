import cv2 as cv

image = []

for i in range(0, 10):
    image[i] = cv.imread(f'schwarz/schwarzbild{i}.png').astype(float)