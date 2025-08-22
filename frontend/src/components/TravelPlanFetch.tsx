import type { CreateTravelPlanPayload, UpdateTravelPlanPayload } from "../types/TravelPlanTypes"

const BASE = "http://localhost:8080/travel-plan"

export async function listTravelPlans(adventureId: number | string) {
    const res = await fetch(`${BASE}/adventure/${adventureId}`, { credentials: "include" })
    if (!res.ok) throw new Error(await res.text())
    return res.json()
}

export async function getTravelPlan(id: number | string) {
    const res = await fetch(`${BASE}/${id}`, { credentials: "include" })
    if (!res.ok) throw new Error(await res.text())
    return res.json()
}

export async function createTravelPlan(adventureId: number | string, payload: CreateTravelPlanPayload) {
    const res = await fetch(`${BASE}/adventure/${adventureId}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(payload),
    })
    if (!res.ok) throw new Error(await res.text())
    return res.json()
}

export async function updateTravelPlan(id: number | string, payload: UpdateTravelPlanPayload) {
    const res = await fetch(`${BASE}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(payload),
    })
    if (!res.ok) throw new Error(await res.text())
    return res.json()
}

export async function deleteTravelPlan(id: number | string) {
    const res = await fetch(`${BASE}/${id}`, { method: "DELETE", credentials: "include" })
    if (!res.ok) throw new Error(await res.text())
}
