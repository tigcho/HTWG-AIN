import pyaudio
import numpy
import matplotlib.pyplot as plt

FORMAT = pyaudio.paInt16
SAMPLEFREQ = 44100
FRAMESIZE = 1024
NOFFRAMES = 220

for i in range(5):

    p = pyaudio.PyAudio()
    print(f'running {i + 1}')

    stream = p.open(format=FORMAT, channels=1, rate=SAMPLEFREQ, input=True, frames_per_buffer=FRAMESIZE)
    data = stream.read(NOFFRAMES * FRAMESIZE)
    decoded = numpy.frombuffer(data, dtype=int)

    stream.stop_stream()
    stream.close()
    p.terminate()
    print('done')
    # plt.plot(decoded)
    # plt.show()

    name = f"testCommands/sprecher2/tief/tief{i}"
    numpy.save(name, decoded)
