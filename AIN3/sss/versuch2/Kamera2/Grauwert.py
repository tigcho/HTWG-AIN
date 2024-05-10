import cv2 as cv
import matplotlib.pyplot as plt
import numpy as np

image = cv.imread('frame.png')

print("Min: ", image.min())
print("Max: ", image.max())
print("Mean: ", image.mean())
print("Std: ", image.std())


schwarz = image[:,0:50]
grau1 = image[:,150:200]
grau2 = image[:,300:350]
grau3 = image[:,400:450]
weiß = image[:,550:600]

cv.imwrite("unterbildschwarz.png", schwarz)
cv.imwrite("unterbildgrau1.png", grau1)
cv.imwrite("unterbildgrau2.png", grau2)
cv.imwrite("unterbildgrau3.png", grau3)
cv.imwrite("unterbildweiss.png", weiß)

print("Schwarz: STD: %f | Mean: %f" %(np.std(schwarz),np.mean(schwarz)))
print("Grau1: STD: %f | Mean: %f" %(np.std(grau1),np.mean(grau1)))
print("Grau2: STD: %f | Mean: %f" %(np.std(grau2),np.mean(grau2)))
print("Grau3: STD: %f | Mean: %f" %(np.std(grau3),np.mean(grau3)))
print("Weiß: STD: %f | Mean: %f" %(np.std(weiß),np.mean(weiß)))