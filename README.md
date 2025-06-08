
---

## ✅ `backend/README.md`

```markdown
# WasteWise Backend ♻️

This is the Spring Boot backend for **WasteWise**, a real-time smart waste bin monitoring platform.

> 🛠️ Live Backend: [https://wastewise-backend.onrender.com](https://wastewise-backend.onrender.com)

> 🔌 WebSocket Endpoint: `wss://wastewise-backend.onrender.com/ws`

---

## 📦 Tech Stack

- Java 17
- Spring Boot (REST + WebSocket)
- PostgreSQL
- Redis (Optional Caching Layer)
- STOMP + SockJS for Real-time updates
- Deployed on Render

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
