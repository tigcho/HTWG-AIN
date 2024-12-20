import socket
import base64
import ssl
import time
import os

class SMTPClient:
    def __init__(self, server: str = "asmtp.htwg-konstanz.de", port: int = 587):
        self.server = server
        self.port = port
        self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)  
        self.ssl_socket = None

    def connect(self) -> None:
        try:
            print(f"Connecting to {self.server}:{self.port}...")
            self.socket.connect((self.server, self.port))

            response = self.socket.recv(1024).decode('utf-8')
            print(f"Server: {response}")
        
            self.send_command("EHLO " + self.server)
            self.send_command("STARTTLS")

            context = ssl.create_default_context()
            context.check_hostname = False
            context.verify_mode = ssl.CERT_NONE

            self.ssl_socket = context.wrap_socket(
                self.socket,
                server_hostname=self.server,
                do_handshake_on_connect=True
            )
            print("SSL/TLS connection established")

        except (socket.error, ssl.SSLError) as e:
            print(f"Connection error: {e}")
            self.close()
            raise

    def send_command(self, command: str) -> str:
        print(f"Sending: {command}")
        socket_to_use = self.ssl_socket if self.ssl_socket else self.socket
        
        if socket_to_use is None:
            raise RuntimeError("No socket connection available")
            
        try:
            socket_to_use.send((command + "\r\n").encode('utf-8'))
            response = socket_to_use.recv(1024).decode('utf-8')
            print(f"Server: {response}")
            return response
        except socket.error as e:
            print(f"Socket error: {e}")
            raise

    def login(self, username: str, password: str) -> None:
        self.send_command("AUTH LOGIN")
        self.send_command(base64.b64encode(username.encode()).decode())
        self.send_command(base64.b64encode(password.encode()).decode())

    def send_email(self, sender: str, recipient: str, subject: str, body: str) -> None:
        try:
            self.send_command(f"MAIL FROM: <{sender}>")
            self.send_command(f"RCPT TO: <{recipient}>")
            self.send_command("DATA")
            
            email_content = [
                f"From: {sender}",
                f"To: {recipient}",
                f"Subject: {subject}",
                f"Datetime: {time.strftime('%a, %d %b %Y %H:%M:%S %z')}",
                "",
                body,
                "."
            ]

            self.send_command("\r\n".join(email_content))

        except Exception as e:
            print(f"Error sending email: {e}")
            raise

    def close(self) -> None:
        try:
            if self.ssl_socket or self.socket:
                self.send_command("QUIT")
            if self.ssl_socket:
                self.ssl_socket.close()
            if self.socket:
                self.socket.close()
        except Exception as e:
            print(f"Error closing connection: {e}")
            raise

def main():
    smtp = SMTPClient()

    try:
        smtp.connect()
        smtp.login(
            os.environ["SMTPUSER"],
            os.environ["SMTPPASS"]
        )
        smtp.send_email(
            sender = "se681kab@htwg-konstanz.de",
            recipient = "damianjoan@protonmail.com",
            subject = "Eternal Love ❤️",
            body = "I love you"
        )
        print("Email sent successfully!")

    except Exception as e:
        print(f"An error occurred: {e}")

    finally:
        smtp.close()

if __name__ == "__main__":
    main()
