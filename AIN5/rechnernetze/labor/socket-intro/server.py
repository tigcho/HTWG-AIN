import socket
import struct

def calculate_result(operation, numbers):
    if operation == "SUM": return sum(numbers)
    elif operation == "PRO": 
        result = 1
        for n in numbers: result *= n
        return result
    elif operation == "MIN": return min(numbers)
    elif operation == "MAX": return max(numbers)
    return 0

socket_type = input("Choose server type (tcp/udp): ").lower()

if socket_type == "tcp":
    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server.bind(('127.0.0.1', 12345))
    server.listen(1)
    print("TCP Server running on port 12345")
    
    while True:
        client_sock, addr = server.accept()
        try:
            data = client_sock.recv(4)
            task_id = struct.unpack('!I', data)[0]
            operation = client_sock.recv(3).decode('utf-8')
            n = struct.unpack('!B', client_sock.recv(1))[0]
            
            numbers = []
            for _ in range(n):
                num = struct.unpack('!i', client_sock.recv(4))[0]
                numbers.append(num)
            
            result = calculate_result(operation, numbers)
            response = struct.pack('!Ii', task_id, result)
            client_sock.send(response)
        finally:
            client_sock.close()
else:
    server = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    server.bind(('127.0.0.1', 12345))
    print("UDP Server running on port 12345")
    
    while True:
        data, addr = server.recvfrom(1024)
        task_id = struct.unpack('!I', data[0:4])[0]
        operation = data[4:7].decode('utf-8')
        n = struct.unpack('!B', data[7:8])[0]
        
        numbers = []
        for i in range(n):
            num = struct.unpack('!i', data[8+i*4:12+i*4])[0]
            numbers.append(num)
        
        result = calculate_result(operation, numbers)
        response = struct.pack('!Ii', task_id, result)
        server.sendto(response, addr)

        if input("Do you want to exit? (y/n): ").lower() == "y":
            break
    server.close()
