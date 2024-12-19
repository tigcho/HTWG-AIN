import socket
import struct
from enum import Enum

# convert because socket functions require bytes
class Operation(Enum):
    SUM = b"SUM"
    PRODUCT = b"PRO"
    MINIMUM = b"MIN"
    MAXIMUM = b"MAX"

class CalculatorClient:
    def __init__(self):
        self.host = "localhost"
        self.port = 12345
        # let the user choose socket type
        self.socket = None

    def create_socket(self, sock_type):
        if sock_type == "1": # UDP
            self.socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        else: # TCP
            self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    def get_op(self):
        print("Choose operation (1-4):")
        print("1. Sum")
        print("2. Product")
        print("3. Minimum")
        print("4. Maximum")
        
        choice = input()

        if choice == "1":
            return Operation.SUM
        elif choice == "2":
            return Operation.PRODUCT
        elif choice == "3":
            return Operation.MINIMUM
        elif choice == "4":
            return Operation.MAXIMUM
        else:
            print("Invalid choice. Try again.")
            return self.get_op()

    def get_numbers(self):
        print("Enter numbers separated by space: ")
        numbers_input = input()
        return [int(x) for x in numbers_input.split()]

    def create_msg(self, op, numbers):
    # ! - network big-endian, makes sure every computer stores numbers the same way
    # I - unsigned int (4 bytes) for msg_id
    # 3s - for operation value
    # B - length of numbers
        msg = struct.pack("!I3sB", 1, op.value, len(numbers))

        for num in numbers:
        # i - signed int (4 bytes) for calc nums
            msg += struct.pack("!i", num)

        return msg

    def send_and_receive(self, msg):
        if self.socket.type == socket.SOCK_DGRAM:
            self.socket.sendto(msg, (self.host, self.port))
            response, _ = self.socket.recvfrom(1024)
        else:
            self.socket.connect((self.host, self.port))
            self.socket.send(msg)
            response = self.socket.recv(1024)

        msg_id, result = struct.unpack("!Ii", response)
        return result

    def run(self):
        try:
            op = self.get_op()
            numbers = self.get_numbers()
            msg = self.create_msg(op, numbers)
            result = self.send_and_receive(msg)
            print(f"\nResult: {result}")
        finally:
            if self.socket:
                self.socket.close()

def main():
    print("Choose socket type (1-2):")
    print("1. UDP\n2. TCP")
    sock_type = input().strip()

    client = CalculatorClient()
    client.create_socket(sock_type)
    client.run()

if __name__ == "__main__":
    main()
