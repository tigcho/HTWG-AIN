import socket
import threading
import logging
import json

# example output:
# 2021-07-11 14:40:45,123 - [username]: Hello, world!
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(message)s')

class ChatClient:
    def __init__(self, username, host='localhost', port=8080):
        self.username = username
        self.peers = {}
        self.peer_connections = {}

        # server connection
        self.socket = self.create_tcp_socket((host, port))
        
        # discover peers
        self.udp_socket = self.create_udp_socket()
        self.udp_port = self.udp_socket.getsockname()[1]

        # start listening for peers
        self.tcp_listener = self.create_tcp_listener()
        self.tcp_port = self.tcp_listener.getsockname()[1]

        logging.info(f"Client initialized on UDP: {self.udp_port}, TCP: {self.tcp_port}")
        
        # start listening for messages
        threading.Thread(target=self.listen_for_peers, daemon=True).start()

    def create_tcp_socket(self, address):
        try:
            sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            sock.connect(address)
            logging.info(f"Connected to server at {address}")
            return sock
        except Exception as e:
            logging.error(f"Failed to connect to server: {e}")
        raise

    def create_udp_socket(self):
        sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        sock.bind(('', 0))
        return sock

    def create_tcp_listener(self):
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        sock.bind(('', 0))
        # 5 is the maximum number of queued connections
        sock.listen(5)
        return sock

    
    def listen_for_peers(self):
        while True:
            try:
                peer_socket, address = self.tcp_listener.accept()
                if peer_socket:
                    logging.info(f"Accepted connection from {address}")
                    threading.Thread(target=self.handle_peer, args=(peer_socket, address), daemon=True).start()
            except Exception as e:
                logging.error(f"Error accepting connection: {e}")

            
    def handle_peer(self, peer_socket, address):
        peer_username = 'Unknown'
        try:
            peer_data = json.loads(peer_socket.recv(1024).decode('utf-8'))
            peer_username = peer_data.get('username', 'Unknown')
            logging.info(f"Connected to {peer_username} at {address}")

            self.peer_connections[peer_username] = peer_socket
            while True:
                message = peer_socket.recv(1024).decode('utf-8')
                if not message:
                    break
                logging.info(f"{peer_username}: {message}")
        except Exception as e:
            logging.error(f"Error handling peer: {e}")
        finally:
            peer_socket.close()
            logging.info(f"Connection to {peer_username} closed")

    def initiate_chat(self, peer_username):
        if peer_username not in self.peers:
            logging.warning(f"User {peer_username} not found")
            return

        if peer_username in self.peer_connections:
            logging.info(f"Chat with {peer_username} already initiated")
            return
        
        peer_ip, peer_udp_port = self.peers[peer_username]
        message = json.dumps({"type": "chat_request", "nickname": self.username, "tcp_port": self.tcp_port})

        try:
            self.udp_socket.sendto(message.encode('utf-8'), (peer_ip, peer_udp_port))
            logging.info(f"Sent chat request to {peer_username} at {peer_ip}:{peer_udp_port}")
        except Exception as e:
            logging.error(f"Error sending chat request: {e}")
    
    def close(self):
        try:
            self.socket.close()
            self.udp_socket.close()
            self.tcp_listener.close()
            for peer_socket in self.peer_connections.values():
                peer_socket.close()
            logging.info("Client shut down successfully")
        except Exception as e:
            logging.error(f"Error during shutdown: {e}")
