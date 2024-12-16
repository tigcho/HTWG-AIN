import socket
import threading
import logging
import json

logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(message)s')


class MessageProtocol:
    @staticmethod
    def pack(message):
        return json.dumps(message).encode('utf-8')

    @staticmethod
    def unpack(data):
        return json.loads(data.decode('utf-8'))

    @staticmethod
    def receive(sock):
        try:
            data = sock.recv(1024)
            if data:
                return MessageProtocol.unpack(data)
            return None
        except Exception as e:
            logging.error(f"Error receiving data: {e}")
            return None


class ChatServer:
    def __init__(self, host='localhost', port=8080):
        self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.socket.bind((host, port))
        self.socket.listen(5)

        self.clients = {}  
        self.client_sockets = {}  
        self.lock = threading.Lock()  

        logging.info(f"Server listening on {host}:{port}")

    def broadcast(self):
        while True:
            try:
                client_socket, client_address = self.socket.accept()
                logging.info(f"New connection from {client_address}")
                threading.Thread(target=self.handle_client, args=(client_socket, client_address), daemon=True).start()
            except Exception as e:
                logging.error(f"Error accepting connection: {e}")

    def handle_client(self, client_socket, client_address):
        peer_username = None
        try:
            message = MessageProtocol.receive(client_socket)
            if not message or message.get("type") != "register":
                raise ValueError("Invalid message")

            peer_username = message.get("username")
            udp_port = message.get("udp_port")
            if not peer_username or not udp_port:
                raise ValueError("Missing required fields")

            with self.lock:
                if peer_username in self.clients:
                    client_socket.send(MessageProtocol.pack({"type": "error", "message": "Username already in use"}))
                    client_socket.close()
                    return

                self.clients[peer_username] = (client_address[0], udp_port)
                self.client_sockets[peer_username] = client_socket

            logging.info(f"Client {peer_username} registered with UDP port {udp_port}")

            response = {"type": "registered", "username": peer_username, "clients": list(self.clients)}
            client_socket.send(MessageProtocol.pack(response))

            self.broadcast_update("join", peer_username)

            while True:
                message = MessageProtocol.receive(client_socket)
                if not message:
                    break
                if message.get("type") == "broadcast":
                    self.handle_broadcast(peer_username, message)
        except Exception as e:
            logging.error(f"Error handling client {peer_username}: {e}")
        finally:
            self.disconnect_client(peer_username, client_socket)

    def handle_broadcast(self, sender, message):
        if "message" not in message:
            return

        broadcast_message = {
            "type": "broadcast",
            "from": sender,
            "message": message["message"]
        }

        packed_message = MessageProtocol.pack(broadcast_message)
        with self.lock:
            for username, client in list(self.client_sockets.items()):
                try:
                    client.send(packed_message)
                except Exception as e:
                    logging.error(f"Error sending broadcast to {username}: {e}")
                    self.client_sockets.pop(username, None)

    def broadcast_update(self, event_type, username):
        update_message = {
            "type": "update",
            "event": event_type,
            "username": username
        }

        packed_message = MessageProtocol.pack(update_message)
        with self.lock:
            for username, client in list(self.client_sockets.items()):
                try:
                    client.send(packed_message)
                except Exception as e:
                    logging.error(f"Error sending update to {username}: {e}")
                    self.client_sockets.pop(username, None)

    def disconnect_client(self, username, client_socket):
        if username:
            logging.info(f"Client {username} disconnected")
            with self.lock:
                self.clients.pop(username, None)
                self.client_sockets.pop(username, None)
            self.broadcast_update("leave", username)
        client_socket.close()

    def start_udp_listener(self, udp_port):
        udp_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        udp_socket.bind(('', udp_port))
        logging.info(f"UDP listener started on port {udp_port}")
        while True:
            try:
                data, address = udp_socket.recvfrom(1024)
                message = MessageProtocol.unpack(data)
                if message.get("type") == "discover":
                    response = {"type": "discovery_response", "clients": list(self.clients)}
                    udp_socket.sendto(MessageProtocol.pack(response), address)
            except Exception as e:
                logging.error(f"Error handling UDP message: {e}")

    def shutdown(self):
        logging.info("Shutting down server")
        with self.lock:
            for client_socket in self.client_sockets.values():
                try:
                    client_socket.close()
                except Exception as e:
                    logging.error(f"Error closing client socket: {e}")
        self.socket.close()
        logging.info("Server shutdown")


if __name__ == "__main__":
    server = ChatServer(host='localhost', port=8080)
    try:
        threading.Thread(target=server.start_udp_listener, args=(8081,), daemon=True).start()
        server.broadcast()
    except KeyboardInterrupt:
        logging.info("Keyboard interrupt detected. Shutting down server...")
    finally:
        server.shutdown()

