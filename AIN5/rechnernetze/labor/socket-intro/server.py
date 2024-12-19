import socket
import struct
from enum import Enum

class Operation(Enum):
    SUM = b"SUM"
    PRODUCT = b"PRO"
    MINIMUM = b"MIN"
    MAXIMUM = b"MAX"

class CalculationServer:
    def __init__(self):
        self.host = "localhost"
        self.port = 12345
        self.socket = None

    def create_socket(self, sock_type):
        if sock_type == "1": 
            self.socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        else:
            self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    def decode_msg(self, msg):
    # Message format:
    #
    # struct.unpack("!I3sB", msg[:8])
    # [1][SUM][3] [5][-2][10]     # actual values
    #  |  |   |   |  |   |
    #  4b 3b  1b  4b 4b  4b       # byte size
    #  ID op  cnt num num num     # what it means
    #  <-header-> <-numbers->
    #
        msg_id, op, count = struct.unpack("!I3sB", msg[:8]) 
        numbers = list(struct.unpack(f"!{count}i", msg[8:])) # take everything after header
        return msg_id, op, numbers

    def calc(self, op, numbers):
        if op == Operation.SUM.value:
            return sum(numbers)
        elif op == Operation.PRODUCT.value:
            result = 1
            for num in numbers:
                result *= num
            return result
        elif op == Operation.MINIMUM.value:
            return min(numbers)
        elif op == Operation.MAXIMUM.value:
            return max(numbers)

    def serve_forever(self):
        self.socket.bind((self.host, self.port))

        if self.socket.type == socket.SOCK_STREAM:
            self.socket.listen(5)
            print(f"TCP Server listening on {self.host}:{self.port}")

            while True:
                client_socket, address = self.socket.accept()
                try:
                    msg = client.socket.recv(1024)
                    if msg:
                        msg_id, op, numbers = self.decode_msg(msg)
                        result = self.calc(op, numbers)
                        response = struct.pack("!Ii", msg_id, result)
                        client_socket.send(response)
                finally:
                    client_socket.close()
        else:
            print(f"UDP Server listening on {self.host}:{self.port}")
            
            while True:
                msg, address = self.socket.recvfrom(1024)
                msg_id, op, numbers = self.decode_msg(msg)
                result = self.calc(op, numbers)
                response = struct.pack("!Ii", msg_id, result)
                self.socket.sendto(response, address)

def main():
    print("Choose socket type (1-2): ")
    print("1. UDP\n2. TCP")
    sock_type = input().strip()

    server = CalculationServer()
    server.create_socket(sock_type)

    try:
        server.serve_forever()
    except KeyboardInterrupt:
        print("Server stopped...")
    finally:
        if server.socket:
            server.socket.close()

if __name__ == "__main__":
    main()
