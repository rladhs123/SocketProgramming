# Term Project #2  (part.1)

## 202312123_김온

## 프로젝트 설명

이 프로그램은 Java의 UDP 소켓(`DatagramSocket`, `DatagramPacket`)을 사용하여  
클라이언트가 파일을 서버로 전송하고, 서버는 이를 수신하여 저장하는 기능을 수행합니다.  

---

## 🛠️ 컴파일 및 실행 방법

```bash
# 컴파일
터미널(명령 프롬프트)에서 프로젝트 루트 디렉토리로 이동한 뒤 아래 명령어를 입력합니다
javac udp/Server.java
javac udp/Client.java

# 서버 실행
java udp.Server

# 클라이언트 실행
java udp.Client

# 예시
[Server 터미널]
$ java udp.Server
PORT 번호를 입력하세요: 12345

[Client 터미널]
$ java udp.Client
서버 IP를 입력하세요: 127.0.0.1
포트 번호를 입력하세요: 12345
파일 이름을 입력하세요: sample.txt

