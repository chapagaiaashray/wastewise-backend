# ♻️ WasteWise Backend

This is the Spring Boot backend for **WasteWise**, a real-time smart waste bin monitoring system.

- 🌍 Live Backend API: [https://wastewise-backend.onrender.com](https://wastewise-backend.onrender.com)
- 🔌 WebSocket Endpoint: `wss://wastewise-backend.onrender.com/ws`

---

## 🚀 Features

- RESTful API for bin data (CRUD operations)
- WebSocket support with STOMP for real-time bin updates
- Optional Redis caching with TTL
- PostgreSQL-based persistence
- Uptime monitoring via UptimeRobot (5-minute pings)
- Ready for production with environment variable configuration

---

## ⚙️ Technologies Used

- **Java 17**
- **Spring Boot** (Web, Data JPA, WebSocket, Redis)
- **PostgreSQL**
- **Redis** (optional, not required for production)
- **STOMP + SockJS**
- **Deployed on Render**

---

## 🗃️ Data Model

```java
BinEntity {
  Long id;
  String locationName;
  double latitude;
  double longitude;
  double fillLevel;
  String status; // OK, FULL, OVERFLOW
  String type;   // Recyclable, Organic, Mixed
}
