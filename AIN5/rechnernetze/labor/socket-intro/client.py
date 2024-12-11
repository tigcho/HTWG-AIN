import socket
import struct

socket_type = input("Choose socket type (tcp/udp): ").lower()
if socket_type == "tcp":
    client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client.connect(('127.0.0.1', 12345))
else:
    client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

try:
    task_id = int(input("Task ID (1-999): "))
    print("\nOperations: SUM, PRO, MIN, MAX")
    operation = input("Operation: ").upper()
    numbers = [int(x) for x in input("Numbers (space-separated): ").split()]

    message = struct.pack('!I', task_id)
    message += operation.encode('utf-8')
    message += struct.pack('!B', len(numbers))
    for num in numbers:
        message += struct.pack('!i', num)

    if socket_type == "tcp":
        client.send(message)
        response = client.recv(8)
    else:
        client.sendto(message, ('127.0.0.1', 12345))
        response, _ = client.recvfrom(8)

    resp_id, result = struct.unpack('!Ii', response)
    print(f"Result: {result}")

finally:
    client.close()
