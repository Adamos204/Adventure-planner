import type {CreateTrainingPayload, UpdateTrainingPayload } from "../types/TrainingTypes"

const BASE = "http://localhost:8080/user-trainings"

export async function listTrainings(id: string | number | undefined) {
    const res = await fetch(`${BASE}/user/${id}`, { credentials: "include" })
    if (!res.ok) throw new Error("Failed to load trainings")
    return res.json()
}

export async function getTraining(id: string | number){
    const res = await fetch(`${BASE}/${id}`, { credentials: "include" })
    if (!res.ok) throw new Error("Training not found")
    return res.json()
}

export async function createTraining(payload: CreateTrainingPayload){
    const res = await fetch(BASE, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(payload),
    })
    if (!res.ok) throw new Error("Failed to create training")
    return res.json()
}

export async function updateTraining(id: string | number, payload: UpdateTrainingPayload){
    const res = await fetch(`${BASE}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(payload),
    })
    if (!res.ok) throw new Error("Failed to update training")
    return res.json()
}

export async function deleteTraining(id: string | number){
    const res = await fetch(`${BASE}/${id}`, { method: "DELETE", credentials: "include" })
    if (!res.ok) throw new Error("Failed to delete training")
}