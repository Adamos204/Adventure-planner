import { useEffect, useState } from "react"
import { Navigate, Outlet, useLocation } from "react-router-dom"
import type { UserDto } from "../dto/dto.tsx"

export default function RequireAuth() {
    const [user, setUser] = useState<UserDto | null | "loading">("loading")
    const location = useLocation()

    useEffect(() => {
        fetch("http://localhost:8080/auth/me", { credentials: "include" })
            .then(res => (res.ok ? res.json() : null))
            .then((u) => setUser(u))
            .catch(() => setUser(null))
    }, [])

    if (user === "loading") {
        return (
            <div className="min-h-[calc(100vh-4rem)] grid place-items-center text-gray-600">
                Checking your session…
            </div>
        )
    }

    if (!user) {
        return <Navigate to="/login" replace state={{ from: location }} />
    }

    return <Outlet />
}
