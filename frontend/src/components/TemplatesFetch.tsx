const BASE = "http://localhost:8080"

export async function listAdventureTemplates() {
    const res = await fetch(`${BASE}/adventure-template`, { credentials: "include" })
    if (!res.ok) throw new Error("Failed to load templates")
    return res.json()
}
