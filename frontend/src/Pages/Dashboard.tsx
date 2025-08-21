import {Link, useNavigate} from "react-router-dom"
import {useEffect, useState} from "react";
import type {UserDto} from "../dto/dto.tsx";
import {fetchMe, logout} from "../helper/auth.ts";
import {listAdventures} from "../components/AdventuresFecth.tsx";
import type {Adventure} from "../types/AdventureTypes.ts";

type Training = { id: string; date: string; title: string; duration: string }

const recentTrainings: Training[] = [
    { id: "t1", date: "2025-08-18", title: "Gym – Push Day", duration: "60 min" },
    { id: "t2", date: "2025-08-17", title: "Krav Maga Session", duration: "90 min" },
    { id: "t3", date: "2025-08-15", title: "Hike – Local Trail", duration: "120 min" },
]

const formatDate = (iso: string) =>
    new Date(iso).toLocaleDateString(undefined, { year: "numeric", month: "short", day: "numeric" })

export default function Dashboard() {

    const [user, setUser] = useState<UserDto | null>(null);
    const navigate = useNavigate();
    const [adventures, setAdventures] = useState<Adventure[] | null>(null)

    useEffect(() => {
        fetchMe().then(setUser);
    }, []);

    useEffect(() => {
        listAdventures(user?.id)
            .then((data) => {
                const sorted = [...data].sort((a, b) => +new Date(a.date) - +new Date(b.date))
                setAdventures(sorted.slice(0, 3))
            })
            .catch(() => setAdventures([]))
    }, [user?.id])

    async function handleLogout() {
        await logout();
        localStorage.removeItem("userName");
        navigate("/");
    }

    return (
        <main className="min-h-[calc(100vh-4rem)] bg-white">
            <section className="border-b">
                <div className="max-w-7xl mx-auto px-6 py-8 flex items-center justify-between">
                    <div>
                        <h1 className="text-3xl font-extrabold tracking-tight">
                            <span className="bg-gradient-to-r from-blue-600 to-purple-500 bg-clip-text text-transparent">
                                 Welcome back, {user ? `${user.firstname}!` : "Welcome."}
                            </span>
                        </h1>
                        <p className="mt-2 text-gray-600">
                            Here’s a quick look at your training and upcoming adventures.
                        </p>
                    </div>
                    <button
                        onClick={handleLogout}
                        className="px-4 py-2 rounded-full bg-gradient-to-r from-blue-500 to-purple-500 text-white shadow hover:opacity-90 transition"
                    >
                        Logout
                    </button>
                </div>
            </section>


            <section>
                <div className="max-w-7xl mx-auto px-6 py-8 grid gap-8 md:grid-cols-2">
                    <div className="rounded-2xl border bg-white shadow-sm">
                        <div className="flex items-center justify-between px-6 py-4 border-b">
                            <h2 className="text-lg font-semibold text-gray-900">Recent Trainings</h2>
                            <div className="flex items-center gap-3">
                                <Link to="/training" className="text-sm text-gray-700 hover:underline">View all</Link>
                                <Link
                                    to="/training/new"
                                    className="inline-flex items-center rounded-xl bg-blue-600 px-3 py-1.5 text-white text-sm font-medium shadow hover:opacity-90 transition"
                                >
                                    Add
                                </Link>
                            </div>
                        </div>
                        <ul className="divide-y">
                            {recentTrainings.length === 0 ? (
                                <li className="px-6 py-6 text-gray-600">No training yet. Add your first session.</li>
                            ) : (
                                recentTrainings.map((t) => (
                                    <li key={t.id} className="px-6 py-4 flex items-center justify-between">
                                        <div>
                                            <div className="font-medium text-gray-900">{t.title}</div>
                                            <div className="text-sm text-gray-600">{formatDate(t.date)} • {t.duration}</div>
                                        </div>
                                        <Link to={`/training/${t.id}`} className="text-blue-600 text-sm hover:underline">
                                            Details
                                        </Link>
                                    </li>
                                ))
                            )}
                        </ul>
                    </div>

                    <div className="rounded-2xl border bg-white shadow-sm">
                        <div className="flex items-center justify-between px-6 py-4 border-b">
                            <h2 className="text-lg font-semibold text-gray-900">Upcoming Adventures</h2>
                            <div className="flex items-center gap-3">
                                <Link to={`/adventures/user/${user?.id}`} className="text-sm text-gray-700 hover:underline">View all</Link>
                                <Link
                                    to="/adventures/new"
                                    className="inline-flex items-center rounded-xl bg-blue-600 px-3 py-1.5 text-white text-sm font-medium shadow hover:opacity-90 transition"
                                >
                                    Add
                                </Link>
                            </div>
                        </div>
                        <ul className="divide-y">
                            {!adventures && <li className="px-6 py-6 text-gray-600">Loading…</li>}
                            {adventures && adventures.length === 0 && <li className="px-6 py-6 text-gray-600">No adventures planned.</li>}
                            {adventures && adventures.map((a) => (
                                <li key={a.id} className="px-6 py-4 flex items-center justify-between">
                                    <div>
                                        <div className="font-medium text-gray-900">{a.name}</div>
                                        <div className="text-sm text-gray-600">{formatDate(a.date)}{a.startLocation ? ` • ${a.endLocation}` : ""}</div>
                                    </div>
                                    <Link to={`/adventures/${a.id}`} className="text-blue-600 text-sm hover:underline">Details</Link>
                                </li>
                            ))}
                        </ul>
                    </div>
                </div>
            </section>
        </main>
    )
}