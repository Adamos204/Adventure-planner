import type {CreateGearPayload, UpdateGearPayload} from "../types/GearTypes"

const BASE = "http://localhost:8080/gear-items"

export async function listGear(id: number | string | undefined) {
    const res = await fetch(`${BASE}/adventure/${id}`, {credentials: "include"})
    if (!res.ok) throw new Error(await res.text())
    return res.json()
}

export async function createGear(id: number | string | undefined, payload: CreateGearPayload) {
    const res = await fetch(`${BASE}/adventure/${id}`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        credentials: "include",
        body: JSON.stringify(payload),
    })
    if (!res.ok) throw new Error(await res.text())
    return res.json()
}

export async function updateGear(id: number | string | undefined, payload: UpdateGearPayload) {
    const res = await fetch(`${BASE}/${id}`, {
        method: "PUT",
        headers: {"Content-Type": "application/json"},
        credentials: "include",
        body: JSON.stringify(payload),
    })
    if (!res.ok) throw new Error(await res.text())
    return res.json()
}

export async function getGear(id: number | string | undefined) {
    const res = await fetch(`${BASE}/${id}`, {
        credentials: "include",
    })
    if (!res.ok) throw new Error(await res.text())
    return res.json()
}


export async function deleteGear(id: number | string | undefined) {
    const res = await fetch(`${BASE}/${id}`, {
        method: "DELETE",
        credentials: "include",
    })
    if (!res.ok) throw new Error(await res.text())
}

