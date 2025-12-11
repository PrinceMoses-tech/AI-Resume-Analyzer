import { BrowserRouter, Routes, Route, NavLink } from "react-router-dom";
import "./App.css";
import Register from "./components/Register";
import Login from "./components/Login";
import UploadResume from "./components/UploadResume";
import Reports from "./components/Reports";

const navLinks = [
  { to: "/", label: "Login" },
  { to: "/register", label: "Register" },
  { to: "/upload", label: "Upload" },
  { to: "/reports", label: "Reports" },
];

function App() {
  return (
    <BrowserRouter>
      <div className="app-shell">
        <header className="topbar">
          <div className="brand">
            <div className="brand-mark" aria-hidden />
            <div>
              <p className="brand-eyebrow">AI Resume Analyzer</p>
              <p className="brand-title">Insights that get you hired</p>
            </div>
          </div>
          <nav className="nav-links">
            {navLinks.map((link) => (
              <NavLink
                key={link.to}
                to={link.to}
                className={({ isActive }) =>
                  `nav-link ${isActive ? "nav-link--active" : ""}`
                }
              >
                {link.label}
              </NavLink>
            ))}
          </nav>
        </header>

        <main className="page">
          <Routes>
            <Route path="/" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/upload" element={<UploadResume />} />
            <Route path="/reports" element={<Reports />} />
          </Routes>
        </main>

        <footer className="footer">
          <p>Built for candidates who want actionable, AI-powered feedback.</p>
        </footer>
      </div>
    </BrowserRouter>
  );
}

export default App;
