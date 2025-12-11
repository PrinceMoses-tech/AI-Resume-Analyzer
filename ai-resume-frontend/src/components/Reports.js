import React, { useEffect, useState } from "react";
import { getReports, deleteReport } from "../api/api";

export default function Reports() {
  const email = localStorage.getItem("email");
  const [reports, setReports] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function loadReports() {
      const result = await getReports(email);
      if (result.status) setReports(result.reports);
      setLoading(false);
    }
    loadReports();
  }, [email]);

  const remove = async (id) => {
    await deleteReport(id);
    setReports((current) => current.filter((r) => r.id !== id));
  };

  return (
    <section className="panel">
      <div className="panel-header">
        <div>
          <p className="pill">Insights</p>
          <h1 className="panel-title">Your AI-generated reports</h1>
          <p className="panel-subtitle">
            Every upload produces an actionable breakdown—ATS friendliness,
            clarity, and recruiter-ready talking points.
          </p>
        </div>
      </div>

      {loading ? (
        <div className="empty">Loading your reports...</div>
      ) : reports.length === 0 ? (
        <div className="empty">
          No reports yet. Upload a resume to get AI suggestions.
        </div>
      ) : (
        <div className="report-grid">
          {reports.map((r) => (
            <article className="report-card" key={r.id}>
              <div className="report-meta">
                <span className="pill" style={{ background: "rgba(139,92,246,0.12)", borderColor: "rgba(139,92,246,0.35)", color: "#c4b5fd" }}>
                  Target role
                </span>
                <button className="btn btn-secondary" onClick={() => remove(r.id)}>
                  Delete
                </button>
              </div>
              <div>
                <p className="muted" style={{ margin: "0 0 6px 0" }}>Description</p>
                <div className="report-text">{r.description}</div>
              </div>
              <div>
                <p className="muted" style={{ margin: "0 0 6px 0" }}>Report</p>
                <div className="report-text">{r.generatedText}</div>
              </div>
            </article>
          ))}
        </div>
      )}
    </section>
  );
}
