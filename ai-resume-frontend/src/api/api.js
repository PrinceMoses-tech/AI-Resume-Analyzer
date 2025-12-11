const BASE_URL = "https://ai-resume-analyzer-fpx0.onrender.com";

export async function registerUser(data) {
  return fetch(`${BASE_URL}/account/register`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data)
  }).then(res => res.json());
}

export async function loginUser(data) {
  return fetch(`${BASE_URL}/account/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data)
  }).then(res => res.json());
}

export async function uploadResume(email, file, description) {
  const formData = new FormData();
  formData.append("file", file);
  formData.append("description", description);

  return fetch(`${BASE_URL}/api/resume/upload/${email}`, {
    method: "POST",
    body: formData
  }).then(res => res.json());
}

export async function getReports(email) {
  return fetch(`${BASE_URL}/api/resume/report/${email}`)
    .then(res => res.json());
}

export async function deleteReport(id) {
  return fetch(`${BASE_URL}/api/resume/delete/${id}`, {
    method: "DELETE"
  }).then(res => res.json());
}
