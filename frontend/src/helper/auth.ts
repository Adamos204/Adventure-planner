import type {RegisterPayload} from "../types/RegisterPayload.ts";

export async function   fetchMe() {
    const res = await fetch("http://localhost:8080/auth/me", {
        credentials: "include",
    });
    if (!res.ok) return null;
    return res.json();
}

export async function login(email: string, password: string) {
    const res = await fetch("http://localhost:8080/login", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: new URLSearchParams({ username: email, password }),
        credentials: "include",
    });
    if (!res.ok) {
        throw new Error("Login failed");
    }
}

export async function logout() {
    await fetch("http://localhost:8080/logout", {
        method: "POST",
        credentials: "include",
    });
}

export async function createUser(payload: RegisterPayload) {
    const res = await fetch("http://localhost:8080/users", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
        credentials: "include",
    })
    if (!res.ok) throw new Error("Registration failed")
    return res.json()
}