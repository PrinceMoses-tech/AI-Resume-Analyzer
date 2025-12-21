import React, { useEffect, useState } from "react";
import { getReports, deleteReport } from "../api/api";

export default function Reports() {
  const email = localStorage.getItem("email");
  const [reports, setReports] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    async function loadReports() {
      if (!email) {
        setError("Please login to view your reports.");
        setLoading(false);
        return;
      }

      try {
        setError("");
        const result = await getReports(email);
        console.log("Reports API response:", result);
        
        // Handle different response formats
        if (result && result.status) {
          // Check if reports is an array or if data is in a different property
          const reportsData = result.reports || result.data || (Array.isArray(result) ? result : []);
          setReports(Array.isArray(reportsData) ? reportsData : []);
        } else if (result && Array.isArray(result)) {
          // Handle case where API returns array directly
          setReports(result);
        } else {
          setError(result?.message || "Failed to load reports. Please try again.");
        }
      } catch (err) {
        console.error("Error loading reports:", err);
        setError("Unable to load reports. Please check your connection and try again.");
      } finally {
        setLoading(false);
      }
    }
    loadReports();
  }, [email]);

  const remove = async (id) => {
    try {
      const result = await deleteReport(id);
      if (result && result.status) {
        setReports((current) => current.filter((r) => r.id !== id));
      } else {
        setError(result?.message || "Failed to delete report.");
      }
    } catch (err) {
      console.error("Error deleting report:", err);
      setError("Unable to delete report. Please try again.");
    }
  };

  return (
    <section className="panel">
      <div className="panel-header">
        <p className="pill">Insights</p>
        <h1 className="panel-title">Your AI-generated reports</h1>
        <p className="panel-subtitle">
          Every upload produces an actionable breakdown—ATS friendliness,
          clarity, and recruiter-ready talking points.
        </p>
      </div>

      {error && <div className="message error">{error}</div>}

      {loading ? (
        <div className="empty">⏳ Loading your reports...</div>
      ) : !error && reports.length === 0 ? (
        <div className="empty">
          📭 No reports yet. Upload a resume to get AI suggestions.
        </div>
      ) : !error && reports.length > 0 ? (
        <div className="report-grid">
          {reports.map((r) => (
            <article className="report-card" key={r.id}>
              <div className="report-meta">
                <span className="pill">🎯 Target Role</span>
                <button className="btn btn-secondary" onClick={() => remove(r.id)}>
                  🗑️ Delete
                </button>
              </div>
              <div className="input-group">
                <label className="input-label">Description</label>
                <div className="report-text">{r.description || "No description provided"}</div>
              </div>
              <div className="input-group">
                <label className="input-label">AI Analysis Report</label>
                <div className="report-text">{r.generatedText || "No report generated yet."}</div>
              </div>
            </article>
          ))}
        </div>
      ) : null}
    </section>
  );
}
