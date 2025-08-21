import type {CreateAdventurePayload, UpdateAdventurePayload} from "../types/AdventureTypes.ts";

const BASE = "http://localhost:8080"

export async function listAdventures(id: string | number | undefined) {
    const res = await fetch(`${BASE}/user-adventure/user/${id}`, { credentials: "include" });
    if (!res.ok){
        throw new Error("Failed to load adventures");
    }
    return res.json();
}

export async function getAdventure(id: string | undefined){
    const res = await fetch(`${BASE}/user-adventure/${id}`, { credentials: "include" })
    if (!res.ok) throw new Error("Adventure not found")
    return res.json()
}

export async function createAdventure(payload: CreateAdventurePayload) {
    const res = await fetch(`${BASE}/user-adventure`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(payload),
    })
    if (!res.ok) throw new Error("Failed to create adventure")
    return res.json()
}

export async function updateAdventure(id: string | undefined, payload: UpdateAdventurePayload){
    const res = await fetch(`${BASE}/user-adventure/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(payload),
    })
    if (!res.ok) throw new Error("Failed to update adventure")
    return res.json()
}

export async function deleteAdventure(id: string | undefined){
    const res = await fetch(`${BASE}/user-adventure/${id}`, {
        method: "DELETE",
        credentials: "include",
    })
    if (!res.ok) throw new Error("Failed to delete adventure")
}