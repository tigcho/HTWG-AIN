import struct
import json

class MessageProtocol:
    HEADER_SIZE = 4  # Size of length prefix in bytes
    
    @staticmethod
    def pack_message(message_dict):
        """Pack a dictionary into a message with length prefix"""
        json_data = json.dumps(message_dict)
        encoded_data = json_data.encode()
        length = len(encoded_data)
        header = struct.pack('!I', length)  # Network byte order (big-endian)
        return header + encoded_data
    
    @staticmethod
    def receive_message(sock):
        """Receive a complete message from a socket"""
        # First receive the header with message length
        header = sock.recv(MessageProtocol.HEADER_SIZE)
        if not header:
            return None
            
        msg_length = struct.unpack('!I', header)[0]
        
        # Receive the full message
        data = b''
        while len(data) < msg_length:
            chunk = sock.recv(min(msg_length - len(data), 4096))
            if not chunk:
                return None
            data += chunk
            
        try:
            return json.loads(data.decode())
        except json.JSONDecodeError:
            raise ValueError("Received malformed JSON message")
