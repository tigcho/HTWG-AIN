import socket
from messageprotocol import MessageProtocol

class UDPHandler:
    MAX_RETRIES = 3
    TIMEOUT = 2.0  # seconds
    
    def __init__(self, udp_socket):
        self.udp_socket = udp_socket
        
    def send_with_ack(self, message, address):
        """Send a UDP message and wait for acknowledgment"""
        self.udp_socket.settimeout(self.TIMEOUT)
        message_data = MessageProtocol.pack_message(message)
        
        for attempt in range(self.MAX_RETRIES):
            try:
                self.udp_socket.sendto(message_data, address)
                data, addr = self.udp_socket.recvfrom(1024)
                if data.decode() == "ACK":
                    return True
            except socket.timeout:
                print(f"Attempt {attempt + 1} timed out")
                continue
        return False
        
    def receive_and_ack(self):
        """Receive a UDP message and send acknowledgment"""
        try:
            data, addr = self.udp_socket.recvfrom(4096)
            # Send acknowledgment
            self.udp_socket.sendto("ACK".encode(), addr)
            return MessageProtocol.receive_message(data), addr
        except:
            return None, None
