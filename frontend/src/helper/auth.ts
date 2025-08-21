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