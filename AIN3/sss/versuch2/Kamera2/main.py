import cv2 as cv
import matplotlib.pyplot as plt
from skimage.color import rgb2gray

cap = cv.VideoCapture(0)
print(cap.get(cv.CAP_PROP_EXPOSURE))
while(True):
    ret, frame = cap.read()
    grayscale = cv.cvtColor(frame, cv.COLOR_BGR2GRAY)
    hist = cv.calcHist([grayscale], [0], None, [256], [0, 256])
    cv.imshow('Histogram', hist)
    cv.imshow('Grayscale', grayscale)

    if cv.waitKey(1) & 0xFF == ord('q'):
        for i in range(0, 10):
            ret, frame = cap.read()
            grayscale = cv.cvtColor(frame, cv.COLOR_BGR2GRAY)
            cv.imwrite(f'weiss/weissbild{i}.png', grayscale)
            cv.waitKey(1)
        break

cap.release()
cv.destroyAllWindows()